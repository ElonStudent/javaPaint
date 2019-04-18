package app;

import java.awt.Shape;
import java.awt.geom.*;
//<<Visitor Pattern>>
//Create a concrete class exentding the visitable interface
public class Circle extends BaseShape implements Visitable {
    //Call the BaseShape constructor and fill in the behaviours.
    Circle() {
        super(new DrawCircle(), new MoveCircle(), new ResizeCircle(), new Ellipse2D.Float());
    }
    //Accept the visitor
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
    //Clone the current Circle Shape and return the cloned shape
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
