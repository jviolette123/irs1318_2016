package org.usfirst.frc.team1318.robot;

import org.usfirst.frc.team1318.robot.Compressor.CompressorComponent;
import org.usfirst.frc.team1318.robot.DefenseArm.DefenseArmComponent;
import org.usfirst.frc.team1318.robot.DriveTrain.DriveTrainComponent;
import org.usfirst.frc.team1318.robot.Shooter.ShooterComponent;
import org.usfirst.frc.team1318.robot.TestMechanism.TestComponent;

public class ComponentManager
{
    private CompressorComponent compressorComponent;
    private DriveTrainComponent driveTrainComponent;
    private TestComponent testComponent;
    private DefenseArmComponent defenseArmComponent;
    private ShooterComponent shooterComponent;

    public ComponentManager()
    {
        this.compressorComponent = new CompressorComponent();
        this.testComponent = new TestComponent();
        this.defenseArmComponent = new DefenseArmComponent();
        this.shooterComponent = new ShooterComponent();
    }

    public CompressorComponent getCompressor()
    {
        return this.compressorComponent;
    }

    public DriveTrainComponent getDriveTrain()
    {
        return this.driveTrainComponent;
    }

    public TestComponent getTest()
    {
        return this.testComponent;
    }

    public DefenseArmComponent getDefenseArm()
    {
        return this.defenseArmComponent;
    }
    
    public ShooterComponent getShooterComponent() 
    {
        return this.shooterComponent;
    }
}
