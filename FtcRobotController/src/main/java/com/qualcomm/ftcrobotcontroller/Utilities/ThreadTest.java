package com.qualcomm.ftcrobotcontroller.Utilities;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.robocol.Telemetry;


public class ThreadTest extends LinearOpMode
{
    DcMotor motor_1;
    DcMotor motor_2;

    @Override
    public void runOpMode() throws InterruptedException
    {


        motor_1 = hardwareMap.dcMotor.get("motor_1");
        motor_2 = hardwareMap.dcMotor.get("motor_2");

        motor_1.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        motor_2.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        Thread m1 = new Thread(new testThread(motor_1, gamepad1.a, telemetry));
        Thread m2 = new Thread(new testThread(motor_2, gamepad1.a, telemetry));

        waitForStart();

        m1.start();
        m2.start();

        m1.join();
        m2.join();


        telemetry.addData("motor_1", motor_1.getCurrentPosition());
        telemetry.addData("motor_2", motor_2.getCurrentPosition());
    }


}

class testThread implements Runnable
{
    DcMotor motor;
    int time;
    Telemetry telemetry;
    boolean gamepad1;

    testThread (DcMotor motor, boolean gamepad1, Telemetry telemetry)
    {
        this.motor = motor;
        this.gamepad1 = gamepad1;
        this.telemetry = telemetry;



    }

    @Override
    public void run()
    {


        if(gamepad1)
        {
            motor.setPower(1);
        }else
        {
            motor.setPower(0);
        }

    }

}
