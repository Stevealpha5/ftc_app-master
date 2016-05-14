package com.qualcomm.ftcrobotcontroller.Utilities;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class Duo extends LinearOpMode
{
    boolean isOne;

    @Override
    public void runOpMode() throws InterruptedException
    {
        /**
         * waits for user to input a selection for an OPMode
         *
         * If more than two OPModes are needed change the boolean isOne to an
         * int, use it for a selector...and rename it to something sensible if
         * I return and some future Summervile programmer is using the name isOne
         * as any non-boolean name, i will hurt you
         *
         * Optional: change if statement to a switch statement
         */

        while(true)
        {
            if(gamepad1.a)
            {
                isOne = true;
                break;
            }else if(gamepad1.b)
            {
                isOne = false;
                break;
            }
        }

        /**
         * Global Init
         */

        waitForStart();


        if(isOne)
        {
            /**
             * Init of function one
             */

            while(true)
            {
                /**
                 * Body of function one
                 */

                if(gamepad1.guide) //Breaks the infinite loop as to avoid crashes on program shutdown
                {
                    /**
                     * Stop of function two
                     */
                    break;
                }
            }
        }else if(!isOne)
        {
            /**
             * Init of function two
             */

            while (true)
            {
                /**
                 * Body of function two
                 */

                if(gamepad1.guide)//Breaks the infinite loop as to avoid crashes on program shutdown (the guide button must be pushed before program stop!!!)
                {
                    /**
                     * Stop of function two
                     */
                    break;
                }
            }
        }

        /**
         * Global Stop
         */

        telemetry.addData("Loop Break Complete", "");
    }


}
