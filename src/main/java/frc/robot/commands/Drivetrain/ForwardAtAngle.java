// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drivetrain;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain.SwerveSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ForwardAtAngle extends SequentialCommandGroup {  
  
  SwerveSubsystem m_swerve = SwerveSubsystem.getInstance();
  
  /** Creates a new ForwardAtAngle. */
  public ForwardAtAngle(double angle) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new ToAngle(angle), 
      new SwerveDriveJoysticks(m_swerve, () -> 0.1, () -> 0.0, () -> 0.0, () -> false)
    );
  }
}
