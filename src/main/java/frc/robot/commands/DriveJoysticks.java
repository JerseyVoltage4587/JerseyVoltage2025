// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.RobotConstants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain.SwerveSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class DriveJoysticks extends Command {
  
  Supplier<Double> xSpd, ySpd, thetaFunc;
  Supplier<Boolean> fieldOriented;
  SwerveSubsystem m_swerve;
  
  /** Creates a new DriveJoysticks. */
  public DriveJoysticks(Supplier<Double> xSpeedFunction, Supplier<Double> ySpeedFunction,
  Supplier<Double> thetaFunction, Supplier<Boolean> fieldOrientedFunction) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_swerve = RobotContainer.getSwerve();
    addRequirements(m_swerve);

    xSpd = xSpeedFunction;
    ySpd = ySpeedFunction;
    thetaFunc = thetaFunction;
    fieldOriented = fieldOrientedFunction;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    ChassisSpeeds spds;

      if (!fieldOriented.get()) {
        spds = ChassisSpeeds.fromFieldRelativeSpeeds(
          MathUtil.applyDeadband(xSpd.get(), RobotConstants.kDeadBand),
          MathUtil.applyDeadband(ySpd.get(), RobotConstants.kDeadBand),
          MathUtil.applyDeadband(thetaFunc.get(), RobotConstants.kDeadBand),
          m_swerve.getGyroToRotation2d()
        );
      } else {
        spds = ChassisSpeeds.fromRobotRelativeSpeeds(
          MathUtil.applyDeadband(xSpd.get() * 0.25, RobotConstants.kDeadBand),
          MathUtil.applyDeadband(ySpd.get() * 0.25, RobotConstants.kDeadBand),
          MathUtil.applyDeadband(thetaFunc.get() * 0.5, RobotConstants.kDeadBand),
          m_swerve.getGyroToRotation2d()
        );
      }
      
      m_swerve.setModuleStates(m_swerve.kinematics.toSwerveModuleStates(spds));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_swerve.zeroModules();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
