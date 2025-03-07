// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.sim.SparkAbsoluteEncoderSim;
import com.revrobotics.sim.SparkMaxAlternateEncoderSim;
import com.revrobotics.spark.ClosedLoopSlot;
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

import edu.wpi.first.epilogue.CustomLoggerFor;
import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.trajectory.TrapezoidProfile.State;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
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
  
  private static ElevatorFeedforward leftElevatorMotorFeedForward = new ElevatorFeedforward(
    ElevatorConstants.kElevatorVolts,
    ElevatorConstants.kElevatorGravityVolts,
    ElevatorConstants.kElevatorVoltsPerSecond, 
    ElevatorConstants.kElevatorVoltsPerSecondSquared, 0.02);
    

  private static TrapezoidProfile elevatorProfile = new TrapezoidProfile(
    new TrapezoidProfile.Constraints(
      ElevatorConstants.kElevatorMaxVelocity, 
      ElevatorConstants.kElevatorMaxAcceleration));

  Timer elevatorTimer = new Timer();

  private static PIDController elevatorPIDController = new PIDController(
    Constants.ElevatorConstants.kElevatorP, 
    Constants.ElevatorConstants.kElevatorI, 
    Constants.ElevatorConstants.kElevatorD
  );
        
  public Elevator() {
    rightElevatorConfig.follow(Constants.kLeftElevatorMotorID, true);
    rightElevatorConfig.idleMode(IdleMode.kBrake);
    rightElevatorMotor.configure(rightElevatorConfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

    leftElevatorConfig.idleMode(IdleMode.kBrake);
    leftElevatorConfig.closedLoop.pidf(
      ElevatorConstants.kElevatorP,
      ElevatorConstants.kElevatorI,
      ElevatorConstants.kElevatorD,
      ElevatorConstants.kElevatorF
      );
      leftElevatorConfig.closedLoop.outputRange(-0.4, 0.4);
    leftElevatorMotor.configure(leftElevatorConfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    leftElevatorEncoder.setPosition(0);
  }
  
  public void robotInit() {
    
    //leftElevatorClosedLoopController = leftElevatorMotor.getClosedLoopController();

    // leftElevatorConfig.encoder.positionConversionFactor(1/15);
    // leftElevatorConfig.encoder.velocityConversionFactor(1/15);

    // leftElevatorConfig.closedLoop
    // .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
    // .pidf(ElevatorConstants.kElevatorP, ElevatorConstants.kElevatorI, ElevatorConstants.kElevatorD, ElevatorConstants.kElevatorF)
    // .iZone(ElevatorConstants.kElevatorIZone)
    // .outputRange(-1, 1);

    // leftElevatorConfig.closedLoop.maxMotion
    // .maxVelocity(ElevatorConstants.kElevatorMaxVelocity)
    // .maxAcceleration(ElevatorConstants.kElevatorMaxAcceleration)
    // .allowedClosedLoopError(ElevatorConstants.kClosedLoopError);

    // leftElevatorMotor.configure(leftElevatorConfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    initialDistance = 0;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    SmartDashboard.putNumber("LeftElevatorEncoderValue", leftElevatorEncoder.getPosition());
    SmartDashboard.putNumber("RightElevatorEncoderValue", rightElevatorEncoder.getPosition());
  }

  public double getEncoderValue() {
    return leftElevatorEncoder.getPosition();
  }

  public void setElevatorStates(TrapezoidProfile.State current, TrapezoidProfile.State next) {
    leftElevatorMotor.getClosedLoopController().setReference(
      current.position, 
      ControlType.kPosition, 
      ClosedLoopSlot.kSlot0, 
      leftElevatorMotorFeedForward.calculateWithVelocities(current.velocity, next.velocity));
  } 

  private double initialDistance;
  private double currentTime;
  private TrapezoidProfile.State currentSetpoint;
  private TrapezoidProfile.State nextSetpoint;

  public void profiledElevatorDistanceInit() {
    elevatorTimer.restart();
    initialDistance = leftElevatorEncoder.getPosition();
  }

  public void profiledElevatorDistance(double distance) {
      currentTime = elevatorTimer.get();
    
    currentSetpoint = elevatorProfile.calculate(
      currentTime, 
      new State(initialDistance, 0), 
      new State(distance, 0));

    nextSetpoint = elevatorProfile.calculate(
      currentTime + 0.02, 
      new State(initialDistance, 0), 
      new State(distance, 0));

    setElevatorStates(currentSetpoint, nextSetpoint);
  }

  public boolean profiledElevatorDistanceFinished()
  {
    currentTime = elevatorTimer.get();
    return elevatorProfile.isFinished(currentTime);
  }

  public void elevatorUp() {
    leftElevatorMotor.set(.1);
  }

  public void elevatorUpAt3() {
    leftElevatorMotor.set(.3);
  }

  public void elevatorDown() {
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
