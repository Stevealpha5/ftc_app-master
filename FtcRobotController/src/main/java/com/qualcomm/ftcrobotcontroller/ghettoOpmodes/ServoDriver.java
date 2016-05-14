package com.qualcomm.ftcrobotcontroller.ghettoOpmodes;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoDriver extends OpMode
{

    Servo servo_1;
    Servo servo_2;

    int delay = 300;

    boolean lastCheck = false;
    boolean counter = false;

    static double degreesInPoint3Seconds = 30;
    static double pinionGearDiameter = 17.59;//mm
    static double pinionGearCircumference = pinionGearDiameter * 3.1415;//mm
    static double distancePerSecond = 3.3333333333333 * ((degreesInPoint3Seconds / 360) * pinionGearCircumference);

    boolean toggle (boolean input){
        if((input != lastCheck) & (input)){
            counter = ! counter;
        }
        lastCheck = input;
        return counter;
    }

    public void init()
    {

        servo_1 = hardwareMap.servo.get("servo_1");
        servo_2 = hardwareMap.servo.get("servo_2");

        servo_2.setDirection(Servo.Direction.REVERSE);

        servo_1.setPosition(0.5);
        servo_2.setPosition(0.5);

    }

    public void loop()
    {

        telemetry.addData("Servo 1", servo_1.getPosition());
        telemetry.addData("Servo 2", servo_2.getPosition());
        telemetry.addData("Right Bumper", gamepad1.right_bumper);
        telemetry.addData("Toggle", counter);
        telemetry.addData("Left Stick Y", gamepad1.left_stick_y);

        if (toggle(gamepad1.right_bumper))
        {
            servo_1.setPosition(1);
            servo_2.setPosition(1);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            servo_1.setPosition(0.5);
            servo_2.setPosition(0.5);
        }else if (!toggle(gamepad1.right_bumper))
        {
            servo_1.setPosition(0);
            servo_2.setPosition(0);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            servo_1.setPosition(0.5);
            servo_1.setPosition(0.5);
        }else{
            servo_1.setPosition(0.5);
            servo_2.setPosition(0.5);
        }

    }

    static void servoRunDistance(Servo servo, double targetMM)//distance in mm
    {
        long waitTime = (long)((targetMM / pinionGearCircumference) * 3600);

        servo.setPosition(1.0);
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        servo.setPosition(0.5);
    }

    static void servoRunDistanceReverse(Servo servo, double targetMM)//distance in mm
    {
        long waitTime = (long)((targetMM / pinionGearCircumference) * 3600);

        servo.setPosition(0.0);
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        servo.setPosition(0.5);
    }

    static void autoActuateBlue(ColorSensor colourSenzor, Servo servo, double pos1, double pos2)//pos1 pushes the same side as the colour sensor, pos2 pushes the other one
    {
        if(colourSenzor.blue() > colourSenzor.red())
        {
            servo.setPosition(pos1);
        }else if(colourSenzor.red() > colourSenzor.blue())
        {
            servo.setPosition(pos2);
        }
        //AxonReadCompositeBluept2.init();
    }

    static void autoActuateRed(ColorSensor colourSenzor, Servo servo, double pos1, double pos2)//pos1 pushes the same side as the colour sensor, pos2 pushes the other one
    {
        if(colourSenzor.blue() > colourSenzor.red())
        {
            servo.setPosition(pos2);
        }else if(colourSenzor.red() > colourSenzor.blue())
        {
            servo.setPosition(pos1);
        }
        //AxonReadCompositeRedpt2.init();
    }
}
