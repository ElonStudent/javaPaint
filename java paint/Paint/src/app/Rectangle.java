package app;

import java.awt.Shape;
import java.awt.geom.*;

public class Rectangle extends BaseShape implements Visitable {
    Rectangle() {
        super(new DrawRectangle(), new MoveRectangle(), new ResizeRectangle(), new Rectangle2D.Float());
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public BaseShape clone() {
        BaseShape newbase;
        newbase = new Rectangle();
        newbase.shape = new Rectangle2D.Float(getX(), getY(), getWidth(), getHeight());
        for (BaseText text : textList) {
            newbase.addText(text.position, text.text);
        }
        return newbase;
    }
}
