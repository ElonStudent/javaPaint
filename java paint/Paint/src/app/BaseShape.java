package app;

import java.util.List;
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
//<<uses the composite pattern>>
//<<uses the strategy pattern>>
//<<uses the singleton pattern>>
public abstract class BaseShape {
    //Sets a vairable set to the ShapeActions instance <<Singleton Pattern>>
    ShapeActions actions = ShapeActions.getInstance();
    //Define variable for the Drawbehaviour, MoveBehaviour and ResizeBehaviour<<Strategy Pattern>>
    DrawBehaviour drawBehaviour;
    MoveBehaviour moveBehaviour;
    ResizeBehaviour resizeBehaviour;
    ArrayList<BaseText> textList = new ArrayList<BaseText>();
    //<<composite pattern>>
    //Create list of BaseShapes who are in the same group
    List<BaseShape> group;
     
    Shape shape;

    //Constructor
    public BaseShape(DrawBehaviour drawBehaviour, MoveBehaviour moveBehaviour, ResizeBehaviour resizeBehaviour,
            Shape shape) {
        this.drawBehaviour = drawBehaviour;
        this.moveBehaviour = moveBehaviour;
        this.resizeBehaviour = resizeBehaviour;
        this.shape = shape;
    }
    //returns the X position of the shape
    public int getX() {
        return (int) shape.getBounds2D().getX();
    }
    //Returns the Y position of the shape
    public int getY() {
        return (int) shape.getBounds2D().getY();
    }
    //Returns the width of the shape
    public int getWidth() {
        return (int) shape.getBounds2D().getWidth();
    }
    //Returns the height of the shape
    public int getHeight() {
        return (int) shape.getBounds2D().getHeight();
    }
    //Call the draw function of the DrawBehaviour, given in the parameters of the constructor   <<Strategy Pattern>>
    public void draw(int sx, int sy, int ex, int ey) {
        drawBehaviour.draw(shape, sx, sy, ex, ey);
        PushToArray();
    }
    //Call the move function of the moveBehaviour, given in the parameters of the constructor   <<Strategy Pattern>>
    public void move(int x, int y) {
        moveBehaviour.move(shape, x, y);
        updateTextPos();
    }
    //Call the resize function of the resizeBehaviour, given in the parameters of the constructor   <<Strategy Pattern>>
    public void resize(int width, int height) {
        resizeBehaviour.resize(shape, width, height);
        updateTextPos();
    }
    //Converts the shape to text
    public String toString() {
        String s = "";
        s += "x = " + getX();
        s += " | y = " + getY();
        s += " | width = " + getWidth();
        s += " | height = " + getHeight();
        return s;
    }
    //Push the shape to the surface through the ShapeActions instance <<Command Pattern>>
    public void PushToArray() {
        actions.addShapeToArray(this);
    }
    //Clone the BaseShape
    public abstract BaseShape clone();

    //Add text to shape
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

    //Update the position of the text
    public void updateTextPos(){
        for(BaseText text : textList){
            text.setPos(getX(), getY(), getWidth(), getHeight());
        }
    }

    //<<Composite Pattern>>
    //Create a list of shapes who are in the same group
    public void CreateList(BaseShape addShape){
        if(group == null){
            group = new ArrayList<BaseShape>();
            group.add(addShape);
        }else{
            group.add(addShape);
        }
    }
    
    //Returns the group
    public List<BaseShape> GetList(){
        return group;
    }
}