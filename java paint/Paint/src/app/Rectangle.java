package app;

import java.awt.Shape;
import java.awt.geom.*;
//<<Visitor Pattern>>
//Create a concrete class exentding the visitable interface
public class Rectangle extends BaseShape implements Visitable {
    //Define the parameters of the constructor of the baseShape
    Rectangle() {
        super(new DrawRectangle(), new MoveRectangle(), new ResizeRectangle(), new Rectangle2D.Float());
    }
    //accept the visitor
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    //Clone the current Rectangle Shape and return the cloned shape
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
