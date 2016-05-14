package com.qualcomm.ftcrobotcontroller.ReadWrite.PITA;

import android.annotation.TargetApi;
import android.os.Build;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PITA_Read extends OpMode
{
    DcMotor motor_1;
    DcMotor motor_2;
    DcMotor motor_3;
    DcMotor motor_4;

    @Override
    public void init()
    {

        motor_1 = hardwareMap.dcMotor.get("motor_1");
        motor_2 = hardwareMap.dcMotor.get("motor_2");
        motor_3 = hardwareMap.dcMotor.get("motor_3");
        motor_4 = hardwareMap.dcMotor.get("motor_4");

    }


    @Override
    public void loop()
    {

        telemetry.addData("Report", "Report 1");

        try {
            read("edd");
        } catch (IOException e) {
            e.printStackTrace();
            telemetry.addData("IOException", "IOException");
        } catch (InterruptedException e) {
            e.printStackTrace();
            telemetry.addData("InterruptedException", "InterruptedException");
        }
        telemetry.addData("Report3", "Fuck Yah");
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void read(String fileName) throws IOException, InterruptedException
    {
        telemetry.addData("Report", "Report 3");
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        telemetry.addData("Report", "Report 4");

        while ((line = br.readLine()) != null)
        {
            telemetry.addData("Report", "Report 5");
            writeToMotors(line);
            Thread.sleep(5);
            telemetry.addData("Report", "Report 6");
        }
    }

    public void writeToMotors(String input)
    {
        char[] in = ("" + input).toCharArray();

        telemetry.addData("Report", "Report 7");
        if (in[0] == 'R')
        {
            telemetry.addData("Report#", "Report 8r");
            in[0] = ' ';
            motor_3.setPower(Double.parseDouble(new String(in)));
            motor_4.setPower(Double.parseDouble(new String(in)));
            telemetry.addData("Report$", "Report 9r");
        } else if (in[0] == 'L') {
            telemetry.addData("Report@", "Report 8l");
            in[0] = ' ';
            motor_1.setPower(-1 * (Double.parseDouble(new String(in))));
            motor_2.setPower(-1 * (Double.parseDouble(new String(in))));
            telemetry.addData("Report!", "Report 9l");
        }
    }

    /*public void read(String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(fileName + ".txt");

        if ( inputStream != null )
        {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString = "";
            StringBuilder stringBuilder = new StringBuilder();

            while ( (receiveString = bufferedReader.readLine()) != null )
            {
                stringBuilder.append(receiveString);
            }

            inputStream.close();
            ret = stringBuilder.toString();
        }
    }*/

}
