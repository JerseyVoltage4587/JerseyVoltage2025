// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

// package frc.robot.commands.Climber;

// import edu.wpi.first.wpilibj2.command.Command;
// import frc.robot.subsystems.Climber;

// /* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
// public class ClimberOut extends Command {
  
//   Climber m_climber = Climber.getInstance();
  
//   /** Creates a new ClimberOut. */
//   public ClimberOut() {
//     // Use addRequirements() here to declare subsystem dependencies.
//     addRequirements(m_climber);
//   }

//   // Called when the command is initially scheduled.
//   @Override
//   public void initialize() {}

//   // Called every time the scheduler runs while the command is scheduled.
//   @Override
//   public void execute() {
//     m_climber.climberOut();
//   }

//   // Called once the command ends or is interrupted.
//   @Override
//   public void end(boolean interrupted) {
//     m_climber.zeroMotor();
//   }

//   // Returns true when the command should end.
//   @Override
//   public boolean isFinished() {
//     return false;
//   }
// }
