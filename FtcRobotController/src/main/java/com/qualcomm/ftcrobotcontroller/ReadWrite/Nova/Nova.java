package com.qualcomm.ftcrobotcontroller.ReadWrite.Nova;


import android.os.Environment;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robocol.Telemetry;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Nova //extends LinearOpMode
{
    MotorGhost[] motorBox;
    ServoGhost[] servoBox;

    ArrayList<DcMotor> motorArrayList = new ArrayList<DcMotor>();
    ArrayList<Servo> servoArrayList = new ArrayList<Servo>();

    int motorCount;
    int servoCount;

    double motorDelay = 3e7;
    double servoDelay = 3e7;

    FileWriter logOutputWriter;
    public String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
    public String filename;
    File sourceFile;
    Telemetry telemetry;

    public Nova(String filename, int motorCount, int servoCount, Telemetry telemetry)
    {
        motorBox = new MotorGhost[motorCount];
        servoBox = new ServoGhost[servoCount];
        this.motorCount = motorCount;
        this.servoCount = servoCount;
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

    public void addMotorGhost(MotorGhost ghost)
    {
        motorBox[ghost.getID() - 1] = ghost;
    }

    public void addServoGhost(ServoGhost ghost)
    {
        servoBox[ghost.getID() - 1] = ghost;
    }

    public void mapGhosts(HardwareMap hardwareMap)
    {
        for(int i = 0; i < motorCount; i++)
        {
            motorBox[i].register(hardwareMap);
        }

        for(int i = 0; i < servoCount; i++)
        {
            servoBox[i].register(hardwareMap);
        }
    }

    public void powerGhost(MotorGhost ghost, double controller)
    {
        motorBox[ghost.getID() - 1].setDoubleController(controller);
        motorBox[ghost.getID() - 1] = ghost;
    }

    public void powerGhost(MotorGhost ghost, boolean controllerPositive, boolean controllerNegitive, double power)
    {
        motorBox[ghost.getID() - 1].setBooleanController(controllerPositive, controllerNegitive, power);
        motorBox[ghost.getID() - 1] = ghost;
    }

    public void powerGhost(ServoGhost ghost, boolean controller, double init, double active)
    {
        servoBox[ghost.getID() - 1].setController(controller, init, active);
        servoBox[ghost.getID() - 1] = ghost;
    }

    /**
     * Initializes file writer object and writes a new log-file to disk at the specified directory.
     * @throws IOException If the specified directory is read-only, inaccessible, or does not exist.
     */
    public void writeInit() throws IOException //Create log file and initialize writer
    {
        File path = new File(filePath); //Create a File object containing the external file path
        File logFile = new File(path, filename); //Create the virtual representation of the log file

        //Delete old file, if it exists
        if (logFile.exists()) {
            logFile.delete();
        }

        logFile.createNewFile(); //Write new, empty log file

        logOutputWriter = new FileWriter(logFile); //Initialize the file writer object4

    }


    /**public void writeLogFile() throws IOException//Write data to the file with the specified tag and input value
    {


        for(int i = 0; i < motorCount; i++)
        {
            //resetStartTime();
            double start = System.nanoTime();
            telemetry.addData("Motor NanoTime", start);

            String writeData = /*motorBox[i].getTag() + Double.toString(motorBox[i].motor.getPower()) + '\n';

            logOutputWriter.write(writeData);

            logOutputWriter.flush();

            double finish = System.nanoTime();

            while(finish - start < motorDelay)
            {
                finish = System.nanoTime();

               /* telemetry.addData("Motor NanoTime While", finish);
                try
                {
                    Thread.sleep(0, 10);
                }catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            telemetry.addData("Motor Runtime", System.nanoTime() - start);

        }


        for(int i = 0; i < servoCount; i++)
        {

            double start = System.nanoTime();
            telemetry.addData("Servo NanoTime", start);

            String writeData = /*servoBox[i].getTag() + Double.toString(servoBox[i].servo.getPosition()) + '\n'; //Add a space between the tag and value, and add a line end marker

            logOutputWriter.write(writeData);

            double finish = System.nanoTime();

            logOutputWriter.flush();

            while(finish - start < servoDelay)
            {
                finish = System.nanoTime();
                /*telemetry.addData("Servo NanoTime While", start);
                try
                {
                   Thread.sleep(0, 10);
                }catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            telemetry.addData("Servo Runtime", System.nanoTime() - start);
        }
    }*/

    public void writeLogFile() throws IOException//Write data to the file with the specified tag and input value
    {


        for(DcMotor motor : motorArrayList)
        {
            //resetStartTime();
            double start = System.nanoTime();
            telemetry.addData("Motor NanoTime", start);

            String writeData = /*motorBox[i].getTag() +*/ Double.toString(motor.getPower()) + '\n';

            logOutputWriter.write(writeData);

            logOutputWriter.flush();

            double finish = System.nanoTime();

            while(finish - start < motorDelay)
            {
                finish = System.nanoTime();

               /* telemetry.addData("Motor NanoTime While", finish);
                try
                {
                    Thread.sleep(0, 10);
                }catch(InterruptedException e)
                {
                    e.printStackTrace();
                }*/
            }

            telemetry.addData("Motor Runtime", System.nanoTime() - start);

        }


        for(Servo servo : servoArrayList)
        {

            double start = System.nanoTime();
            telemetry.addData("Servo NanoTime", start);

            String writeData = /*servoBox[i].getTag() +*/ Double.toString(servo.getPosition()) + '\n'; //Add a space between the tag and value, and add a line end marker

            logOutputWriter.write(writeData);

            double finish = System.nanoTime();

            logOutputWriter.flush();

            while(finish - start < servoDelay)
            {
                finish = System.nanoTime();
                /*telemetry.addData("Servo NanoTime While", start);
                try
                {
                   Thread.sleep(0, 10);
                }catch(InterruptedException e)
                {
                    e.printStackTrace();
                }*/
            }
            telemetry.addData("Servo Runtime", System.nanoTime() - start);
        }
    }

    /**public void writeToMotors(boolean abort) throws IOException, InterruptedException
    {
        long cycleNumber = 0;

        boolean breakState = false;
        String line;
        BufferedReader br = new BufferedReader(new FileReader(sourceFile)); //Create reader object

        while (true)
        {
            telemetry.addData("Cycle Number", cycleNumber++);

            for(int i = 0; i < motorCount; i++)
            {
                double start = System.nanoTime();

                line = br.readLine();

                if(line == null)
                {
                    breakState = true;
                    break;
                }

                telemetry.addData("Line Data", line);
                char[] in = (line).toCharArray();

                in[0] = ' ';
                in[1] = ' ';

                double devicePower = (Double.parseDouble(/*new String(in)line)); //Read the motor power setting
                motorBox[i].setDoubleController(devicePower);

                double finish = System.nanoTime();

                while(finish - start < motorDelay)
                {
                    finish = System.nanoTime();
                    /*try
                    {
                        Thread.sleep(0, 10);
                    }catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }

                telemetry.addData("Motor Runtime", finish - start);
            }

            for(int i = 0; i < servoCount; i++)
            {
                double start = System.nanoTime();
                line = br.readLine();

                if(line == null)
                {
                    breakState = true;
                    break;
                }

                telemetry.addData("Line Data", line);

                /*char[] in = (line).toCharArray();

                in[0] = ' ';
                in[1] = ' ';

                double deviceState = (Double.parseDouble(/*new String(in)line));

                servoBox[i].servo.setPosition(deviceState);

                double finish = System.nanoTime();

                while(finish - start < servoDelay)
                {
                    finish = System.nanoTime();

                    telemetry.addData("Servo Boolean", (finish - start < servoDelay));

                    /*try
                    {
                        Thread.sleep(0, 10);
                    }catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }

                telemetry.addData("Servo Runtime", finish - start);
            }

            if(breakState)
            {
                break;
            }
        }


    }*/

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
               /* char[] in = (line).toCharArray();

                in[0] = ' ';
                in[1] = ' ';*/

                double devicePower = (Double.parseDouble(/*new String(in)*/line)); //Read the motor power setting
                motor.setPower(devicePower);

                double finish = System.nanoTime();

                while(finish - start < motorDelay)
                {
                    finish = System.nanoTime();
                    /*try
                    {
                        Thread.sleep(0, 10);
                    }catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }*/
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

                /*char[] in = (line).toCharArray();

                in[0] = ' ';
                in[1] = ' ';*/

                double deviceState = (Double.parseDouble(/*new String(in)*/line));

                servo.setPosition(deviceState);

                double finish = System.nanoTime();

                while(finish - start < servoDelay)
                {
                    finish = System.nanoTime();

                    telemetry.addData("Servo Boolean", (finish - start < servoDelay));

                    /*try
                    {
                        Thread.sleep(0, 10);
                    }catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }*/
                }

                telemetry.addData("Servo Runtime", finish - start);
            }

            if(breakState)
            {
                break;
            }
        }


    }

    /*@Override
    public void runOpMode() throws InterruptedException
    {

    }*/
}

