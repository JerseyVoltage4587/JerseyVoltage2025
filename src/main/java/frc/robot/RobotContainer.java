// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.Autos.ScoreL4;
import frc.robot.commands.Carriage.AlgaePivot.AlgaePivotIn;
import frc.robot.commands.Carriage.AlgaePivot.AlgaePivotOut;
import frc.robot.commands.Carriage.AlgaeScorer.AlgaeCollect;
import frc.robot.commands.Carriage.AlgaeScorer.AlgaeScore;
import frc.robot.commands.Carriage.CoralScorer.CoralForward;
import frc.robot.commands.Climber.ClimberIn;
import frc.robot.commands.Climber.ClimberOut;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.commands.Autos.CoralStationIntake;
import frc.robot.commands.Elevator.ElevatorDown;
import frc.robot.commands.Elevator.ElevatorUp;
import frc.robot.commands.Elevator.HoldPosition;
import frc.robot.commands.Elevator.ProfiledElevatorDistance;
import frc.robot.commands.Intake.RunIntake;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Drivetrain.SwerveSubsystem;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  private final SwerveSubsystem m_swervesubsystem = new SwerveSubsystem();
  Elevator m_elevator = Elevator.getInstance();
  private final SendableChooser<Command> autoChooser;

  private final Joystick j = new Joystick(0);
  private final Joystick k = new Joystick(1);

  // Replace with CommandPS4Controller or CommandJoystick if needed
  

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    final Command swerveDef = m_swervesubsystem.DriveCommand(
      () -> -j.getRawAxis(1),
      () -> -j.getRawAxis(0),
      () -> -j.getRawAxis(2),
      () -> true);
    SmartDashboard.putData("SwevreDefault", swerveDef);
    m_swervesubsystem.setDefaultCommand(swerveDef);

    m_elevator.setDefaultCommand(new HoldPosition());
    
    CameraServer.startAutomaticCapture();

    NamedCommands.registerCommand("ScoreL4", new ScoreL4());
    NamedCommands.registerCommand("CoralStationIntake", new CoralStationIntake());

    autoChooser = AutoBuilder.buildAutoChooser();
    autoChooser.addOption("LeaveOnly",
          m_swervesubsystem.DriveCommand(() -> -0.25, () -> 0.0, () -> 0.0, () -> true).withTimeout(1));
    SmartDashboard.putData("Auto Chooser", autoChooser);

    // Configure the trigger bindings
    configureBindings();
    configureButtonBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
  }

  private void configureButtonBindings()
  {
    Trigger jButtonY, jButtonX, jButtonA, jButtonB, jLeftBumper, jRightBumper, jLeftTrigger, jRightTrigger,
    jMinusButton, jPlusButton, jLeftStickButton, jRightStickButton;
  
    Trigger kButtonY, kButtonX, kButtonA, kButtonB, kLeftBumper, kRightBumper, kLeftTrigger, kRightTrigger,
    kMinusButton, kPlusButton, kLeftStickButton, kRightStickButton, kHouseButton, kCircleButton;
  
    POVButton jUpArrow, jUpRightArrow, jRightArrow, jDownRightArrow, jDownArrow, jDownLeftArrow, jLeftArrow, jUpLeftArrow;
  
    POVButton kUpArrow, kRightArrow, kDownArrow, kLeftArrow;
  
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
    final Command robotLeft = m_swervesubsystem.DriveCommand(() -> 0.0, () -> 0.1, () -> 0.0, () -> false);
    final Command robotRight = m_swervesubsystem.DriveCommand(() -> 0.0, () -> -0.1, () -> 0.0, () -> false);
    SmartDashboard.putData("CmdRobotLeft", robotLeft);
    SmartDashboard.putData("CmdRobotRight", robotLeft);
    jLeftTrigger.whileTrue(robotLeft);
    jRightTrigger.whileTrue(robotRight);

    jUpArrow.whileTrue(m_swervesubsystem.ForwardAtAngleCommand(() -> {return 0.0;}));
    jUpRightArrow.whileTrue(m_swervesubsystem.ForwardAtAngleCommand(() -> 45.0));
    jRightArrow.whileTrue(m_swervesubsystem.ForwardAtAngleCommand(() -> 90.0));
    jDownRightArrow.whileTrue(m_swervesubsystem.ForwardAtAngleCommand(() -> 135.0));
    jDownArrow.whileTrue(m_swervesubsystem.ForwardAtAngleCommand(() -> 180.0));
    jDownLeftArrow.whileTrue(m_swervesubsystem.ForwardAtAngleCommand(() -> 225.0));
    jLeftArrow.whileTrue(m_swervesubsystem.ForwardAtAngleCommand(() -> 270.0));
    jUpLeftArrow.whileTrue(m_swervesubsystem.ForwardAtAngleCommand(() -> 315.0));
    

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

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return autoChooser.getSelected();
  }

  //Boring template stuff
  public SwerveSubsystem getSwerve()
  {
    return m_swervesubsystem;
  }
  

}
