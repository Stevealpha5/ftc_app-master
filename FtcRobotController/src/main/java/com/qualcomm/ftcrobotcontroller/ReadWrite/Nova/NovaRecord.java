package com.qualcomm.ftcrobotcontroller.ReadWrite.Nova;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.io.IOException;

public class NovaRecord extends OpMode
{
    MotorGhost rightDrive;
    MotorGhost leftDrive;
    MotorGhost liftMotor;
    MotorGhost hangMotor;
    MotorGhost armExtendor;
    MotorGhost armExtendor2;

    ServoGhost leftBucket;
    ServoGhost rightBucket;
    ServoGhost collertorDrive;
    ServoGhost collertorDrive2;
    ServoGhost climberCarrier;
    ServoGhost leftClimber;
    ServoGhost rightClimber;

    public String fileName = "novaDriverLog.txt";

    Nova nova = new Nova(fileName, 6, 7, telemetry);

    @Override
    public void init()
    {
        rightDrive = new MotorGhost("RD", 1);
        leftDrive = new MotorGhost("LD", 2);
        liftMotor = new MotorGhost("LM", 3);
        hangMotor = new MotorGhost("HM", 4);
        armExtendor = new MotorGhost("AE", 5);
        armExtendor2 = new MotorGhost("Ae", 6);

        leftBucket = new ServoGhost("LB", 1);
        rightBucket = new ServoGhost("RB", 2);
        collertorDrive = new ServoGhost("CD", 3);
        collertorDrive2 = new ServoGhost("Cd", 4);
        climberCarrier = new ServoGhost("CC", 5);
        leftClimber = new ServoGhost("LC", 6);
        rightClimber = new ServoGhost("RC", 7);

        nova.addMotorGhost(rightDrive);
        nova.addMotorGhost(leftDrive);
        nova.addMotorGhost(liftMotor);
        nova.addMotorGhost(hangMotor);
        nova.addMotorGhost(armExtendor);
        nova.addMotorGhost(armExtendor2);

        nova.addServoGhost(leftBucket);
        nova.addServoGhost(rightBucket);
        nova.addServoGhost(collertorDrive);
        nova.addServoGhost(collertorDrive2);
        nova.addServoGhost(climberCarrier);
        nova.addServoGhost(leftClimber);
        nova.addServoGhost(rightClimber);

        nova.mapGhosts(hardwareMap);

        leftDrive.motor.setDirection(DcMotor.Direction.REVERSE);
        armExtendor.motor.setDirection(DcMotor.Direction.REVERSE);

        leftBucket.servo.setDirection(Servo.Direction.REVERSE);
        rightBucket.servo.setDirection(Servo.Direction.FORWARD);

        leftClimber.servo.setDirection(Servo.Direction.REVERSE);
        rightClimber.servo.setDirection(Servo.Direction.FORWARD);


        try
        {
            nova.writeInit();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void loop()
    {
        nova.powerGhost(rightDrive, gamepad1.right_stick_y);
        nova.powerGhost(leftDrive, gamepad1.left_stick_y);

        nova.powerGhost(liftMotor, gamepad2.right_stick_y);
        nova.powerGhost(armExtendor, gamepad2.left_stick_y);
        nova.powerGhost(armExtendor2, gamepad2.left_stick_y);

        nova.powerGhost(hangMotor, gamepad2.a, gamepad2.b, gamepad2.right_trigger);

        nova.powerGhost(leftBucket, gamepad2.right_bumper, 0.5, 0.8);
        nova.powerGhost(rightBucket, gamepad2.right_bumper, 0.5, 0.8);

        nova.powerGhost(collertorDrive, gamepad1.right_stick_button, 0.5, 0.0);
        nova.powerGhost(collertorDrive2, gamepad1.right_stick_button, 0.5, 1.0);
/*
        nova.powerGhost(collertorDrive, gamepad1.left_stick_button, 0.5, 1.0);
        nova.powerGhost(collertorDrive2, gamepad1.left_stick_button, 0.5, 0.0);
*/
        nova.powerGhost(climberCarrier, gamepad1.dpad_up, 1.0, 0.0);

        nova.powerGhost(rightClimber, gamepad1.dpad_right, 0.05, 0.65);
        nova.powerGhost(leftClimber, gamepad1.dpad_left, 0.05, 0.65);

        //telemetry.addData("run time", getRuntime());
        try
        {
            nova.writeLogFile();
        }catch(IOException e)
        {
            e.printStackTrace();
        }

        resetStartTime();
    }
}
