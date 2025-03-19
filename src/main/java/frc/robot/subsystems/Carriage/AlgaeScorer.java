// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Carriage;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class AlgaeScorer extends SubsystemBase {
  
  private static SparkMax algaeScorerMotor = new SparkMax(Constants.kAlgaeScorerMotorID, MotorType.kBrushless);
  static AlgaeScorer m_Instance = null;
  
  /** Creates a new AlgaeScorer. */
  public AlgaeScorer() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void algaeCollect() {
    algaeScorerMotor.set(-.1);
  }

  public void algaeScore() {
    algaeScorerMotor.set(.1);
  }

  public void zeroAlgaeScoreMotor() {
    algaeScorerMotor.set(0);
  }

  // Command Methods

  public Command AlgaeCollectCommand() {
    return runEnd(() -> algaeCollect(), () -> zeroAlgaeScoreMotor());
  }

  public Command AlgaeScoreCommand() {
    return runEnd(() -> algaeScore(), () -> zeroAlgaeScoreMotor());
  }

  public Command ZeroAlgaeScorerMotorCommand() {
    return runOnce(() -> zeroAlgaeScoreMotor());
  }
}
