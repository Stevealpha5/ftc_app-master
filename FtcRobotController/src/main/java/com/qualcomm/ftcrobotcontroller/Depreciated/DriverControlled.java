package com.qualcomm.ftcrobotcontroller.Depreciated;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


public class DriverControlled extends OpMode
{

    double standardDeadband = .05;//+ or - in percent

    double pinionGearRadius = 17.75; //mm
    double pinionGearCircumfrnce = 2 * 3.1415926535 * pinionGearRadius;//mm

    double leftStick1;
    double rightStick1;

    double leftStick2;
    double rightStick2;

    double rightTrigger;
    double leftTrigger;

    DcMotor rightDrive; //direct
    DcMotor leftDrive;//direct
    DcMotor armAngle;//worm
    DcMotor extendor;//2:1
    DcMotor gatherer;//not releveant / subject to change

    Servo lollipopExtendor1;
    Servo lollipopExtendor2;

    @Override
    public void init()
    {

        rightDrive = hardwareMap.dcMotor.get("motor_1");
        leftDrive = hardwareMap.dcMotor.get("motor_2");
        armAngle = hardwareMap.dcMotor.get("motor_3");
        extendor = hardwareMap.dcMotor.get("motor_4");
        gatherer = hardwareMap.dcMotor.get("motor_5");

        leftDrive.setDirection(DcMotor.Direction.REVERSE);

        lollipopExtendor1 = hardwareMap.servo.get("servo_1");
        lollipopExtendor2 = hardwareMap.servo.get("servo_2");
    }

    @Override
    public void loop()
    {
        controllUpdater();
        motorUpdater();
    }

    void controllUpdater()
    {
        leftStick1 = gamepad1.left_stick_y;//updates sticks for drive
        rightStick1 = gamepad1.right_stick_y;

        leftStick2 = gamepad2.left_stick_y;//updates stick for arm angle
        rightStick2 = gamepad2.right_stick_y;//updates stick for extendor

        leftTrigger = gamepad2.left_trigger;//updates triggers for gatherer
        rightTrigger = gamepad2.right_trigger;
    }

    void motorUpdater()
    {
        leftDrive.setPower(deadband(leftStick1, standardDeadband));//run drive base
        rightDrive.setPower(deadband(rightStick1, standardDeadband));

        armAngle.setPower(deadband(rightStick2, standardDeadband));//arm angel

        extendor.setPower(deadband(leftStick2, standardDeadband));//extendor

        if(leftTrigger > standardDeadband)//gatherer stuff
        {
            gatherer.setPower(-leftTrigger);
        }else if(rightTrigger > standardDeadband)
        {
            gatherer.setPower(rightTrigger);
        }

    }

    double deadband(double value, double deadband)
    {
        if(Math.abs(value) > deadband)
        {
            return value;
        }

        return 0;
    }

    int deadband(int value, int deadband)
    {
        if(Math.abs(value) > deadband)
        {
            return value;
        }

        return 0;
    }

    /**Depreciated*/
    void sync (DcMotor motor1, DcMotor motor2, double decreasedPercentage, int acceptableVariance)
    {
        if(Math.abs(motor1.getCurrentPosition() - motor2.getCurrentPosition()) >= acceptableVariance)
        {
            if(motor1.getCurrentPosition() > motor2.getCurrentPosition())
            {
               motor1.setPower(motor1.getPower() * decreasedPercentage);
            }

            if(motor2.getCurrentPosition() > motor1.getCurrentPosition())
            {
                motor2.setPower(motor2.getPower() * decreasedPercentage);
            }
        }
    }
}
