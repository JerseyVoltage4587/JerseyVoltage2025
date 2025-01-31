// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  
  public static class SwerveConstants {

    public static final int frontLeftDriveMotor = 7;
    public static final int frontLeftTurnMotor = 6;
    public static final boolean frontLeftDriveMotorReversed = true;
    public static final boolean frontLeftTurnMotorReversed = true;
    public static final int frontLeftAbsoluteEncoderPort = 16;
    public static final double frontLeftAbsoluteEncoderOffesetRad = 1.9;//-0.11;
    public static final boolean frontLeftAbsoluteEncoderReversed = false;

    public static final int frontRightDriveMotor = 1;
    public static final int frontRightTurnMotor = 0;
    public static final boolean frontRightDriveMotorReversed = true;
    public static final boolean frontRightTurnMotorReversed = true;
    public static final int frontRightAbsoluteEncoderPort = 17;
    public static final double frontRightAbsoluteEncoderOffesetRad = -0.12;//-1.75;
    public static final boolean frontRightAbsoluteEncoderReversed = false;

    public static final int backLeftDriveMotor = 3;
    public static final int backLeftTurnMotor = 2;
    public static final boolean backLeftDriveMotorReversed = true;
    public static final boolean backLeftTurnMotorReversed = true;
    public static final int backLeftAbsoluteEncoderPort = 18;
    public static final double backLeftAbsoluteEncoderOffesetRad = -0.61;//0.65;
    public static final boolean backLeftAbsoluteEncoderReversed = false;

    public static final int backRightDriveMotor = 5;
    public static final int backRightTurnMotor = 4;
    public static final boolean backRightDriveMotorReversed = true;
    public static final boolean backRightTurnMotorReversed = true;
    public static final int backRightAbsoluteEncoderPort = 19;
    public static final double backRightAbsoluteEncoderOffesetRad = 2.88;//-2.63;
    public static final boolean backRightAbsoluteEncoderReversed = false;

    public static final double kP = 0.2;
    public static final double kI = 0;
    public static final double kD = 0;
  }

  public static class RobotConstants {
    
    public static final double robotWidthMeters = Units.inchesToMeters(29);
    public static final double robotLengthMeters = Units.inchesToMeters(29);
  
    public static final double maxSpeed = 4.5;
    public static final double deadBand = 0.1;
    public static final double MaxAcceleration = 0.5;
    public static final double MaxAngularAcceleration = 0.5;

    public static final double wheelDiameter = Units.inchesToMeters(4);
    public static final double driveMotorGearRatio = 6.75;
    public static final double turnMotorGearRatio = 150/7;
  }
}
