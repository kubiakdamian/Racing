package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Level2 {
    public static double startCar2[] = {150.0, 280.0};
    public static double startCar1[] = {110.0, 280.0};
    public static double startAfterCollision[] = {130.0, 280.0};
    public static double meta[] = {60.0, 300.0, 200.0, 300.0};

    public static Rectangle getRectangle(){
        Rectangle rectangle = new Rectangle(200, 150, 400, 300);
        rectangle.setFill(Color.BLACK);

        return rectangle;
    }

    public static Rectangle getCollisionRectangle(){
        Rectangle rectangle = new Rectangle(0, 0, 800, 600);
        rectangle.setFill(null);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(120);

        return rectangle;
    }
}