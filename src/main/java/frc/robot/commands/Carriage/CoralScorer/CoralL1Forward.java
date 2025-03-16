// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Carriage.CoralScorer;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Carriage.CoralScorer;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class CoralL1Forward extends Command {
  
  CoralScorer m_scorer = CoralScorer.getInstance();
  
  /** Creates a new CoralL1Forward. */
  public CoralL1Forward() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_scorer);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_scorer.coralL1Forward();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_scorer.zeroCoralMotors();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
