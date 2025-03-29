// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Carriage;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class AlgaePivot extends SubsystemBase {
  
  private static SparkMax algaePivotMotor = new SparkMax(Constants.kAlgaePivotMotorID, MotorType.kBrushless);
  private static SparkMaxConfig pivotConfig = new SparkMaxConfig();
  private static RelativeEncoder algaePivotEncoder = algaePivotMotor.getEncoder();
  private static PIDController algaePivotPIDController = new PIDController(
    Constants.kAlgaePivotP, 
    Constants.kAlgaePivotI, 
    Constants.kAlgaePivotD
  );
  static AlgaePivot m_Instance = null;
  
  /** Creates a new AlgaePivot. */
  public AlgaePivot() {
    pivotConfig.idleMode(IdleMode.kBrake);
    // pivotConfig.smartCurrentLimit(30);
    algaePivotMotor.configure(pivotConfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    algaePivotEncoder.setPosition(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    SmartDashboard.putNumber("AlgaePivotPos", algaePivotEncoder.getPosition());
    SmartDashboard.putNumber("AlgaeMotorOutput", algaePivotMotor.getAppliedOutput());
    SmartDashboard.putNumber("AlgaeSetpoint", algaePivotPIDController.getSetpoint());
  }

  public void algaeGoToPosition(double position) {
    algaePivotMotor.set(algaePivotPIDController.calculate(
      algaePivotEncoder.getPosition(), position) + 
      (Math.sin(algaePivotEncoder.getPosition() * Math.PI / 6) * Constants.kAlgaePivotGravity)
    );
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

  // Command Methods

  public Command AlgaePivotGoToPositionCommand(double pos) {
    return runEnd(() -> algaeGoToPosition(pos), () -> zeroAlgaePivotMotor());
  }

  public Command AlgaePivotHoldPositionCommand() {
    return runEnd(() -> algaeGoToPosition(algaePivotEncoder.getPosition()), () -> zeroAlgaePivotMotor());
  }

  public Command AlgaePivotInCommand() {
    return runEnd(() -> algaePivotIn(), () -> zeroAlgaePivotMotor());
  }

  public Command AlgaePivotOutCommand() {
    return runEnd(() -> algaePivotOut(), () -> zeroAlgaePivotMotor());
  }

  public Command ZeroAlgaePivotMotorCommand() {
    return runOnce(() -> zeroAlgaePivotMotor());
  }
}
