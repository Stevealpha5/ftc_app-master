package com.qualcomm.ftcrobotcontroller.ReadWrite.Concurrent;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoGhostConcurrent
{
    public Servo servo;

    private String tag;
    private int ID;
    private boolean controller
            ;
    private double init;
    private double active;

    private char[] tagArray;

    public ServoGhostConcurrent(String tag, int ID)
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


    public void setController(boolean controller, double init, double active)
    {
        this.controller = controller;
        this.init = init;
        this.active = active;

        if(controller)
        {
            servo.setPosition(active);
        }else{
            servo.setPosition(init);
        }
    }

    public boolean getController()
    {
        return controller;
    }

    public double getInitPos()
    {
        return init;
    }

    public double getActivePos()
    {
        return active;
    }

    public void register(HardwareMap hardwareMap)
    {
        this.servo = hardwareMap.servo.get("servo_" + ID);
    }

}
