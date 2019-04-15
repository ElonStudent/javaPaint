package app;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.text.Position;
/**
 * 
 * @author Elon Gielink
 */
public abstract class BaseShape {

    ShapeActions actions = ShapeActions.getInstance();
    DrawBehaviour drawBehaviour;
    MoveBehaviour moveBehaviour;
    ResizeBehaviour resizeBehaviour;
    ArrayList<BaseText> textList = new ArrayList<BaseText>();
     
    Shape shape;

    public BaseShape(DrawBehaviour drawBehaviour, MoveBehaviour moveBehaviour, ResizeBehaviour resizeBehaviour,
            Shape shape) {
        this.drawBehaviour = drawBehaviour;
        this.moveBehaviour = moveBehaviour;
        this.resizeBehaviour = resizeBehaviour;
        this.shape = shape;
    }

    public int getX() {
        return (int) shape.getBounds2D().getX();
    }

    public int getY() {
        return (int) shape.getBounds2D().getY();
    }

    public int getWidth() {
        return (int) shape.getBounds2D().getWidth();
    }

    public int getHeight() {
        return (int) shape.getBounds2D().getHeight();
    }

    public void draw(int sx, int sy, int ex, int ey) {
        drawBehaviour.draw(shape, sx, sy, ex, ey);
        PushToArray();
    }

    public void move(int x, int y) {
        moveBehaviour.move(shape, x, y);
        updateTextPos();
    }

    public void resize(int width, int height) {
        resizeBehaviour.resize(shape, width, height);
        updateTextPos();
    }

    public String toString() {
        String s = "";
        s += "x = " + getX();
        s += " | y = " + getY();
        s += " | width = " + getWidth();
        s += " | height = " + getHeight();
        return s;
    }

    public void PushToArray() {
        actions.addShapeToArray(this);
    }

    public abstract BaseShape clone();

    public void addText(String shapeType, String textString) {
        for(BaseText text : textList){
            if(shapeType == text.position){
                text.text = textString;
                return;
            }
        }        
        BaseText text = new BaseText(textString, shapeType);
        text.setPos(getX(), getY(), getWidth(), getHeight());
        textList.add(text);
    }

    public void updateTextPos(){
        for(BaseText text : textList){
            text.setPos(getX(), getY(), getWidth(), getHeight());
        }
    }
}