package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@TeleOp

public class LaunchingTest extends OpMode{

    DcMotor Motor0;

    @Override
    public void init() {
        DcMotor motor0 = hardwareMap.dcMotor.get("right forward");
    }

    @Override
    public void loop() {
        DcMotor motor0 = hardwareMap.dcMotor.get("right forward");
        if (gamepad1.b == true) {
            motor0.setPower(1);
        }
        else {
            motor0.setPower(0);
        }
    
        
        
        
        
    }
    
    
    
    
}