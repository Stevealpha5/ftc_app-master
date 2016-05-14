package com.qualcomm.ftcrobotcontroller.ReadWrite.PITA;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class PITARead_obj extends LinearOpMode
{
    //Initialize motor objects
    DcMotor rightWheels;
    DcMotor leftWheels;
    DcMotor liftMotor;
    DcMotor armExtender;
    DcMotor armExtender2;
    DcMotor armExtender3;

    Servo leftBucket;
    Servo rightBucket;

    ColorSensor armSensor;

    String path = Environment.getExternalStorageDirectory().getAbsolutePath(); //Get logfile storage directory
    String filename;//Input name of logfile

    public PITARead_obj(String filename)
    {
        this.filename = filename;
    }

    public PITARead_obj(String filename, String path)
    {
        this.filename = filename;
        this.path = path;
    }

    File sourceFile = new File(path, filename); //Stores logfile directory and name

    public void hardwareInit()
    {
        telemetry.addData("Read Status", "Program started");

        //Tie motor objects to hardware map
        rightWheels = hardwareMap.dcMotor.get("motor_1");
        leftWheels = hardwareMap.dcMotor.get("motor_2");
        liftMotor = hardwareMap.dcMotor.get("motor_3");
        armExtender = hardwareMap.dcMotor.get("motor_4");
        armExtender2 = hardwareMap.dcMotor.get("motor_5");
        armExtender3 = hardwareMap.dcMotor.get("motor_6");

        armSensor = hardwareMap.colorSensor.get("color_sensor_1");

        leftBucket = hardwareMap.servo.get("servo_1");
        rightBucket = hardwareMap.servo.get("servo_2");

        leftWheels.setDirection(DcMotor.Direction.REVERSE);
        armExtender2.setDirection(DcMotor.Direction.REVERSE);
        armExtender3.setDirection(DcMotor.Direction.REVERSE);

        leftBucket.setPosition(0.5);
        rightBucket.setPosition(0.5);

        leftBucket.setDirection(Servo.Direction.REVERSE);
    }


    public void readFromLog() throws IOException, InterruptedException //Read the logfile
    {
        telemetry.addData("Read Status", "Read started");
        String line;
        BufferedReader br = new BufferedReader(new FileReader(sourceFile)); //Create reader object
        telemetry.addData("Read Status", "Reader created");

        while ((line = br.readLine()) != null) //Check to make sure the reader is not at the end of the logfile
        {
            telemetry.addData("Read Status", "Data copy started");
            writeToMotors(line); //Start decode process
            telemetry.addData("Read Status", "Data copy complete");
            Thread.sleep(3);
        }

    }

    public void writeToMotors(String input) //Decode the previously read text and send power commands to the motors
    {
        char[] in = (input).toCharArray(); //Declare character array
        double devicePower;
        double device2Power;
        double device3Power;
        double device4Power;
        double device5Power;

        if (String.valueOf(in[0]).equals("L")) //Self-explanatory
        {
            telemetry.addData("Decode Status", "Starting data parse");

            in[0] = ' '; //Set the character at index 0 to null

            devicePower = (Double.parseDouble(new String(in))); //Read the motor power setting
            leftWheels.setPower(devicePower); //Send motor power command

        }else if (String.valueOf(in[0]).equals("R")) //Self-explanatory
    {
        telemetry.addData("Decode Status", "Starting data parse");

        in[0] = ' '; //Set the character at index 0 to null

        device2Power = (Double.parseDouble(new String(in))); //Read the motor power setting
        rightWheels.setPower(device2Power); //Send motor power command

    }else if (String.valueOf(in[0]).equals("E")) //Self-explanatory
        {
            telemetry.addData("Decode Status", "Starting data parse");

            in[0] = ' '; //Set the character at index 0 to null

            device3Power = (Double.parseDouble(new String(in))); //Read the motor power setting
            liftMotor.setPower(device3Power); //Send motor power command

        }else if (String.valueOf(in[0]).equals("P")) //Self-explanatory
        {
            telemetry.addData("Decode Status", "Starting data parse");

            in[0] = ' '; //Set the character at index 0 to null

            device4Power = (Double.parseDouble(new String(in))); //Read the motor power setting
            armExtender.setPower(device4Power);
            armExtender2.setPower(device4Power);
            armExtender3.setPower(device4Power); //Send motor power command

        }else if (String.valueOf(in[0]).equals("T")) //Self-explanatory
        {
            telemetry.addData("Decode Status", "Starting data parse");

            in[0] = ' '; //Set the character at index 0 to null

            device5Power = (Double.parseDouble(new String(in))); //Read the motor power setting
            leftBucket.setPosition(device5Power); //Send motor power command
            rightBucket.setPosition(device5Power); //Send motor power command

        }

        telemetry.addData("Decode Status", "Decode complete");
    }

    @Override
    public void runOpMode() throws InterruptedException {

    }
}
