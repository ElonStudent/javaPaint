package app;
/*
interface for the shapes, every shape should implement the draw method
*/
public interface Shapes{
    void draw(int sx, int sy, int ex, int ey);
    void addText(String position, String text);
}