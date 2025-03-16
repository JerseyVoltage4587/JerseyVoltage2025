// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drivetrain;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain.SwerveSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class ToAngle extends Command {
  
  SwerveSubsystem m_swerve = SwerveSubsystem.getInstance();
  double correctAngle;

  /** Creates a new ToAngle. */
  public ToAngle(double angle) {
    // Use addRequirements() here to declare subsystem dependencies.
    correctAngle = angle;
    addRequirements(m_swerve);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    while (m_swerve.getGyro() != correctAngle) {
      new SwerveDriveJoysticks(m_swerve, () -> 0.0, () -> 0.0, () -> m_swerve.angleDirection(correctAngle), () -> false);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
