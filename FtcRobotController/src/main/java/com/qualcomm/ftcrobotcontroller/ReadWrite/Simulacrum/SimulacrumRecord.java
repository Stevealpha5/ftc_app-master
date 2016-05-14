package com.qualcomm.ftcrobotcontroller.ReadWrite.Simulacrum;

import android.annotation.SuppressLint;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Records controller input and writes it to a log-file stored in the controller
 * phone's internal memory, to be later replayed by the SimulacrumReplay class.
 * @see Simulacrum
 * @see SimulacrumReplay
 */
public class SimulacrumRecord extends OpMode
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

    public String fileName = "driverLog.txt";

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
    Servo leftClimberServo;
    Servo rightClimberServo;

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

    Simulacrum readWriteObj = new Simulacrum(fileName, leftWheels, rightWheels, armExtender2, liftMotor, armExtender, hangMotor, climberCarrierServo, leftBucket, rightBucket, collectorDrive, collectorDrive2);

    /**
     * Initializes hardware devices and ties them to the hardware map.
     * Also sets starting directions and initialization positions for servos and motors.
     * Initializes a new Simulacrum object.
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode
     */
    @SuppressLint("SimpleDateFormat")
    public void init()
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
        leftClimberServo = hardwareMap.servo.get("servo_6");
        rightClimberServo = hardwareMap.servo.get("servo_7");

        leftWheels.setDirection(DcMotor.Direction.REVERSE);
        armExtender.setDirection(DcMotor.Direction.REVERSE);

        leftBucket.setDirection(Servo.Direction.REVERSE);
        rightBucket.setDirection(Servo.Direction.FORWARD);

        leftClimberServo.setDirection(Servo.Direction.REVERSE);
        rightClimberServo.setDirection(Servo.Direction.FORWARD);

        leftBucket.setPosition(0.5);
        rightBucket.setPosition(0.5);
        collectorDrive.setPosition(0.5);
        collectorDrive2.setPosition(0.5);

        rightClimberServo.setPosition(0.05);
        leftClimberServo.setPosition(0.05);

        climberCarrierServo.setPosition(1.0);

        try {
            readWriteObj.writeInit();
        } catch (IOException e) {
            e.printStackTrace();
            telemetry.addData("Fatal Error", "IOException @ Init");
        }

    }

    /**
     * Performs the same function as DriverControl, but additionally records all control inputs to a log-file,
     * to be read later by the SimulacrumReplay method.
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode
     */
    public void loop()
    {

        left = gamepad1.left_stick_y;
        right = gamepad1.right_stick_y;
        left2 = gamepad2.left_stick_y;
        right2 = gamepad2.right_stick_y;
        leftTrig = 0;
        rightTrig = 0;

        rightTrig = rightTrig * -1;

        leftWheels.setPower(left);
        rightWheels.setPower(right);
        armExtender2.setPower(right2);
        liftMotor.setPower(left2);
        armExtender.setPower(right2);



        if(gamepad2.right_bumper)
        {

            leftBucket.setPosition(0.8);
            rightBucket.setPosition(0.8);
            rightBumperDouble = 0.8;

        }else
        {

            leftBucket.setPosition(0.5);
            rightBucket.setPosition(0.5);
            rightBumperDouble = 0.5;

        }

        if(Toggle(gamepad1.right_stick_button))
        {

            collectorDrive.setDirection(Servo.Direction.REVERSE);
            collectorDrive2.setDirection(Servo.Direction.FORWARD);
            collectorDrive.setPosition(1.0);
            collectorDrive2.setPosition(1.0);
            leftStickButton = 1.0;

        }else if(Toggle2(gamepad1.left_stick_button))
        {

            collectorDrive.setDirection(Servo.Direction.FORWARD);
            collectorDrive2.setDirection(Servo.Direction.REVERSE);
            collectorDrive.setPosition(1.0);
            collectorDrive2.setPosition(1.0);
            leftStickButton = 0.0;

        }else
        {

            collectorDrive.setPosition(0.5);
            collectorDrive2.setPosition(0.5);
            leftStickButton = 0.5;

        }

        if(gamepad1.dpad_up) {
            climberCarrierServo.setPosition(0.0);
            dpadUp = 0.0;
        }else {
            climberCarrierServo.setPosition(1.0);
            dpadUp = 1.0;
        }

        if(leftTrig > 0) {
            hangMotor.setPower(leftTrig);
        }else if(rightTrig < 0) {
            hangMotor.setPower(rightTrig);
        }else {
            hangMotor.setPower(0);
        }


        try {
            timeLog = time.format(Calendar.getInstance().getTime());
            initTime = (Integer.parseInt(timeLog));
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

            differential = 26 - runTime;  //Increase to reduce run length (lol), decrease to increase run length

            if(differential > 0)
            {
                try {
                    Thread.sleep(differential);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                telemetry.addData("Notice", "Differential limit exceeded");
            }

        } catch (IOException e) {
            e.printStackTrace();
            telemetry.addData("Fatal Error", "IOException @ Write");
        }

        telemetry.addData("Run time", runTime);
        //Your mom's a sadist! -Good Lord Jesus Tits, 2016

    }

}
