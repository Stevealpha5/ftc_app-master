package com.qualcomm.ftcrobotcontroller.Depreciated;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

public class Variable_Speed extends OpMode
{
    double left;
    double right;

    DcMotor motor_1;
    DcMotor motor_2;
    DcMotor motor_3;
    DcMotor motor_4;

    boolean firstPush;
    boolean secondPush;

    boolean isHalf = false;

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
        joySetUp();

        if(isPushed())
            isHalf = !isHalf;

        if(isHalf)
            tankDrive(left / 2, right / 2);

        tankDrive(left, right);
    }

    @Override
    public void stop()
    {

    }

    void joySetUp()
    {
        left = gamepad1.left_stick_y;
        right = gamepad1.right_stick_y;

        left = Range.clip(left, -1, 1);
        right = Range.clip(right, -1, 1);

        tankDrive(left, right);
        telemetry.addData("Trigger",gamepad1.left_trigger);
        telemetry.addData("isPused",isPushed());

    }

    void tankDrive(double leftJoy, double rightJoy)
    {
        motor_1.setPower(-1 * leftJoy);
        motor_2.setPower(-1 * leftJoy);
        motor_3.setPower(rightJoy);
        motor_4.setPower(rightJoy);
    }

    boolean isPushed()
    {
        firstPush = gamepad1.a;
/*
        try {
            wait(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        secondPush = gamepad1.a;

        return (firstPush != secondPush && !firstPush);
    }
}