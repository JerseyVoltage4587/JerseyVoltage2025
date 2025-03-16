// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Carriage.AlgaeScorer;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Carriage.AlgaeScorer;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ZeroAlgaeScorerMotor extends InstantCommand {
  
  AlgaeScorer m_scorer = AlgaeScorer.getInstance();
  
  public ZeroAlgaeScorerMotor() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_scorer);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_scorer.zeroAlgaeScoreMotor();
  }
}
