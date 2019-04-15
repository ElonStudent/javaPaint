package app;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/*
interface for the shapes, every shape should implement the draw method
*/

class DrawRectangle implements DrawBehaviour {
    public void draw(Shape shape, int sx, int sy, int ex, int ey) {
        Rectangle2D.Float rectangle = (Rectangle2D.Float) shape;
        rectangle.setRect(Math.min(sx, ex), Math.min(sy, ey), Math.abs(sx - ex), Math.abs(sy - ey));
    }
}