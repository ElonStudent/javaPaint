package app;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

class ResizeVisitor implements Visitor {

    private Point point;

    public ResizeVisitor(Point point) {
        this.point = point;
    }

    public void Resize(Shape ResizedShape) {
        
        /*
        if (ResizedShape instanceof Rectangle) {
            Rectangle rect = (Rectangle) ResizedShape;
            rect.setRect(Math.min(rect.x, point.x), Math.min(rect.y, point.y), Math.abs(rect.x - point.x),
                    Math.abs(rect.y - point.y));
        } else if (ResizedShape instanceof Circle) {
            Circle circle = (Circle) ResizedShape;
            circle.setFrame(Math.min(circle.x, point.x), Math.min(circle.y, point.y), Math.abs(circle.x - point.x),
                    Math.abs(circle.y - point.y));
        }
        return ResizedShape;
        */
    }

    @Override
    public void visit(Circle circle) {
        Resize((Shape) circle);
    }

    @Override
    public void visit(Rectangle rectangle) {
        Resize((Shape) rectangle);
    }

}