package app;
public abstract class ShapeDecorator implements Shapes {
    protected BaseShape decoratedShape;
 
    public ShapeDecorator(BaseShape decoratedShape){
       this.decoratedShape = decoratedShape;
    }
 
    public void draw(int sx, int sy, int ex, int ey){
       decoratedShape.draw(sx, sy, ex, ey);
       
    }	
 }