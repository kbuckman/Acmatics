package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="TestMotors", group="Iterative Opmode")

public class Test extends OpMode {
    DcMotor one;
    DcMotor two;
    DcMotor three;
    DcMotor four;
    @Override
    public void init() {
        one = hardwareMap.dcMotor.get("motor0");
        two= hardwareMap.dcMotor.get("motor1");
        three = hardwareMap.dcMotor.get("motor2");
        four = hardwareMap.dcMotor.get("motor3");
    }

    @Override
    public void loop() {
        if(gamepad1.a){
            one.setPower(1);
        }
        if(gamepad1.b){
            two.setPower(1);
        }
        if(gamepad1.y){
            three.setPower(1);
        }
        if(gamepad1.x){
            four.setPower(1);
        }
    }
}
