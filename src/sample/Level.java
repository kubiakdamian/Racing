package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.EllipseBuilder;

public class Level {
    public static double startCar2[] = {90.0, 280.0};
    public static double startCar1[] = {50.0, 280.0};
    public static double startAfterCollision[] = {60.0, 280.0};
    public static double meta[] = {0.0, 300.0, 119.0, 300.0};

    public static Ellipse ellipse = EllipseBuilder.create()
            .centerX(400)
            .centerY(300)
            .radiusX(280)
            .radiusY(180)
            .strokeWidth(0)
            .stroke(Color.BLACK)
            .fill(Color.BLACK)
            .build();

    public static Ellipse ellipse2 = EllipseBuilder.create()
            .centerX(400)
            .centerY(300)
            .radiusX(480)
            .radiusY(380)
            .strokeWidth(150)
            .stroke(Color.BLACK)
            .fill(null)
            .build();
}