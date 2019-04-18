package app;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Elon Gielink, Daan Eekhof
 */
// used as the "BuyStock" replacement for the Command Pattern
public class ShapeActions {
  //<<singleton object>>
  //Create instance
  private static ShapeActions instance = new ShapeActions();

  //Create new shapeFileWriter
  private ShapeFileWriter f = new ShapeFileWriter();

  //Uses the PaintSurface
  public PaintSurface surface;

  //Create new list of actions (undo/redo)
  public ArrayList<ArrayList<BaseShape>> undo = new ArrayList<ArrayList<BaseShape>>();
  public ArrayList<ArrayList<BaseShape>> redo = new ArrayList<ArrayList<BaseShape>>();

  //<<singleton object>>
  //Return current instance
  public static ShapeActions getInstance() {
    return instance;
  }

  //Save the action performed
  public void saveAction() {
    undo.add(clone(surface.shapes));
  }

  //Undo the previous move
  public void undo() {
    if (undo.size() <= 0)
      return;

    redo.add(clone(surface.shapes));
    surface.shapes = clone(undo.get(undo.size() - 1));
    undo.remove(undo.size() - 1);
    surface.repaint();
  }

  //Save the file
  public void save(){
    try {
      f.SaveShapeToFile(surface.shapes);
    } catch (Exception ex) {
      Logger.getLogger(ShapeActions.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  //Load the previous saven file
  public void load(){
    try {
      f.LoadShapeFromFile();
    } catch (Exception ex) {
      Logger.getLogger(ShapeActions.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  //Redo to previous move
  public void redo() {
    if (redo.size() <= 0)
      return;

    undo.add(clone(surface.shapes));
    surface.shapes = clone(redo.get(redo.size() - 1));
    redo.remove(redo.size() - 1);
    surface.repaint();
  }

  //Clear all shapes from the surface
  public void clear() {
    saveAction();
    surface.shapes.clear();
  }

  //Add shape to the surface
  public void addShapeToArray(BaseShape r) {
    saveAction();
    surface.shapes.add(r);
  }
  
  //Clone all shapes on the surface
  public ArrayList<BaseShape> clone(ArrayList<BaseShape> list) {
    ArrayList<BaseShape> clonedList = new ArrayList<BaseShape>(list.size());
    for (BaseShape shape : list) {
      clonedList.add(shape.clone());
    }
    return clonedList;
  }
}