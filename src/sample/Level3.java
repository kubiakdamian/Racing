package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Level3 {
    public static double startCar2[] = {150.0, 280.0};
    public static double startCar1[] = {110.0, 280.0};
    public static double startAfterCollision[] = {130.0, 280.0};
    public static double meta[] = {0.0, 300.0, 230.0, 300.0};

    public static Polygon getPolygon(){
        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(new Double[]{
                200.0, 150.0,
                700.0, 300.0,
                300.0, 540.0 });
        polygon.setStroke(Color.BLACK);
        polygon.setStrokeWidth(10);
        polygon.setFill(Color.BLACK);

        return polygon;
    }
}