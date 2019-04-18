package app;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

//<<Strategy Pattern>>
//Move rectangle to other position on the frame, implements movebehaviour
class MoveRectangle implements MoveBehaviour {
    public void move(Shape shape, int x, int y) {
        Rectangle2D.Float rectangle = (Rectangle2D.Float) shape;
        rectangle.setRect(x, y, rectangle.width, rectangle.height);
    }
}