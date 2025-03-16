// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.config.RobotConfig;

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

    public static final int frontLeftDriveMotor = 1;
    public static final int frontLeftTurnMotor = 0;
    public static final boolean frontLeftDriveMotorReversed = true;
    public static final boolean frontLeftTurnMotorReversed = true;
    public static final int frontLeftAbsoluteEncoderPort = 17;
    public static final double frontLeftAbsoluteEncoderOffesetRad = -1.534;//-0.11;
    public static final boolean frontLeftAbsoluteEncoderReversed = false;

    public static final int frontRightDriveMotor = 7;
    public static final int frontRightTurnMotor = 6;
    public static final boolean frontRightDriveMotorReversed = true;
    public static final boolean frontRightTurnMotorReversed = true;
    public static final int frontRightAbsoluteEncoderPort = 16;
    public static final double frontRightAbsoluteEncoderOffesetRad = -2.801; // -3.032
    public static final boolean frontRightAbsoluteEncoderReversed = false;

    public static final int backLeftDriveMotor = 5;
    public static final int backLeftTurnMotor = 4;
    public static final boolean backLeftDriveMotorReversed = true;
    public static final boolean backLeftTurnMotorReversed = true;
    public static final int backLeftAbsoluteEncoderPort = 19;
    public static final double backLeftAbsoluteEncoderOffesetRad = -1.883;//0.65;
    public static final boolean backLeftAbsoluteEncoderReversed = false;

    public static final int backRightDriveMotor = 3;
    public static final int backRightTurnMotor = 2;
    public static final boolean backRightDriveMotorReversed = true;
    public static final boolean backRightTurnMotorReversed = true;
    public static final int backRightAbsoluteEncoderPort = 18;
    public static final double backRightAbsoluteEncoderOffesetRad = -2.11;//-2.63; 2.01
    public static final boolean backRightAbsoluteEncoderReversed = false;

    public static final double kSwerveP = 0.2;
    public static final double kSwerveI = 0;
    public static final double kSwerveD = 0;
  }

  public static class RobotConstants {
    
    public static final double kRobotWidthMeters = Units.inchesToMeters(29);
    public static final double kRobotLengthMeters = Units.inchesToMeters(29);
  
    public static final double kMaxSpeed = 4.5;
    public static final double kDeadBand = 0.1;
    public static final double kSwerveMaxAcceleration = 0.5;
    public static final double kSwerveMaxAngularAcceleration = 0.5;

    public static final double kWheelDiameter = Units.inchesToMeters(4);
    public static final double kDriveMotorGearRatio = 6.75;
    public static final double kTurnMotorGearRatio = 150/7;
    
  }
    
  public static class ElevatorConstants {

    public static final double kElevatorP = 0.1;
    public static final double kElevatorI = 0;
    public static final double kElevatorD = 0;
    public static final double kElevatorF = 0;
    public static final double kElevatorIZone = 100;

    public static final double kMinSetpoint = 0;
    
    public static final double kRestingSetpoint = 0;
    public static final double kL1Setpoint = 20;
    public static final double kProcessorSetpoint = 0;
    public static final double kL2Setpoint = 35;
    public static final double kL2Algae = 33.3;
    public static final double kL3Setpoint = 60;
    public static final double kL3Algae = 57.7;
    public static final double kL4Setpoint = 101;

    public static final double kMaxSetpoint = 101;

    public static final double kElevatorMaxVelocity = 80; //75
    public static final double kElevatorMaxAcceleration = 240; //135
    public static final double kClosedLoopError = 0.05; 
    public static final double kMinOutput = -0.5;
    public static final double kMaxOutput = 0.5;

    public static final double kElevatorVolts = 0;
    public static final double kElevatorGravityVolts = 0.05; //.1
    public static final double kElevatorVoltsPerSecond = 0.135; //.175
    public static final double kElevatorVoltsPerSecondSquared = 0.001;

  }

    //IDs
    public static final int kLeftElevatorMotorID = 11; //11
    public static final int kRightElevatorMotorID = 10; //10

    public static final int kIntakeFunnelMotorID = 30; //30

    public static final int kClimberMotorID = 40; //40

    public static final int kCoralLeftMotorID = 21; //20
    public static final int kCoralRightMotorID = 20; //21
    public static final int kAlgaePivotMotorID = 24; //24
    public static final int kAlgaeScorerMotorID = 22; //22
}
