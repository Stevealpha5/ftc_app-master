package com.qualcomm.ftcrobotcontroller.ReadWrite.Axon;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AxonWrite_obj extends OpMode
{

    double leftCont;
    double rightCont;
    double left2Cont;
    double right2Cont;
    double leftTrig;
    double rightTrig;

    //Declare motors and servos
    DcMotor leftWheels;
    DcMotor rightWheels;
    DcMotor armExtender2;
    DcMotor armExtender3;
    DcMotor liftMotor;
    DcMotor armExtender;

    Servo leftBucket;
    Servo rightBucket;

    //Initialize variables and objects
    FileWriter logOutputWriter;

    String extFilePath = Environment.getExternalStorageDirectory().getAbsolutePath(); //Get the path to the virtualized external storage directory
    String filename; //Input desired log file name. Will overwrite existing file if it has the same name

    public AxonWrite_obj(String filename)
    {
        this.filename = filename;
    }

    public AxonWrite_obj(String filename, String extFilePath)
    {
        this.filename = filename;
        this.filename = extFilePath;
    }

    public void writeInit() throws IOException //Create log file and initialize writer
    {
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

        logOutputWriter = new FileWriter(logFile); //Initialize the file writer object4

    }

    public void writeLogFile(String tag, double value) throws IOException //Write data to the file with the specified tag and input value
    {

        String writeData = tag + " " + value + '\n'; //Add a space between the tag and value, and add a line end marker

        logOutputWriter.write(writeData); //Write data to file
        logOutputWriter.flush(); //Flush internal writer buffer

    }

    public void hardwareInit()
    {
        //Link objects to hardware map
        leftWheels = hardwareMap.dcMotor.get("motor_2");
        rightWheels = hardwareMap.dcMotor.get("motor_1");
        armExtender2 = hardwareMap.dcMotor.get("motor_5");
        armExtender3 = hardwareMap.dcMotor.get("motor_6");
        liftMotor = hardwareMap.dcMotor.get("motor_3");
        armExtender = hardwareMap.dcMotor.get("motor_4");

        leftBucket = hardwareMap.servo.get("servo_1");
        rightBucket = hardwareMap.servo.get("servo_2");

        leftWheels.setDirection(DcMotor.Direction.REVERSE);
        armExtender.setDirection(DcMotor.Direction.REVERSE);
        armExtender2.setDirection(DcMotor.Direction.REVERSE);

        leftBucket.setPosition(0.5);
        rightBucket.setPosition(0.5);

        leftBucket.setDirection(Servo.Direction.REVERSE);
    }

    public void write()
    {
        //Write input data to file
        try {
            writeLogFile("L", leftCont);
            writeLogFile("R", rightCont);
            writeLogFile("E", left2Cont);
            writeLogFile("P", right2Cont);
            writeLogFile("T", leftTrig);

            Thread.sleep(3);

        } catch (IOException e) {
            e.printStackTrace();
            telemetry.addData("Fatal Error", "IOException @ Write");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void init()
    {

    }

    public void loop()
    {
/*
        //Compile write data from inputs
        leftCont = gamepad1.left_stick_y;
        rightCont = gamepad1.right_stick_y;
        left2Cont = gamepad2.left_stick_y;
        right2Cont = gamepad2.right_stick_y;

        leftTrig = gamepad1.left_trigger;
        rightTrig = gamepad1.right_trigger;

        //Make sure that no inputs are out of range
        leftCont = Range.clip(leftCont, -1, 1);
        rightCont = Range.clip(rightCont, -1, 1);
        left2Cont = Range.clip(left2Cont, -1, 1);
        right2Cont = Range.clip(right2Cont, -1, 1);

        leftTrig = Range.clip(leftTrig, 0, 1);
        rightTrig = Range.clip(rightTrig, 0, 1);

        //Actuate devices in accordance with inputs
        leftWheels.setPower(leftCont);
        rightWheels.setPower(rightCont);
        armExtender2.setPower(right2Cont);
        armExtender3.setPower(right2Cont);
        liftMotor.setPower(left2Cont);
        armExtender.setPower(right2Cont);

        leftBucket.setPosition(leftTrig);
        rightBucket.setPosition(leftTrig);

*/
    }
}
