package com.qualcomm.ftcrobotcontroller.ReadWrite.Nova;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class WhatTheHell extends LinearOpMode
{
    int leftJoy;
    int rightJoy;

    MotorGhost rightDrive;
    MotorGhost leftDrive;

    DcMotor DcRightDrive;
    DcMotor DcLeftDrive;

    public String fileName = "novaDriverLog.txt";

    Nova nova = new Nova(fileName, 2, 1, telemetry);

    boolean hasRun = false;

    String extFilePath = Environment.getExternalStorageDirectory().getAbsolutePath();

    File sourceFile = new File(extFilePath, fileName); //Stores logfile directory and name




    @Override
    public void runOpMode()
    {
        try
        {
            waitForStart();
        }catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        DcRightDrive = hardwareMap.dcMotor.get("motor_1");
        DcLeftDrive = hardwareMap.dcMotor.get("motor_2");
        DcLeftDrive.setDirection(DcMotor.Direction.REVERSE);

        rightDrive = new MotorGhost("RD", 1);
        leftDrive = new MotorGhost("LD", 2);

        nova.addMotorGhost(rightDrive);
        nova.addMotorGhost(leftDrive);

        if(!hasRun)
        {

            try {
                String line;
                BufferedReader br = new BufferedReader(new FileReader(sourceFile)); //Create reader object

                //line = br.readLine();
               // char[] up = line.toCharArray();
                //telemetry.addData("If", (String.valueOf(up[0]).equals(String.valueOf(nova.motorBox[0].getTagCharacter(0)))) && (String.valueOf(up[1]).equals(String.valueOf(nova.motorBox[1].getTagCharacter(1)))));

                while (/*(line = br.readLine()) != null*/true) //Check to make sure the reader is not at the end of the logfile
                {
                    line = br.readLine();
                    if(line == null)
                    {
                        break;
                    }

                    telemetry.addData("line", line);

                    try
                    {
                        Thread.sleep(4);
                    }catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }

                    char[] in = (line).toCharArray();

                   for(int i = 0; i < nova.motorCount; i++)
                    {
                        telemetry.addData(":|", (String.valueOf(in[0]).equals(String.valueOf(nova.motorBox[i].getTagCharacter(0)))) && (String.valueOf(in[1]).equals(String.valueOf(nova.motorBox[i].getTagCharacter(1)))));
                        if( (String.valueOf(in[0]).equals(String.valueOf(nova.motorBox[i].getTagCharacter(0)))) && (String.valueOf(in[1]).equals(String.valueOf(nova.motorBox[i].getTagCharacter(1))))) //Self-explanatory
                        {
                            telemetry.addData("in 0", in[0]);
                            telemetry.addData("in 1", in[1]);
                            telemetry.addData("in 2", in[2]);
                            telemetry.addData("motor box 0", nova.motorBox[0].getTagCharacter(0));
                            in[0] = ' '; //Set the character at index 0 to null
                            in[1] = ' '; //Set the character at index 1 to null
                            telemetry.addData("in 2", in[2]);
                            telemetry.addData("in 3", in[3]);
                            telemetry.addData("in 4", in[4]);
                            String step = new String(in);
                            telemetry.addData("step", step);
                            double devicePower = (Double.parseDouble(new String(in))); //Read the motor power setting
                            telemetry.addData("deice power", Double.parseDouble(step));
                            telemetry.addData("i", i);
                            nova.motorBox[i].setDoubleController(devicePower);
                            try
                            {
                                Thread.sleep(4);
                            }catch(InterruptedException e)
                            {
                                e.printStackTrace();
                            }


                        }



                    }



                }
            } catch (IOException e) {
                e.printStackTrace();
                telemetry.addData("Fatal Error", "IOException @ Read");
            }

            hasRun = true;


        }else{
            telemetry.addData("Status", "Program Complete");
        }

    }


}
