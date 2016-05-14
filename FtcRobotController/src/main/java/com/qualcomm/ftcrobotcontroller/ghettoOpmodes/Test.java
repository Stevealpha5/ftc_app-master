package com.qualcomm.ftcrobotcontroller.ghettoOpmodes;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class Test extends OpMode
{
    double left;
    double right;
    boolean buttonX;
    boolean buttonA;

    DcMotor motor_1;
    DcMotor motor_2;
    DcMotor motor_3;
    DcMotor motor_4;
    DcMotor motor_5;
    DcMotor motor_6;

    Servo servo_1;
    Servo servo_2;
    Servo servo_3;
    Servo servo_4;
    Servo servo_5;
    Servo servo_6;
    Servo servo_7;
    Servo servo_8;


    public void init()
    {

        motor_1 = hardwareMap.dcMotor.get("motor_1");
        motor_2 = hardwareMap.dcMotor.get("motor_2");
        motor_3 = hardwareMap.dcMotor.get("motor_3");
        motor_4 = hardwareMap.dcMotor.get("motor_4");
        motor_5 = hardwareMap.dcMotor.get("motor_5");
        motor_6 = hardwareMap.dcMotor.get("motor_6");

        servo_1 = hardwareMap.servo.get("servo_1");
        servo_2 = hardwareMap.servo.get("servo_2");
        servo_3 = hardwareMap.servo.get("servo_3");
        servo_4 = hardwareMap.servo.get("servo_4");
        servo_5 = hardwareMap.servo.get("servo_5");
        servo_6 = hardwareMap.servo.get("servo_6");
        servo_7 = hardwareMap.servo.get("servo_7");
        servo_8 = hardwareMap.servo.get("servo_8");

        servo_1.setDirection(Servo.Direction.FORWARD);
        servo_3.setDirection(Servo.Direction.FORWARD);
        servo_5.setDirection(Servo.Direction.FORWARD);
        servo_7.setDirection(Servo.Direction.FORWARD);

        servo_2.setDirection(Servo.Direction.REVERSE);
        servo_4.setDirection(Servo.Direction.REVERSE);
        servo_6.setDirection(Servo.Direction.REVERSE);
        servo_8.setDirection(Servo.Direction.REVERSE);

        servo_1.setPosition(0.5);
        servo_2.setPosition(0.5);

    }

    public void loop()
    {

        left = gamepad1.left_stick_y;
        right = gamepad1.right_stick_y;
        buttonA = gamepad1.a;
        buttonX = gamepad1.x;

        left = Range.clip(left, -1, 1);
        right = Range.clip(right, -1, 1);

        motor_1.setPower(left);
        motor_3.setPower(right);
        //motor_5.setPower(left);

        if (buttonA)
        {

          motor_5.setPower(1);

        }else
        {

         motor_5.setPower(0);

        }

        if (buttonX)
        {

            servo_3.setPosition(1);

        }else
        {

            servo_3.setPosition(0);

        }

    }

}
