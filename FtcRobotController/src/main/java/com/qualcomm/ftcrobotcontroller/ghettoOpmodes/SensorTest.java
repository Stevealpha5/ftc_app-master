package com.qualcomm.ftcrobotcontroller.ghettoOpmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class SensorTest extends OpMode
{
    OpticalDistanceSensor distanceSensor;
    TouchSensor buttonz;
    Servo servo1;

    @Override
    public void init()
    {
        distanceSensor = hardwareMap.opticalDistanceSensor.get("distance");
        buttonz = hardwareMap.touchSensor.get("touch");
        servo1 = hardwareMap.servo.get("servo_1");
    }

    @Override
    public void loop()
    {
        telemetry.addData("button(boolean): ", buttonz.isPressed());
        telemetry.addData("button(double): ", buttonz.getValue());
        telemetry.addData("distance(light detected): ", distanceSensor.getLightDetected());
        telemetry.addData("distance(light detected raw): ", distanceSensor.getLightDetectedRaw());

        servo1.setPosition(.5);
    }
}
