package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import java.lang.Math;




@TeleOp (name="MecWheelDrive")
public class MecWheelDrive extends OpMode {
    DcMotor RightBack;
    DcMotor RightFront;
    DcMotor LeftBack;
    DcMotor LeftFront; 
    
    DcMotor UpDownArm;
    DcMotor ExtendArm;
    
    DcMotor DrewIntake;
    
    Servo Claw;
    DcMotor Carosel;
    double j = 0;
    double outputDR = 0;
    double outputDL = 0;
    double xPrime = 0;
    double yPrime = 0;
    double x = 0;
    double y = 0;
    double rightx;

    double RightPow1;
    double RightPow2;
    double LeftPow1;
    double LeftPow2;
    double k;

    double reverseS;
    double slowS;
    double slowW;
   
   
   // VELOCITY vars
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
    
    static long startTime;
    static double time;
    static double previousTime;
    static double deltaTime;
    
    double armSetPoint;
    double armStartPoint;
    double armPower;
    double armKp;
    double armKd;
    double armPositionPrevious;
    
    @Override
    public void init() {
        LeftFront = hardwareMap.dcMotor.get("motor 1 c");
        LeftBack = hardwareMap.dcMotor.get("motor 2 e");
        RightBack = hardwareMap.dcMotor.get("motor 0 e");
        RightFront = hardwareMap.dcMotor.get("motor 0 c");
        UpDownArm = hardwareMap.dcMotor.get("motor 1 e");
        ExtendArm = hardwareMap.dcMotor.get("motor 2 c");
        //Claw = hardwareMap.servo.get("servo 1 c");
        Carosel = hardwareMap.dcMotor.get("motor 3 c"); //CHANGE THIS
        
        DrewIntake = hardwareMap.dcMotor.get("motor 3 e");
        
        startTime = System.nanoTime();
                
        pSRF = 0;
        pSRB = 0;
        pSLF = 0;
        pSLB = 0;
        
        armSetPoint = 0;
        armStartPoint = UpDownArm.getCurrentPosition();
        armPower = 0;
        armKp = 0.008;
        armKd = 0.0001;
        armPositionPrevious = 0;
    }

    @Override
    public void loop() {
        updateTime();
        
        if(gamepad2.x){
            armSetPoint += 15;
        }
        else if(gamepad2.y){
            armSetPoint -= 15;
        }
        armPower = 0; //setting to zero so we can add p, i, and d parts seperatly.
        armPower += armKp*(armSetPoint-UpDownArm.getCurrentPosition()+armStartPoint);
        armPower -= armKd*(UpDownArm.getCurrentPosition()-armPositionPrevious)/deltaTime;
        armPositionPrevious = UpDownArm.getCurrentPosition();

        UpDownArm.setPower(armPower);
        
        telemetry.addData("armPosError ", UpDownArm.getCurrentPosition()-armStartPoint);
        
        SRF = RightFront.getCurrentPosition();
        SRB = RightBack.getCurrentPosition();
        SLF = LeftFront.getCurrentPosition();
        SLB = LeftBack.getCurrentPosition();
        
        
        telemetry.addData("time: ", time);
        telemetry.addData("arm power: ", armPower);
        telemetry.addData("arm position: ", UpDownArm.getCurrentPosition());
        telemetry.update();
        
        //incoming really needlessly complex code
        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;

        double rightx = gamepad1.right_stick_x;

        xPrime = (Math.pow(2, 0.5) / 2) * (x + y);
        yPrime = (Math.pow(2, 0.5) / 2) * (y - x);

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
        
        RightPow1 = -(DiagnolRight+rightx)/2;
        RightPow2 = -(DiagnolLeft+rightx)/2;
        LeftPow1 = (DiagnolLeft-rightx)/2;
        LeftPow2 = (DiagnolRight-rightx)/2;
        
        if (gamepad1.left_bumper) {
            slowW = 0.50;
        }
        else {
            slowW = 1;
        }
        
        if (((RightPow1 > RightPow2) && (RightPow1 > LeftPow1)) && (RightPow1 > LeftPow2)) {
            k = RightPow1;
        }
        else if (((RightPow2 > RightPow1) && (RightPow2 > LeftPow1)) && (RightPow2 > LeftPow2)) {
            k = RightPow2;
        }
        else if (((LeftPow2 > RightPow1) && (LeftPow2 > LeftPow1)) && (LeftPow2 > RightPow2)) {
            k = LeftPow2;
        }
        else if (((LeftPow1 > RightPow1) && (LeftPow1 > LeftPow2)) && (LeftPow1 > RightPow2)) {
            k = LeftPow1;
        }
        else {
            k = Math.abs(LeftPow1);
        }
        
        RightBack.setPower(-(slowW*(DiagnolRight+rightx)/2)/k);
        RightFront.setPower(-(slowW*(DiagnolLeft+rightx)/2)/k);
        LeftBack.setPower((slowW*(DiagnolLeft-rightx)/2)/k);
        LeftFront.setPower((slowW*(DiagnolRight-rightx)/2)/k);
        //needlessly complex code ended
        
        
        if (gamepad2.right_bumper){
            ExtendArm.setPower(0.4);
        }
        else if (gamepad2.left_bumper){
            ExtendArm.setPower(-0.4);
        }
    
        /* else if(gamepad2.a){
            Claw.setPosition(-1);
        }
        else if (gamepad2.b){
            Claw.setPosition(1);
            
        }*/
        else if (gamepad1.a){
            Carosel.setPower(1);
            
        }
        else if (gamepad1.b){
            Carosel.setPower(-1);
            
        }
        else if (gamepad2.b) {
            DrewIntake.setPower(1);
        }
        else if (gamepad2.a){
            DrewIntake.setPower(-1);
        }
        else{
            ExtendArm.setPower(0);
            //Claw.setPosition(0);
            Carosel.setPower(0);
            DrewIntake.setPower(0);
            
        }
        
        
    
    }
    
    public static void updateTime(){
        previousTime = time;
        time = ((double)(System.nanoTime() - startTime))/Math.pow(10,9);
        deltaTime = time-previousTime;
    }
}
