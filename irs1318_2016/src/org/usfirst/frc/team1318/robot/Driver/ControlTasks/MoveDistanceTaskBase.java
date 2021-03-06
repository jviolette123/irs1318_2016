package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.Driver.IControlTask;
import org.usfirst.frc.team1318.robot.Driver.Operation;

/**
 * Abstract class defining a task that moves the robot a certain distance using Positional PID.
 * 
 */
public abstract class MoveDistanceTaskBase extends ControlTaskBase implements IControlTask
{
    private final boolean resetPositionalOnEnd;

    protected double startLeftEncoderDistance;
    protected double startRightEncoderDistance;

    protected double desiredFinalLeftEncoderDistance;
    protected double desiredFinalRightEncoderDistance;

    /**
     * Initializes a new MoveDistanceTaskBase
     * @param resetPositionalOnEnd
     */
    protected MoveDistanceTaskBase(boolean resetPositionalOnEnd)
    {
        this.resetPositionalOnEnd = resetPositionalOnEnd;
    }

    /**
     * Begin the current task
     */
    @Override
    public void begin()
    {
        // get the start location
        this.startLeftEncoderDistance = this.getComponents().getDriveTrain().getLeftEncoderDistance();
        this.startRightEncoderDistance = this.getComponents().getDriveTrain().getRightEncoderDistance();

        // calculate the desired end location
        this.determineFinalEncoderDistance();
    }

    /**
     * Determine the final encoder distance
     */
    protected abstract void determineFinalEncoderDistance();

    /**
     * Run an iteration of the current task and apply any control changes
     */
    @Override
    public void update()
    {
        this.setDigitalOperationState(Operation.DriveTrainUsePositionalMode, true);
        this.setAnalogOperationState(Operation.DriveTrainLeftPosition, this.desiredFinalLeftEncoderDistance);
        this.setAnalogOperationState(Operation.DriveTrainRightPosition, this.desiredFinalRightEncoderDistance);
    }

    /**
     * Cancel the current task and clear control changes
     */
    @Override
    public void stop()
    {
        this.setDigitalOperationState(Operation.DriveTrainUsePositionalMode, false);
        this.setAnalogOperationState(Operation.DriveTrainLeftPosition, 0.0);
        this.setAnalogOperationState(Operation.DriveTrainRightPosition, 0.0);
    }

    /**
     * End the current task and reset control changes appropriately
     */
    @Override
    public void end()
    {
        if (this.resetPositionalOnEnd)
        {
            this.setDigitalOperationState(Operation.DriveTrainUsePositionalMode, false);
            this.setAnalogOperationState(Operation.DriveTrainLeftPosition, 0.0);
            this.setAnalogOperationState(Operation.DriveTrainRightPosition, 0.0);
        }
    }

    /**
     * Checks whether this task has completed, or whether it should continue being processed
     * @return true if we should continue onto the next task, otherwise false (to keep processing this task)
     */
    @Override
    public boolean hasCompleted()
    {
        double leftEncoderDistance = this.getComponents().getDriveTrain().getLeftEncoderDistance();
        double rightEncoderDistance = this.getComponents().getDriveTrain().getRightEncoderDistance();

        // check how far away we are from the desired end location
        double leftDelta = Math.abs(this.desiredFinalLeftEncoderDistance - leftEncoderDistance);
        double rightDelta = Math.abs(this.desiredFinalRightEncoderDistance - rightEncoderDistance);

        // return that we have completed this task if are within an acceptable distance
        // from the desired end location for both left and right. 
        return leftDelta < TuningConstants.DRIVETRAIN_POSITIONAL_ACCEPTABLE_DELTA &&
            rightDelta < TuningConstants.DRIVETRAIN_POSITIONAL_ACCEPTABLE_DELTA;
    }
}
