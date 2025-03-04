// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.sim.SparkAbsoluteEncoderSim;
import com.revrobotics.sim.SparkMaxAlternateEncoderSim;
import com.revrobotics.spark.SparkAbsoluteEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkMaxAlternateEncoder;
import com.revrobotics.spark.SparkRelativeEncoder;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ElevatorConstants;


public class Elevator extends SubsystemBase {
  /** Creates a new Elevator. */
  
  static Elevator m_Instance = null;

  private static SparkMax leftElevatorMotor = new SparkMax(Constants.kLeftElevatorMotorID, MotorType.kBrushless);
  private static SparkMax rightElevatorMotor = new SparkMax(Constants.kRightElevatorMotorID, MotorType.kBrushless);
  private static RelativeEncoder leftElevatorEncoder = leftElevatorMotor.getEncoder();
  private static RelativeEncoder rightElevatorEncoder = rightElevatorMotor.getEncoder();
  private static SparkMaxConfig leftElevatorConfig = new SparkMaxConfig();
  private static SparkMaxConfig rightElevatorConfig = new SparkMaxConfig();
  private static SparkClosedLoopController leftElevatorClosedLoopController;

  private static PIDController elevatorPIDController = new PIDController(
    Constants.ElevatorConstants.kElevatorP, 
    Constants.ElevatorConstants.kElevatorI, 
    Constants.ElevatorConstants.kElevatorD
  );
        
  public Elevator() {

    rightElevatorMotor.configure(
      rightElevatorConfig.follow(Constants.kLeftElevatorMotorID, true), 
      ResetMode.kResetSafeParameters, 
      PersistMode.kNoPersistParameters);
    leftElevatorConfig.idleMode(IdleMode.kBrake);
    leftElevatorEncoder.setPosition(0);
    leftElevatorMotor.configure(leftElevatorConfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
  }
  
  public void robotInit() {
      
    leftElevatorClosedLoopController = leftElevatorMotor.getClosedLoopController();

    leftElevatorConfig.encoder.positionConversionFactor(1/15);
    leftElevatorConfig.encoder.velocityConversionFactor(1/15);

    leftElevatorConfig.closedLoop
    .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
    .pidf(ElevatorConstants.kElevatorP, ElevatorConstants.kElevatorI, ElevatorConstants.kElevatorD, ElevatorConstants.kElevatorF)
    .iZone(ElevatorConstants.kElevatorIZone)
    .outputRange(-1, 1);

    leftElevatorConfig.closedLoop.maxMotion
    .maxVelocity(ElevatorConstants.kElevatorMaxVelocity)
    .maxAcceleration(ElevatorConstants.kElevatorMaxAcceleration)
    .allowedClosedLoopError(ElevatorConstants.kClosedLoopError);

    leftElevatorMotor.configure(leftElevatorConfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);      
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    SmartDashboard.putNumber("LeftElevatorEncoderValue", leftElevatorEncoder.getPosition());
    SmartDashboard.putNumber("RightElevatorEncoderValue", rightElevatorEncoder.getPosition());
  }
  
  public void ElevatorUp() {
    leftElevatorMotor.set(.1);
  }

  public void ElevatorUpAt3() {
    leftElevatorMotor.set(.3);
  }

  public void ElevatorDown() {
    leftElevatorMotor.set(-.1);
  }
  
  public void zeroMotors() {
    leftElevatorMotor.set(0);
  }

  public void goToPosition(double pos) {
    leftElevatorClosedLoopController.setReference(pos, ControlType.kMAXMotionPositionControl);
  }

  public static Elevator getInstance() {
    if (m_Instance == null) {
      synchronized (Elevator.class) {
        if (m_Instance == null) {
          m_Instance = new Elevator();
        }
      }
    }
    return m_Instance;
  }
}
