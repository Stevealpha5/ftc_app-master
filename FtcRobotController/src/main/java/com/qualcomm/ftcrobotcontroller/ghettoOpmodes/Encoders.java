package com.qualcomm.ftcrobotcontroller.ghettoOpmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Encoders extends OpMode
{
    DcMotor motor_1;
    DcMotor motor_2;

    int test =  motor_1.getCurrentPosition();

    @Override
    public void init()
    {
        telemetry.addData("Encoderc", "hj");
        motor_1 = hardwareMap.dcMotor.get("motor_1");
        motor_2 = hardwareMap.dcMotor.get("motor_2");
    }

    @Override
    public void loop()
    {
        motor_1.setPower(1);
        motor_2.setPower(1);

        telemetry.addData("Encoder1", motor_1.getCurrentPosition());
        telemetry.addData("Encoder2", motor_2.getCurrentPosition());
    }
}
