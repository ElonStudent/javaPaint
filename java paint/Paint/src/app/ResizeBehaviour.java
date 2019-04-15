package app;

import java.awt.Shape;

/*
interface for the shapes, every shape should implement the draw method
*/
public interface ResizeBehaviour{
    void resize(Shape shape, int width, int height);
}