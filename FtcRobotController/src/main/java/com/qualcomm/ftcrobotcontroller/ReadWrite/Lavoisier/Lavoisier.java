package com.qualcomm.ftcrobotcontroller.ReadWrite.Lavoisier;


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

public class Lavoisier
{
    ArrayList<DcMotor> motorArrayList = new ArrayList<DcMotor>();
    ArrayList<Servo> servoArrayList = new ArrayList<Servo>();

    double motorDelay = 1.7e7;
    double servoDelay = 1.7e7;

    public String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
    public String filename;
    File sourceFile;
    Telemetry telemetry;

    public Lavoisier(String filename, Telemetry telemetry)
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

    public void start()
    {
        Thread writer = new Thread(new RecordThread(motorArrayList, servoArrayList, motorDelay, servoDelay, telemetry, filename));

        writer.start();
    }

    public void writeToMotors() throws IOException, InterruptedException
    {
        long cycleNumber = 0;

        boolean breakState = false;
        String line;
        BufferedReader br = new BufferedReader(new FileReader(sourceFile)); //Create reader object

        while (cycleNumber < 250)
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

class RecordThread implements Runnable
{
    ArrayList<DcMotor> motorArrayList;
    ArrayList<Servo> servoArrayList;

    FileWriter logOutputWriter;
    public String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
    public String filename;
    File sourceFile;
    Telemetry telemetry;

    double motorDelay;
    double servoDelay;

    public RecordThread(ArrayList<DcMotor> motorArrayList, ArrayList<Servo> servoArrayList, double motorDelay, double servoDelay, Telemetry telemetry, String filename)
    {
        this.motorArrayList = motorArrayList;
        this.servoArrayList = servoArrayList;

        this.motorDelay = motorDelay;
        this.servoDelay = servoDelay;

        this.telemetry = telemetry;

        this.filename = filename;

        this.sourceFile = new File(filePath, filename); //Stores logfile directory and name
    }

    @Override
    public void run()
    {
        try
        {
            writeInit();

            int counter = 0;

            while(!Thread.currentThread().isInterrupted())
            {
                writeLogFile();
                counter++;
            }

        }catch(IOException e)
        {
            e.printStackTrace();
        }

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

            String writeData = Double.toString(motor.getPower()) + '\n';

            logOutputWriter.write(writeData);

            logOutputWriter.flush();

            double finish = System.nanoTime();

            while(finish - start < motorDelay)
            {
                finish = System.nanoTime();
            }
        }

        for(Servo servo : servoArrayList)
        {
            double start = System.nanoTime();

            String writeData = Double.toString(servo.getPosition()) + '\n'; //Add a space between the tag and value, and add a line end marker

            logOutputWriter.write(writeData);

            double finish = System.nanoTime();

            logOutputWriter.flush();

            while(finish - start < servoDelay)
            {
                finish = System.nanoTime();
            }
        }
    }
}

