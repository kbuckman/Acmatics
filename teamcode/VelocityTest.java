package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import java.lang.Math;




@Autonomous(name="VelTest")
public class VelocityTest extends LinearOpMode{

    DcMotor forwardRight; //m0
    DcMotor forwardLeft; //m2
    DcMotor backRight; //m1
    DcMotor backLeft; //m3
    DcMotor Shooter;
    DcMotor Shooterp2;
    Servo Wobble2;

    double SRF; //sensor values of FR, FB, LR, LB (forward right, left back, etc...)
    double SRB;
    double SLF;
    double SLB;
    
    double pSRF; //previous sensor values of wheels
    double pSRB;
    double pSLF;
    double pSLB;
    
    double vRF; //velocity of corresponding wheels
    double vRB;
    double vLF;
    double vLB;
    
    double t;  //times ran
    double ti; //time intervals between velocity measurements
    
    @Override
    public void runOpMode() throws InterruptedException {
        forwardRight = hardwareMap.dcMotor.get("motor 1 c");
        forwardLeft = hardwareMap.dcMotor.get("motor 3 e");
        backRight = hardwareMap.dcMotor.get("motor 2 e");
        backLeft = hardwareMap.dcMotor.get("motor 0 c");
        Shooter = hardwareMap.dcMotor.get("motor 2 c"); 
        Shooterp2 = hardwareMap.dcMotor.get("motor 1 e");
        Wobble2 = hardwareMap.servo.get("servo 0 e");
        
        SRF = 0;
        SRB = 0;
        SLF = 0;
        SLB = 0;
        
        pSRF = 0;
        pSRB = 0;
        pSLF = 0;
        pSLB = 0;
        
        vRB = 0;
        vRF = 0;
        vLF = 0;
        vLB = 0;
        
        t = 0;
        ti = 40;
        waitForStart();
        
        SRF = forwardRight.getCurrentPosition();
        telemetry.addData("pos: ", SRF);
        if (Math.abs(pSRF) > 0) {
            if (t%ti == 0) {
                vRF = (SRF - pSRF)/0.01;
                pSRF = SRF;
            }
            else {
                vRF = vRF;
            }
        }
        else if (pSRF == 0) {
            SRF = forwardRight.getCurrentPosition();
            vRF = forwardRight.getCurrentPosition();
            pSRF = SRF;
        }
        
        t = t + 1;
        
        telemetry.addData("vel: ", vRF);
        telemetry.update();
        
        sleep(100);
        SRF = forwardRight.getCurrentPosition();
        if (t%ti == 0) {
            vRF = (SRF - pSRF)/0.01;
            pSRF = SRF;
        }
        else {
            vRF = vRF;
        }
        t = t + 1;
        telemetry.addData("vel: ", vRF);
        telemetry.addData("pos: ", SRF);
        telemetry.addData("t: ", t);
        telemetry.addData("time", time);
        
        telemetry.update();

        
        Wobble2.setPosition(0.5);
        forwardRight.setPower(-1);
        backRight.setPower(-1);
        backLeft.setPower(1);
        forwardLeft.setPower(1);

        sleep(2900);
        SRF = forwardRight.getCurrentPosition();

        if (t%ti == 0) {
            SRF = forwardRight.getCurrentPosition();
            vRF = (SRF - pSRF)/0.01;
            pSRF = SRF;
        }
        else {
            vRF = vRF;
        }
        t = t + 1;
        
        telemetry.addData("pos: ", SRF);

        telemetry.addData("vel: ", vRF);
        telemetry.update();
        
        forwardRight.setPower(0.8);
        backRight.setPower(-0.6);
        backLeft.setPower(0.7);
        forwardLeft.setPower(-1);
        
        sleep(2500);
        
        SRF = forwardRight.getCurrentPosition();
        if (t%ti == 0) {
            vRF = (SRF - pSRF)/0.01;
            pSRF = SRF;
        }
        else {
            vRF = vRF;
        }
        t = t + 1;
        telemetry.addData("vel: ", vRF);
        telemetry.update();

        
        forwardRight.setPower(1);
        backRight.setPower(1);
        backLeft.setPower(1);
        forwardLeft.setPower(1);
        
        sleep(100);
        
        SRF = forwardRight.getCurrentPosition();
        
        if (t%ti == 0) {
            vRF = (SRF - pSRF)/0.01;
            pSRF = SRF;
        }
        else {
            vRF = vRF;
        }
        t = t + 1;
        telemetry.addData("vel: ", vRF);
        telemetry.update();
        
        forwardRight.setPower(-0.7);
        backRight.setPower(-0.7);
        backLeft.setPower(1);
        forwardLeft.setPower(1);
        
        sleep(360);
        
        SRF = forwardRight.getCurrentPosition();
        if (t%ti == 0) {
            vRF = (SRF - pSRF)/0.01;
            pSRF = SRF;
        }
        else {
            vRF = vRF;
        }
        t = t + 1;
        telemetry.addData("vel: ", vRF);
        telemetry.update();
        
        Shooterp2.setPower(-1);
        forwardRight.setPower(0);
        backRight.setPower(0);
        backLeft.setPower(0);
        forwardLeft.setPower(0);
        
        sleep(1000);
        
        SRF = forwardRight.getCurrentPosition();
        if (t%ti == 0) {
            SRF = forwardRight.getCurrentPosition();
            vRF = (SRF - pSRF)/0.01;
            pSRF = SRF;
        }
        else {
            vRF = vRF;
        }
        t = t + 1;
        telemetry.addData("vel: ", vRF);
        telemetry.update();
        
        Shooter.setPower(1);
        Shooterp2.setPower(-0.96);   
        
        
        sleep(6000);
        
        SRF = forwardRight.getCurrentPosition();
        if (t%ti == 0) {
            SRF = forwardRight.getCurrentPosition();
            vRF = (SRF - pSRF)/0.01;
            pSRF = SRF;
        }
        else {
            vRF = vRF;
        }
        t = t + 1;
        telemetry.addData("vel: ", vRF);
        telemetry.update();
        
        Shooter.setPower(0);
        Shooterp2.setPower(0); 
        forwardRight.setPower(-1);
        backRight.setPower(-1);
        backLeft.setPower(1);
        forwardLeft.setPower(1);
        
        sleep(550);
        
        SRF = forwardRight.getCurrentPosition();
        if (t%ti == 0) {
            SRF = forwardRight.getCurrentPosition();
            vRF = (SRF - pSRF)/0.01;
            pSRF = SRF;
        }
        else {
            vRF = vRF;
        }
        t = t + 1;
        telemetry.addData("vel: ", vRF);
        telemetry.update();
        
        forwardLeft.setPower(0);
        forwardRight.setPower(0);
        backRight.setPower(0);
        backLeft.setPower(0);
        Shooter.setPower(0);
        Shooterp2.setPower(0);

    }
}