package com.qualcomm.ftcrobotcontroller.ghettoOpmodes;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AxonWrite extends OpMode
{

    double leftCont;
    double rightCont;
    double left2Cont;
    double right2Cont;

    //int delay = 300; //Timer delay for servo latch activation

    //Declare motors and servos
    DcMotor motor_1;
    DcMotor motor_2;
    DcMotor motor_3;
    DcMotor motor_4;
    DcMotor motor_5;
    DcMotor motor_6;

    //Initialize variables and objects
    FileWriter logOutputWriter;

    public void writeInit() throws IOException //Create log file and initialize writer
    {

        String extFilePath = Environment.getExternalStorageDirectory().getAbsolutePath(); //Get the path to the virtualized external storage directory
        String filename = ("controlLogRed.txt"); //Input desired log file name. Will overwrite existing file if it has the same name

        File path = new File(extFilePath); //Create a File object containing the external file path
        File logFile = new File(path, filename); //Create the virtual representation of the log file

        //DEBUG

        telemetry.addData("External Path", extFilePath);
        telemetry.addData("Can Write", logFile.canWrite());
        telemetry.addData("Can Read", logFile.canRead());

        //END DEBUG

        //Delete old file, if it exists
        if (logFile.exists()) {
            telemetry.addData("Old file deleted", logFile.delete());
        }

        boolean fileWriteStatus = logFile.createNewFile(); //Write new, empty log file
        telemetry.addData("File written", fileWriteStatus); //Return file creation status

        telemetry.addData("New logfile exists", logFile.exists()); //Check if the file has written

        logOutputWriter = new FileWriter(logFile); //Initialize the file writer object

    }

    public void writeLogFile(String tag, double value) throws IOException //Write data to the file with the specified tag and input value
    {

        String writeData = tag + " " + value + '\n'; //Add a space between the tag and value, and add a line end marker

        logOutputWriter.write(writeData); //Write data to file
        logOutputWriter.flush(); //Flush internal writer buffer

    }

    public void init()
    {

        //Link objects to hardware map
        motor_1 = hardwareMap.dcMotor.get("motor_1");
        motor_2 = hardwareMap.dcMotor.get("motor_2");
        motor_3 = hardwareMap.dcMotor.get("motor_3");
        motor_4 = hardwareMap.dcMotor.get("motor_4");
        motor_5 = hardwareMap.dcMotor.get("motor_5");
        motor_6 = hardwareMap.dcMotor.get("motor_6");

        motor_2.setDirection(DcMotor.Direction.REVERSE);
        motor_3.setDirection(DcMotor.Direction.REVERSE);

        //Attempt to initialize the logfile
        try {
            writeInit();
        } catch (IOException e) {
            e.printStackTrace();
            telemetry.addData("Fatal Error", "IOException @ Init");
        }

    }

    public void loop()
    {

        //Compile write data from inputs
        leftCont = gamepad1.left_stick_y;
        rightCont = gamepad1.right_stick_y;
        left2Cont = gamepad2.left_stick_y;
        right2Cont = gamepad2.right_stick_y;

        //Make sure that no inputs are out of range
        leftCont = Range.clip(leftCont, -1, 1);
        rightCont = Range.clip(rightCont, -1, 1);
        left2Cont = Range.clip(left2Cont, -1, 1);
        right2Cont = Range.clip(right2Cont, -1, 1);

        //Actuate devices in accordance with inputs
        motor_1.setPower(rightCont);
        motor_2.setPower(leftCont);
        motor_3.setPower(left2Cont);
        motor_5.setPower(right2Cont);

        //Write input data to file
        try {
            writeLogFile("L", leftCont);
            writeLogFile("R", rightCont);
            writeLogFile("E", left2Cont);
            writeLogFile("P", right2Cont);
        } catch (IOException e) {
            e.printStackTrace();
            telemetry.addData("Fatal Error", "IOException @ Write");
        }


    }
}
