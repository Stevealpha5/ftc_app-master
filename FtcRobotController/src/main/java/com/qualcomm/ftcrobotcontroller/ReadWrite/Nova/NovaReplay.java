package com.qualcomm.ftcrobotcontroller.ReadWrite.Nova;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.io.IOException;

public class NovaReplay extends LinearOpMode
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
    public void runOpMode() throws InterruptedException
    {
        waitForStart();

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



        try {
            nova.writeToMotors(gamepad1.guide); //Increase to add run length (lol), decrease to reduce run length
        } catch (IOException e) {
            e.printStackTrace();
            telemetry.addData("Fatal Error", "IOException @ Read");
        }catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        telemetry.addData("Status", "Program Complete");

    }

}
