package com.qualcomm.ftcrobotcontroller.ReadWrite.Simulacrum;


import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * The core Simulacrum class. Contains most of the code responsible for
 * record/replay operations.
 */
public class Simulacrum extends LinearOpMode
{

    //Declare motors and servos
    DcMotor leftWheels;
    DcMotor rightWheels;
    DcMotor armExtender2;
    DcMotor liftMotor;
    DcMotor armExtender;
    DcMotor hangMotor;

    Servo leftBucket;
    Servo rightBucket;
    Servo collectorDrive;
    Servo collectorDrive2;
    Servo climberCarrier;

    char[] in; //Declare character array
    double devicePower;
    double device2Power;
    double device3Power;
    double device4Power;
    double device5Power;
    double device6Power;
    double device7Power;
    double device8Power;

    //Initialize variables and objects
    FileWriter logOutputWriter;

    String extFilePath = Environment.getExternalStorageDirectory().getAbsolutePath(); //Get the path to the virtualized external storage directory
    public String filename; //Input desired log file name. Will overwrite existing file if it has the same name

    File sourceFile;

    /**
     *Constructs a new instance of the Simulacrum object.
     * @param filename Name of the file that read/write operations should be conducted on.
     * @param leftWheels Motor object currently assigned to the left wheel assembly.
     * @param rightWheels Motor object currently assigned to the right wheel assembly.
     * @param armExtender2 Motor object currently assigned to the secondary arm motor.
     * @param liftMotor Motor object currently assigned to the arm angle motor.
     * @param armExtender Motor object currently assigned to the primary arm motor.
     * @param hangMotor Motor object currently assigned to the tape-measure drive motor.
     * @param climberCarrier Servo object currently assigned to the climber carrier assembly.
     * @param leftBucket Servo object currently assigned to the left bucket driver.
     * @param rightBucket Servo object currently assigned to the right bucket driver.
     * @param collectorDrive Servo object currently assigned to the primary gatherer drive.
     * @param collectorDrive2 Servo object currently assigned to the secondary gatherer drive.
     */
    public Simulacrum(String filename, DcMotor leftWheels, DcMotor rightWheels, DcMotor armExtender2, DcMotor liftMotor, DcMotor armExtender, DcMotor hangMotor, Servo climberCarrier, Servo leftBucket, Servo rightBucket, Servo collectorDrive, Servo collectorDrive2)
    {
        this.leftWheels = leftWheels;
        this.rightWheels = rightWheels;
        this.armExtender2 = armExtender2;
        this.liftMotor = liftMotor;
        this.armExtender = armExtender;
        this.hangMotor = hangMotor;
        this.climberCarrier = climberCarrier;
        this.leftBucket = leftBucket;
        this.rightBucket = rightBucket;
        this.collectorDrive = collectorDrive;
        this.collectorDrive2 = collectorDrive2;
        this.sourceFile = new File(extFilePath, filename); //Stores logfile directory and name
        this.filename = filename;
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

    /**
     *
     * @param tag Two-character string identifying the controller input to be written.
     * @param value Double representing the controller value to be written.
     * @throws IOException If the assigned log-file is not present, is corrupt, or has invalid permissions.
     */
    public void writeLogFile(String tag, double value) throws IOException //Write data to the file with the specified tag and input value
    {

        String writeData = tag + " " + value + '\n'; //Add a space between the tag and value, and add a line end marker

        logOutputWriter.write(writeData); //Write data to file
        logOutputWriter.flush(); //Flush internal writer buffer

    }

    /**
     * Reads the log-file and activates the correct hardware devices. Will loop internally
     * until the end of the file is reached. Calls writeToMotors.
     * @param delay Loop delay in milliseconds. Delays are necessary to ensure log-file synchronization.
     * @throws IOException If the assigned log-file is not present, is corrupt, or has invalid permissions.
     */
    public void readFromLog(int delay) throws IOException
    {

            String line;
            BufferedReader br = new BufferedReader(new FileReader(sourceFile)); //Create reader object

            SimpleDateFormat time = new SimpleDateFormat("ssSSSS");
            SimpleDateFormat time2 = new SimpleDateFormat("ssSSSS");

            int initTime;
            int logTime;
            int runTime;
            int differential;

            String timeLog;
            String timeLog2;

            while ((line = br.readLine()) != null) //Check to make sure the reader is not at the end of the logfile
            {
                timeLog = time.format(Calendar.getInstance().getTime());
                initTime = (Integer.parseInt(timeLog));

                writeToMotors(line); //Start decode process

                timeLog2 = time2.format(Calendar.getInstance().getTime());
                logTime = (Integer.parseInt(timeLog2));

                runTime = logTime - initTime;

                differential = delay - runTime;

                if(differential > 0)
                {
                    try {
                        Thread.sleep(differential);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

    }

    /**
     * Internally called method from readFromLog. Should not be called externally.
     * @param input Current line in the log-file.
     */
    public void writeToMotors(String input) //Decode the previously read text and send power commands to the motors
    {

        in = (input).toCharArray();

        if (String.valueOf(in[0]).equals("L") && (String.valueOf(in[1]).equals("1"))) //Self-explanatory
        {

            in[0] = ' '; //Set the character at index 0 to null
            in[1] = ' '; //Set the character at index 1 to null

            devicePower = (Double.parseDouble(new String(in))); //Read the motor power setting
            leftWheels.setPower(devicePower); //Send motor power command

        }else if (String.valueOf(in[0]).equals("R") && String.valueOf(in[1]).equals("1")) //Self-explanatory
        {

            in[0] = ' '; //Set the character at index 0 to null
            in[1] = ' '; //Set the character at index 1 to null

            device2Power = (Double.parseDouble(new String(in))); //Read the motor power setting
            rightWheels.setPower(device2Power); //Send motor power command

        }else if (String.valueOf(in[0]).equals("L") && String.valueOf(in[1]).equals("2")) //Self-explanatory
        {

            in[0] = ' '; //Set the character at index 0 to null
            in[1] = ' '; //Set the character at index 1 to null

            device3Power = (Double.parseDouble(new String(in))); //Read the motor power setting
            liftMotor.setPower(device3Power); //Send motor power command

        }else if (String.valueOf(in[0]).equals("R") && String.valueOf(in[1]).equals("2")) //Self-explanatory
        {

            in[0] = ' '; //Set the character at index 0 to null
            in[1] = ' '; //Set the character at index 1 to null

            device4Power = (Double.parseDouble(new String(in))); //Read the motor power setting
            armExtender.setPower(device4Power);
            armExtender2.setPower(device4Power);

        }else if (String.valueOf(in[0]).equals("R") && String.valueOf(in[1]).equals("B")) //Self-explanatory
        {

            in[0] = ' '; //Set the character at index 0 to null
            in[1] = ' '; //Set the character at index 1 to null

            device5Power = (Double.parseDouble(new String(in))); //Read the motor power setting
            leftBucket.setPosition(device5Power); //Send servo power command
            rightBucket.setPosition(device5Power); //Send servo power command

        }else if (String.valueOf(in[0]).equals("L") && String.valueOf(in[1]).equals("S")) //Self-explanatory
        {

            in[0] = ' '; //Set the character at index 0 to null
            in[1] = ' '; //Set the character at index 1 to null

            device6Power = (Double.parseDouble(new String(in))); //Read the motor power setting
            collectorDrive.setPosition(device6Power); //Send servo power command
            collectorDrive2.setPosition(device6Power); //Send servo power command

        }else if (String.valueOf(in[0]).equals("D") && String.valueOf(in[1]).equals("U")) //Self-explanatory
        {

            in[0] = ' '; //Set the character at index 0 to null
            in[1] = ' '; //Set the character at index 1 to null

            device7Power = (Double.parseDouble(new String(in))); //Read the motor power setting
            climberCarrier.setPosition(device7Power);

        }else if (String.valueOf(in[0]).equals("L") && String.valueOf(in[1]).equals("T")) //Self-explanatory
        {

            in[0] = ' '; //Set the character at index 0 to null
            in[1] = ' '; //Set the character at index 1 to null

            device8Power = (Double.parseDouble(new String(in))); //Read the motor power setting
            hangMotor.setPower(device8Power);

        }else if (String.valueOf(in[0]).equals("R") && String.valueOf(in[1]).equals("T")) //Self-explanatory
        {

            in[0] = ' '; //Set the character at index 0 to null
            in[1] = ' '; //Set the character at index 1 to null

            device8Power = (Double.parseDouble(new String(in))); //Read the motor power setting
            hangMotor.setPower(device8Power);

        }

    }

    /**
     *Not used. Required by parent class LinearOpMode.
     * @see com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
     */
    @Override
    public void runOpMode() //Not used, required by extension class
    {

    }
}
