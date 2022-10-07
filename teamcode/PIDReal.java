package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import java.lang.Math;
import java.lang.Double;

@Autonomous(name="Pid", group="Iterative Opmode")

public class PIDReal extends OpMode
{
    //private ElapsedTime runtime = new ElapsedTime(long 0);

    //define variables
    double t, ti;
    double vRF, SRF, pSRF, vRFsp, RFsp, aRF, pvRF, oRF;
    double vRB, SRB, pSRB, vRBsp, RBsp, aRB, pvRB, oRB;
    double vLF, SLF, pSLF, vLFsp, LFsp, aLF, pvLF, oLF;
    double vLB, SLB, pSLB, vLBsp, LBsp, aLB, pvLB, oLB;
    // experimental velocity, current position, previous position, velocity setpoint, setpoint, current error acceleration, previous velocity, output
    
    double dt1, Vmax;
    
    double p, i, d;
    
    DcMotor RightFront;
    DcMotor RightBack;
    DcMotor LeftFront;
    DcMotor LeftBack;
    
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized"); //give values to variables
        
        LeftFront = hardwareMap.dcMotor.get("motor 1 c");
        LeftBack = hardwareMap.dcMotor.get("motor 2 e");
        RightBack = hardwareMap.dcMotor.get("motor 3 e");
        RightFront = hardwareMap.dcMotor.get("motor 0 c");
        

        ti = 0.01;
        
        dt1 = 1/2;
        Vmax = 51*1000;
        RFsp = -51*1000*5;
        RBsp = -10000;
        LFsp = 7200;
        LBsp = 7200;
        
        p = 0.0001;
        i = 0;
        d = 0.000025;
        
        
        pSRF = 0;


        
    }

    @Override
    public void start() {
        
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
     
    @Override
    public void loop() {
        
        SRF = RightFront.getCurrentPosition();
        SRB = RightBack.getCurrentPosition();
        SLF = LeftFront.getCurrentPosition();
        SLB = LeftBack.getCurrentPosition();
        
        if (time%ti == 0) {
            vRF = (SRF - pSRF)/0.01;
            pSRF = SRF;
        }
        else {
            vRF = vRF;
        }
        
        vRFsp = 2500;
        
        /*
        if (time<=dt1) {    //calculation of theoretical velocity
            vRFsp = time*(Vmax/dt1);
        }
        else if (time<=RFsp/Vmax) {
            vRFsp = Vmax;
        }
        else if (time<=((RFsp/Vmax)+dt1)) {
            vRFsp = (Vmax*(1-(t/dt1)))+(RFsp/dt1);
        }
        else if(time>((RFsp/Vmax)+dt1)) {
            vRFsp = 0;
        }
        */
        
        if (time%(2*ti) == 0) { //calculation of experimental acceleration (of error) (dv)
            aRF = ((vRF-vRFsp) - (pvRF-vRFsp))/0.001;
            pvRF = vRF;
        }
        else {
            aRF = vRF;
        }
        
        oRF = oRF + p*(vRF-vRFsp) + d*(aRF);
        
        telemetry.addData("vRF: ", vRF);
        telemetry.addData("accelleration: ", aRF);
        telemetry.addData("time: ", time);
        telemetry.addData("oRF: ", oRF);
        telemetry.addData("SRF: ", SRF);
        telemetry.addData("pSRF: ", pSRF);
        telemetry.update();
        
        RightFront.setPower(oRF);
        
    }
    /*
     * Code to run ONCE after the driver hits STOP
     */
    /*@Override
    public void stop() {
        
    }*/

}