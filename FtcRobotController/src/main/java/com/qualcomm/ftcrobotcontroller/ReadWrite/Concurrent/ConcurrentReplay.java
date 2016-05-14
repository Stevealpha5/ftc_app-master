package com.qualcomm.ftcrobotcontroller.ReadWrite.Concurrent;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.io.IOException;

public class ConcurrentReplay extends LinearOpMode
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
    public void runOpMode() throws InterruptedException
    {
        waitForStart();

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



        try {
            concurrent.writeToMotors(4, telemetry); //Increase to add run length (lol), decrease to reduce run length
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
