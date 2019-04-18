package app;

import java.awt.Shape;

//<<Strategy Pattern>>
//Interface for drawing a shape
public interface DrawBehaviour{
    public void draw(Shape shape, int sx, int sy, int ex, int ey);
}