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

    public Nova(String filename, Telemetry telemetry)
    {
        this.filename = filename;
        this.sourceFile = new File(filePath, this.filename);
        this.telemetry = telemetry;
    }

    public void writeInit() throws IOException
    {
        File path = new File(filePath);
        File logFile = new File(path, filename);


        if (logFile.exists()) {
            logFile.delete();
        }

        logFile.createNewFile();

        logOutputWriter = new FileWriter(logFile);

    }

    public void addMotor(DcMotor motor)
    {
        motorArrayList.add(motor);
    }

    public void addServo(Servo servo)
    {
        servoArrayList.add(servo);
    }

    public void writeLogFile() throws IOException
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

            String writeData = Double.toString(servo.getPosition()) + '\n';

            logOutputWriter.write(writeData);

            logOutputWriter.flush();

            double finish = System.nanoTime();

            while(finish - start < servoDelay)
            {
                finish = System.nanoTime();
            }
            telemetry.addData("Servo Runtime", System.nanoTime() - start);
        }


    }

    public void logWriterStop()
    {
        try
        {
            logOutputWriter.close();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void writeToMotors() throws IOException, InterruptedException
    {
        long cycleNumber = 0;

        boolean breakState = false;
        String line;
        BufferedReader br = new BufferedReader(new FileReader(sourceFile));

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

