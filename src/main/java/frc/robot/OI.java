// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEvent;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableListener;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.NetworkButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.commands.Carriage.AlgaePivot.AlgaePivotIn;
import frc.robot.commands.Carriage.AlgaePivot.AlgaePivotOut;
import frc.robot.commands.Carriage.AlgaeScorer.AlgaeCollect;
import frc.robot.commands.Carriage.AlgaeScorer.AlgaeScore;
import frc.robot.commands.Carriage.CoralScorer.CoralForward;
import frc.robot.commands.Carriage.CoralScorer.CoralL1Forward;
import frc.robot.commands.Climber.ClimberIn;
import frc.robot.commands.Climber.ClimberOut;
import frc.robot.commands.Drivetrain.ForwardAtAngle;
import frc.robot.commands.Drivetrain.SwerveDriveFixed;
import frc.robot.commands.Drivetrain.SwerveDriveJoysticks;
import frc.robot.commands.Elevator.ElevatorDown;
import frc.robot.commands.Elevator.ElevatorUp;
import frc.robot.commands.Elevator.ElevatorUpAt3;
import frc.robot.commands.Elevator.GoToPosition;
import frc.robot.commands.Elevator.ProfiledElevatorDistance;
import frc.robot.commands.Intake.RunIntake;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Drivetrain.SwerveSubsystem;

public class OI extends SubsystemBase {
  /** Creates a new OI. */
  
  static OI m_Instance = null;
  Elevator m_elevator = Elevator.getInstance();
  SwerveSubsystem m_swerve = SwerveSubsystem.getInstance();

  //Controllers 1 & 2
  public Joystick j, k;
  public Trigger jButtonY, jButtonX, jButtonA, jButtonB, jLeftBumper, jRightBumper, jLeftTrigger, jRightTrigger,
  jMinusButton, jPlusButton, jLeftStickButton, jRightStickButton;

  public Trigger kButtonY, kButtonX, kButtonA, kButtonB, kLeftBumper, kRightBumper, kLeftTrigger, kRightTrigger,
  kMinusButton, kPlusButton, kLeftStickButton, kRightStickButton, kHouseButton, kCircleButton;

  public POVButton jUpArrow, jUpRightArrow, jRightArrow, jDownRightArrow, jDownArrow, jDownLeftArrow, jLeftArrow, jUpLeftArrow;

  public POVButton kUpArrow, kRightArrow, kDownArrow, kLeftArrow;

  public OI() {

    //Controller 1 & 2 ports
    j = new Joystick(0);
    k = new Joystick(1);

    //Controller 1 Buttons
    jButtonY = new JoystickButton(j, 1);
    jButtonB = new JoystickButton(j, 2);
    jButtonA = new JoystickButton(j, 3);
    jButtonX = new JoystickButton(j, 4);
    jLeftBumper = new JoystickButton(j, 5);
    jRightBumper = new JoystickButton(j, 6);
    jLeftTrigger = new JoystickButton(j, 7);
    jRightTrigger = new JoystickButton(j, 8);
    jMinusButton = new JoystickButton(j, 9);
    jPlusButton = new JoystickButton(j, 10);
    jLeftStickButton = new JoystickButton(j, 11);
    jRightStickButton = new JoystickButton(j, 12);

    jUpArrow = new POVButton(j, 0);
    jUpRightArrow = new POVButton(j, 45);
    jRightArrow = new POVButton(j, 90);
    jDownRightArrow = new POVButton(j, 135);
    jDownArrow = new POVButton(j, 180);
    jDownLeftArrow = new POVButton(j, 225);
    jLeftArrow = new POVButton(j, 270);
    jUpLeftArrow = new POVButton(j, 315);

    //Controller 2 Buttons
    kButtonY = new JoystickButton(k, 1);
    kButtonB = new JoystickButton(k, 2);
    kButtonA = new JoystickButton(k, 3);
    kButtonX = new JoystickButton(k, 4);
    kLeftBumper = new JoystickButton(k, 5);
    kRightBumper = new JoystickButton(k, 6);
    kLeftTrigger = new JoystickButton(k, 7);
    kRightTrigger = new JoystickButton(k, 8);
    kMinusButton = new JoystickButton(k, 9);
    kPlusButton = new JoystickButton(k, 10);
    kLeftStickButton = new JoystickButton(k, 11);
    kRightStickButton = new JoystickButton(k, 12);
    kHouseButton = new JoystickButton(k, 13);
    kCircleButton = new JoystickButton(k, 14);

    kUpArrow = new POVButton(k, 0);
    kRightArrow = new POVButton(k, 90);
    kDownArrow = new POVButton(k, 180);
    kLeftArrow = new POVButton(k, 270); 

    //Robot Commands
    //Driver Commands

    jLeftTrigger.whileTrue(new SwerveDriveFixed(m_swerve, 0.0, 0.1, 0.0, false));
    jRightTrigger.whileTrue(new SwerveDriveFixed(m_swerve, 0.0, -0.1, 0.0, false));

    jUpArrow.whileTrue(new ForwardAtAngle(0));
    jUpRightArrow.whileTrue(new ForwardAtAngle(45));
    jRightArrow.whileTrue(new ForwardAtAngle(90));
    jDownRightArrow.whileTrue(new ForwardAtAngle(135));
    jDownArrow.whileTrue(new ForwardAtAngle(180));
    jDownLeftArrow.whileTrue(new ForwardAtAngle(225));
    jLeftArrow.whileTrue(new ForwardAtAngle(270));
    jUpLeftArrow.whileTrue(new ForwardAtAngle(315));
    

    // Operator Commands
    
    kHouseButton.whileTrue(new ElevatorUp());
    kCircleButton.whileTrue(new ElevatorDown());
    // kButtonX.whileTrue(new ElevatorUpAt3());

    kButtonX.whileTrue(new ProfiledElevatorDistance(ElevatorConstants.kL1Setpoint));
    kButtonA.whileTrue(new ProfiledElevatorDistance(ElevatorConstants.kL2Setpoint));
    kButtonB.whileTrue(new ProfiledElevatorDistance(ElevatorConstants.kL3Setpoint));
    kButtonY.whileTrue(new ProfiledElevatorDistance(ElevatorConstants.kL4Setpoint));
    kLeftArrow.whileTrue(new ProfiledElevatorDistance(ElevatorConstants.kRestingSetpoint));
    kDownArrow.whileTrue(new ProfiledElevatorDistance(ElevatorConstants.kL2Algae));
    kUpArrow.whileTrue(new ProfiledElevatorDistance(ElevatorConstants.kL3Algae));

    kPlusButton.whileTrue(new ClimberIn());
    kMinusButton.whileTrue(new ClimberOut());
    kRightStickButton.whileTrue(new AlgaePivotOut());
    kLeftStickButton.whileTrue(new AlgaePivotIn());

    kRightTrigger.whileTrue(new RunIntake());
    kLeftTrigger.whileTrue(new CoralForward());
    // kCircleButton.whileTrue(new CoralL1Forward());
    kRightBumper.whileTrue(new AlgaeCollect());
    kLeftBumper.whileTrue(new AlgaeScore());

  }


  public static OI getInstance() {
    if (m_Instance == null) {
      synchronized (OI.class) {
        if (m_Instance == null) {
          m_Instance = new OI();
        }
      }
    }
    return m_Instance;
  }
}
