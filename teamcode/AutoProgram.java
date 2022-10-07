package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import android.graphics.Color;
import java.lang.Math;

import static android.os.SystemClock.sleep;


@Autonomous

public class AutoProgram extends OpMode {
    NormalizedColorSensor colorSensor;
    final float[] hsvValues = new float[3];
    NormalizedRGBA colors;


    DcMotor RightBack;
    DcMotor RightFront;
    DcMotor LeftBack;
    DcMotor LeftFront;

    DcMotor UpDownArm;
    DcMotor ExtendArm;

    DcMotor DrewIntake;

    Servo Claw;
    DcMotor Carosel;

    double state = 0;

    boolean onRed = false;
    double redCount = 0;

    double delta = 0; //how many variables are changing in color sensor.
    //colors.[red green blue]   . hsvValues[0,1,2]
    double fRed = 0;
    double fGreen = 0;
    double fBlue = 0;
    double fAlpha = 0;
    
    //time stuff
    static long startTime;
    static double time;
    static double previousTime;
    static double deltaTime;
    
    double tempTime; //temporary time;
    
    @Override
    public void init() {
        LeftFront = hardwareMap.dcMotor.get("motor 1 c");
        LeftBack = hardwareMap.dcMotor.get("motor 2 e");
        RightBack = hardwareMap.dcMotor.get("motor 0 e");
        RightFront = hardwareMap.dcMotor.get("motor 0 c");
        UpDownArm = hardwareMap.dcMotor.get("motor 1 e");
        ExtendArm = hardwareMap.dcMotor.get("motor 2 c");
        Claw = hardwareMap.servo.get("servo 1 c");
        Carosel = hardwareMap.dcMotor.get("motor 3 c"); //CHANGE THIS
        DrewIntake=hardwareMap.dcMotor.get("motor 3 e");


        //color stuff below
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "I2C 0 c");
        colorSensor.setGain(1000);
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        Color.colorToHSV(colors.toColor(), hsvValues);
        telemetry.addLine()
                .addData("Red", "%.3f", colors.red)
                .addData("Green", "%.3f", colors.green)
                .addData("Blue", "%.3f", colors.blue);
        telemetry.addLine()
                .addData("Hue", "%.3f", hsvValues[0])
                .addData("Saturation", "%.3f", hsvValues[1])
                .addData("Value", "%.3f", hsvValues[2]);
        telemetry.addData("Alpha", "%.3f", colors.alpha);
        //color stuff above

        fRed = colors.red;
        fGreen = colors.green;
        fBlue = colors.blue;
        fAlpha = colors.alpha;
        
