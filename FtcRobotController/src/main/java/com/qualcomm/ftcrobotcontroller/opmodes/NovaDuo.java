package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftcrobotcontroller.ReadWrite.Nova.MotorGhost;
import com.qualcomm.ftcrobotcontroller.ReadWrite.Nova.Nova;
import com.qualcomm.ftcrobotcontroller.ReadWrite.Nova.ServoGhost;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorController;

import java.io.IOException;

public class NovaDuo extends LinearOpMode
{
    boolean isWrite;

    MotorGhost motor_1;
    MotorGhost motor_2;
    MotorGhost motor_3;
    MotorGhost motor_4;

    ServoGhost servo_1;
    ServoGhost servo_2;

    public String fileName = "novaDriverLog.txt";

    Nova nova = new Nova(fileName, 4, 2, telemetry);


    @Override
    public void runOpMode()
    {
        while(true)
        {
            telemetry.addData("Record", "Press A");
            telemetry.addData("Replay", "Press B");

            if(gamepad1.a)
            {
                isWrite = true;

                telemetry.addData("Record", "Active");
                telemetry.addData("Replay", "Inactive");

                break;
            }else if(gamepad1.b)
            {
                isWrite = false;

                telemetry.addData("Record", "Inactive");
                telemetry.addData("Replay", "Active");

                break;
            }
        }

        telemetry.addData("Program Status", "Global Initialization Starting");

        motor_1 = new MotorGhost("M1", 1);
        motor_2 = new MotorGhost("M2", 2);
        motor_3 = new MotorGhost("M3", 3);
        motor_4 = new MotorGhost("M4", 4);

        servo_1 = new ServoGhost("S1", 1);
        servo_2 = new ServoGhost("S2", 2);

        nova.addMotorGhost(motor_1);
        nova.addMotorGhost(motor_2);
        nova.addMotorGhost(motor_3);
        nova.addMotorGhost(motor_4);

        nova.addServoGhost(servo_1);
        nova.addServoGhost(servo_2);

        nova.mapGhosts(hardwareMap);

        motor_1.motor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motor_2.motor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motor_3.motor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motor_4.motor.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        motor_1.motor.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        motor_2.motor.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        motor_3.motor.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        motor_4.motor.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);


        telemetry.addData("Program Status", "Global Initialization Complete");

        try
        {
            waitForStart();
        }catch(InterruptedException e)
        {
            telemetry.addData("Program Status", "Died at waitForStart()");
            e.printStackTrace();
        }


        if(isWrite)
        {
            telemetry.addData("Program Status", "Record Initialization Starting");

            try
            {
                nova.writeInit();
            }catch(IOException e)
            {
                e.printStackTrace();
            }

            telemetry.addData("Program Status", "Record Initialization Complete");

            while(true)
            {
                telemetry.addData("Program Status", "Record Running");

                nova.powerGhost(motor_1, gamepad1.right_stick_y);
                nova.powerGhost(motor_2, gamepad1.left_stick_y);

                nova.powerGhost(motor_3, gamepad1.a, gamepad1.b, gamepad1.left_trigger);
                nova.powerGhost(motor_4, gamepad1.a, gamepad1.b, gamepad1.right_trigger);

                nova.powerGhost(servo_1, gamepad1.right_stick_button, 0.0, 1.0);
                nova.powerGhost(servo_2, gamepad1.left_stick_button, 0.0, 1.0);

                telemetry.addData("motor_1", motor_1.motor.getCurrentPosition());
                telemetry.addData("motor_2", motor_2.motor.getCurrentPosition());
                telemetry.addData("motor_3", motor_3.motor.getCurrentPosition());
                telemetry.addData("motor_4", motor_4.motor.getCurrentPosition());

                try
                {
                    nova.writeLogFile();
                }catch(IOException e)
                {
                    e.printStackTrace();
                }

                //telemetry.addData("Program Status", "Record Finished");

                if(gamepad1.guide) //Breaks the infinite loop as to avoid crashes on program shutdown
                {
                    telemetry.addData("motor_1", motor_1.motor.getCurrentPosition());
                    telemetry.addData("motor_2", motor_2.motor.getCurrentPosition());
                    telemetry.addData("motor_3", motor_3.motor.getCurrentPosition());
                    telemetry.addData("motor_4", motor_4.motor.getCurrentPosition());

                    telemetry.addData("Program Status", "Record Exiting");
                    break;
                }
            }
        }else if(!isWrite)
        {
            telemetry.addData("Program Status", "Replay Initialization Starting");

            telemetry.addData("Program Status", "Replay Initialization Complete");

            telemetry.addData("Program Status", "Replay Running");

            try {
                nova.writeToMotors(gamepad1.guide);
            } catch (IOException e) {
                e.printStackTrace();
                telemetry.addData("Fatal Error", "IOException @ Read");
            }catch(InterruptedException e)
            {
                e.printStackTrace();
                telemetry.addData("Fatal Error", "InterruptedException @ Read");
            }

            telemetry.addData("Program Status", "Replay Finished, Press Guide To Exit");

            telemetry.addData("motor_1", motor_1.motor.getCurrentPosition());
            telemetry.addData("motor_2", motor_2.motor.getCurrentPosition());
            telemetry.addData("motor_3", motor_3.motor.getCurrentPosition());
            telemetry.addData("motor_4", motor_4.motor.getCurrentPosition());

            telemetry.addData("Program Status", "Replay Exiting");

        }
        telemetry.addData("Program Status", "Global Stop Completed");
    }
}
