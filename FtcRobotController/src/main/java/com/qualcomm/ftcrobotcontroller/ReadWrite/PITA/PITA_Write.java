package com.qualcomm.ftcrobotcontroller.ReadWrite.PITA;


import android.content.Context;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PITA_Write extends OpMode
{
    static Context fileContext;

    double leftCont;
    double rightCont;

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
    public void init_loop()
    {

    }


    @Override
    public void loop()
    {
        leftCont = gamepad1.left_stick_y;
        rightCont = gamepad1.right_stick_y;

        leftCont = Range.clip(leftCont, -1, 1);
        rightCont = Range.clip(rightCont, -1, 1);

        motor_1.setPower(-1 * leftCont);
        motor_2.setPower(-1 * leftCont);

        motor_3.setPower(rightCont);
        motor_4.setPower(rightCont);

        try {
            write("edd");
        } catch (InterruptedException e) {
            e.printStackTrace();
            telemetry.addData("InterruptedException", "InterruptedException");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            telemetry.addData("FileNotFoundException", "FileNotFoundException");
        } catch (IOException e) {
            e.printStackTrace();
            telemetry.addData("InterruptedException", "InterruptedException");
        }

        telemetry.addData("report#", "done");
    }


    public void write(String filename) throws InterruptedException, IOException
    {
        telemetry.addData("report", "report1");

        String leftJoy = "L" + leftCont + '\n';
        String rightJoy = "R" + rightCont + '\n';
        FileOutputStream outputStream;
        telemetry.addData("report", "report2");
        try {
            telemetry.addData("report", "report2.4");
            outputStream = new FileOutputStream(filename);//fileContext.openFileOutput(filename, Context.MODE_PRIVATE);
            telemetry.addData("report", "report3");
            outputStream.write(leftJoy.getBytes());
            outputStream.write(rightJoy.getBytes());
            outputStream.close();
            telemetry.addData("report1", "!!!!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        telemetry.addData("report21", "report4");


    }

}
