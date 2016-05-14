package com.qualcomm.ftcrobotcontroller.ghettoOpmodes;


import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PITA_Write extends OpMode
{

    double leftCont;
    double rightCont;
    double left2Cont;
    double right2Cont;

    DcMotor motor_1;
    DcMotor motor_2;
    DcMotor motor_3;
    DcMotor motor_4;

    FileWriter logOutputWriter;

    String leftJoy;
    String rightJoy;
    String left2Joy;
    String right2Joy;

    @Override
    public void init()
    {

        motor_1 = hardwareMap.dcMotor.get("motor_1");
        motor_2 = hardwareMap.dcMotor.get("motor_2");
        motor_3 = hardwareMap.dcMotor.get("motor_3");
        motor_4 = hardwareMap.dcMotor.get("motor_4");

        try {
            writeInit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void loop()
    {
        leftCont = gamepad1.left_stick_y;
        rightCont = gamepad1.right_stick_y;
        left2Cont = gamepad2.left_stick_y;
        right2Cont = gamepad2.right_stick_y;

        leftCont = Range.clip(leftCont, -1, 1);
        rightCont = Range.clip(rightCont, -1, 1);
        left2Cont = Range.clip(left2Cont, -1, 1);
        right2Cont = Range.clip(right2Cont, -1, 1);

        motor_1.setPower(rightCont);
        motor_2.setPower(leftCont);

        motor_3.setPower(-1 * left2Cont);
        motor_4.setPower(-1 * right2Cont);

            try {
                telemetry.addData("Function started", "true");
                writeLogFile();
                Thread.sleep(5);
            }catch (InterruptedException e) {
                telemetry.addData("Fatal Error", "InterruptedException");
            }
             catch (IOException e) {
                telemetry.addData("Fatal Error", "IOException");
            }
    }


    public void writeInit() throws IOException
    {
        telemetry.addData("Write Status", "Started");

        String extFilePath = Environment.getExternalStorageDirectory().getAbsolutePath(); //Get the path to the virtualized external storage directory
        String filename = ("controlLog.txt");


        File path = new File(extFilePath); //Create a File object containing the external file path

        telemetry.addData("Write Status", "Variables initialized");

        File logFile = new File(path, filename); //Create the virtual representation of the log file

        telemetry.addData("Write Status", "File initialized");

        //DEBUG

        telemetry.addData("External Path", extFilePath);
        telemetry.addData("Can Write", logFile.canWrite());
        telemetry.addData("Can Read", logFile.canRead());

        //END DEBUG

            boolean fileExists = (logFile.exists()); //Check if the old logfile exists
            telemetry.addData("Old logfile exists", fileExists);

            if (fileExists) {
                telemetry.addData("Old file deleted", logFile.delete());
            }

            boolean fileWriteStatus = logFile.createNewFile(); //Write new, empty log file
            telemetry.addData("Write Status", "File created");
            telemetry.addData("File written", fileWriteStatus); //Return file creation status

            fileExists = (logFile.exists()); //Check if the file has actually written to disk
            telemetry.addData("New logfile exists", fileExists);

            logOutputWriter = new FileWriter(logFile); //Create the file writer object
            telemetry.addData("Write Status", "File writer created");

        logOutputWriter.write("395234\n"); //Log parity check
    }

    public void writeLogFile() throws IOException
    {
            leftJoy = "L" + " " + leftCont + '\n';
            rightJoy = "R" + " " + rightCont + '\n';
            left2Joy = "L2" + " " + left2Cont + '\n';
            right2Joy = "R2" + " " + right2Cont + '\n';

            telemetry.addData("Controller 1 Right Stick Status", rightCont);
            telemetry.addData("Controller 1 Left Stick Status", leftCont);
            telemetry.addData("Controller 2 Left Stick Status", left2Cont);
            telemetry.addData("Controller 2 Right Stick Status", right2Cont);

            logOutputWriter.write(leftJoy); //Write left joystick input status
            logOutputWriter.write(rightJoy); //Write right joystick input status
            logOutputWriter.write(left2Joy); //Write left joystick 2 input status
            logOutputWriter.write(right2Joy); //Write right joystick 2 input status

            telemetry.addData("Write Status", "Data written");

            logOutputWriter.flush();

            telemetry.addData("Write Status", "Flushed, done");
    }

}
