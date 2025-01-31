// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.studica.frc.AHRS;
import com.studica.frc.AHRS.NavXComType;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.RobotConstants;
import frc.robot.Constants.SwerveConstants;

public class SwerveSubsystem extends SubsystemBase {
  /** Creates a new SwerveSubsystem. */
  static SwerveSubsystem m_Instance = null;

  private SwerveModule frontLeftModule = new SwerveModule(
    SwerveConstants.frontLeftDriveMotor,
    SwerveConstants.frontLeftTurnMotor,
    SwerveConstants.frontLeftDriveMotorReversed,
    SwerveConstants.frontLeftTurnMotorReversed,
    SwerveConstants.frontLeftAbsoluteEncoderPort,
    SwerveConstants.frontLeftAbsoluteEncoderReversed,
    SwerveConstants.frontLeftAbsoluteEncoderOffesetRad
  );
  
  private SwerveModule frontRightModule = new SwerveModule(
    SwerveConstants.frontRightDriveMotor,
    SwerveConstants.frontRightTurnMotor,
    SwerveConstants.frontRightDriveMotorReversed,
    SwerveConstants.frontRightTurnMotorReversed,
    SwerveConstants.frontRightAbsoluteEncoderPort,
    SwerveConstants.frontRightAbsoluteEncoderReversed,
    SwerveConstants.frontRightAbsoluteEncoderOffesetRad
  );

  private SwerveModule backLeftModule = new SwerveModule(
    SwerveConstants.backLeftDriveMotor,
    SwerveConstants.backLeftTurnMotor,
    SwerveConstants.backLeftDriveMotorReversed,
    SwerveConstants.backLeftTurnMotorReversed,
    SwerveConstants.backLeftAbsoluteEncoderPort,
    SwerveConstants.backLeftAbsoluteEncoderReversed,
    SwerveConstants.backLeftAbsoluteEncoderOffesetRad
  );

  private SwerveModule backRightModule = new SwerveModule(
    SwerveConstants.backRightDriveMotor,
    SwerveConstants.backRightTurnMotor,
    SwerveConstants.backRightDriveMotorReversed,
    SwerveConstants.backRightTurnMotorReversed,
    SwerveConstants.backRightAbsoluteEncoderPort,
    SwerveConstants.backRightAbsoluteEncoderReversed,
    SwerveConstants.backRightAbsoluteEncoderOffesetRad
  );

  private AHRS gyro = new AHRS(NavXComType.kMXP_SPI);
  
  //Position of Swerve Modules relative to center of robot
  Translation2d frontLeftLocation = new Translation2d(RobotConstants.robotLengthMeters / 2, RobotConstants.robotWidthMeters / 2);
  Translation2d frontRightLocation = new Translation2d(RobotConstants.robotLengthMeters / 2, -RobotConstants.robotWidthMeters / 2);
  Translation2d backLeftLocation = new Translation2d(-RobotConstants.robotLengthMeters / 2, RobotConstants.robotWidthMeters / 2);
  Translation2d backRightLocation = new Translation2d(-RobotConstants.robotLengthMeters / 2, -RobotConstants.robotWidthMeters / 2);

  //Kinematics object: ChassisSpeeds -> SwerveModuleStates
  public SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
    frontLeftLocation,
    frontRightLocation,
    backLeftLocation,
    backRightLocation
    );
    
    // private SwerveDriveOdometry odometer = new SwerveDriveOdometry(kinematics, new Rotation2d(0), new SwerveModulePosition[] {
    //   frontLeftModule.getPosition(),
    //   frontRightModule.getPosition(),
    //   backLeftModule.getPosition(),
    //   backRightModule.getPosition()
    // });


  //Constructor
  public SwerveSubsystem() {
    new Thread(() -> {
      try {
        Thread.sleep(1000);
        zeroGyro();
      } catch (Exception e) {
      }
    }).start();
  }

  public void zeroGyro() {
    gyro.reset();
  }

  public double getGyro() {
    return Math.IEEEremainder(gyro.getAngle(), 360);
  }

  public Rotation2d getGyroToRotation2d() {
    return Rotation2d.fromDegrees(getGyro());
  }

  public void zeroModules() {
    frontLeftModule.zeroMotors();
    frontRightModule.zeroMotors();
    backLeftModule.zeroMotors();
    backRightModule.zeroMotors();
  }

  public void setModuleStates(SwerveModuleState[] desiredStates) {
    SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, RobotConstants.maxSpeed);
    
    frontLeftModule.setDesiredState(desiredStates[0]);
    frontRightModule.setDesiredState(desiredStates[1]);
    backLeftModule.setDesiredState(desiredStates[2]);
    backRightModule.setDesiredState(desiredStates[3]);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // odometer.update(getGyroToRotation2d(), currentStates[]);
    
    SmartDashboard.putNumber("Gyro Degrees", getGyro());
    SmartDashboard.putNumber("Front Left Offset Rad", frontLeftModule.getAbsoluteEncoderRad());
    SmartDashboard.putNumber("Front Right Offset Rad", frontRightModule.getAbsoluteEncoderRad());
    SmartDashboard.putNumber("Back Left Offset Rad", backLeftModule.getAbsoluteEncoderRad());
    SmartDashboard.putNumber("Back Right Offset Rad", backRightModule.getAbsoluteEncoderRad());
    SmartDashboard.putNumber("Front Left Motor Rad", frontLeftModule.getTurnPosition());
    SmartDashboard.putNumber("Front Right Motor Rad", frontRightModule.getTurnPosition());
    SmartDashboard.putNumber("Back Left Motor Rad", backLeftModule.getTurnPosition());
    SmartDashboard.putNumber("Back Right Motor Rad", backRightModule.getTurnPosition());

  }



  
  public static SwerveSubsystem getInstance() {
    if (m_Instance == null) {
      synchronized (SwerveSubsystem.class) {
        if (m_Instance == null) {
          m_Instance = new SwerveSubsystem();
        }
      }
    }
    return m_Instance;
  }
}
