package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftcrobotcontroller.Utilities.Synchronizer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

public class Test extends OpMode
{
    DcMotor motor_1;
    DcMotor motor_2;


    @Override
    public void init()
    {
        motor_1 = hardwareMap.dcMotor.get("motor_4");
        motor_2 = hardwareMap.dcMotor.get("motor_3");
    }

    @Override
    public void loop()
    {

        //motor_1.setPower(.05);

        motor_2.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motor_2.setTargetPosition(200);
        motor_2.setPower(.025);

        motor_1.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motor_1.setTargetPosition(200);
        motor_1.setPower(.05);

        telemetry.addData("motor_1", motor_1.getCurrentPosition());
        telemetry.addData("motor_2", motor_2.getCurrentPosition());
    }
}
