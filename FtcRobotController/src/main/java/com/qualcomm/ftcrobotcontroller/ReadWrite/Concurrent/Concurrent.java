package com.qualcomm.ftcrobotcontroller.ReadWrite.Concurrent;


import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.robocol.Telemetry;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Concurrent extends OpMode
{
    MotorGhostConcurrent[] motorBox;
    ServoGhostConcurrent[] servoBox;

    int motorCount;
    int servoCount;

    FileWriter logOutputWriter;
    String extFilePath = Environment.getExternalStorageDirectory().getAbsolutePath(); //Get the path to the virtualized external storage directory
    public String filename; //Input desired log file name. Will overwrite existing file if it has the same name
    File sourceFile;

    public Concurrent(String filename, int motorCount, int servoCount)
    {
        motorBox = new MotorGhostConcurrent[motorCount];
        servoBox = new ServoGhostConcurrent[servoCount];
        this.motorCount = motorCount;
        this.servoCount = servoCount;
        this.filename = filename;
        this.sourceFile = new File(extFilePath, this.filename); //Stores logfile directory and name
    }

    public void addMotorGhost(MotorGhostConcurrent ghost)
    {
        motorBox[ghost.getID() - 1] = ghost;
    }

    public void addServoGhost(ServoGhostConcurrent ghost)
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

    public void powerGhost(MotorGhostConcurrent ghost, double controller)
    {
        motorBox[ghost.getID() - 1].setController(controller);
        motorBox[ghost.getID() - 1] = ghost;
    }

    public void powerGhost(MotorGhostConcurrent ghost, boolean controllerPositive, boolean controllerNegitive, double power)
    {
        motorBox[ghost.getID() - 1].setController(controllerPositive, controllerNegitive, power);
        motorBox[ghost.getID() - 1] = ghost;
    }

    public void powerGhost(ServoGhostConcurrent ghost, boolean controller, double init, double active)
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
        File path = new File(extFilePath); //Create a File object containing the external file path
        File logFile = new File(path, filename); //Create the virtual representation of the log file

        //Delete old file, if it exists
        if (logFile.exists()) {
            logFile.delete();
        }

        logFile.createNewFile(); //Write new, empty log file

        logOutputWriter = new FileWriter(logFile); //Initialize the file writer object4

    }


    public void writeLogFile(int delay, Telemetry telemetry) throws IOException//Write data to the file with the specified tag and input value
    {



            for(int i = 0; i < motorCount; i++)
            {
                //resetStartTime();

                /*if(motorBox[i].isBoolean())
                {
                    if(motorBox[i].getBooleanControllerPositive())
                    {*/
                        String writeData = motorBox[i].getTag() + motorBox[i].motor.getPower() + '\n';

                        logOutputWriter.write(writeData); //Write data to file


                   /* }else if(motorBox[i].getbooleanControllerNegitive())
                    {
                        String writeData = motorBox[i].getTag() + -motorBox[i].getPower() + '\n';

                        logOutputWriter.write(writeData); //Write data to file


                    }else
                    {
                        String writeData = motorBox[i].getTag() + 0 + '\n';

                        logOutputWriter.write(writeData); //Write data to file

                    }

                }else if(!motorBox[i].isBoolean())
                {
                    String writeData = motorBox[i].getTag() + motorBox[i].getDoubleController() + '\n'; //Add a space between the tag and value, and add a line end marker

                    logOutputWriter.write(writeData); //Write data to file
                }

               /* while(getRuntime() < 0.002)
                {
                    try
                    {
                        Thread.sleep(0, 1);
                    }catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                telemetry.addData("motor", getRuntime());*/

            }






            for(int i = 0; i < servoCount; i++)
            {
               // resetStartTime();

                /*if(servoBox[i].getController())
                {*/
                    String writeData = servoBox[i].getTag() + servoBox[i].servo.getPosition() + '\n'; //Add a space between the tag and value, and add a line end marker

                    logOutputWriter.write(writeData); //Write data to file

                /*}else
                {
                    String writeData = servoBox[i].getTag() + servoBox[i].getInitPos() + '\n'; //Add a space between the tag and value, and add a line end marker

                    logOutputWriter.write(writeData); //Write data to file

                }

               /* while(getRuntime() < 0.002)
                {
                    try
                    {
                        Thread.sleep(0, 1);
                    }catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                telemetry.addData("servo", getRuntime());*/
            }






/*
        while(getRuntime() < .01)
        {
            telemetry.addData("waiting", getRuntime());
            try
            {
                Thread.sleep(0, 1);
            }catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        telemetry.addData("Run Time 2", getRuntime());*/

        logOutputWriter.flush();

    }

    /**
     * Reads the log-file and activates the correct hardware devices. Will loop internally
     * until the end of the file is reached. Calls writeToMotors.
     * @param delay Loop delay in milliseconds. Delays are necessary to ensure log-file synchronization.
     * @throws IOException If the assigned log-file is not present, is corrupt, or has invalid permissions.
     */
    public void writeToMotors(int delay, Telemetry telemetry) throws IOException, InterruptedException
    {
        long lineNumber = 0;
        String line;
        BufferedReader br = new BufferedReader(new FileReader(sourceFile)); //Create reader object
        telemetry.addData("Stage", "1");
        while (true)
        {
            telemetry.addData("Stage", "2");
            if(br.readLine() == null)
            {
                telemetry.addData("Stage", "2a");
                break;
            }
            telemetry.addData("Stage", "2b");

            for(int j = 0; j < motorCount + servoCount; j++)
            {
                telemetry.addData("Stage", "3a");

                line = br.readLine();

                if(line == null)
                {
                    break;
                }


                char[] in = (line).toCharArray();

                telemetry.addData("line data", line);
                telemetry.addData("line number", lineNumber++);

                //resetStartTime();

                for(int i = 0; i < motorCount; i++)
                {
                   telemetry.addData("motor i", i);

                    if((String.valueOf(in[0]).equals(String.valueOf(motorBox[i].getTagCharacter(0)))) && (String.valueOf(in[1]).equals(String.valueOf(motorBox[i].getTagCharacter(1))))) //Self-explanatory
                    {
                        telemetry.addData("motor if", true);
                        in[0] = ' '; //Set the character at index 0 to null
                        in[1] = ' '; //Set the character at index 1 to null

                        double devicePower = (Double.parseDouble(new String(in))); //Read the motor power setting
                        motorBox[i].setController(devicePower);
                        break;
                    }

                    telemetry.addData("motor if", false);
                }

                /*while(getRuntime() < 0.002)
                {
                    Thread.sleep(0, 1);
                }
                telemetry.addData("motor", getRuntime());

                resetStartTime();*/

                for(int i = 0; i < servoCount; i++)
                {
                   // telemetry.addData("servo i", i);
                    if((String.valueOf(in[0]).equals(String.valueOf(servoBox[i].getTagCharacter(0)))) && (String.valueOf(in[1]).equals(String.valueOf(servoBox[i].getTagCharacter(1))))) //Self-explanatory
                    {
                        //telemetry.addData("servo if", true);
                        in[0] = ' '; //Set the character at index 0 to null
                        in[1] = ' '; //Set the character at index 1 to null

                        telemetry.addData(";)", new String(in));
                        telemetry.addData(";P", Double.parseDouble(new String(in)));
                        double deviceState = (Double.parseDouble(new String(in))); //Read the motor power setting

                        telemetry.addData("WTF", deviceState);
                        servoBox[i].servo.setPosition(deviceState);//setController(deviceState, servoBox[i].getInitPos(), servoBox[i].getActivePos());
                        break;
                    }

                    //telemetry.addData("servo if", false);
                }

               /* while(getRuntime() < 0.002)
                {
                    Thread.sleep(0, 1);
                }

            telemetry.addData("servo", getRuntime());*/
           }
        }
    }

    @Override
    public void init()
    {

    }

    @Override
    public void loop()
    {

    }
}
