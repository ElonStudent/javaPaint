package app;

import java.awt.Shape;
import java.awt.geom.*;

public class TestDecorator extends ShapeDecorator {

   public TestDecorator(BaseShape decoratedShape) {
      super(decoratedShape);
   }

   @Override
   public void draw(int sx, int sy, int ex, int ey) {
      decoratedShape.draw(sx, sy, ex, ey);
   }

   @Override
   public void addText(String position, String text) {
      
   }
}