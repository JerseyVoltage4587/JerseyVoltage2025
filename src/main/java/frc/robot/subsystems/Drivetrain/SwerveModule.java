// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Drivetrain;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.RobotConstants;
import frc.robot.Constants.SwerveConstants;

/** Add your docs here. */
public class SwerveModule {
  
  private TalonFX driveMotor;
  private TalonFX turnMotor;

  private PIDController turnPIDController;

  private final CANcoder absoluteEncoder;
  private final boolean absoluteEncoderReversed;
  private final double absoluteEncoderOffsetRad;
  
  //Constructor
  public SwerveModule(int driveMotorPort, int turnMotorPort, boolean driveMotorReversed, boolean turnMotorReversed,
  int absoluteEncoderID, boolean absoluteEncoderReversed, double absoluteEncoderOffset) {

    this.absoluteEncoderOffsetRad = absoluteEncoderOffset;
    this.absoluteEncoderReversed = absoluteEncoderReversed;
    this.absoluteEncoder = new CANcoder(absoluteEncoderID);

    driveMotor = new TalonFX(driveMotorPort);
    turnMotor = new TalonFX(turnMotorPort);

    driveMotor.getConfigurator().apply(new TalonFXConfiguration().MotorOutput.withNeutralMode(NeutralModeValue.Brake));

    if (driveMotorReversed) {
      driveMotor.getConfigurator().apply(new TalonFXConfiguration().MotorOutput.withInverted(InvertedValue.Clockwise_Positive));
    }
    
    if (turnMotorReversed) {
      turnMotor.getConfigurator().apply(new TalonFXConfiguration().MotorOutput.withInverted(InvertedValue.Clockwise_Positive));
    }
    
    turnPIDController = new PIDController(SwerveConstants.kSwerveP, SwerveConstants.kSwerveI, SwerveConstants.kSwerveD);
    turnPIDController.enableContinuousInput(-Math.PI, Math.PI);

    resetEncoders();
  }

  public double getDrivePosition() {
    return driveMotor.getRotorPosition().getValueAsDouble()
    / RobotConstants.kDriveMotorGearRatio * Math.PI * RobotConstants.kWheelDiameter;
  }

  public double getTurnPosition() {
    return turnMotor.getRotorPosition().getValueAsDouble()
    / RobotConstants.kTurnMotorGearRatio * 2 * Math.PI;
  }

  public double getDriveVelocity() {
    return driveMotor.getRotorVelocity().getValueAsDouble()
    / RobotConstants.kDriveMotorGearRatio * Math.PI * RobotConstants.kWheelDiameter;
  }

  public double getTurnVelocity() {
    return turnMotor.getRotorVelocity().getValueAsDouble()
    / RobotConstants.kTurnMotorGearRatio * 2 * Math.PI;
  }

  public double getAbsoluteEncoderRad() {
    double angle = absoluteEncoder.getAbsolutePosition().getValueAsDouble();
    angle *= 2.0 * Math.PI;
    angle -= absoluteEncoderOffsetRad;
    return angle * (absoluteEncoderReversed ? -1.0 : 1.0);
  }

  public void resetEncoders() {
    driveMotor.setPosition(0);
    turnMotor.setPosition(getAbsoluteEncoderRad()*RobotConstants.kTurnMotorGearRatio/(2*Math.PI));
  }

  public SwerveModuleState getState() {
    return new SwerveModuleState(getDriveVelocity(), new Rotation2d(getTurnPosition()));
  }

  public SwerveModulePosition getPosition() {
    return new SwerveModulePosition(getDrivePosition(), new Rotation2d(getTurnPosition()));
  }

  public void zeroMotors() {
    driveMotor.set(0);
    turnMotor.set(0);
  }

  public void setDesiredState(SwerveModuleState desiredState) {
    desiredState = SwerveModuleState.optimize(desiredState, getState().angle);
    if (Math.abs(desiredState.speedMetersPerSecond) < 0.001) {
      driveMotor.set(0);
    }
    else
    {
      driveMotor.set(desiredState.speedMetersPerSecond);
    }
    
    turnMotor.set(turnPIDController.calculate(getTurnPosition(), desiredState.angle.getRadians()));
    SmartDashboard.putString("SwerveModule[" + absoluteEncoder.getDeviceID() + "] state", desiredState.toString());
  }
}
