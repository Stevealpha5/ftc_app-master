package com.qualcomm.ftcrobotcontroller.ghettoOpmodes;


import com.qualcomm.ftcrobotcontroller.Utilities.Synchronizer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import java.util.Timer;
import java.util.TimerTask;

public class DriverControl extends OpMode
{

    double left;
    double right;
    double left2;
    double right2;
    double leftTrig;
    double rightTrig;
    double left2Trig;

    boolean rightBumper;
    boolean leftBumper;
    boolean buttonX;
    boolean buttonY;
    boolean dpadLeft;
    boolean dpadRight;

    DcMotor leftWheel;
    DcMotor rightWheel;
    DcMotor leftLift;
    DcMotor rightLift;
    DcMotor liftExtender;
    DcMotor gathererDrive;

    Servo leftBrake;
    Servo rightBrake;
    Servo leftAllClear;
    Servo rightAllClear;
    Servo actuatorPlate;
    Servo leftKnockyThing;
    Servo rightKnockyThing;

    /*boolean lastCheck = false;
    boolean counter = false;

    boolean toggle (boolean input){
        if((input != lastCheck) & (input)){
            counter = ! counter;
        }
        lastCheck = input;
        return counter;
    }*/

    Timer brakeTimer;
    TimerTask brakeDeactivate;

    @Override
    public void init()
    {

        hardwareMap.logDevices();

        leftWheel = hardwareMap.dcMotor.get("motor_1");
        rightWheel = hardwareMap.dcMotor.get("motor_2");
        leftLift = hardwareMap.dcMotor.get("motor_3");
        rightLift = hardwareMap.dcMotor.get("motor_4");
        liftExtender = hardwareMap.dcMotor.get("motor_5");
        gathererDrive = hardwareMap.dcMotor.get("motor_6");

        leftBrake = hardwareMap.servo.get("servo_1");
        rightBrake = hardwareMap.servo.get("servo_2");
        leftAllClear = hardwareMap.servo.get("servo_5");
        rightAllClear = hardwareMap.servo.get("servo_4");
        actuatorPlate = hardwareMap.servo.get("servo_3");
        leftKnockyThing = hardwareMap.servo.get("servo_7");
        rightKnockyThing = hardwareMap.servo.get("servo_8");

        rightWheel.setDirection(DcMotor.Direction.REVERSE);
        rightLift.setDirection(DcMotor.Direction.REVERSE);
        rightBrake.setDirection(Servo.Direction.REVERSE);
        rightAllClear.setDirection(Servo.Direction.REVERSE);
        rightKnockyThing.setDirection(Servo.Direction.REVERSE);
        liftExtender.setDirection(DcMotor.Direction.REVERSE);

        leftBrake.setPosition(0.5);
        rightBrake.setPosition(0.5);
        actuatorPlate.setPosition(0.5);
        leftAllClear.setPosition(0.0);
        rightAllClear.setPosition(0.0);
        leftKnockyThing.setPosition(1.0);
        rightKnockyThing.setPosition(1.0);

    }


    @Override
    public void loop() {

        left = gamepad1.left_stick_y;
        right = gamepad1.right_stick_y;
        left2 = gamepad2.left_stick_y;
        right2 = gamepad2.right_stick_y;
        leftTrig = gamepad1.left_trigger;
        rightTrig = gamepad1.right_trigger;
        left2Trig = gamepad2.left_trigger;

        leftBumper = gamepad1.left_bumper;
        rightBumper = gamepad1.right_bumper;
        dpadLeft = gamepad1.dpad_left;
        dpadRight = gamepad1.dpad_right;
        buttonX = gamepad1.x;
        buttonY = gamepad1.y;

        left = Range.clip(left, -1, 1);
        right = Range.clip(right, -1, 1);
        left2 = Range.clip(left2, -1, 1);
        right2 = Range.clip(right2, -1, 1);
        leftTrig = Range.clip(leftTrig, 0, 1);
        rightTrig = Range.clip(rightTrig, 0, 1);
        left2Trig = Range.clip(left2Trig, 0, 1);

        leftWheel.setPower(right);
        rightWheel.setPower(left);
        leftLift.setPower(left2);
        rightLift.setPower(left2);
        //
        // Synchronizer.sync(leftLift, rightLift, 0.999999999, 20, left2, 0.05);
        liftExtender.setPower(right2);
        gathererDrive.setPower(left2Trig);

        leftAllClear.setPosition(leftTrig);
        rightAllClear.setPosition(rightTrig);

        if(dpadLeft)
        {

            leftKnockyThing.setPosition(0.5);

        }else
        {

            leftKnockyThing.setPosition(1.0);

        }

        if(dpadRight)
        {

            rightKnockyThing.setPosition(0.5);

        }else
        {

            rightKnockyThing.setPosition(1.0);

        }

        if(buttonX)
        {

            leftBrake.setPosition(1.0);
            rightBrake.setPosition(1.0);

        }else if(buttonY)
        {

            leftBrake.setPosition(0.0);
            rightBrake.setPosition(0.0);

        }else
        {
            leftBrake.setPosition(0.5);
            rightBrake.setPosition(0.5);
        }

        if(leftBumper)
        {

            actuatorPlate.setPosition(0.4);

        }else if(rightBumper)
        {

            actuatorPlate.setPosition(0.6);

        }else
        {

            actuatorPlate.setPosition(0.5);

        }

        telemetry.addData("Controller 1 Left Stick", left);
        telemetry.addData("Controller 1 Right Stick", right);
        telemetry.addData("Controller 2 Left Stick", left2);
        telemetry.addData("Controller 2 Right Stick", right2);
        telemetry.addData("Controller 1 X", buttonX);
        telemetry.addData("Controller 1 LB", leftBumper);
        telemetry.addData("Controller 1 RB", rightBumper);
        telemetry.addData("Controller 2 LT", leftTrig);
        telemetry.addData("Controller 2 RT", rightTrig);

    }

}