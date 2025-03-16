// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Carriage;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class AlgaePivot extends SubsystemBase {
  
  private static SparkMax algaePivotMotor = new SparkMax(Constants.kAlgaePivotMotorID, MotorType.kBrushless);
  private static SparkMaxConfig pivotConfig = new SparkMaxConfig();
  static AlgaePivot m_Instance = null;
  
  /** Creates a new AlgaePivot. */
  public AlgaePivot() {
    pivotConfig.idleMode(IdleMode.kBrake);
    algaePivotMotor.configure(pivotConfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void algaePivotOut() {
    algaePivotMotor.set(.1);
  }

  public void algaePivotIn() {
    algaePivotMotor.set(-.1);
  }

  public void zeroAlgaePivotMotor() {
    algaePivotMotor.set(0);
  }

  public static AlgaePivot getInstance() {
    if (m_Instance == null) {
      synchronized (AlgaePivot.class) {
        if (m_Instance == null) {
          m_Instance = new AlgaePivot();
        }
      }
    }
    return m_Instance;
  }
}
