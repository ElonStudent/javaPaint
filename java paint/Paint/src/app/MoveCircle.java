package app;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

//<<Strategy Pattern>>
//Move circle to other position on the frame, implements movebehaviour
class MoveCircle implements MoveBehaviour {
    public void move(Shape shape, int x, int y) {
        Ellipse2D.Float ellipse = (Ellipse2D.Float) shape;
        ellipse.setFrame(x, y, ellipse.width, ellipse.height);
    }
}