        //time stuff 
        startTime = System.nanoTime();
    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {
        //time stuff;
        updateTime();
        
        NormalizedRGBA colors = colorSensor.getNormalizedColors();

        //color stuff ignore messy indents (copy + paste)
        Color.colorToHSV(colors.toColor(), hsvValues);

        telemetry.addLine()
                .addData("Red", "%.3f", colors.red)
                .addData("Green", "%.3f", colors.green)
                .addData("Blue", "%.3f", colors.blue);
        telemetry.addLine()
                .addData("Hue", "%.3f", hsvValues[0])
                .addData("Saturation", "%.3f", hsvValues[1])
                .addData("Value", "%.3f", hsvValues[2]);
        telemetry.addData("Alpha", "%.3f", colors.alpha);
        telemetry.addData("state: ", state);
        telemetry.addData("delta", delta);
        
        telemetry.addData("arm position: ", UpDownArm.getCurrentPosition());
        telemetry.addData("redcount: ", redCount);
        
        //dont type above - reserved for colors
        onRed = false;
        if(colors.red>0.8){
            onRed=true;
        }
        
        
        telemetry.addData("redCount: ", redCount);

        if (state == 0) {
            if (redCount == 0){
                if (onRed == false) {
                    moveVert(0.6);
                }
                else{
                    redCount += 1;
                }
            }
            else if (redCount == 1){
                if (onRed == true) {
                    moveVert(0.6);
                }
                else{
                    moveHoriz(0.5);
                    redCount += 1;
                }
            }
            else if (redCount == 2) {
                if (onRed == false) {
                    moveHoriz(0.4);
                }
                else {
                    moveHoriz(0);
                    tempTime = time;
                    redCount=0;
                    state += 1;
                }
            }
            //colors.[red green blue]   . hsvValues[0,1,2]
                       telemetry.update();
            }
        else if (state == 1) {
            moveHoriz(-0.5);
           
            if (time-tempTime>0.9){
                state += 1;
                tempTime = time;
            }
        }
        else if (state == 2) {
            moveVert(-0.3);
             DrewIntake.setPower(1);
            if (time-tempTime>2.0){
                state += 1;
                tempTime = time;
                
            }
        }
        else if (state == 3) {
            moveVert(0);
             DrewIntake.setPower(0);
            Carosel.setPower(-1);
             if (time-tempTime>6){
                state += 1;
                tempTime = time;
            }
            
        }
        else if (state == 4) {
            Carosel.setPower(0);
            DrewIntake.setPower(0);
            if (time-tempTime>0.25){
                state += 1;
                redCount=0;
                tempTime = time;
            }
        
        }
        else if (state == 5) {
            if (redCount == 0){
                moveVert(0.5); //going towards red
                if (onRed == true) {
                    redCount += 1;
                }
            }
            else if (redCount == 1){
                if (onRed == true) { //on red
                }
                else{
                    moveHoriz(0.5);
                    redCount += 1;
                }
            }
            else if (redCount == 2) {
                if (onRed == false) {
                    moveHoriz(0.4);
                }
                else {
                    moveHoriz(0);
                    tempTime = time;
                    state += 1;
                }
            }
            //colors.[red green blue]   . hsvValues[0,1,2]
           
            telemetry.addData("redCount: ", redCount);
            telemetry.update();
           
        }
        else if (state == 6) {
            rotate(-1);
             if (time-tempTime>0.4){
                state += 1;
                tempTime = time;
            }
        }
        else if (state ==7){
            rotate(0);
            moveVert(-0.3);
             if (time-tempTime>3){
                state += 1;
                tempTime = time;
            }
        }
        else if (state ==8){
            moveHoriz(0.7);
             if (time-tempTime>0.22){
                state += 1;
                tempTime = time;
            }
        }
        else if(state == 9){
            moveVert(1);
            if(time-tempTime>3.5){
                state += 1;
                tempTime =time;
            }
        }
        else if (state == 10){
            moveVert(0);
            state+=1;
        }
        else {
            telemetry.addData("Analysis: ", "we all die");
        }
    }

    /*
     * Code
     *  to run ONCE after the driver hits STOP
     */

    @Override
    public void stop() {

    }


    private void moveHoriz(double power){ //move horizontal
        LeftBack.setPower(-power);
        LeftFront.setPower(power);
        RightBack.setPower(-power);
        RightFront.setPower(power);
    }
    private void moveVert(double power){ //move vertical
        LeftBack.setPower(power);
        LeftFront.setPower(power);
        RightBack.setPower(-power);
        RightFront.setPower(-power);
    }
    private void moveRDiag(double power){
        LeftFront.setPower(power);
        RightBack.setPower(-power);


    }
    private void move(double power, double angle) {
        angle = -1*angle; //code issues too lazy to fix
        double yPrime = power*Math.cos(angle+(3.1415926/4));
        double xPrime = power*Math.sin(angle+(3.1415926/4));
        RightFront.setPower(-power*yPrime);
        LeftBack.setPower(power*yPrime);
        RightBack.setPower(-power*xPrime);
        LeftFront.setPower(power*xPrime);
    }
    
    public static void updateTime(){
        previousTime = time;
        time = ((double)(System.nanoTime() - startTime))/Math.pow(10,9);
        deltaTime = time-previousTime;
    }
    
    public void rotate(double power){
        RightFront.setPower(power);
        LeftBack.setPower(power);
        RightBack.setPower(power);
        LeftFront.setPower(power);    
    }
}
