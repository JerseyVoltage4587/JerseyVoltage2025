// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class HoldPosition extends Command {
  
  Elevator m_elevator = Elevator.getInstance();
  double position;
  
  /** Creates a new GoToPosition. */
  public HoldPosition() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_elevator);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    position = m_elevator.getEncoderValue();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_elevator.goToPosition(position);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_elevator.zeroMotors();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
