package app;
//Class that can decorate a shape
public abstract class ShapeDecorator implements Shapes {
    //Shape to decorate
    protected BaseShape decoratedShape;
   
    //constructor
    public ShapeDecorator(BaseShape decoratedShape){
       this.decoratedShape = decoratedShape;
    }
    
    //Draw the given shape
    public void draw(int sx, int sy, int ex, int ey){
       decoratedShape.draw(sx, sy, ex, ey);
    }	
 }