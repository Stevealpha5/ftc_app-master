package com.qualcomm.ftcrobotcontroller.opmodes;

import
com.qualcomm.ftcrobotcontroller.ReadWrite.Nova.MotorGhost;
import com.qualcomm.ftcrobotcontroller.ReadWrite.Nova.Nova;
import com.qualcomm.ftcrobotcontroller.ReadWrite.Nova.ServoGhost;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.io.IOException;
public class NovaReplayTestBed extends LinearOpMode
{
    MotorGhost motor_1;
    MotorGhost motor_2;
    MotorGhost motor_3;
    MotorGhost motor_4;

    ServoGhost servo_1;
    ServoGhost servo_2;


    public String fileName = "novaDriverLog.txt";

    Nova nova = new Nova(fileName, 4, 2, telemetry);

    @Override
    public void runOpMode() throws InterruptedException
    {
        waitForStart();

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

        try {
            nova.writeToMotors(gamepad1.guide); //Increase to add run length (lol), decrease to reduce run length
        } catch (IOException e) {
            e.printStackTrace();
            telemetry.addData("Fatal Error", "IOException @ Read");
        }catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        telemetry.addData("Status", "Program Complete");

        telemetry.addData("motor_1", motor_1.motor.getCurrentPosition());
        telemetry.addData("motor_2", motor_2.motor.getCurrentPosition());
        telemetry.addData("motor_3", motor_3.motor.getCurrentPosition());
        telemetry.addData("motor_4", motor_4.motor.getCurrentPosition());
    }
}
