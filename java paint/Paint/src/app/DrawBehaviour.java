package app;

import java.awt.Shape;

/*
interface for the shapes, every shape should implement the draw method
*/
public interface DrawBehaviour{
    public void draw(Shape shape, int sx, int sy, int ex, int ey);
}