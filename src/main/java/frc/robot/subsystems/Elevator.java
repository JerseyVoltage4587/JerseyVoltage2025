// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.trajectory.TrapezoidProfile.State;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
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

  private static DigitalInput elevatorBaseLimitSwitch = new DigitalInput(9);
  
  // private static SparkClosedLoopController leftElevatorClosedLoopController;
  
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

  // private static PIDController elevatorPIDController = new PIDController(
  //   Constants.ElevatorConstants.kElevatorP, 
  //   Constants.ElevatorConstants.kElevatorI, 
  //   Constants.ElevatorConstants.kElevatorD
  // );
        
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
    leftElevatorConfig.closedLoop.outputRange(ElevatorConstants.kMinOutput, ElevatorConstants.kMaxOutput);
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
    initialVelocity = 0;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    if (!elevatorBaseLimitSwitch.get()) {
      leftElevatorEncoder.setPosition(0);
    }

    SmartDashboard.putNumber("LeftElevatorEncoderValue", leftElevatorEncoder.getPosition());
    SmartDashboard.putNumber("RightElevatorEncoderValue", rightElevatorEncoder.getPosition());
    SmartDashboard.putBoolean("LimitSwitchValue", elevatorBaseLimitSwitch.get());
    SmartDashboard.putData(this);
    // if (this.getCurrentCommand() != null)
    //   SmartDashboard.putData("Elev Cmd", this.getCurrentCommand());

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

      SmartDashboard.putNumber("ElvPos\\Cmd", current.position);
      double actPos = leftElevatorEncoder.getPosition();
      SmartDashboard.putNumber("ElvPos\\Act", actPos);
      SmartDashboard.putNumber("ElvPos\\Err", current.position - actPos);
      SmartDashboard.putNumber("ElvVel\\Cmd", current.velocity);
      SmartDashboard.putNumber("ElvVel\\Act", leftElevatorEncoder.getVelocity()/60);
      SmartDashboard.putNumber("ElvCurrent", leftElevatorMotor.getOutputCurrent());
      SmartDashboard.putNumber("ElvOut\\Volts", leftElevatorMotor.getAppliedOutput() * leftElevatorMotor.getBusVoltage());
      SmartDashboard.putNumber("ElvOut\\Feedf", leftElevatorMotorFeedForward.calculateWithVelocities(current.velocity, next.velocity));

  } 

  private double initialDistance;
  private double initialVelocity;
  private double currentTime;
  private TrapezoidProfile.State currentSetpoint;
  private TrapezoidProfile.State nextSetpoint;

  public void profiledElevatorDistanceInit() {
    elevatorTimer.restart();
    initialDistance = leftElevatorEncoder.getPosition();
    //try using leftElevatorEncoder.getVelocity() here anyways?  Then we could interrupt motions on the fly every time
    initialVelocity = 0;
  }

  //returns target position for smooth stopping
  public double profiledElevatorStopInit() {
    elevatorTimer.restart();
    initialDistance = leftElevatorEncoder.getPosition();
    initialVelocity = leftElevatorEncoder.getVelocity();
    // d = 1/2*a*t^2  t = v/a  d = 1/2*a*v^2/a^2  abs is to preserve direction
    double target = initialDistance + initialVelocity * Math.abs(initialVelocity) / (ElevatorConstants.kElevatorMaxAcceleration * 2);
    return Math.max(ElevatorConstants.kMinSetpoint, Math.min(target, ElevatorConstants.kMaxSetpoint));
  }

  public void profiledElevatorDistance(double distance) {
      currentTime = elevatorTimer.get();
    
    currentSetpoint = elevatorProfile.calculate(
      currentTime,
      new State(initialDistance, initialVelocity), 
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
    if (currentSetpoint != null) {
      return elevatorProfile.isFinished(currentTime) && (Math.abs(currentSetpoint.position - leftElevatorEncoder.getPosition()) < 0.1 );
    } else {
      return false;
    }
  }

  public void elevatorUp() {
    leftElevatorMotor.set(0.1);
  }

  public void elevatorUpAt3() {
    leftElevatorMotor.set(.3);
  }

  public void elevatorDown() {
    leftElevatorMotor.set(-0.05);
  }
  
  public void zeroMotors() {
    leftElevatorMotor.set(0);
  }

  public void goToPosition(double pos) {
    leftElevatorMotor.getClosedLoopController().setReference(
      pos, 
      ControlType.kPosition, 
      ClosedLoopSlot.kSlot0,
      leftElevatorMotorFeedForward.calculateWithVelocities(0, 0));
    
      SmartDashboard.putNumber("ElvPos\\Cmd", pos);
      double actPos = leftElevatorEncoder.getPosition();
      SmartDashboard.putNumber("ElvPos\\Act", actPos);
      SmartDashboard.putNumber("ElvPos\\Err", pos - actPos);
      SmartDashboard.putNumber("ElvVel\\Cmd", 0);
      SmartDashboard.putNumber("ElvVel\\Act", leftElevatorEncoder.getVelocity()/60);
      SmartDashboard.putNumber("ElvCurrent", leftElevatorMotor.getOutputCurrent());
      SmartDashboard.putNumber("ElvOut\\Volts", leftElevatorMotor.getAppliedOutput() * leftElevatorMotor.getBusVoltage());
      SmartDashboard.putNumber("ElvOut\\Feedf", leftElevatorMotorFeedForward.calculateWithVelocities(0, 0));
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

  // Command Methods

  public Command ElevatorDownCommand() {
    return runEnd(() -> elevatorDown(), () -> zeroMotors());
  }

  public Command ElevatorUpCommand() {
    return runEnd(() -> elevatorUp(), () -> zeroMotors());
  }

  public Command ElevatorUpAt3Command() {
    return runEnd(() -> elevatorUpAt3(), () -> zeroMotors());
  }

  public Command ZeroElevatorMotorCommand() {
    return runOnce(() -> zeroMotors());
  }

  public Command ProfiledElevatorDistanceCommand(double distance) {
    return runEnd(() -> profiledElevatorDistance(distance), () -> zeroMotors())
    .beforeStarting(() -> profiledElevatorDistanceInit())
    .until(() -> profiledElevatorDistanceFinished());
  }

  public Command ProfiledElevatorDistanceStopCommand() {
    return runEnd(() -> profiledElevatorDistance(profiledElevatorStopInit()), () -> zeroMotors())
    .until(() -> profiledElevatorDistanceFinished());
  }

  public Command GoToPositionCommand(double position) {
    return runEnd(() -> goToPosition(position), () -> zeroMotors());
  }

  public Command HoldPositionCommand() {
    return runEnd(() -> goToPosition(getEncoderValue()), () -> zeroMotors());
  }
}
