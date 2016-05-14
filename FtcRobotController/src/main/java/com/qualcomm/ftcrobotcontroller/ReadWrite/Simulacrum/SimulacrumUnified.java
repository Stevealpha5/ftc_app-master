package com.qualcomm.ftcrobotcontroller.ReadWrite.Simulacrum;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SimulacrumUnified extends LinearOpMode
{

    double left;
    double right;
    double left2;
    double right2;
    double leftStickButton;
    double rightBumperDouble;
    double leftTrig;
    double rightTrig;
    double dpadUp;

    String timeLog;
    String timeLog2;

    int initTime;
    int logTime;
    int runTime;
    int differential;

    DcMotor leftWheels;
    DcMotor rightWheels;
    DcMotor armExtender2;
    DcMotor hangMotor;
    DcMotor liftMotor;
    DcMotor armExtender;

    Servo leftBucket;
    Servo rightBucket;
    Servo collectorDrive;
    Servo collectorDrive2;
    Servo climberCarrierServo;

    SimpleDateFormat time = new SimpleDateFormat("ssSSSS");
    SimpleDateFormat time2 = new SimpleDateFormat("ssSSSS");

    boolean lastCheck = false;
    boolean counter = false;

    boolean Toggle (boolean input)
    {
        if((input != lastCheck) & (input)){
            counter = ! counter;
        }
        lastCheck = input;
        return counter;
    }

    boolean lastCheck2 = false;
    boolean counter2 = false;

    boolean Toggle2 (boolean input)
    {
        if((input != lastCheck2) & (input)){
            counter2 = ! counter2;
        }
        lastCheck2 = input;
        return counter2;
    }

    boolean lastCheck3 = false;
    boolean counter3 = false;

    boolean Toggle3 (boolean input)
    {
        if((input != lastCheck3) & (input)){
            counter3 = ! counter3;
        }
        lastCheck3 = input;
        return counter3;
    }


    public void runOpMode()
    {

        leftWheels = hardwareMap.dcMotor.get("motor_2");
        rightWheels = hardwareMap.dcMotor.get("motor_1");
        armExtender2 = hardwareMap.dcMotor.get("motor_6");
        hangMotor = hardwareMap.dcMotor.get("motor_4");
        liftMotor = hardwareMap.dcMotor.get("motor_3");
        armExtender = hardwareMap.dcMotor.get("motor_5");

        leftBucket = hardwareMap.servo.get("servo_1");
        rightBucket = hardwareMap. servo.get("servo_2");
        collectorDrive = hardwareMap.servo.get("servo_3");
        collectorDrive2 = hardwareMap.servo.get("servo_4");
        climberCarrierServo = hardwareMap.servo.get("servo_5");

        Simulacrum readWriteObj = new Simulacrum("driverLog.txt", leftWheels, rightWheels, armExtender2, liftMotor, armExtender, hangMotor, climberCarrierServo, leftBucket, rightBucket, collectorDrive, collectorDrive2);

        leftWheels.setDirection(DcMotor.Direction.REVERSE);
        rightBucket.setDirection(Servo.Direction.FORWARD);
        armExtender.setDirection(DcMotor.Direction.REVERSE);

        collectorDrive.setDirection(Servo.Direction.REVERSE);
        leftBucket.setDirection(Servo.Direction.REVERSE);
        rightBucket.setDirection(Servo.Direction.FORWARD);

        leftBucket.setPosition(0.5);
        rightBucket.setPosition(0.5);
        collectorDrive.setPosition(0.5);
        collectorDrive2.setPosition(0.5);

        telemetry.addData("Status", "Waiting for input");
        if(Toggle3(gamepad1.a))
        {

            telemetry.addData("Mode", "Record");
            telemetry.addData("Status", "Program started");

            while (!gamepad1.start){
            try {
                readWriteObj.writeInit();
            } catch (IOException e) {
                e.printStackTrace();
                telemetry.addData("Fatal Error", "IOException @ Init");
            }

            left = gamepad1.left_stick_y;
            right = gamepad1.right_stick_y;
            left2 = gamepad2.left_stick_y;
            right2 = gamepad2.right_stick_y;
            leftTrig = gamepad2.left_trigger;
            rightTrig = gamepad2.right_trigger;

            left = Range.clip(left, -1, 1);
            right = Range.clip(right, -1, 1);
            left2 = Range.clip(left2, -1, 1);
            right2 = Range.clip(right2, -1, 1);
            leftTrig = Range.clip(leftTrig, 0, 1);
            rightTrig = Range.clip(rightTrig, 0, 1);

            rightTrig = Range.scale(rightTrig, 0.0, 1.0, 0.51, 1.0);
            leftTrig = Range.scale(leftTrig, 0.0, 1.0, 0.51, 1.0);

            rightTrig = rightTrig * -1;

            leftWheels.setPower(left);
            rightWheels.setPower(right);
            armExtender2.setPower(right2);
            liftMotor.setPower(left2);
            armExtender.setPower(right2);


            if (gamepad2.right_bumper) {

                leftBucket.setPosition(0.8);
                rightBucket.setPosition(0.8);
                rightBumperDouble = 0.8;

            } else {

                leftBucket.setPosition(0.5);
                rightBucket.setPosition(0.5);
                rightBumperDouble = 0.5;

            }

            if (Toggle(gamepad1.right_stick_button)) {

                collectorDrive.setDirection(Servo.Direction.REVERSE);
                collectorDrive2.setDirection(Servo.Direction.FORWARD);
                collectorDrive.setPosition(1.0);
                collectorDrive2.setPosition(1.0);
                leftStickButton = 1.0;

            } else if (Toggle2(gamepad1.left_stick_button)) {

                collectorDrive.setDirection(Servo.Direction.FORWARD);
                collectorDrive2.setDirection(Servo.Direction.REVERSE);
                collectorDrive.setPosition(1.0);
                collectorDrive2.setPosition(1.0);
                leftStickButton = 0.0;

            } else {

                collectorDrive.setPosition(0.5);
                collectorDrive2.setPosition(0.5);
                leftStickButton = 0.5;

            }

            if (gamepad1.dpad_up) {
                climberCarrierServo.setPosition(1.0);
                dpadUp = 1.0;
            } else {
                climberCarrierServo.setPosition(0.0);
                dpadUp = 0.0;
            }

            if (leftTrig > 0.55) {
                hangMotor.setPower(leftTrig);
            } else if (rightTrig < -0.55) {
                hangMotor.setPower(rightTrig);
            } else {
                hangMotor.setPower(0);
            }


            try {
                timeLog = time.format(Calendar.getInstance().getTime());
                initTime = (Integer.parseInt(timeLog));
                telemetry.addData("Init time", initTime);
                readWriteObj.writeLogFile("L1", left);
                readWriteObj.writeLogFile("R1", right);
                readWriteObj.writeLogFile("L2", left2);
                readWriteObj.writeLogFile("R2", right2);
                readWriteObj.writeLogFile("RB", rightBumperDouble);
                readWriteObj.writeLogFile("LS", leftStickButton);
                readWriteObj.writeLogFile("DU", dpadUp);
                readWriteObj.writeLogFile("LT", leftTrig);
                readWriteObj.writeLogFile("RT", rightTrig);
                timeLog2 = time2.format(Calendar.getInstance().getTime());
                logTime = (Integer.parseInt(timeLog2));

                runTime = logTime - initTime;

                differential = 18 - runTime;

                if (differential > 0) {
                    try {
                        Thread.sleep(differential);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    telemetry.addData("Notice", "Differential limit exceeded");
                }

            } catch (IOException e) {
                e.printStackTrace();
                telemetry.addData("Fatal Error", "IOException @ Write");
            }

            telemetry.addData("Differential", differential);
            telemetry.addData("Run time", runTime);
        }

        }else
        {

            telemetry.addData("Mode", "Replay");
            try {
                readWriteObj.readFromLog(4);
            } catch (IOException e) {
                e.printStackTrace();
            }
            telemetry.addData("Status", "Program Complete");

        }

    }

}
