package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import android.graphics.Color;
import java.lang.Math;
import java.util.*;
import static android.os.SystemClock.sleep;


@Autonomous

public class AutoCode extends OpMode {

    double startAngle;
    double posAngle;
    static double setAngle;
    
    double posX;
    double posY;
    
    static double setX;
    static double setY;
    
    double relX;
    double relY;
    double relAngle;
    
    double kp;
    double LDPower;
    double RDPower;
    
    
    DcMotor RightBack;
    DcMotor RightFront;
    DcMotor LeftBack;
    DcMotor LeftFront;
    
    BNO055IMU.Parameters parameters;

    BNO055IMU imu;
    
    static void setSetPoint(double pointX, double pointY, double targetAngle) {
        setX = pointX;
        setY = pointY;
        setAngle = targetAngle;
    }
    
    @Override
    public void init() {
        imu = hardwareMap.get(BNO055IMU.class ,"imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        imu.initialize(parameters);
        
        LeftFront = hardwareMap.dcMotor.get("motor 1 c");
        LeftBack = hardwareMap.dcMotor.get("motor 2 e");
        RightBack = hardwareMap.dcMotor.get("motor 0 e");
        RightFront = hardwareMap.dcMotor.get("motor 0 c");
        
        if (!imu.isGyroCalibrated()){
            sleep(50);
        }
        
    }
    
    @Override
    public void start() {
        
    }

    @Override
    public void loop() {
        posAngle = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS).firstAngle;
        
        telemetry.addData("orientation: ", posAngle);
        
        relX = setX-posX;
        relY = setY-posY;
        
        relX = Math.cos(posAngle)*relX + Math.sin(posAngle)*relY;
        relY = -Math.sin(posAngle)*relX + Math.cos(posAngle)*relY;
        
        RDPower = kp*(relX*Math.cos(Math.PI/4)+relY*Math.sin(Math.PI/4)); //right diagnol power. Remember to reverse motor powers
        LDPower = kp*(-relX*Math.sin(Math.PI/4)+relY*Math.cos(Math.PI/4)); //left diagnol power
    
        LeftFront.setPower(RDPower);
        LeftBack.setPower(LDPower);
        RightBack.setPower(-RDPower);
        RightFront.setPower(-LDPower);
    }
}