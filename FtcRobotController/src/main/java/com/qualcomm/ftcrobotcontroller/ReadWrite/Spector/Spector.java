package com.qualcomm.ftcrobotcontroller.ReadWrite.Spector;


import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Spector extends OpMode
{
    int motorCount;
    int servoCount;

    private FileWriter logOutputWriter;
    private String extFilePath = Environment.getExternalStorageDirectory().getAbsolutePath();
    private File sourceFile;
    private BufferedReader br;

    public String filename;

    public Spector(String filename, int motorCount, int servoCount)
    {
        this.motorCount = motorCount;
        this.servoCount = servoCount;
        this.filename = filename;
        this.sourceFile = new File(extFilePath, this.filename);

        try
        {
            br = new BufferedReader(new FileReader(sourceFile));
        }catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void writeInit() throws IOException
    {
        File path = new File(extFilePath);
        File logFile = new File(path, filename);

        if (logFile.exists()) {
            logFile.delete();
        }

        logFile.createNewFile();

        logOutputWriter = new FileWriter(logFile);

    }


    public void writeMotor(DcMotor motor, String tag) throws IOException
    {
        String writeData = tag + motor.getPower() + '\n';

        logOutputWriter.write(writeData);

    }

    public void writeServo(Servo servo, String tag) throws IOException
    {
        String writeData = tag + servo.getPosition() + '\n';

        logOutputWriter.write(writeData);

    }

    public void readMotor(DcMotor motor, String tag) throws IOException, InterruptedException
    {
        String line = br.readLine();
        char[] in = (line).toCharArray();

        char[] tagArray = tag.toCharArray();

        if((String.valueOf(in[0]).equals(String.valueOf(tagArray[0]))) && (String.valueOf(in[1]).equals(String.valueOf(tagArray[1])))) //Self-explanatory
        {
            in[0] = ' ';
            in[1] = ' ';

            double devicePower = (Double.parseDouble(new String(in)));
            motor.setPower(devicePower);
        }
    }

    public void readServo(Servo servo, String tag) throws IOException, InterruptedException
    {
        String line = br.readLine();
        char[] in = (line).toCharArray();

        char[] tagArray = tag.toCharArray();

        if((String.valueOf(in[0]).equals(String.valueOf(tagArray[0]))) && (String.valueOf(in[1]).equals(String.valueOf(tagArray[1])))) //Self-explanatory
        {
            in[0] = ' ';
            in[1] = ' ';

            double devicePower = (Double.parseDouble(new String(in)));
            servo.setPosition(devicePower);
        }
    }

    boolean isFileNull() throws IOException
    {
        return br.readLine() == null;
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
