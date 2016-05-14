package com.qualcomm.ftcrobotcontroller.ReadWrite.Simulacrum.Temp;

import com.qualcomm.ftcrobotcontroller.ReadWrite.Simulacrum.SimulacrumReplay;

public class SimulacrumWildcardReplay extends SimulacrumReplay
{
    @Override
    public void init()
    {
        super.init();
        super.fileName = "driverLogWildcard.txt";
    }

    public void loop()
    {
        super.loop();
    }
}
