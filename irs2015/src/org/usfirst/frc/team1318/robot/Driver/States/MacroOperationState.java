package org.usfirst.frc.team1318.robot.Driver.States;

import org.usfirst.frc.team1318.robot.Driver.Operation;
import org.usfirst.frc.team1318.robot.Driver.Buttons.ClickButton;
import org.usfirst.frc.team1318.robot.Driver.Buttons.IButton;
import org.usfirst.frc.team1318.robot.Driver.Descriptions.DigitalOperationDescription;
import org.usfirst.frc.team1318.robot.Driver.Descriptions.MacroOperationDescription;
import org.usfirst.frc.team1318.robot.Driver.Macros.MacroTask;

import edu.wpi.first.wpilibj.Joystick;

public class MacroOperationState extends OperationState
{
    private final IButton button;
    private MacroTask task;
    private boolean isActive;

    public MacroOperationState(MacroOperationDescription description)
    {
        super(description);

        this.button = new ClickButton();
        this.task = null;
        this.isActive = false;
    }

    /**
     * Sets whether the current operation is being interrupted by a macro
     * @param enable value of true indicates that we are interrupted
     */
    @Override
    public void setIsInterrupted(boolean enable)
    {
        if (enable)
        {
            this.isActive = false;
        }
    }

    /**
     * Gets whether the current operation is being interrupted by a macro
     * @return value of true indicates that we are interrupted
     */
    @Override
    public boolean getIsInterrupted()
    {
        return false;
    }

    /**
     * Checks whether the operation state should change based on the driver and co-driver joysticks. 
     * @param driver joystick to update from
     * @param coDriver joystick to update from
     * @return true if there was any active user input that triggered a state change
     */
    @Override
    public boolean checkUserInput(Joystick driver, Joystick coDriver)
    {
        DigitalOperationDescription description = (DigitalOperationDescription)this.getDescription();

        Joystick relevantJoystick;
        int relevantButton;
        switch (description.getUserInputDevice())
        {
            case None:
                return false;

            case Driver:
                relevantJoystick = driver;
                break;

            case CoDriver:
                relevantJoystick = coDriver;
                break;

            default:
                throw new RuntimeException("unexpected user input device " + description.getUserInputDevice().toString());
        }

        relevantButton = description.getUserInputDeviceButton();

        boolean buttonPressed = relevantJoystick.getRawButton(relevantButton);
        this.button.updateState(buttonPressed);

        if (this.button.isActivated())
        {
            this.isActive = !this.isActive;
        }

        return buttonPressed;
    }

    public Operation[] getAffectedOperations()
    {
        return ((MacroOperationDescription)this.getDescription()).getAffectedOperations();
    }

    public boolean getIsActive()
    {
        return this.isActive;
    }

    public void run()
    {
        if (this.isActive)
        {
            if (this.task == null)
            {
                // start task
                this.task = ((MacroOperationDescription)this.getDescription()).constructMacroTask();
                this.task.begin();
            }
            else
            {
                if (this.task.hasCompleted())
                {
                    this.task.end();
                    this.task = null;
                    this.isActive = false;
                }
                else
                {
                    this.task.update();
                }
            }
        }
        else if (this.task != null)
        {
            // cancel task:
            this.task.stop();
            this.task = null;
        }
    }
}