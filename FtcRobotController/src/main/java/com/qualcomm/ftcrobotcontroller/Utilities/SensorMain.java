package com.qualcomm.ftcrobotcontroller.Utilities;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

public class SensorMain extends LinearOpMode
{

    static void autoActuateBlue(ColorSensor colorSensor, Servo servo, double pos1, double pos2)//pos1 pushes the same side as the colour sensor, pos2 pushes the other one
    {
        if(colorSensor.blue() > colorSensor.red())
        {
            servo.setPosition(pos1);
        }else if(colorSensor.red() > colorSensor.blue())
        {
            servo.setPosition(pos2);
        }

    }

    static void autoActuateRed(ColorSensor colorSensor, Servo servo, double pos1, double pos2)//pos1 pushes the same side as the colour sensor, pos2 pushes the other one
    {
        if(colorSensor.blue() > colorSensor.red())
        {
            servo.setPosition(pos2);
        }else if(colorSensor.red() > colorSensor.blue())
        {
            servo.setPosition(pos1);
        }

    }

    static void autoDistanceActuate(OpticalDistanceSensor ods, Servo servo, double minDistance, double maxDistance, double servoPositionTarget)
    {

        ods.enableLed(true);

        if(ods.getLightDetected() > minDistance & ods.getLightDetected() < maxDistance)
        {

            servo.setPosition(servoPositionTarget);

        }else
        {

            servo.setPosition(0.0);

        }

    }

    public void runOpMode()
    {



    }

}
