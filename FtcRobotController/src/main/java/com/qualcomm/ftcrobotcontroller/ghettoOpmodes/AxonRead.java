package com.qualcomm.ftcrobotcontroller.ghettoOpmodes;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class AxonRead extends OpMode
{

    //Declare motor and servo objects
    DcMotor motor_1;
    DcMotor motor_2;
    DcMotor motor_3;
    DcMotor motor_4;
    DcMotor motor_5;
    DcMotor motor_6;

    Servo actuatorPlate;

    ColorSensor armColorSensor;

    //Initialize variables and objects
    String line;

    String path = Environment.getExternalStorageDirectory().getAbsolutePath(); //Get logfile storage directory
    String filename = ("controlLogRed.txt"); //Input name of logfile to be read

    File sourceFile = new File(path, filename); //Stores logfile directory and name

    BufferedReader br; //Create reader object

    char[] input;

    public void readInit() throws IOException //Call ONCE before calling readFromLog
    {

        br = new BufferedReader(new FileReader(sourceFile));

        telemetry.addData("Read Status", "Variables/Objects Created");

        line = br.readLine(); //Read file
        input = (line).toCharArray(); //Declare character array
        if (line != null)
        {

            telemetry.addData("Index", line);
            telemetry.addData("Index ID 0", String.valueOf(input[0]));
            telemetry.addData("Index ID 2-4", String.valueOf(input[2]) + String.valueOf(input[3]) + String.valueOf(input[4]));

        }else
        {

            telemetry.addData("Read Status", "Line null");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            com.qualcomm.ftcrobotcontroller.ghettoOpmodes.ServoDriver.autoActuateRed(armColorSensor, actuatorPlate, 0.4, 0.6); //Activate color sensor read routine at end of file

        }


    }

    public void readFromLog(String tag, DcMotor motor) throws IOException //Read from the logfile and output to a motor
    {

        double devicePower;

        telemetry.addData("Read Status", "Data Read");

        if(String.valueOf(input[0]).equals(tag))
        {

            telemetry.addData("Read Status", "IF (not a reference!)");

            input[0] = ' '; //Clear character index 0 (tag ID) before reading state value

            telemetry.addData("Read Status", "Index Cleared");

            devicePower = Double.parseDouble(new String(input)); //Read object state value
            motor.setPower(devicePower); //Set motor power

            telemetry.addData("Read Status", "Power Set, Complete");

        }

    }

    public void init()
    {

        //Link objects to hardware map
        motor_1 = hardwareMap.dcMotor.get("motor_1");
        motor_2 = hardwareMap.dcMotor.get("motor_2");
        motor_3 = hardwareMap.dcMotor.get("motor_3");
        motor_4 = hardwareMap.dcMotor.get("motor_4");
        motor_5 = hardwareMap.dcMotor.get("motor_5");
        motor_6 = hardwareMap.dcMotor.get("motor_6");

        actuatorPlate = hardwareMap.servo.get("servo_3");

        motor_2.setDirection(DcMotor.Direction.REVERSE);
        motor_3.setDirection(DcMotor.Direction.REVERSE);
        motor_4.setDirection(DcMotor.Direction.REVERSE);

        armColorSensor = hardwareMap.colorSensor.get("color_sensor_1");

    }

    public void loop()
    {

            try { //Try to read the file

                readInit();
                readFromLog("L", motor_1);
                readFromLog("R", motor_2);
                readFromLog("E", motor_3);
                readFromLog("E", motor_4);
                readFromLog("P", motor_5);

                Thread.sleep(5);

            } catch (IOException e) {
                e.printStackTrace();
                telemetry.addData("Fatal Error", "IOException @ Read");
            } catch (InterruptedException e){
                e.printStackTrace();
            }

    }

}
