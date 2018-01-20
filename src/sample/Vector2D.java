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
    public double getY() {
        return y;
    }

    private void refreshPolar(){
        radius = Math.sqrt(x * x + y * y);
        angle = Math.atan2(y, x);
    }

    private void refreshCartesian(){
        x = radius * Math.cos(angle);
        y = radius * Math.sin(angle);
    }

}
