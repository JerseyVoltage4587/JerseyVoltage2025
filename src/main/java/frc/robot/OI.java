// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.SwerveSubsystem;

public class OI extends SubsystemBase {
  /** Creates a new OI. */
  
  static OI m_Instance = null;
  SwerveSubsystem m_Swerve;

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
