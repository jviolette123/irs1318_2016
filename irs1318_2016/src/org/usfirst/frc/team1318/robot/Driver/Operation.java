package org.usfirst.frc.team1318.robot.Driver;

public enum Operation
{
    // DriveTrain operations:
    DriveTrainMoveForward,
    DriveTrainTurn,
    DriveTrainShiftGearUp,
    DriveTrainShiftGearDown,
    DriveTrainSimpleMode,
    DriveTrainUsePositionalMode,
    DriveTrainLeftPosition,
    DriveTrainRightPosition,
    DriveTrainSwapFrontOrientation,
    // Defense arm operations:
    DefenseArmFrontPosition,
    DefenseArmPortcullisPosition,
    DefenseArmDrawbridgePosition,
    DefenseArmSallyPortPosition,
    DefenseArmBackPosition,
    DefenseArmMoveBack,
    DefenseArmMoveForward,
    DefenseArmTakePositionInput,
    DefenseArmSetAngle,
    // Shooter operations:
    ShooterSpeed,
    ShooterSpin,
    ShooterKick,
    ShooterExtendHood,
    // Intake operations:
    IntakeRotatingIn,
    IntakeRotatingOut,
    IntakeExtend,
    IntakeRetract,
    //Climbing Arm Operation
    ClimbingArmExtend,
    ClimbingArmRetract,
    ClimbingArmShoulderUp,
    ClimbingArmElbowUp;
    
}