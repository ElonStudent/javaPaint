package app;

import java.awt.Shape;

//<<Strategy Pattern>>
//Interface for moving a shape
public interface MoveBehaviour{
    void move(Shape shape, int x, int y);
}