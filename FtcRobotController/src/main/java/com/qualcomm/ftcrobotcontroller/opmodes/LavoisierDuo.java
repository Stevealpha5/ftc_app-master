package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftcrobotcontroller.ReadWrite.Lavoisier.Lavoisier;
import com.qualcomm.ftcrobotcontroller.ReadWrite.Nova.Nova;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.io.IOException;

public class LavoisierDuo extends LinearOpMode
{
    boolean isWrite;

    DcMotor motor_1;
    DcMotor motor_2;
    DcMotor motor_3;
    DcMotor motor_4;

    Servo servo_1;
    Servo servo_2;

    public String fileName = "novaDriverLog.txt";

    Lavoisier lavoisier = new Lavoisier(fileName, telemetry);


    @Override
    public void runOpMode() throws InterruptedException
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

        motor_1 = hardwareMap.dcMotor.get("motor_1");
        motor_2 = hardwareMap.dcMotor.get("motor_2");
        motor_3 = hardwareMap.dcMotor.get("motor_3");
        motor_4 = hardwareMap.dcMotor.get("motor_4");

        servo_1 = hardwareMap.servo.get("servo_1");
        servo_2 = hardwareMap.servo.get("servo_2");

        lavoisier.addMotor(motor_1);
        lavoisier.addMotor(motor_2);
        lavoisier.addMotor(motor_3);
        lavoisier.addMotor(motor_4);

        lavoisier.addServo(servo_1);
        lavoisier.addServo(servo_2);

        telemetry.addData("Program Status", "Global Initialization Complete");

        try
        {
            waitForStart();
        }catch(InterruptedException e)
        {
            e.printStackTrace();
        }


        if(isWrite)
        {
            telemetry.addData("Program Status", "Record Initialization Starting");
            lavoisier.start();
            telemetry.addData("Program Status", "Record Initialization Complete");

            int counter = 0;

            while(counter < 250)
            {
                resetStartTime();

                telemetry.addData("Program Status", "Record Running");
                telemetry.addData("Counter", counter);

                motor_1.setPower(0.9);
                motor_2.setPower(0);

                if(gamepad1.a)
                {
                    motor_3.setPower(gamepad1.left_trigger);
                    motor_4.setPower(gamepad1.right_trigger);
                }else if(gamepad1.b)
                {
                    motor_3.setPower(-gamepad1.left_trigger);
                    motor_4.setPower(-gamepad1.right_trigger);
                }else
                {
                    motor_3.setPower(0.0);
                    motor_4.setPower(0.0);
                }

                if(gamepad1.right_stick_button)
                {
                    servo_1.setPosition(1.0);
                }else
                {
                    servo_1.setPosition(0.0);
                }

                if(gamepad1.left_stick_button)
                {
                    servo_2.setPosition(1.0);
                }else
                {
                    servo_2.setPosition(0.0);
                }



                telemetry.addData("motor_1", motor_1.getCurrentPosition());
                telemetry.addData("motor_2", motor_2.getCurrentPosition());
                telemetry.addData("motor_3", motor_3.getCurrentPosition());
                telemetry.addData("motor_4", motor_4.getCurrentPosition());

                long start = System.nanoTime();


                long finish = System.nanoTime() - start;

                telemetry.addData("runtime", finish);

                counter++;
                Thread.sleep(10);
                //telemetry.addData("Program Status", "Record Finished");

                if(gamepad1.guide) //Breaks the infinite loop as to avoid crashes on program shutdown
                {
                    telemetry.addData("Runtime", getRuntime());

                    telemetry.addData("motor_1", motor_1.getCurrentPosition());
                    telemetry.addData("motor_2", motor_2.getCurrentPosition());
                    telemetry.addData("motor_3", motor_3.getCurrentPosition());
                    telemetry.addData("motor_4", motor_4.getCurrentPosition());

                    telemetry.addData("Program Status", "Record Exiting");
                    break;
                }
            }

            motor_1.setPower(0);


            telemetry.addData("Runtime", getRuntime());

            telemetry.addData("motor_1", motor_1.getCurrentPosition());
            telemetry.addData("motor_2", motor_2.getCurrentPosition());
            telemetry.addData("motor_3", motor_3.getCurrentPosition());
            telemetry.addData("motor_4", motor_4.getCurrentPosition());

            telemetry.addData("Program Status", "Record Exiting");

        }else if(!isWrite)
        {
            resetStartTime();
            telemetry.addData("Program Status", "Replay Initialization Starting");

            telemetry.addData("Program Status", "Replay Initialization Complete");

            telemetry.addData("Program Status", "Replay Running");

            try {
                lavoisier.writeToMotors();
            } catch (IOException e) {
                e.printStackTrace();
                telemetry.addData("Fatal Error", "IOException @ Read");
            }catch(InterruptedException e)
            {
                e.printStackTrace();
                telemetry.addData("Fatal Error", "InterruptedException @ Read");
            }

            telemetry.addData("Runtime", getRuntime());
            telemetry.addData("Program Status", "Replay Finished, Press Guide To Exit");
            telemetry.addData("motor_1", motor_1.getCurrentPosition());
            telemetry.addData("motor_2", motor_2.getCurrentPosition());
            telemetry.addData("motor_3", motor_3.getCurrentPosition());
            telemetry.addData("motor_4", motor_4.getCurrentPosition());

            telemetry.addData("Program Status", "Replay Exiting");

        }
        telemetry.addData("Program Status", "Global Stop Completed");
    }
}
