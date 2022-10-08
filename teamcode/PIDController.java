public class PIDController {
    /*
    Proportional Integeral Derivative control system
    takes distance (error) from setPoint (error = setPoint - position)
    takes change in error over small time interval de/dt
    takes integral of error from t=0 to current : integral = e0*dt0 + e1*dt1 + e2*dt2 + ... + ei*dti

    constant doubles, p (proportional), i (integral), d (derivative)

    getSum returns p * e + d * de/dt + i * integral
    getSum is how much to impact the system's output (ex : motor position (via power), angle position (via velocity change), velocity (via adding to current motor power) etc...)
     */

    // feed this program position and time.

    private double position = 0, velocity = 0, integral = 0, error = 0;

    private double p = 0, i = 0, d = 0;

    private double setPoint = 0


    public void setPoint(double setPoint) {
        this.setPoint = setPoint;
    }

    public void increaseSetPoint(double distance) {
        this.setPoint += distance;
    }

    public void setCoefficients(double p, double i, double d) {
        this.p = p;
        this.i = i;
        this.d = d;
    }


    /*
    The only method that is needed to be called routinely
     */
    public void update(double position, double dt) {
        this.integral += dt*(setPoint - this.position + setPoint - position)*0.5; // average of error is added to the integral
        this.velocity = (position - this.position) / dt; //error2 - error1 = (sp-pos2)-(sp-pos1) = pos1-pos2 . Difference of errors is used for velocity.
        this.position = position;
        this.error = this.setpoint - this.position;
    }

    public double getSum() {
        return p * error + d * velocity + i * integral;
    }

    public double getSetPoint() {
        return setPoint;
    }
}