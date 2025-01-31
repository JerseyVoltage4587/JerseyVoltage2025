// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.RobotConstants;
import frc.robot.subsystems.SwerveSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class SwerveDriveJoysticks extends Command {
  /** Creates a new SwerveDriveJoysticks. */
  private final SwerveSubsystem m_Swerve;
    private final Supplier<Double> xSpeedFunction, ySpeedFunction, thetaFunction;
    private final Supplier<Boolean> fieldOrientedFunction;
    private final SlewRateLimiter xLimiter, yLimiter, thetaLimiter;

    public SwerveDriveJoysticks(SwerveSubsystem m_Swerve, Supplier<Double> xSpeedFunction, Supplier<Double> ySpeedFunction,
    Supplier<Double> thetaFunction, Supplier<Boolean> fieldOrientedFunction) {
        
    this.m_Swerve = m_Swerve;
    this.xSpeedFunction = xSpeedFunction;
    this.ySpeedFunction = ySpeedFunction;
    this.thetaFunction = thetaFunction;
    this.fieldOrientedFunction = fieldOrientedFunction;
    this.xLimiter = new SlewRateLimiter(RobotConstants.MaxAcceleration);
    this.yLimiter = new SlewRateLimiter(RobotConstants.MaxAcceleration);
    this.thetaLimiter = new SlewRateLimiter(RobotConstants.MaxAngularAcceleration);

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_Swerve);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    

    double xSpeed = xSpeedFunction.get();
    double ySpeed = ySpeedFunction.get();
    double theta = thetaFunction.get();

    xSpeed = Math.abs(xSpeed) > RobotConstants.deadBand ? xSpeed : 0.0;
    ySpeed = Math.abs(ySpeed) > RobotConstants.deadBand ? ySpeed : 0.0;
    theta = Math.abs(theta) > RobotConstants.deadBand ? theta : 0.0;

    // xSpeed = xLimiter.calculate(xSpeed) * RobotConstants.MaxAcceleration;
    // ySpeed = yLimiter.calculate(ySpeed) * RobotConstants.MaxAcceleration;
    // theta = thetaLimiter.calculate(theta) * RobotConstants.MaxAngularAcceleration;

    ChassisSpeeds newSpeeds;
    if (fieldOrientedFunction.get()) {
        newSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, theta, m_Swerve.getGyroToRotation2d());
    } else {
        newSpeeds = new ChassisSpeeds(xSpeed, ySpeed, theta);
    }

    SwerveModuleState[] moduleStates = m_Swerve.kinematics.toSwerveModuleStates(newSpeeds);

    m_Swerve.setModuleStates(moduleStates);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_Swerve.zeroModules();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
