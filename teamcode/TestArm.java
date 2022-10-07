package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp (name= "TestArm")
public class TestArm extends OpMode {

    DcMotor Arm;
    Servo Claw;
    @Override
    public void init() {
       Arm = hardwareMap.dcMotor.get("motor 1 e");
        Claw = hardwareMap.servo.get("servo 1 c");
    }

    @Override
    public void loop() {
        if(gamepad1.x){
            Arm.setPower(0.5);
            
        }
        else if(gamepad1.y){
            Arm.setPower(-0.5);
            
        }
    
         else if(gamepad1.a){
            Claw.setPosition(0.3);
        }
        else if (gamepad1.b){
            Claw.setPosition(0.8);
            
        }
        else{
            Arm.setPower(0);
            
            Claw.setPosition(1);
        }
    }
}
