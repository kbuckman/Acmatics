package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import java.lang.Math;
@TeleOp (name="MechWheelDriveTrial")
public class MechWheelDriveTrial extends OpMode {

   DcMotor Right45in;
    DcMotor Right45out;
    DcMotor Left45in;
    DcMotor Left45out;
    DcMotor Shooter;
    DcMotor Shooterp2;
    DcMotor Intake;
    DcMotor Wobble1;
    Servo Wobble2;
    double j = 0;
    double outputDR = 0;
    double outputDL = 0;
    double xPrime = 0;
    double yPrime = 0;
    double x = 0;
    double y = 0;
    double rightx;
    double reverseS;
    
    static double time = 0;
    static double previousTime = 0;
    static double deltaTime = 0;
    static long startTime;




    @Override
    public void init() {
        Right45in = hardwareMap.dcMotor.get("motor 3 e");
        Right45out = hardwareMap.dcMotor.get("motor 0 c");
        Left45in = hardwareMap.dcMotor.get("motor 1 c");
        Left45out = hardwareMap.dcMotor.get("motor 2 e");
        Shooter = hardwareMap.dcMotor.get("motor 2 c");
        Shooterp2 = hardwareMap.dcMotor.get("motor 3 c");
        Intake = hardwareMap.dcMotor.get("motor 1 e");
        Wobble1 = hardwareMap.dcMotor.get("motor 0 e");
        Wobble2 = hardwareMap.servo.get("servo 0 c");
        startTime = System.nanoTime();
    }
    
    static void updateTime(){
        previousTime = time;
        time = ((double)(System.nanoTime()-startTime)/(double)1_000_000_000);
        deltaTime = time-previousTime;
    }

    @Override
    public void loop() {
        updateTime();
        
        double x = gamepad2.left_stick_y;
        double y = -gamepad2.left_stick_x;
        
        double rightx = gamepad2.right_stick_x;

        xPrime = (Math.pow(2, 0.5) / 2) * (x + y);
        yPrime = (Math.pow(2, 0.5) / 2) * (y - x);

        double x2 = -gamepad2.right_stick_x;
        double y2 = gamepad2.right_stick_y;
        double absX = Math.abs(x);
        double absY = Math.abs(y);
        double J = absX + absY;
        double absXPrime = Math.abs(xPrime);
        double absYPrime = Math.abs(yPrime);

        if (J == 0) {
            j=1;
        }
        else if (absXPrime > absYPrime) {
            j = absXPrime;
        }
        else if (absYPrime > absXPrime) {
            j = absYPrime;
        }

        double Distance = Math.pow(Math.pow(x, 2)+Math.pow(y, 2), 0.5);

        double DiagnolRight = Distance*(1/j)*xPrime;
        double DiagnolLeft  = Distance*(1/j)*yPrime;

        Left45out.setPower((DiagnolRight-rightx)/2);
        Left45in.setPower((DiagnolLeft+rightx)/2);
        Right45out.setPower((DiagnolLeft-rightx)/2);
        Right45in.setPower((-rightx-DiagnolRight)/2);


  reverseS = 1;
        if (gamepad1.x) {
            reverseS =-0.7;
        }
        else {
            reverseS = 1;
        }



        if (gamepad1.left_bumper){
            Shooter.setPower(1*reverseS);
        }
        else{
            Shooter.setPower(0);
        }
        if (gamepad1.b) {
            Shooterp2.setPower(-1*reverseS);
        }
        else{
            Shooterp2.setPower(0);
        }
        if(gamepad1.right_bumper){
            Intake.setPower(-1*reverseS);
        }
        else{
            Intake.setPower(0);
        }
        if (gamepad1.a) {
            Wobble1.setPower(0.75);
        }
        else if (gamepad1.y) {
            Wobble1.setPower(-0.75);
        }
        else {
            Wobble1.setPower(0);
        }
        if (gamepad1.x) {
            Wobble2.setPosition(0.5);
        }
        else {
            Wobble2.setPosition(0);
        }


    }

      






    }