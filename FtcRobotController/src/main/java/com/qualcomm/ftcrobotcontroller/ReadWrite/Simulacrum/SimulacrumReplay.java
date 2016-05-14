package com.qualcomm.ftcrobotcontroller.ReadWrite.Simulacrum;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.io.IOException;

/**
 * Replays inputs recorded to a log-file by SimulacrumRecord.
 * @see Simulacrum
 * @see SimulacrumRecord
 */
public class SimulacrumReplay extends OpMode
{

    DcMotor leftWheels;
    DcMotor rightWheels;
    DcMotor armExtender2;
    DcMotor hangMotor;
    DcMotor liftMotor;
    DcMotor armExtender;

    Servo leftBucket;
    Servo rightBucket;
    Servo collectorDrive;
    Servo collectorDrive2;
    Servo climberCarrierServo;
    Servo leftClimberServo;
    Servo rightClimberServo;

    public String fileName = "driverLog.txt";

    boolean hasRun = false;

    Simulacrum readWriteObj;

    /**
     * Initializes hardware devices and ties them to the hardware map.
     * Also sets starting directions and initialization positions for servos and motors.
     * Initializes a new Simulacrum object.
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode
     */
    public void init()
    {

        leftWheels = hardwareMap.dcMotor.get("motor_2");
        rightWheels = hardwareMap.dcMotor.get("motor_1");
        armExtender2 = hardwareMap.dcMotor.get("motor_6");
        hangMotor = hardwareMap.dcMotor.get("motor_4");
        liftMotor = hardwareMap.dcMotor.get("motor_3");
        armExtender = hardwareMap.dcMotor.get("motor_5");

        leftBucket = hardwareMap.servo.get("servo_1");
        rightBucket = hardwareMap.servo.get("servo_2");
        collectorDrive = hardwareMap.servo.get("servo_3");
        collectorDrive2 = hardwareMap.servo.get("servo_4");
        climberCarrierServo = hardwareMap.servo.get("servo_5");
        leftClimberServo = hardwareMap.servo.get("servo_6");
        rightClimberServo = hardwareMap.servo.get("servo_7");

        leftWheels.setDirection(DcMotor.Direction.REVERSE);
        armExtender.setDirection(DcMotor.Direction.REVERSE);

        leftBucket.setDirection(Servo.Direction.REVERSE);
        rightBucket.setDirection(Servo.Direction.FORWARD);

        leftClimberServo.setDirection(Servo.Direction.REVERSE);
        rightClimberServo.setDirection(Servo.Direction.FORWARD);

        leftBucket.setPosition(0.5);
        rightBucket.setPosition(0.5);
        collectorDrive.setPosition(0.5);
        collectorDrive2.setPosition(0.5);

        rightClimberServo.setPosition(0.05);
        leftClimberServo.setPosition(0.05);

        climberCarrierServo.setPosition(1.0);

        readWriteObj = new Simulacrum(fileName, leftWheels, rightWheels, armExtender2, liftMotor, armExtender, hangMotor, climberCarrierServo, leftBucket, rightBucket, collectorDrive, collectorDrive2);

    }

    /**
     * Replays inputs until the end of the logfile is reached.
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode
     */
    public void loop()
    {

        if(!hasRun)
        {

            try {
                readWriteObj.readFromLog(4); //Increase to add run length (lol), decrease to reduce run length
            } catch (IOException e) {
                e.printStackTrace();
                telemetry.addData("Fatal Error", "IOException @ Read");
            }

            hasRun = true;
        }else{
            telemetry.addData("Status", "Program Complete");
        }

    }

}
