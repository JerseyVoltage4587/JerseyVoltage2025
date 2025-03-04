// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

// package frc.robot.subsystems;

// import com.revrobotics.spark.SparkMax;
// import com.revrobotics.spark.SparkLowLevel.MotorType;

// import edu.wpi.first.wpilibj.motorcontrol.Spark;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.Constants;

// public class Climber extends SubsystemBase {
  
//   private static SparkMax climberMotor = new SparkMax(Constants.kClimberMotorID, MotorType.kBrushless);
//   static Climber m_Instance = null;
  
//   /** Creates a new Climber. */
//   public Climber() {}

//   @Override
//   public void periodic() {
//     // This method will be called once per scheduler run
//   }

//   public void climberOut() {
//     climberMotor.set(.8);
//   }

//   public void climberIn() {
//     climberMotor.set(-.8);
//   }

//   public void zeroMotor() {
//     climberMotor.set(0);
//   }

//   public static Climber getInstance() {
//     if (m_Instance == null) {
//       synchronized (Climber.class) {
//         if (m_Instance == null) {
//           m_Instance = new Climber();
//         }
//       }
//     }
//     return m_Instance;
//   }
// }
