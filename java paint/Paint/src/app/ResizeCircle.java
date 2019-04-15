package app;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

/*
interface for the shapes, every shape should implement the draw method
*/

class ResizeCircle implements ResizeBehaviour {
    public void resize(Shape shape, int endX, int endY) {
        Ellipse2D.Float ellipse =(Ellipse2D.Float) shape;
        int shapeX = (int) ellipse.getBounds2D().getX();
        int shapeY = (int) ellipse.getBounds2D().getY();

        ellipse.setFrame(Math.min(shapeX, endX), Math.min(shapeY, endY),
        Math.abs(shapeX - endX), Math.abs(shapeY - endY));
    }
}