package com.qualcomm.ftcrobotcontroller.ghettoOpmodes;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Synchronization
{
    public static void sync (DcMotor motor1, DcMotor motor2, double decreasedPercentage, int acceptableVariance, double left, double right, double deadband)
    {
        if(Math.abs(motor1.getCurrentPosition() + motor2.getCurrentPosition()) >= acceptableVariance)
        {
            if(((left < 0) && (Math.abs(left) > Math.abs(deadband))|| ((right < 0) && (Math.abs(right) > Math.abs(deadband)))))
            {
                if (motor1.getCurrentPosition() < motor2.getCurrentPosition())
                {
                        motor2.setPower(motor1.getPower() * decreasedPercentage);
                }

                if (motor2.getCurrentPosition() < motor1.getCurrentPosition())
                {
                        motor1.setPower(motor2.getPower() * decreasedPercentage);
                }
            }else if(((left > 0) && (Math.abs(left) > Math.abs(deadband))|| ((right > 0) && (Math.abs(right) > Math.abs(deadband))))){
                if (motor1.getCurrentPosition() > motor2.getCurrentPosition())
                {
                        motor1.setPower(motor1.getPower() * decreasedPercentage);
                }

                if (motor2.getCurrentPosition() > motor1.getCurrentPosition())
                {
                        motor2.setPower(motor2.getPower() * decreasedPercentage);
                }
            }else {
                motor1.setPower(0);
                motor2.setPower(0);
            }
        }

    }

    public static void sync (DcMotor motor1, DcMotor motor2, double decreasedPercentage, int acceptableVariance, double input, double deadband)
    {
        if (Math.abs(motor1.getCurrentPosition() + motor2.getCurrentPosition()) >= acceptableVariance)
        {
            if ((input < 0) && (Math.abs(input) > Math.abs(deadband))) {
                if (motor1.getCurrentPosition() < motor2.getCurrentPosition())
                {
                    motor2.setPower(input * decreasedPercentage);
                }

                if (motor2.getCurrentPosition() < motor1.getCurrentPosition())
                {
                    motor1.setPower(input * decreasedPercentage);
                }
            } else if ((input > 0) && ((Math.abs(input)) > (Math.abs(deadband)))){
                if (motor1.getCurrentPosition() > motor2.getCurrentPosition())
                {
                    motor1.setPower(input * decreasedPercentage);
                }

                if (motor2.getCurrentPosition() > motor1.getCurrentPosition())
                {
                    motor2.setPower(input * decreasedPercentage);
                }
            }else{
                motor1.setPower(0);
                motor2.setPower(0);
            }
        }
    }
}
