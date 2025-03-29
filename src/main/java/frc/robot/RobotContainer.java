// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.ElevatorConstants;
import frc.robot.commands.DriveJoysticks;
import frc.robot.commands.Elevator.ElevatorDown;
import frc.robot.commands.Elevator.ElevatorUp;
import frc.robot.commands.Elevator.HoldPosition;
import frc.robot.commands.Elevator.ProfiledElevatorDistance;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Carriage.AlgaePivot;
import frc.robot.subsystems.Carriage.AlgaeScorer;
import frc.robot.subsystems.Carriage.CoralScorer;
import frc.robot.subsystems.Drivetrain.SwerveSubsystem;

import java.net.URL;
import java.util.List;

import org.photonvision.PhotonCamera;

import com.ctre.phoenix6.swerve.jni.SwerveJNI.DriveState;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.math.filter.Debouncer.DebounceType;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Command.InterruptionBehavior;
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

  private static final SwerveSubsystem m_swervesubsystem = new SwerveSubsystem();
  private static final Intake m_intake = new Intake();
  private static final Climber m_climber = new Climber();
  private static final CoralScorer m_coralscorer = new CoralScorer();
  private static final AlgaeScorer m_algaescorer = new AlgaeScorer();
  private static final AlgaePivot m_algaepivot = new AlgaePivot();
  // private static final Elevator m_elevator = new Elevator();

  Elevator m_elevator = Elevator.getInstance();
  private final SendableChooser<Command> autoChooser;

  private final Joystick j = new Joystick(0);
  private final Joystick k = new Joystick(1);
  private final Trigger jRightTrigger = new JoystickButton(j, 8);

  // Replace with CommandPS4Controller or CommandJoystick if needed
  
  PhotonCamera frontCamera;
  PhotonCamera backCamera;
  // final CameraServer frontCameraServer;
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  

  public RobotContainer() {

    m_swervesubsystem.setDefaultCommand(new DriveJoysticks(
      () -> -j.getRawAxis(1),
      () -> -j.getRawAxis(0),
      () -> -j.getRawAxis(2),
      () -> jRightTrigger.getAsBoolean()
    ));

    m_elevator.setDefaultCommand(new HoldPosition());
    // m_elevator.setDefaultCommand(m_elevator.HoldPositionCommand());
    m_algaepivot.setDefaultCommand(m_algaepivot.AlgaePivotHoldPositionCommand()); 
    
    frontCamera = new PhotonCamera("FrontCamera");
    backCamera = new PhotonCamera("RearCamera");
    // CameraServer.startAutomaticCapture();
    // Shuffleboard.getTab("Cameras").addCamera("FrontCamera", "FrontCamera", "mjpg:http://10.45.87.11:1182/");
    // Shuffleboard.getTab("Cameras").addCamera("RearCamera", "RearCamera", "mjpg:http://10.45.87.11:1183/?action=stream");

    NamedCommands.registerCommand("ScoreL4", ScoreL4());
    NamedCommands.registerCommand("CoralStationIntake", CoralStationIntake());

    autoChooser = AutoBuilder.buildAutoChooser();
    // autoChooser.setDefaultOption("LeaveOnly",
    //       m_swervesubsystem.DriveCommand(() -> -0.25, () -> 0.0, () -> 0.0, () -> true).withTimeout(1));
    // autoChooser.setDefaultOption("StraightDrive", autoPathCommand("StraightDrive"));
    // autoChooser.addOption("BlueRight2Coral", autoPathCommand("BlueRight2Coral"));
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
    Trigger jButtonY, jButtonX, jButtonA, jButtonB, jLeftBumper, jRightBumper, jLeftTrigger,
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
    jButtonY.whileTrue(m_swervesubsystem.DriveCommand(() -> 0.0, () -> 0.1, () -> 0.0, () -> false).withInterruptBehavior(InterruptionBehavior.kCancelIncoming));
    jButtonA.onTrue(m_swervesubsystem.DriveCommand(() -> 0.0, () -> -0.1, () -> 0.0, () -> false).withInterruptBehavior(InterruptionBehavior.kCancelIncoming));

    jUpArrow.whileTrue(m_swervesubsystem.ForwardAtAngleCommand(() -> {return 0.0;}));
    jUpRightArrow.whileTrue(m_swervesubsystem.ForwardAtAngleCommand(() -> 45.0));
    jRightArrow.whileTrue(m_swervesubsystem.ForwardAtAngleCommand(() -> 90.0));
    jDownRightArrow.whileTrue(m_swervesubsystem.ForwardAtAngleCommand(() -> 135.0));
    jDownArrow.whileTrue(m_swervesubsystem.ForwardAtAngleCommand(() -> 180.0));
    jDownLeftArrow.whileTrue(m_swervesubsystem.ForwardAtAngleCommand(() -> 225.0));
    jLeftArrow.whileTrue(m_swervesubsystem.ForwardAtAngleCommand(() -> 270.0));
    jUpLeftArrow.whileTrue(m_swervesubsystem.ForwardAtAngleCommand(() -> 315.0));
    

    // Operator Commands
    
    // kHouseButton.whileTrue(new ElevatorUp());
    // kCircleButton.whileTrue(new ElevatorDown());
    // kButtonX.whileTrue(new ElevatorUpAt3());

    kButtonX.whileTrue(new ProfiledElevatorDistance(ElevatorConstants.kL1Setpoint));
    kButtonA.whileTrue(new ProfiledElevatorDistance(ElevatorConstants.kL2Setpoint));
    kButtonB.whileTrue(new ProfiledElevatorDistance(ElevatorConstants.kL3Setpoint));
    kButtonY.whileTrue(new ProfiledElevatorDistance(ElevatorConstants.kL4Setpoint));
    kLeftArrow.whileTrue(new ProfiledElevatorDistance(ElevatorConstants.kRestingSetpoint));
    kDownArrow.whileTrue(new ProfiledElevatorDistance(ElevatorConstants.kL2Algae));
    kUpArrow.whileTrue(new ProfiledElevatorDistance(ElevatorConstants.kL3Algae));

    // kHouseButton.whileTrue(m_elevator.ElevatorUpCommand());
    // kCircleButton.whileTrue(m_elevator.ElevatorDownCommand());

    // kButtonX.whileTrue(m_elevator.ProfiledElevatorDistanceCommand(ElevatorConstants.kL1Setpoint));
    // kButtonA.whileTrue(m_elevator.ProfiledElevatorDistanceCommand(ElevatorConstants.kL2Setpoint));
    // kButtonB.whileTrue(m_elevator.ProfiledElevatorDistanceCommand(ElevatorConstants.kL3Setpoint));
    // kButtonY.whileTrue(m_elevator.ProfiledElevatorDistanceCommand(ElevatorConstants.kL4Setpoint));
    // kLeftArrow.whileTrue(m_elevator.ProfiledElevatorDistanceCommand(ElevatorConstants.kRestingSetpoint));
    // kDownArrow.whileTrue(m_elevator.ProfiledElevatorDistanceCommand(ElevatorConstants.kL2Algae));
    // kUpArrow.whileTrue(m_elevator.ProfiledElevatorDistanceCommand(ElevatorConstants.kL3Algae));

    kPlusButton.whileTrue(m_climber.ClimberInCommand());
    kMinusButton.whileTrue(m_climber.ClimberOutCommand());
    kLeftStickButton.whileTrue(m_algaepivot.AlgaePivotGoToPositionCommand(Constants.kAlgaePivotUpSetpoint));
    kRightArrow.whileTrue(m_algaepivot.AlgaePivotGoToPositionCommand(Constants.kAlgaeLoadedSetpoint));
    kRightStickButton.whileTrue(m_algaepivot.AlgaePivotGoToPositionCommand(Constants.kAlgaePivotDownSetpoint));

    kRightTrigger.whileTrue(m_intake.RunIntakeCommand());
    kLeftTrigger.whileTrue(m_coralscorer.CoralForwardCommand());
    kHouseButton.whileTrue(m_coralscorer.CoralBackwardCommand());
    kRightBumper.whileTrue(m_algaescorer.AlgaeCollectCommand());
    kLeftBumper.whileTrue(m_algaescorer.AlgaeScoreCommand());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    // return new DriveJoysticks(() -> -0.25, () -> 0.0, () -> 0.0, () -> false).withTimeout(2.5);
    return autoChooser.getSelected();
    // return new PathPlannerAuto("StraightDrive");
  }

  //Boring template stuff
  public static SwerveSubsystem getSwerve()
  {
    return m_swervesubsystem;
  }
  
  public static AlgaePivot getAlgaePivot()
  {
    return m_algaepivot;
  }

  public SequentialCommandGroup ScoreL4() {
    return new SequentialCommandGroup(
      m_elevator.ProfiledElevatorDistanceCommand(ElevatorConstants.kL4Setpoint).withTimeout(1.5), 
      m_coralscorer.CoralForwardCommand().withTimeout(.5),
      m_elevator.ProfiledElevatorDistanceCommand(ElevatorConstants.kRestingSetpoint).withTimeout(1.5)
    );
  }

  public SequentialCommandGroup CoralStationIntake() {
    return new SequentialCommandGroup(
      new ParallelCommandGroup(
        m_intake.RunIntakeCommand(),
        m_coralscorer.CoralForwardCommand()
      ).withTimeout(1),
      m_intake.ZeroIntakeMotorCommand(),
      m_coralscorer.ZeroCoralScorerMotorCommand()
    );
  }

  // public Command autoPathCommand(String name) {
  //   try {
  //     // List<PathPlannerPath> auto = PathPlannerAuto.getPathGroupFromAutoFile(name);

  //     return AutoBuilder.buildAuto(name);
  //   } catch (Exception e) {
  //     DriverStation.reportError(e.getMessage(), e.getStackTrace());
  //     return Commands.none();
  //   }
  // }

}
