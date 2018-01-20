package sample;

public class Vector2D {
    private double x;
    private double y;
    private double radius;
    private double angle;
    static byte Cartesian = 0, Polar = 1;

    public Vector2D(){
        this.x = 0;
        this.y = 0;
        this.radius = 0;
        this.angle = 0;
    }

    public Vector2D(double param1, double param2, byte coordinates){
        this.angle = 0;
        this.y = 0;
        this.x = 0;
        this.radius = 0;

        if(coordinates == Cartesian){
            this.x = param1;
            this.y = param2;
            refreshPolar();
        }else{
            this.radius = param1;
            this.angle = param2;
            refreshCartesian();
        }
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        refreshCartesian();
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        refreshCartesian();
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
        refreshCartesian();
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
        refreshCartesian();
    }

    private void refreshPolar(){
        radius = Math.sqrt(x * x + y * y);
        angle = Math.atan2(y, x);
    }

    private void refreshCartesian(){
        x = radius * Math.cos(angle);
        y = radius * Math.sin(angle);
    }

    static double normalizeAngle(double angle){
        while(angle >= 2* Math.PI){
            angle -= 2 * Math.PI;
        }

        return angle;
    }
}
