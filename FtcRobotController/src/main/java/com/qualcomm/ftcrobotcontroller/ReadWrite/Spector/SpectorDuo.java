package com.qualcomm.ftcrobotcontroller.ReadWrite.Spector;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.io.IOException;

public class SpectorDuo extends LinearOpMode
{
    boolean isRecord;

    @Override
    public void runOpMode() throws InterruptedException
    {
        Spector spector = new Spector("SpectorTest1", 1, 0);

        DcMotor motor_1;
        while(true)
        {
            telemetry.addData("Record", "Press A");
            telemetry.addData("Replay", "Press B");

            if(gamepad1.a)
            {
                isRecord = true;
                break;
            }else if(gamepad1.b)
            {
                isRecord = false;
                break;
            }
        }

        motor_1 = hardwareMap.dcMotor.get("motor_1");

        waitForStart();

        if(isRecord)
        {
            try
            {
                spector.writeInit();
            }catch(IOException e)
            {
                e.printStackTrace();
            }

            while(true)
            {
                telemetry.addData("Record", "Active");
                telemetry.addData("Replay", "Inactive");

                if(gamepad1.guide)
                {
                    break;
                }

                motor_1.setPower(gamepad1.right_stick_y);

                try
                {
                    spector.writeMotor(motor_1, "m1");
                }catch(IOException e)
                {
                    e.printStackTrace();
                }


            }
        }else
        {
            while (true)
            {
                telemetry.addData("Record", "Inactive");
                telemetry.addData("Replay", "Active");

                if(gamepad1.guide)
                {
                    break;
                }
            }
        }

        telemetry.addData("Loop Break Complete", "");
    }
}
