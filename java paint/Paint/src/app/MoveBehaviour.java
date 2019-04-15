package app;

import java.awt.Shape;

/*
interface for the shapes, every shape should implement the draw method
*/
public interface MoveBehaviour{
    void move(Shape shape, int x, int y);
}