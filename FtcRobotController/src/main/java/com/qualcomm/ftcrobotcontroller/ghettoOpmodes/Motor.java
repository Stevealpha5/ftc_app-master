package com.qualcomm.ftcrobotcontroller.ghettoOpmodes;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * It don't work
 * */

public class Motor extends OpMode
{
    DcMotor motor_1;
    DcMotor motor_2;

    @Override
    public void init()
    {
        motor_1 = hardwareMap.dcMotor.get("motor_1");
        motor_2 = hardwareMap.dcMotor.get("motor_2");
    }

    @Override
    public void loop()
    {

        motor_1.setPower(1);
        motor_2.setPower(1);

        telemetry.addData("Trigger",gamepad1.left_trigger);
        telemetry.addData("encoder 1",motor_1.getCurrentPosition());
        telemetry.addData("encoder 2",motor_2.getCurrentPosition());
    }

}
