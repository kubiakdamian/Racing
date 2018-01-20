package sample;

import javafx.scene.shape.*;

public class CollisionDetectors {
    public static boolean DetacteCollision(Shape shape, Shape board){
        Path result = (Path) Shape.intersect(shape, board);
        return !result.getElements().isEmpty();
    }
}
