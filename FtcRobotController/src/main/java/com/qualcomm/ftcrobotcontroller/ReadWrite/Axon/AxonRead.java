package com.qualcomm.ftcrobotcontroller.ReadWrite.Axon;

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
    DcMotor leftFrontWheel;
    DcMotor rightFrontWheel;
    DcMotor leftRearWheel;
    DcMotor rightRearWheel;
    DcMotor liftMotor;
    DcMotor armExtender;

    Servo leftBucket;
    Servo rightBucket;

    ColorSensor armColorSensor;

    //Initialize variables and objects
    String line;

    String path = Environment.getExternalStorageDirectory().getAbsolutePath(); //Get logfile storage directory
    String filename = ("controlLog.txt"); //Input name of logfile to be read

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

            telemetry.addData("Nullity", "true");

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
            telemetry.addData("Nothing", "false");

        }

    }

    public void readFromLog(String tag, Servo servo) throws IOException //Read from the logfile and output to a motor
    {

        double devicePower;

        telemetry.addData("Read Status", "Data Read");

        if(String.valueOf(input[0]).equals(tag))
        {

            telemetry.addData("Read Status", "IF (not a reference!)");

            input[0] = ' '; //Clear character index 0 (tag ID) before reading state value

            telemetry.addData("Read Status", "Index Cleared");

            devicePower = Double.parseDouble(new String(input)); //Read object state value
            servo.setPosition(devicePower); //Set motor power

            telemetry.addData("Read Status", "Power Set, Complete");
            telemetry.addData("Nothing", "false");

        }

    }

    public void init()
    {

        //Link objects to hardware map
        leftFrontWheel = hardwareMap.dcMotor.get("motor_1");
        rightFrontWheel = hardwareMap.dcMotor.get("motor_2");
        leftRearWheel = hardwareMap.dcMotor.get("motor_3");
        rightRearWheel = hardwareMap.dcMotor.get("motor_4");
        liftMotor = hardwareMap.dcMotor.get("motor_5");
        armExtender = hardwareMap.dcMotor.get("motor_6");

        leftBucket = hardwareMap.servo.get("servo_1");
        rightBucket = hardwareMap.servo.get("servo_2");

        rightFrontWheel.setDirection(DcMotor.Direction.REVERSE);
        rightRearWheel.setDirection(DcMotor.Direction.REVERSE);

        armColorSensor = hardwareMap.colorSensor.get("color_sensor_1");

    }

    public void loop()
    {

            try { //Try to read the file
                readInit();
                readFromLog("L", leftFrontWheel);
                readFromLog("R", rightFrontWheel);
                readFromLog("L", leftRearWheel);
                readFromLog("R", rightRearWheel);
                readFromLog("E", liftMotor);
                readFromLog("P", armExtender);
                readFromLog("T", leftBucket);
                readFromLog("B", rightBucket);

            } catch (IOException e) {
                e.printStackTrace();
                telemetry.addData("Fatal Error", "IOException @ Read");
            }

    }

}
