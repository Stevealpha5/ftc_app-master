package com.qualcomm.ftcrobotcontroller.ReadWrite.Concurrent;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.io.IOException;

public class ConcurrentRecord extends OpMode
{
    MotorGhostConcurrent rightDrive;
    MotorGhostConcurrent leftDrive;
    MotorGhostConcurrent liftMotor;
    MotorGhostConcurrent hangMotor;
    MotorGhostConcurrent armExtendor;
    MotorGhostConcurrent armExtendor2;

    ServoGhostConcurrent leftBucket;
    ServoGhostConcurrent rightBucket;
    ServoGhostConcurrent collertorDrive;
    ServoGhostConcurrent collertorDrive2;
    ServoGhostConcurrent climberCarrier;
    ServoGhostConcurrent leftClimber;
    ServoGhostConcurrent rightClimber;

    public String fileName = "novaDriverLog.txt";

    Concurrent concurrent = new Concurrent(fileName, 6, 7);

    @Override
    public void init()
    {
        rightDrive = new MotorGhostConcurrent("RD", 1);
        leftDrive = new MotorGhostConcurrent("LD", 2);
        liftMotor = new MotorGhostConcurrent("LM", 3);
        hangMotor = new MotorGhostConcurrent("HM", 4);
        armExtendor = new MotorGhostConcurrent("AE", 5);
        armExtendor2 = new MotorGhostConcurrent("Ae", 6);

        leftBucket = new ServoGhostConcurrent("LB", 1);
        rightBucket = new ServoGhostConcurrent("RB", 2);
        collertorDrive = new ServoGhostConcurrent("CD", 3);
        collertorDrive2 = new ServoGhostConcurrent("Cd", 4);
        climberCarrier = new ServoGhostConcurrent("CC", 5);
        leftClimber = new ServoGhostConcurrent("LC", 6);
        rightClimber = new ServoGhostConcurrent("RC", 7);

        concurrent.addMotorGhost(rightDrive);
        concurrent.addMotorGhost(leftDrive);
        concurrent.addMotorGhost(liftMotor);
        concurrent.addMotorGhost(hangMotor);
        concurrent.addMotorGhost(armExtendor);
        concurrent.addMotorGhost(armExtendor2);

        concurrent.addServoGhost(leftBucket);
        concurrent.addServoGhost(rightBucket);
        concurrent.addServoGhost(collertorDrive);
        concurrent.addServoGhost(collertorDrive2);
        concurrent.addServoGhost(climberCarrier);
        concurrent.addServoGhost(leftClimber);
        concurrent.addServoGhost(rightClimber);

        concurrent.mapGhosts(hardwareMap);

        leftDrive.motor.setDirection(DcMotor.Direction.REVERSE);
        armExtendor.motor.setDirection(DcMotor.Direction.REVERSE);

        leftBucket.servo.setDirection(Servo.Direction.REVERSE);
        rightBucket.servo.setDirection(Servo.Direction.FORWARD);

        leftClimber.servo.setDirection(Servo.Direction.REVERSE);
        rightClimber.servo.setDirection(Servo.Direction.FORWARD);


        try
        {
            concurrent.writeInit();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void loop()
    {
        concurrent.powerGhost(rightDrive, gamepad1.right_stick_y);
        concurrent.powerGhost(leftDrive, gamepad1.left_stick_y);

        concurrent.powerGhost(liftMotor, gamepad2.right_stick_y);
        concurrent.powerGhost(armExtendor, gamepad2.left_stick_y);
        concurrent.powerGhost(armExtendor2, gamepad2.left_stick_y);

        concurrent.powerGhost(hangMotor, gamepad2.a, gamepad2.b, gamepad2.right_trigger);

        concurrent.powerGhost(leftBucket, gamepad2.right_bumper, 0.5, 0.8);
        concurrent.powerGhost(rightBucket, gamepad2.right_bumper, 0.5, 0.8);

        concurrent.powerGhost(collertorDrive, gamepad1.right_stick_button, 0.5, 0.0);
        concurrent.powerGhost(collertorDrive2, gamepad1.right_stick_button, 0.5, 1.0);
/*
        concurrent.powerGhost(collertorDrive, gamepad1.left_stick_button, 0.5, 1.0);
        concurrent.powerGhost(collertorDrive2, gamepad1.left_stick_button, 0.5, 0.0);
*/
        concurrent.powerGhost(climberCarrier, gamepad1.dpad_up, 1.0, 0.0);

        concurrent.powerGhost(rightClimber, gamepad1.dpad_right, 0.05, 0.65);
        concurrent.powerGhost(leftClimber, gamepad1.dpad_left, 0.05, 0.65);

        //telemetry.addData("run time", getRuntime());
        try
        {
            concurrent.writeLogFile(4, telemetry);
        }catch(IOException e)
        {
            e.printStackTrace();
        }

        resetStartTime();
    }
}
