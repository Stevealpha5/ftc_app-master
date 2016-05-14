package com.qualcomm.ftcrobotcontroller.ghettoOpmodes;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

public class BasicDrive extends OpMode
{

    double left;
    double right;
    DcMotor motor_1;
    DcMotor motor_2;
    DcMotor motor_3;
    DcMotor motor_4;

    @Override
    public void init()
    {
        motor_1 = hardwareMap.dcMotor.get("motor_1");
        motor_2 = hardwareMap.dcMotor.get("motor_2");
        motor_3 = hardwareMap.dcMotor.get("motor_3");
        motor_4 = hardwareMap.dcMotor.get("motor_4");

    }


    @Override
    public void loop()
    {
        left = gamepad1.left_stick_y;
        right = gamepad1.right_stick_y;

        left = Range.clip(left, -1, 1);
        right = Range.clip(right, -1, 1);

        motor_1.setPower(-1 * left);
        motor_2.setPower(-1 * left);

        motor_3.setPower(right);
        motor_4.setPower(right);
        telemetry.addData("Trigger",gamepad1.left_trigger);
        telemetry.addData("encoder 1",motor_1.getCurrentPosition());
    }

    @Override
    public void stop()
    {

    }

}