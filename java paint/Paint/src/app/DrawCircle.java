package app;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

//<<Strategy Pattern>>
//Draw a circle on the frame, implements DrawBehaviour interface
class DrawCircle implements DrawBehaviour {
    public void draw(Shape shape, int sx, int sy, int ex, int ey) {
        Ellipse2D.Float ellipse = (Ellipse2D.Float) shape;
        ellipse.setFrame(Math.min(sx, ex), Math.min(sy, ey), Math.abs(sx - ex), Math.abs(sy - ey));
    }
}