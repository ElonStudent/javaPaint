package app;

import java.awt.Shape;
import java.awt.geom.*;

public class Circle extends BaseShape implements Visitable {
    Circle() {
        super(new DrawCircle(), new MoveCircle(), new ResizeCircle(), new Ellipse2D.Float());
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public BaseShape clone() {
        BaseShape newbase;
        newbase = new Circle();
        newbase.shape = new Ellipse2D.Float(getX(), getY(), getWidth(), getHeight());
        for (BaseText text : textList) {
            newbase.addText(text.position, text.text);
        }
        return newbase;
    }

}
