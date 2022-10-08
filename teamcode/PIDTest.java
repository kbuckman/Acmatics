package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.lang.Math;

@Autonomous

public class PIDTest extends OpMode {

    RobotHardware robot = new RobotHardware();
    PIDController cMotor11;
    static long startTime;
    static double time;
    static double deltaTime;

    @Override
    public void init() {

        robot.init(hardwareMap); //inititalizes all motors

        cMotor11 = new PIDController();

        cMotor11.setPoint(robot.motor11.getCurrentPosition());
        cMotor11.setCoefficients(0.008, 0.0001, 0); // P I D

    }

    @Override
    public void start() {
        startTime = System.nanoTime();
        time = 0;
    }

    @Override
    public void loop() {
        updateTime();
        cMotor11.update( robot.motor11.getCurrentPosition() , deltaTime );
        robot.motor1.setPower(cMotor11.getSum());

        if(gamepad2.x){
            cMotor11.increaseSetPoint(15);
        }
        else if(gamepad2.y){
            cMotor11.increaseSetPoint(-15);
        }

        /*
        This should be the entirety of a PID ctrl system
         */
    }

    public static void updateTime(){
        previousTime = time;
        time = ((double)(System.nanoTime() - startTime))/1_000_000_000.0;
        deltaTime = time-previousTime;
    }
}
