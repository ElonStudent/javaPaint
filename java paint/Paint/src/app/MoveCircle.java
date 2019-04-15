package app;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

/*
interface for the shapes, every shape should implement the draw method
*/

class MoveCircle implements MoveBehaviour {
    public void move(Shape shape, int x, int y) {
        Ellipse2D.Float ellipse = (Ellipse2D.Float) shape;
        ellipse.setFrame(x, y, ellipse.width, ellipse.height);
    }
}