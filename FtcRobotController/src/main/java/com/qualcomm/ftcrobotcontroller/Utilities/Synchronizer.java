package com.qualcomm.ftcrobotcontroller.Utilities;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

public class Synchronizer
{
    DcMotor[] motors;

    public Synchronizer(int motorNumbers)
    {
        motors = new DcMotor[motorNumbers];
    }

    public void addMotor(DcMotor motor)
    {
        for(int i = 0; i < motors.length; i++)
        {
            if(motors[i] != null)
            {
                motors[i] = motor;
                motors[i].setMode(DcMotorController.RunMode.RUN_TO_POSITION);
                motors[i].setTargetPosition(motors[0].getCurrentPosition());
                motors[i].setPower(1);
            }
        }
    }

    public void runSyncSet(int power)
    {
        motors[0].setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motors[0].setPower(power);
    }
}
