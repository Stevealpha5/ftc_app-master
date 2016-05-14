package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Allows for manual (driver) control of the robot.
 */
public class DriverControl extends OpMode
{

    double left;
    double right;
    double left2;
    double right2;
    double leftTrig2;
    double rightTrig2;

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

    boolean lastCheck = false;
    boolean counter = false;

    /**
     *  Acts as a digital toggle latch or flip-flop.
     * @param input The boolean variable to be used as the switch for the toggle
     * @return The boolean variable that represents the current state of the toggle
     */
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

    /**
     * Initializes hardware devices and ties them to the hardware map.
     * Also sets starting directions and initialization positions for servos and motors.
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode
     */
    @Override
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

    }

    /**
     * Gets input values from the controllers and passes them to the hardware mapped in Init.
     * Also outputs telemetry indicating current controller input states.
     * Comes with a default 10 millisecond loop delay to avoid input buffer overflow.
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode
     */
    @Override
    public void loop()
    {

        left = gamepad1.left_stick_y;
        right = gamepad1.right_stick_y;
        left2 = gamepad2.left_stick_y;
        right2 = gamepad2.right_stick_y;
        leftTrig2 = gamepad2.left_trigger;
        rightTrig2 = gamepad2.right_trigger;

        left = Range.clip(left, -1, 1);
        right = Range.clip(right, -1, 1);
        left2 = Range.clip(left2, -1, 1);
        right2 = Range.clip(right2, -1, 1);
        leftTrig2 = Range.clip(leftTrig2, 0, 1);
        rightTrig2 = Range.clip(rightTrig2, 0, 1);

        rightTrig2 = Range.scale(rightTrig2, 0.0, 1.0, 0.51, 1.0);
        leftTrig2 = Range.scale(leftTrig2, 0.0, 1.0, 0.51, 1.0);

        rightTrig2 = rightTrig2 * -1;

        leftWheels.setPower(left);
        rightWheels.setPower(right);
        armExtender2.setPower(right2);
        liftMotor.setPower(left2);
        armExtender.setPower(right2);

        if(leftTrig2 > 0.55) {
            hangMotor.setPower(leftTrig2);
        }else if(rightTrig2 < -0.55) {
            hangMotor.setPower(rightTrig2);
        }else {
            hangMotor.setPower(0);
        }

        if(Toggle3(gamepad2.a))
        {
            leftBucket.setPosition(0.65);
            rightBucket.setPosition(0.65);
        }else if(gamepad2.right_bumper)
        {
            leftBucket.setPosition(0.8);
            rightBucket.setPosition(0.8);
        }else
        {
            leftBucket.setPosition(0.5);
            rightBucket.setPosition(0.5);
        }

        if(Toggle(gamepad1.right_stick_button))
        {
            collectorDrive.setDirection(Servo.Direction.REVERSE);
            collectorDrive2.setDirection(Servo.Direction.FORWARD);
            collectorDrive.setPosition(1.0);
            collectorDrive2.setPosition(1.0);
        }else if(Toggle2(gamepad1.left_stick_button))
        {
            collectorDrive.setDirection(Servo.Direction.FORWARD);
            collectorDrive2.setDirection(Servo.Direction.REVERSE);
            collectorDrive.setPosition(1.0);
            collectorDrive2.setPosition(1.0);
        }else
        {
            collectorDrive.setPosition(0.5);
            collectorDrive2.setPosition(0.5);
        }

        if(gamepad1.dpad_up) {
            climberCarrierServo.setPosition(0.0);
        }else {
            climberCarrierServo.setPosition(1.0);
        }

        if(gamepad1.dpad_left){
            leftClimberServo.setPosition(0.65);
        }else{
            leftClimberServo.setPosition(0.05);
        }

        if(gamepad1.dpad_right){
            rightClimberServo.setPosition(0.65);
        }else{
            rightClimberServo.setPosition(0.05);
        }

        telemetry.addData("Start Time", getRuntime());

        if(getRuntime() > 10)
        {
            resetStartTime();
        }

        //DEBUG
        telemetry.addData("Controller 1 Left Stick", left);
        telemetry.addData("Controller 1 Right Stick", right);
        telemetry.addData("Controller 2 Left Stick", left2);
        telemetry.addData("Controller 2 Right Stick", right2);
        telemetry.addData("Controller 2 LT", leftTrig2);
        telemetry.addData("Controller 2 RT", rightTrig2);
        //END DEBUG

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}