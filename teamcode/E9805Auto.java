
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import java.lang.Math;

/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */
 
@Autonomous(name="E9805Auto", group="Linear Opmode")

public class E9805Auto extends LinearOpMode {

    // Declare OpMode members.
    DcMotor rightForward;
    DcMotor rightBack;
    DcMotor leftForward;
    DcMotor leftBack;
    double Time;
    double TStart;
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        
        //DcMotor rightForward = hardwareMap.dcMotor.get("right forward");
        //DcMotor rightBack = hardwareMap.dcMotor.get("right back");
        DcMotor leftForward = hardwareMap.dcMotor.get("test3");
        DcMotor leftBack = hardwareMap.dcMotor.get("test2");
        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        double TStart = time;
    
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            double Time = time-TStart;
            telemetry.addData("Status", time);
        
            while (Time<1)  {  // Convert to if statements
                rightBack.setPower(-1*Time);
                //rightForward.setPower(-1*Time);
                leftBack.setPower(1*Time);
                leftForward.setPower(1*Time);
                telemetry.update();
            }
            while (Time<2) {   // Convert to if statements
                rightBack.setPower(Time-2);
                //rightForward.setPower(Time-2);
                //leftBack.setPower(2-Time);
                leftForward.setPower(2-Time);
                telemetry.update();
            }
            while (2<Time) {   // Convert to if statements
                //rightBack.setPower(0);
                //rightForward.setPower(0);
                leftBack.setPower(0);
                leftForward.setPower(0);
                telemetry.update();
            }
        }
    }
}
