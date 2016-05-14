package com.qualcomm.ftcrobotcontroller.Utilities;

public class Toggle
{

    boolean lastCheck = false;
    boolean counter = false;
    boolean input;

    public Toggle (boolean input)
    {
        this.input = input;
    }

    public boolean getToggleState()
    {

        if((input != lastCheck) & (input)){
            counter = ! counter;
        }
        lastCheck = input;

        return counter;
    }

}
