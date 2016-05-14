package com.qualcomm.ftcrobotcontroller.ReadWrite.Concurrent;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.robocol.Telemetry;

public class MotorGhostConcurrent
{
    public DcMotor motor;

    private String tag;
    private int ID;
    private double doubleController;
    private boolean booleanControllerPositive;
    private boolean booleanControllerNegitive;
    private double power;

    private char[] tagArray;

    private boolean isBoolean;

    public MotorGhostConcurrent(String tag, int ID)
    {
        tag = tag.substring(0, 2);
        tagArray = tag.toCharArray();

        this.tag = tag;
        this.ID = ID;

    }

    public char getTagCharacter(int characterNumber)
    {
        return tagArray[characterNumber];
    }

    public String getTag()
    {
        return tag;
    }

    public int getID()
    {
        return ID;
    }

    public double getDoubleController()
    {
        return doubleController;
    }

    public void setController(double doubleController)
    {
        isBoolean = false;
        this.doubleController = doubleController;
        motor.setPower(doubleController);
    }

    public void setController(boolean controllerPositive, boolean controllerNegative, double power)
    {
        isBoolean = true;
        this.booleanControllerPositive = controllerPositive;
        this.booleanControllerNegitive = controllerNegative;
        this.power = power;

        if(controllerPositive)
        {
            motor.setPower(power);
        }else if(controllerNegative)
        {
            motor.setPower(-power);
        }else{
            motor.setPower(0);
        }
    }

    public boolean getBooleanControllerPositive()
    {
        return booleanControllerPositive;
    }

    public boolean getbooleanControllerNegitive()
    {
        return booleanControllerNegitive;
    }

    public double getPower()
    {
        return power;
    }

    public void register(HardwareMap hardwareMap)
    {
        this.motor = hardwareMap.dcMotor.get("motor_" + ID);
    }

    public boolean isBoolean()
    {
        return isBoolean;
    }
}

class motorGhostThread implements Runnable
{
    DcMotor motor;
    int time;
    Telemetry telemetry;

    motorGhostThread(DcMotor motor, int time, Telemetry telemetry)
    {
        this.motor = motor;
        this.time = time;
        this.telemetry = telemetry;


    }

    @Override
    public void run()
    {


    }
}
