package org.usfirst.frc.team1318.robot.Driver;

import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc.team1318.robot.HardwareConstants;
import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.Driver.Buttons.AnalogAxis;
import org.usfirst.frc.team1318.robot.Driver.Buttons.ButtonType;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.BreachPortcullisTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.ClimbingArmLifterUpTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.ClimbingArmElbowUpTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.ClimbingArmShoulderUpTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.ConcurrentTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.DefenseArmPositionTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.DriveDistanceTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.DriveRouteTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.ShooterKickTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.SequentialTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.ShooterSpinUpTask;
import org.usfirst.frc.team1318.robot.Driver.ControlTasks.TurnTask;
import org.usfirst.frc.team1318.robot.Driver.Descriptions.AnalogOperationDescription;
import org.usfirst.frc.team1318.robot.Driver.Descriptions.DigitalOperationDescription;
import org.usfirst.frc.team1318.robot.Driver.Descriptions.MacroOperationDescription;
import org.usfirst.frc.team1318.robot.Driver.Descriptions.OperationDescription;
import org.usfirst.frc.team1318.robot.Driver.Descriptions.UserInputDevice;
import org.usfirst.frc.team1318.robot.Driver.States.AnalogOperationState;
import org.usfirst.frc.team1318.robot.Driver.States.DigitalOperationState;
import org.usfirst.frc.team1318.robot.Driver.States.OperationState;

/**
 * Driver that represents something that operates the robot.  This is either autonomous or teleop/user driver.
 *
 */
