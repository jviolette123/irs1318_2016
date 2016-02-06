package org.usfirst.frc.team1318.robot.Driver.ControlTasks;

import org.usfirst.frc.team1318.robot.HardwareConstants;
import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.DriveTrain.DriveTrainComponent;
import org.usfirst.frc.team1318.robot.Driver.Operation;

/**
 * @author Preston and Corbin
 * This class is a task designed to automatically drive the robot through the portcullis.
 */
public class BreachPortcullisTask extends ControlTaskBase
{
    private double startDTDistanceRight;
    private double startDTDistanceLeft;

    private double desiredDTDistanceRight;
    private double desiredDTDistanceLeft;

    private double currentDTDistanceRight;
    private double currentDTDistanceLeft;

    private DriveTrainComponent driveTrain;

    public BreachPortcullisTask()
    {
    }

    @Override
    public void begin()
    {
        this.driveTrain = this.getComponents().getDriveTrain();
        
        // Log the starting distance of the encoders (for the drive train)
        this.startDTDistanceRight = this.driveTrain.getRightEncoderDistance();
        this.startDTDistanceLeft = this.driveTrain.getLeftEncoderDistance();

        // Set the desired position for the drive train
        this.desiredDTDistanceRight = this.startDTDistanceRight + TuningConstants.PORTCULLIS_BREACH_DISTANCE;
        this.desiredDTDistanceLeft = this.startDTDistanceLeft + TuningConstants.PORTCULLIS_BREACH_DISTANCE;
        
        // Reset the defense arm
        this.setDigitalOperationState(Operation.DefenseArmFrontPosition, true);
        
        // Set necessary operations to true
        this.setDigitalOperationState(Operation.DriveTrainUsePositionalMode, true);
        this.setDigitalOperationState(Operation.DefenseArmTakePositionInput, true);
    }

    @Override
    public void update()
    {
        // Find current encoder distances
        this.currentDTDistanceRight = this.driveTrain.getRightEncoderDistance();
        this.currentDTDistanceLeft = this.driveTrain.getLeftEncoderDistance();
        
        // Move the drive train by the PORTCULLIS_BREACH_ITERATIVE constant
        this.setAnalogOperationState(Operation.DriveTrainRightPosition, this.currentDTDistanceRight
            + TuningConstants.PORTCULLIS_BREACH_ITERATIVE);
        this.setAnalogOperationState(Operation.DriveTrainLeftPosition, this.currentDTDistanceLeft
            + TuningConstants.PORTCULLIS_BREACH_ITERATIVE);
        
        // Find distance traveled by both right and left wheels since macro started
        double traveledRightDistance = this.currentDTDistanceRight - this.startDTDistanceRight;
        double traveledLeftDistance = this.currentDTDistanceLeft - this.startDTDistanceLeft;
        
        // Find average of right and left traveled distance (both left and right should be the same theoretically);
        double traveledDistance = (traveledRightDistance + traveledLeftDistance) / 2;
        
        // Find the desired Arm Angle in radians
        double armAngle = Math.acos((traveledDistance - HardwareConstants.DEFENSE_ARM_LENGTH)
            / HardwareConstants.DEFENSE_ARM_LENGTH);
        
        // Set the desired arm angle converted to ticks
        this.setAnalogOperationState(Operation.DefenseArmSetAngle, armAngle * TuningConstants.DEFENSE_ARM_RADIANS_TO_TICKS);
    }

    @Override
    public void stop()
    {
        // Disable positional modes for drive train and defense arm
        this.setDigitalOperationState(Operation.DriveTrainUsePositionalMode, false);
        this.setDigitalOperationState(Operation.DefenseArmTakePositionInput, false);
    }

    @Override
    public void end()
    {
        // Disable positional modes for drive train and defense arm
        this.setDigitalOperationState(Operation.DriveTrainUsePositionalMode, false);
        this.setDigitalOperationState(Operation.DefenseArmTakePositionInput, false);
        
        // Return defense arm to the front of the robot
        this.setDigitalOperationState(Operation.DefenseArmFrontPosition, true);
    }

    @Override
    public boolean hasCompleted()
    {
        // Check that the distance the robot has traveled (with the purpose of returning true if the desired position has been met)
        return this.currentDTDistanceRight >= this.desiredDTDistanceRight && this.currentDTDistanceLeft >= this.desiredDTDistanceLeft;
    }

}
