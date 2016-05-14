package com.qualcomm.ftcrobotcontroller.ghettoOpmodes;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class PITA_Read extends LinearOpMode
{

    //Initialize motor objects
    DcMotor motor_1;
    DcMotor motor_2;
    DcMotor motor_3;
    DcMotor motor_4;
    DcMotor motor_5;

    String path = Environment.getExternalStorageDirectory().getAbsolutePath(); //Get logfile storage directory
    String filename = ("controlLogRed.txt"); //Input name of logfile

    File sourceFile = new File(path, filename); //Stores logfile directory and name

    @Override
    public void runOpMode()
    {

        telemetry.addData("Read Status", "Program started");

        //Tie motor objects to hardware map
        motor_1 = hardwareMap.dcMotor.get("motor_1");
        motor_2 = hardwareMap.dcMotor.get("motor_2");
        motor_3 = hardwareMap.dcMotor.get("motor_3");
        motor_4 = hardwareMap.dcMotor.get("motor_4");
        motor_5 = hardwareMap.dcMotor.get("motor_5");

        try {
            readFromLog(sourceFile, "R", "L", "E", "P", motor_1, motor_2, motor_3, motor_5); //Try to read logfile
        } catch (IOException e) {
            e.printStackTrace();
            telemetry.addData("Fatal Error", "IOException");
        } catch (InterruptedException e) {
            e.printStackTrace();
            telemetry.addData("Fatal Error", "InterruptedException");
        }
        telemetry.addData("Read Status", "Read/decode complete"); //Indicate successful completion of read

    }


    public void readFromLog(File file, String tag, String tag2, String tag3, String tag4, DcMotor device, DcMotor device2, DcMotor device3, DcMotor device4) throws IOException, InterruptedException //Read the logfile
    {
        telemetry.addData("Read Status", "Read started");
        String line;
        BufferedReader br = new BufferedReader(new FileReader(file)); //Create reader object
        telemetry.addData("Read Status", "Reader created");

        while ((line = br.readLine()) != null) //Check to make sure the reader is not at the end of the logfile
        {
            telemetry.addData("Read Status", "Data copy started");
            writeToMotors(line, tag, tag2, tag3, tag4, device, device2, device3, device4); //Start decode process
            Thread.sleep(7); //Synchronize with logfile writer timing interval
            telemetry.addData("Read Status", "Data copy complete");
        }
    }

    public void writeToMotors(String input, String tag, String tag2,String tag3, String tag4, DcMotor device, DcMotor device2, DcMotor device3, DcMotor device4) //Decode the previously read text and send power commands to the motors
    {
        char[] in = (input).toCharArray(); //Declare character array
        double devicePower;
        double device2Power;
        double device3Power;
        double device4Power;

        if (String.valueOf(in[0]).equals(tag)) //Self-explanatory
        {
            telemetry.addData("Decode Status", "Starting data parse");

            in[0] = ' '; //Set the character at index 0 to null

            devicePower = (Double.parseDouble(new String(in))); //Read the motor power setting
            device.setPower(devicePower); //Send motor power command

        }else if (String.valueOf(in[0]).equals(tag2)) //Self-explanatory
    {
        telemetry.addData("Decode Status", "Starting data parse");

        in[0] = ' '; //Set the character at index 0 to null

        device2Power = (Double.parseDouble(new String(in))); //Read the motor power setting
        device2.setPower(device2Power); //Send motor power command

    }else if (String.valueOf(in[0]).equals(tag3)) //Self-explanatory
        {
            telemetry.addData("Decode Status", "Starting data parse");

            in[0] = ' '; //Set the character at index 0 to null

            device3Power = (Double.parseDouble(new String(in))); //Read the motor power setting
            device3.setPower(device3Power * -1); //Send motor power command
            motor_4.setPower(device3Power);

        }else if (String.valueOf(in[0]).equals(tag4)) //Self-explanatory
        {
            telemetry.addData("Decode Status", "Starting data parse");

            in[0] = ' '; //Set the character at index 0 to null

            device4Power = (Double.parseDouble(new String(in))); //Read the motor power setting
            device4.setPower(device4Power * -1); //Send motor power command

        }

        telemetry.addData("Decode Status", "Decode complete");
    }

}
