package com.qualcomm.ftcrobotcontroller.ReadWrite.Lazzy;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Lazzy_Aton_Write extends OpMode
{
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
            write();
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
    }


    public void write() throws InterruptedException, IOException {
        telemetry.addData("report", "report");

        // String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        telemetry.addData("report", "report1");
        File logFile = new File("edd");
        telemetry.addData("report", "report2");
        BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));
        telemetry.addData("report", "report3");
        telemetry.addData("button", "umpressed");
            while(gamepad1.right_bumper)
            {
                telemetry.addData("button", "pressed");
                writer.write("R" + rightCont);
                writer.write('\n');
                writer.write("L" + leftCont);

                Thread.sleep(5);
            }

            writer.close();

    }

}
