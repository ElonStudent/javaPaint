package app;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

class MoveVisitor implements Visitor {
    private Point point;

    public MoveVisitor(Point point) {
        this.point = point;
    }

    public void Move(BaseShape moveShape) {
        moveShape.move(point.x, point.y);
    }

    @Override
    public void visit(Circle circle) {
        Move((BaseShape) circle);
    }

    @Override
    public void visit(Rectangle rectangle) {
        Move((BaseShape) rectangle);
    }
}