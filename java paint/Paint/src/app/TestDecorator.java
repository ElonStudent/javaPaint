package app;

import java.awt.Shape;
import java.awt.geom.*;

//Decorates a shape
public class TestDecorator extends ShapeDecorator {
   //constructor
   public TestDecorator(BaseShape decoratedShape) {
      super(decoratedShape);
   }
   //draw the given shape
   @Override
   public void draw(int sx, int sy, int ex, int ey) {
      decoratedShape.draw(sx, sy, ex, ey);
   }
   //add text to the given shape
   @Override
   public void addText(String position, String text) {

   }
}