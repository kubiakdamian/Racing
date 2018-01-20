package sample;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class Car {
    double w = 35;
    double h = 15;
    public Rectangle graphics;
    public DoubleProperty locationX, locationY;
    public double direction = 0;
    double speed = 7;
    boolean isColliding;

    public Rectangle getGraphics(){
        return graphics;
    }

    public Car(){
        locationX = new SimpleDoubleProperty(0);
        locationY = new SimpleDoubleProperty(0);
        graphics = new Rectangle(w, h);
        graphics.setStroke(Color.BLACK);
        graphics.setRotationAxis(Rotate.Z_AXIS);
        graphics.xProperty().bind(locationX.add(w / 2));
        graphics.yProperty().bind(locationY.multiply(-1).add(Main.height - w / 2));
        graphics.setFill(Color.MEDIUMPURPLE);
        graphics.setWidth(w);
        graphics.setHeight(h);

    }

    public void translateByVector(Vector2D vector){
        locationX.set(locationX.get() + vector.getX());
        locationY.set(locationY.get() + vector.getY());
    }


    public void setLocationByVector(double x, double y){
        locationX.set(x);
        locationY.set(y);
    }

    public void setDirection(double angle){
        graphics.setRotate(180 - angle);
        direction = angle;
    }

    public void translateByRadius(double d){
        Vector2D v = new Vector2D(d, direction / 180.0 * Math.PI, Vector2D.Polar);
        translateByVector(v);
    }
}
