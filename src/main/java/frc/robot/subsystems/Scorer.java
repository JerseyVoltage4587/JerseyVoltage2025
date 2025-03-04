// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.lang.management.MemoryType;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Scorer extends SubsystemBase {
  
  private static SparkMax leftScorerMotor = new SparkMax(Constants.kLeftScorerMotorID, MotorType.kBrushless);
  private static SparkMax rightScorerMotor = new SparkMax(Constants.kRightScorerMotorID, MotorType.kBrushless);
  private static SparkMaxConfig config1 = new SparkMaxConfig();
  static Scorer m_Instance = null;
  
  /** Creates a new Scorer. */
  public Scorer() {
    config1.inverted(true);
    rightScorerMotor.configure(config1, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void scorerForward() {
    leftScorerMotor.set(.4);
    rightScorerMotor.set(.4);
  }

  public void scorerBackward() {
    leftScorerMotor.set(-.4);
    rightScorerMotor.set(-.4);
  }

  public void zeroMotors() {
    leftScorerMotor.set(0);
    rightScorerMotor.set(0);
  }

  public static Scorer getInstance() {
    if (m_Instance == null) {
      synchronized (Scorer.class) {
        if (m_Instance == null) {
          m_Instance = new Scorer();
        }
      }
    }
    return m_Instance;
  }
}
