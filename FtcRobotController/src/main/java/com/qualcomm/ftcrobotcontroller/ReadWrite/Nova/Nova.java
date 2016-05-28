package com.qualcomm.ftcrobotcontroller.ReadWrite.Nova;


import android.os.Environment;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robocol.Telemetry;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Nova
{
    ArrayList<DcMotor> motorArrayList = new ArrayList<DcMotor>();
    ArrayList<Servo> servoArrayList = new ArrayList<Servo>();

    double motorDelay = 1.7e7;
    double servoDelay = 1.7e7;

    FileWriter logOutputWriter;
    public String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
    public String filename;
    File sourceFile;
    Telemetry telemetry;

    public Nova(String filename,Telemetry telemetry)
    {

        this.filename = filename;
        this.sourceFile = new File(filePath, this.filename); //Stores logfile directory and name
        this.telemetry = telemetry;
    }

    public void addMotor(DcMotor motor)
    {
        motorArrayList.add(motor);
    }

    public void addServo(Servo servo)
    {
        servoArrayList.add(servo);
    }


    public void writeInit() throws IOException //Create log file and initialize writer
    {
        File path = new File(filePath); //Create a File object containing the external file path
        File logFile = new File(path, filename); //Create the virtual representation of the log file

        //Delete old file, if it exists
        if (logFile.exists())
        {
            logFile.delete();
        }

        logFile.createNewFile(); //Write new, empty log file

        logOutputWriter = new FileWriter(logFile); //Initialize the file writer object4

    }

    public void writeLogFile() throws IOException//Write data to the file with the specified tag and input value
    {

        for(DcMotor motor : motorArrayList)
        {
            double start = System.nanoTime();
            telemetry.addData("Motor NanoTime", start);

            String writeData = Double.toString(motor.getPower()) + '\n';

            logOutputWriter.write(writeData);

            logOutputWriter.flush();

            double finish = System.nanoTime();

            while(finish - start < motorDelay)
            {
                finish = System.nanoTime();
            }
            telemetry.addData("Motor Runtime", System.nanoTime() - start);
        }

        for(Servo servo : servoArrayList)
        {
            double start = System.nanoTime();
            telemetry.addData("Servo NanoTime", start);

            String writeData = Double.toString(servo.getPosition()) + '\n'; //Add a space between the tag and value, and add a line end marker

            logOutputWriter.write(writeData);

            double finish = System.nanoTime();

            logOutputWriter.flush();

            while(finish - start < servoDelay)
            {
                finish = System.nanoTime();
            }

            telemetry.addData("Servo Runtime", System.nanoTime() - start);
        }
    }

    public void writeToMotors() throws IOException, InterruptedException
    {
        long cycleNumber = 0;

        boolean breakState = false;
        String line;
        BufferedReader br = new BufferedReader(new FileReader(sourceFile)); //Create reader object

        while (true)
        {
            telemetry.addData("Cycle Number", cycleNumber++);

            for(DcMotor motor : motorArrayList)
            {
                double start = System.nanoTime();

                line = br.readLine();

                if(line == null)
                {
                    breakState = true;
                    break;
                }

                telemetry.addData("Line Data", line);

                double devicePower = (Double.parseDouble(line));
                motor.setPower(devicePower);

                double finish = System.nanoTime();

                while(finish - start < motorDelay)
                {
                    finish = System.nanoTime();
                }

                telemetry.addData("Motor Runtime", finish - start);
            }

            for(Servo servo : servoArrayList)
            {
                double start = System.nanoTime();
                line = br.readLine();

                if(line == null)
                {
                    breakState = true;
                    break;
                }

                telemetry.addData("Line Data", line);

                double deviceState = (Double.parseDouble(line));

                servo.setPosition(deviceState);

                double finish = System.nanoTime();

                while(finish - start < servoDelay)
                {
                    finish = System.nanoTime();

                    telemetry.addData("Servo Boolean", (finish - start < servoDelay));
                }

                telemetry.addData("Servo Runtime", finish - start);
            }

            if(breakState)
            {
                break;
            }
        }
    }
}

