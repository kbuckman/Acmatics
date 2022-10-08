// https://www.youtube.com/watch?v=aTXU-fPi9sw&t=1002s <-- helped a lot

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.lang.Math;

public class RobotHardware {
    /*
    The instance of RobotHardware in classes is 'robot'.
    The only part of the robot that the code interacts with is the hardware.
     */

    /*
          motor layout

            front
      left  11 12  right
            21 22
             back
    */

    // INSTANTIATING MOTORS AND SERVOS
    public DcMotor motor11;
    public DcMotor motor12;
    public DcMotor motor21;
    public DcMotor motor22;



    // CREATING THE HARDWARE MAP
    HardwareMap hardwareMap;

    public void init(HardwareMap hardwareMap) { // run on init in opmode

        // DEFINE MOTORS AND SERVOS
        motor11 = hardwareMap.get(DcMotor.class, "motor11"); //motor1 must be defined in config
        motor12 = hardwareMap.get(DcMotor.class, "motor12");
        motor21 = hardwareMap.get(DcMotor.class, "motor21");
        motor22 = hardwareMap.get(DcMotor.class, "motor22");

        // DEFINE SENSORS


        // SET MOTOR POWER
        motor11.setPower(0);
        motor12.setPower(0);
        motor21.setPower(0);
        motor22.setPower(0);

        // CALIBRATE SENSORES
}
