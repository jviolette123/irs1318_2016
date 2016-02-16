package org.usfirst.frc.team1318.robot.ClimbingArm;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.Common.IController;
import org.usfirst.frc.team1318.robot.Driver.Driver;
import org.usfirst.frc.team1318.robot.Driver.Operation;

import edu.wpi.first.wpilibj.DigitalInput;

public class ClimbingArmController implements IController
{
    private ClimbingArmComponent climbingArm;
    private Driver driver;
    
    public ClimbingArmController(ClimbingArmComponent climbingArm)
    {
        this.climbingArm = climbingArm;
    }

    @Override
    public void update()
    {        
        boolean topLimitSwitch = this.climbingArm.getTopLimitSwitch();
        boolean bottomLimitSwitch = this.climbingArm.getBottomLimitSwitch();
        
        // The operations for raising up and standing will probably only be used in macros, so it should be fine to have it like this
        // The toggle for the side solenoid
        if (this.driver.getDigital(Operation.ClimbingArmShoulderUp))
        {
            this.climbingArm.extendShoulderSolenoid(true);
        }
        else
        {
            this.climbingArm.extendShoulderSolenoid(false);
        }
        
        // The toggle for the arm solenoid
        if (this.driver.getDigital(Operation.ClimbingArmElbowUp))
        {
            this.climbingArm.extendElbowSolenoid(true);
        }
        else
        {
            this.climbingArm.extendElbowSolenoid(false);
        }
        
        // Extend climbing arm, retract climbing arm, or stop climbing arm when appropriate
        double currentSpeed = 0.0;
        if (this.driver.getDigital(Operation.ClimbingArmExtend))
        {
            currentSpeed = TuningConstants.CLIMBING_ARM_MAX_SPEED;
        }
        else if (this.driver.getDigital(Operation.ClimbingArmRetract))
        {
            currentSpeed = -TuningConstants.CLIMBING_ARM_MAX_SPEED;
        }
        
        // Check to see if the nut has reached the top of the lead screw, and set the motor speed to 0 if it has.
        if (topLimitSwitch && currentSpeed > 0.0)
        {
            currentSpeed = 0.0;
        }
        
        // Check to see if the nut has reached the bottom of the lead screw, and set the motor speed to 0 if it has.
        if (bottomLimitSwitch && currentSpeed < 0.0)
        {
            currentSpeed = 0.0;
        }
        
        this.climbingArm.setClimbingSpeed(currentSpeed);
    }

    @Override
    public void stop()
    {
        this.climbingArm.stop();
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }
}