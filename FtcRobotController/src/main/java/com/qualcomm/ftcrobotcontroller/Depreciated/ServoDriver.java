package com.qualcomm.ftcrobotcontroller.Depreciated;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoDriver extends OpMode
{

    Servo leftBucket;
    Servo rightBucket;

    public void init()
    {

        leftBucket = hardwareMap.servo.get("servo_1");
        rightBucket = hardwareMap.servo.get("servo_2");

        leftBucket.setPosition(0.5);
        rightBucket.setPosition(0.5);

        leftBucket.setDirection(Servo.Direction.REVERSE);

    }

    public void loop()
    {

    }

}
