    package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import java.lang.Math;

@Autonomous(name="PIDTest")

public class PIDTest extends OpMode {
    DcMotor UpDownArm;
    DcMotor ExtendArm;
    DcMotor FrontRightWheel;
    DcMotor FrontLeftWheel;
    DcMotor motor;
    DcMotor BackLeftWheel;
    Servo Claw;
    
    @Override
    public void init() {
        FrontRightWheel = hardwareMap.dcMotor.get("motor 0 c");
    }

    @Override
    public void loop() {
        pid(1, 0, 0.000001, 1000, FrontRightWheel);
    }
    static void pid(double kp, double kd, double ki, double sp, DcMotor motor) {
        sp = sp + motor.getCurrentPosition();
        double error = sp - motor.getCurrentPosition();
        double times = 0;
        double vel = 0;
        double integral = 0;
        double out = 0;
        while (times < 100){
            vel = sp - motor.getCurrentPosition() - error;
            error = sp - motor.getCurrentPosition();
            integral = integral + error;
            out = kp*error + kd*vel + ki*integral;
            motor.setPower(out);
            if (error < 5) {
                times = times + 1;
            }
        }
    }
}

    
    
    
    
    