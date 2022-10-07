public class PIDController {

    private double position = 0, velocity = 0, integral = 0, error = 0;

    private double p = 0, i = 0, d = 0;

    private double setPoint = 0;

    public void setPoint(double setPoint) {
        this.setPoint = setPoint;
    }

    public void setCoefficients(double p, double i, double d) {
        this.p = p;
        this.i = i;
        this.d = d;
    }

    public void update(double position, double dt) {
        this.integral += dt*(setPoint - this.position + setPoint - position)*0.5; // average of error is added to the integral
        this.velocity = (position - this.position) / dt; //error2 - error1 = (sp-pos2)-(sp-pos1) = pos1-pos2 . Difference of errors is used for velocity.
        this.position = position;
        this.error = this.setpoint - this.position;
    }

    public double getSum() {
        return p * error + d * velocity + i * integral;
    }

}