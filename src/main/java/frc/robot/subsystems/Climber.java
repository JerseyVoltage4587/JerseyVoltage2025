// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.swerve.SwerveRequest.Idle;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {
  
  private static SparkMax climberMotor = new SparkMax(Constants.kClimberMotorID, MotorType.kBrushless);
  private static SparkMaxConfig climberConfig = new SparkMaxConfig();
  static Climber m_Instance = null;
  
  /** Creates a new Climber. */
  public Climber() {
    climberConfig.idleMode(IdleMode.kBrake);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void climberIn() {
    if (!DriverStation.isFMSAttached() || DriverStation.getMatchTime() < 30) {
        climberMotor.set(.8);
    }
  }

  public void climberOut() {
    if (!DriverStation.isFMSAttached() || DriverStation.getMatchTime() < 30) {
        climberMotor.set(-.3);
    }
  }

  public void zeroMotor() {
    climberMotor.set(0);
  }

  //Command Methods

  public Command ClimberInCommand() {
    return runEnd(() -> climberIn(), () -> zeroMotor());
  }

  public Command ClimberOutCommand() {
    return runEnd(() -> climberOut(), () -> zeroMotor());
  }

  public Command ZeroClimberMotorCommand() {
    return runOnce(() -> zeroMotor());
  }
}
