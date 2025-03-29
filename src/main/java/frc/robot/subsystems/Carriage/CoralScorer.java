// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Carriage;

import java.lang.management.MemoryType;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class CoralScorer extends SubsystemBase {
  
  private static SparkMax coralLeftMotor = new SparkMax(Constants.kCoralLeftMotorID, MotorType.kBrushless);
  // private static SparkMax coralRightMotor = new SparkMax(Constants.kCoralRightMotorID, MotorType.kBrushless);
  private static SparkMaxConfig leftCoral = new SparkMaxConfig();
  static CoralScorer m_Instance = null;
  
  /** Creates a new Scorer. */
  public CoralScorer() {
    leftCoral.inverted(false);
    leftCoral.idleMode(IdleMode.kBrake);
    coralLeftMotor.configure(leftCoral, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void coralForward() {
    coralLeftMotor.set(.2);
    // coralRightMotor.set(.2);
  }

  public void coralL1Forward() {
    coralLeftMotor.set(.05);
    // coralRightMotor.set(.2);
  }

  public void coralBackward() {
    coralLeftMotor.set(-.075);
    // coralRightMotor.set(-.1);
  }

  public void zeroCoralMotors() {
    coralLeftMotor.set(0);
    // coralRightMotor.set(0);
  }

  // Command Methods

  public Command CoralForwardCommand() {
    return runEnd(() -> coralForward(), () -> zeroCoralMotors());
  }

  public Command CoralBackwardCommand() {
    return runEnd(() -> coralBackward(), () -> zeroCoralMotors());
  }
  
  public Command CoralL1ForwardCommand() {
    return runEnd(() -> coralL1Forward(), () -> zeroCoralMotors());
  }

  public Command ZeroCoralScorerMotorCommand() {
    return runOnce(() -> zeroCoralMotors());
  }
}
