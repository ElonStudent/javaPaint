package app;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
//<<Visitor Pattern>>
//In MoveVisitor is defined what is done when the visitor visits a shape
class MoveVisitor implements Visitor {
    //Define variable
    private Point point;
    //Constructor
    public MoveVisitor(Point point) {
        this.point = point;
    }
    //Move function which moves the shape
    public void Move(BaseShape moveShape) {
        moveShape.move(point.x, point.y);
    }
    //Add visitor circle
    @Override
    public void visit(Circle circle) {
        Move((BaseShape) circle);
    }
    //Add visitor circle
    @Override
    public void visit(Rectangle rectangle) {
        Move((BaseShape) rectangle);
    }
}