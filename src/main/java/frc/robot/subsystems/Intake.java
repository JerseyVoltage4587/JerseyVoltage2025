// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.IntConsumer;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  
  private static SparkMax intakeMotor = new SparkMax(Constants.kIntakeFunnelMotorID, MotorType.kBrushless);
  private static SparkMaxConfig intakeConfig = new SparkMaxConfig();
  static Intake m_Instance = null;

  /** Creates a new Intake. */
  public Intake() {
    intakeConfig.idleMode(IdleMode.kCoast);
    intakeMotor.configure(intakeConfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void runIntake() {
    intakeMotor.set(-.25);
  }

  public void zeroMotor() {
    intakeMotor.set(0);
  }

  public static Intake getInstance() {
    if (m_Instance == null) {
      synchronized (Intake.class) {
        if (m_Instance == null) {
          m_Instance = new Intake();
        }
      }
    }
    return m_Instance;
  }
}
