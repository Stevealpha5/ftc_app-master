package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Test extends LinearOpMode
{
    DcMotor motor_1;
    DcMotor motor_2;


    @Override
    public void runOpMode() throws InterruptedException
    {
        motor_1 = hardwareMap.dcMotor.get("motor_1");
        motor_2 = hardwareMap.dcMotor.get("motor_3");

        motor_1.setPower(1.0);

        sleep(17*249);

        motor_1.setPower(0.0);

        telemetry.addData("motor_1", motor_1.getCurrentPosition());
        telemetry.addData("motor_2", motor_2.getCurrentPosition());
    }
}
