package app;

import java.awt.Shape;

//<<Strategy Pattern>>
//Create interface for the resizeBehaviour
public interface ResizeBehaviour{
    void resize(Shape shape, int width, int height);
}