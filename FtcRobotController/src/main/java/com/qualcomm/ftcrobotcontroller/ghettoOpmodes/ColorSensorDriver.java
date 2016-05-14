package com.qualcomm.ftcrobotcontroller.ghettoOpmodes;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

public class ColorSensorDriver extends OpMode
{

    ColorSensor MRColorSensor;

    boolean gamepadY;

    boolean lastCheck = false;
    boolean counter = false;

    boolean toggle (boolean input){
        if((input != lastCheck) & (input)){
            counter = ! counter;
        }
        lastCheck = input;
        return counter;
    }

    public void init()
    {

        MRColorSensor = hardwareMap.colorSensor.get("color_sensor_1");
        MRColorSensor.enableLed(true);


    }

    public void loop()
    {
        gamepadY = gamepad1.y;

        MRColorSensor.enableLed(toggle(gamepadY));

        telemetry.addData("Sensor Red", MRColorSensor.red());
        telemetry.addData("Sensor Green", MRColorSensor.green());
        telemetry.addData("Sensor Blue", MRColorSensor.blue());
        telemetry.addData("Sensor Alpha", MRColorSensor.alpha());
        telemetry.addData("Sensor Status", MRColorSensor.getConnectionInfo());

    }

}
