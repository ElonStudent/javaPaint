package app;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/*
interface for the shapes, every shape should implement the draw method
*/

class MoveRectangle implements MoveBehaviour {
    public void move(Shape shape, int x, int y) {
        Rectangle2D.Float rectangle = (Rectangle2D.Float) shape;
        rectangle.setRect(x, y, rectangle.width, rectangle.height);
    }
}