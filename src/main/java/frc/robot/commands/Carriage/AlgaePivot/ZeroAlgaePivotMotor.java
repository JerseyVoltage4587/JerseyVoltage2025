// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Carriage.AlgaePivot;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Carriage.AlgaePivot;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ZeroAlgaePivotMotor extends InstantCommand {
  
  AlgaePivot m_pivot = AlgaePivot.getInstance();

  public ZeroAlgaePivotMotor() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_pivot);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_pivot.zeroAlgaePivotMotor();
  }
}
