// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.commands.Drivetrain.SwerveDriveJoysticks;
import frc.robot.commands.Elevator.ElevatorDown;
import frc.robot.commands.Elevator.ElevatorUp;
import frc.robot.commands.Elevator.ElevatorUpAt3;
import frc.robot.commands.Elevator.GoToPosition;
import frc.robot.commands.Elevator.ProfiledElevatorDistance;
import frc.robot.commands.Intake.RunIntake;
import frc.robot.commands.Scorer.ScorerForward;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.SwerveSubsystem;

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
  kMinusButton, kPlusButton, kLeftStickButton, kRightStickButton;

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
    
    //Robot Commands

    jLeftTrigger.whileTrue(new SwerveDriveJoysticks(m_swerve, () -> 0.0, () -> -0.1, () -> 0.0, () -> true));
    jRightTrigger.whileTrue(new SwerveDriveJoysticks(m_swerve, () -> 0.0, () -> 0.1, () -> 0.0, () -> true));
    
    // jRightBumper.whileTrue(new ClimberIn());
    // jLeftBumper.whileTrue(new ClimberOut());


    kRightTrigger.whileTrue(new ScorerForward());
    kLeftTrigger.whileTrue(new RunIntake());

    // kRightBumper.whileTrue(new CollectAlgae());
    // kLeftBumper.whileTrue(new ScoreAlgae());

    kLeftBumper.whileTrue(new ElevatorUp());
    kRightBumper.whileTrue(new ElevatorDown());
    //kButtonX.whileTrue(new ElevatorUpAt3());

    kButtonX.whileTrue(new ProfiledElevatorDistance(ElevatorConstants.kL1Setpoint));
    kButtonA.whileTrue(new ProfiledElevatorDistance(ElevatorConstants.kL2Setpoint));
    kButtonB.whileTrue(new ProfiledElevatorDistance(ElevatorConstants.kL3Setpoint));
    kButtonY.whileTrue(new ProfiledElevatorDistance(ElevatorConstants.kL4Setpoint));
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
