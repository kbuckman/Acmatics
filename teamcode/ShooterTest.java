package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Shooter Test")
public class ShooterTest extends OpMode {
   DcMotor Shooter;
   DcMotor Shooterp2;
   DcMotor Intake;
   
    @Override
    public void init() {
        Shooter= hardwareMap.dcMotor.get("motor 2 c");
        Shooterp2 = hardwareMap.dcMotor.get("motor 3 c");
        //Intake = hardwareMap.dcMotor.get();
    }

    @Override
    public void loop() {
        if(gamepad2.a){
            Shooter.setPower(1);
        }
         if(gamepad2.b){
            Shooterp2.setPower(-1);
        }
       // if (gamepad2.x){
            //Intake.setPower(1);
        //}
        else {
            Shooter.setPower(0);
            Shooterp2.setPower(0);
           // Intake.setPower(0);
        }

    }
}


