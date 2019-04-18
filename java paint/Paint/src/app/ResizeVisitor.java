package app;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

//<<Visitor Pattern>>
class ResizeVisitor implements Visitor {

    private Point point;

    //Constructor
    public ResizeVisitor(Point point) {
        this.point = point;
    }

    public void Resize(Shape ResizedShape) {
        
    }
    //Visit circle
    @Override
    public void visit(Circle circle) {
        Resize((Shape) circle);
    }
    //Visit rectangle
    @Override
    public void visit(Rectangle rectangle) {
        Resize((Shape) rectangle);
    }

}