public abstract class Driver
{
    @SuppressWarnings("serial")
    protected Map<Operation, OperationDescription> operationSchema = new HashMap<Operation, OperationDescription>()
    {
        {
            put(
                Operation.DriveTrainMoveForward,
                new AnalogOperationDescription(
                    UserInputDevice.Driver,
                    AnalogAxis.Y));
            put(
                Operation.DriveTrainTurn,
                new AnalogOperationDescription(
                    UserInputDevice.Driver,
                    AnalogAxis.X));
            put(
                Operation.DriveTrainSimpleMode,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Toggle));
            put(
                Operation.DriveTrainUsePositionalMode,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Toggle));
            put(
                Operation.DriveTrainLeftPosition,
                new AnalogOperationDescription(
                    UserInputDevice.None,
                    AnalogAxis.None));
            put(
                Operation.DriveTrainRightPosition,
                new AnalogOperationDescription(
                    UserInputDevice.None,
                    AnalogAxis.None));
            put(
                Operation.DriveTrainSwapFrontOrientation,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_TOP_RIGHT_BUTTON,
                    ButtonType.Toggle));
            // Operations for the defense arm
            put(
                Operation.DefenseArmFrontPosition,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_BOTTOM_LEFT_BUTTON,
                    ButtonType.Click));
            put(
                Operation.DefenseArmPortcullisPosition,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_MIDDLE_LEFT_BUTTON,
                    ButtonType.Click));
            put(
                Operation.DefenseArmSallyPortPosition,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_TOP_LEFT_BUTTON,
                    ButtonType.Click));
            put(
                Operation.DefenseArmDrawbridgePosition,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_BOTTOM_RIGHT_BUTTON,
                    ButtonType.Click));
            put(
                Operation.DefenseArmBackPosition,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_BASE_MIDDLE_RIGHT_BUTTON,
                    ButtonType.Click));
            put(
                Operation.DefenseArmMoveForward,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_TOP_LEFT_BUTTON,
                    ButtonType.Simple));
            put(
                Operation.DefenseArmMoveBack,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_BOTTOM_LEFT_BUTTON,
                    ButtonType.Simple));
            put(
                Operation.DefenseArmTakePositionInput,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Simple));
            put(
                Operation.DefenseArmSetAngle,
                new AnalogOperationDescription(
                    UserInputDevice.None,
                    AnalogAxis.None));
            put(
                Operation.ShooterSpeed,
                new AnalogOperationDescription(
                    UserInputDevice.None,
                    AnalogAxis.None));
            put(
                Operation.ShooterSpin,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Click));
            put(
                Operation.ShooterKick,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Click));
            put(
                Operation.IntakeRotatingIn,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_BOTTOM_RIGHT_BUTTON,
                    ButtonType.Simple));
            put(
                Operation.IntakeRotatingOut,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_TOP_RIGHT_BUTTON,
                    ButtonType.Simple));
            put(
                Operation.IntakeExtend,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    0,
                    ButtonType.Click));
            put(
                Operation.IntakeRetract,
                new DigitalOperationDescription(
                    UserInputDevice.Driver,
                    180,
                    ButtonType.Click));
            put(
                Operation.ClimbingArmExtend,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Click));
            put(
                Operation.ClimbingArmRetract,
                new DigitalOperationDescription(
                    UserInputDevice.None,
                    UserInputDeviceButton.NONE,
                    ButtonType.Click));
            put(
                Operation.CancelBreachMacro,
                new DigitalOperationDescription(
                    UserInputDevice.CoDriver,
                    UserInputDeviceButton.BUTTON_PAD_BUTTON_16,
                    ButtonType.Click));
        }
    };

    @SuppressWarnings("serial")
    protected Map<MacroOperation, MacroOperationDescription> macroSchema = new HashMap<MacroOperation, MacroOperationDescription>()
    {
        {            
            // Breaching macros.
            put(
                MacroOperation.BreachPortcullis,
                new MacroOperationDescription(
                    UserInputDevice.CoDriver,
                    UserInputDeviceButton.BUTTON_PAD_BUTTON_1,
                    () -> SequentialTask.Sequence(
                        ConcurrentTask.AllTasks(
                            new DefenseArmPositionTask(HardwareConstants.DEFENSE_ARM_PORTCULLIS_POSITION),
                            new DriveDistanceTask(TuningConstants.PORTCULLIS_OUTER_WORKS_DISTANCE)),
                        new BreachPortcullisTask()),
                    new Operation[]
                    {
                        Operation.DriveTrainRightPosition, 
                        Operation.DriveTrainLeftPosition, 
                        Operation.DefenseArmFrontPosition, 
                        Operation.DriveTrainUsePositionalMode, 
                        Operation.DefenseArmTakePositionInput, 
                        Operation.DefenseArmSetAngle,
                        Operation.CancelBreachMacro,
                    }));
            put(
                MacroOperation.BreachSallyPort,
                new MacroOperationDescription(
                    UserInputDevice.CoDriver,
                    UserInputDeviceButton.BUTTON_PAD_BUTTON_2,
                    () -> SequentialTask.Sequence(
                        ConcurrentTask.AllTasks(
                            new DefenseArmPositionTask(HardwareConstants.DEFENSE_ARM_SALLY_PORT_POSITION),
                            new DriveDistanceTask(TuningConstants.SALLY_PORT_OUTER_WORKS_DISTANCE)),
                        new DefenseArmPositionTask(HardwareConstants.DEFENSE_ARM_SALLY_PORT_POSITION - Math.PI * 0.0625),
                        new DriveRouteTask(
                            (t) -> -0.25 * Math.PI * (TuningConstants.SALLY_PORT_BREACH_BACKWARD_ARC_RADIUS + HardwareConstants.DRIVETRAIN_WHEEL_SEPARATION_DISTANCE) * t,
                            (t) -> -0.25 * Math.PI * TuningConstants.SALLY_PORT_BREACH_BACKWARD_ARC_RADIUS * t,
                            3.0),
                        new DriveRouteTask(
                            (t) -> 0.25 * Math.PI * TuningConstants.SALLY_PORT_BREACH_FORWARD_ARC_RADIUS * t,
                            (t) -> 0.25 * Math.PI * (TuningConstants.SALLY_PORT_BREACH_FORWARD_ARC_RADIUS + HardwareConstants.DRIVETRAIN_WHEEL_SEPARATION_DISTANCE) * t,
                            3.0),
                        new TurnTask(-90),
                        new DriveDistanceTask(TuningConstants.SALLY_PORT_BREACH_FINAL_CHARGE_DISTANCE)),
                    new Operation[]
                    {
                        Operation.DriveTrainUsePositionalMode, 
                        Operation.DriveTrainRightPosition, 
                        Operation.DriveTrainLeftPosition, 
                        Operation.DefenseArmFrontPosition,
                        Operation.CancelBreachMacro,
                    }));
            
            // Macros for shooting distance.
            put(
                MacroOperation.ShootFar,
                new MacroOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_THUMB_BUTTON,
                    () -> SequentialTask.Sequence(
                        new ShooterSpinUpTask(true, TuningConstants.SHOOTER_FAR_SHOT_VELOCITY),
                        new ShooterKickTask()),
                    new Operation[]
                    {
                        Operation.ShooterSpin,
                        Operation.ShooterSpeed,
                        Operation.ShooterKick,
                    }));
            put(
                MacroOperation.ShootClose,
                new MacroOperationDescription(
                    UserInputDevice.Driver,
                    UserInputDeviceButton.JOYSTICK_STICK_TRIGGER_BUTTON,
                    () -> SequentialTask.Sequence(
                            new ShooterSpinUpTask(false, TuningConstants.SHOOTER_CLOSE_SHOT_VELOCITY),
                            new ShooterKickTask()),
                    new Operation[]
                    {
                        Operation.ShooterSpin,
                        Operation.ShooterSpeed,
                        Operation.ShooterKick,
                    }));
            
            // Macros for the climbing arm.
            put(
                MacroOperation.ClimbingArmDeploy,
                new MacroOperationDescription(
                    UserInputDevice.CoDriver,
                    UserInputDeviceButton.BUTTON_PAD_BUTTON_6,
                    () -> SequentialTask.Sequence(
                        new ClimbingArmShoulderUpTask(true),
                        new ClimbingArmElbowUpTask(true)),
                    new Operation[]
                    {
                        Operation.ClimbingArmElbowUp,
                    }));
            put(
                MacroOperation.ClimbingArmRetract,
                new MacroOperationDescription(
                    UserInputDevice.CoDriver,
                    UserInputDeviceButton.BUTTON_PAD_BUTTON_7,
                    () -> SequentialTask.Sequence(
                        new ClimbingArmShoulderUpTask(false),
                        new ClimbingArmElbowUpTask(false)),
                    new Operation[]
                    {
                        Operation.ClimbingArmShoulderUp,
                    }));
            put(
                MacroOperation.ClimbingArmLifterUp,
                new MacroOperationDescription(
                    UserInputDevice.CoDriver,
                    UserInputDeviceButton.BUTTON_PAD_BUTTON_8,
                    () -> new ClimbingArmLifterUpTask(true),
                    new Operation[]
                    {
                        Operation.ClimbingArmExtend,
                        Operation.ClimbingArmRetract,
                    }));
            put(
                MacroOperation.ClimbingArmLifterDown,
                new MacroOperationDescription(
                    UserInputDevice.CoDriver,
                    UserInputDeviceButton.BUTTON_PAD_BUTTON_9,
                    () -> new ClimbingArmLifterUpTask(false),
                    new Operation[]
                    {
                        Operation.ClimbingArmExtend,
                        Operation.ClimbingArmRetract,
                    }));
                }
    };

    protected final Map<Operation, OperationState> operationStateMap;

    /**
     * Initializes a new Driver
     */
    protected Driver()
    {
        this.operationStateMap = new HashMap<Operation, OperationState>(this.operationSchema.size());
        for (Operation operation : this.operationSchema.keySet())
        {
            this.operationStateMap.put(operation, OperationState.createFromDescription(this.operationSchema.get(operation)));
        }
    }

    /**
     * Tell the driver that some time has passed
     */
    public abstract void update();

    /**
     * Tell the driver that operation is stopping
     */
    public abstract void stop();

    /**
     * Get a boolean indicating whether the current digital operation is enabled
     * @param digitalOperation to get
     * @return the current value of the digital operation
     */
    public boolean getDigital(Operation digitalOperation)
    {
        OperationState state = this.operationStateMap.get(digitalOperation);
        if (!(state instanceof DigitalOperationState))
        {
            throw new RuntimeException("not a digital operation!");
        }

        DigitalOperationState digitalState = (DigitalOperationState)state;
        return digitalState.getState();
    }

    /**
     * Get a double between -1.0 and 1.0 indicating the current value of the analog operation
     * @param analogOperation to get
     * @return the current value of the analog operation
     */
    public double getAnalog(Operation analogOperation)
    {
        OperationState state = this.operationStateMap.get(analogOperation);
        if (!(state instanceof AnalogOperationState))
        {
            throw new RuntimeException("not an analog operation!");
        }

        AnalogOperationState analogState = (AnalogOperationState)state;
        return analogState.getState();
    }
}
