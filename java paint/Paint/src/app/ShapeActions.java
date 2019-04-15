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
public class ShapeActions {
  private static ShapeActions instance = new ShapeActions();

  private ShapeFileWriter f = new ShapeFileWriter();

  public PaintSurface surface;
  public ArrayList<ArrayList<BaseShape>> undo = new ArrayList<ArrayList<BaseShape>>();
  public ArrayList<ArrayList<BaseShape>> redo = new ArrayList<ArrayList<BaseShape>>();


  public static ShapeActions getInstance() {
    return instance;
  }

  public void saveAction() {
    undo.add(clone(surface.shapes));
  }

  public void undo() {
    if (undo.size() <= 0)
      return;

    redo.add(clone(surface.shapes));
    surface.shapes = clone(undo.get(undo.size() - 1));
    undo.remove(undo.size() - 1);
    surface.repaint();
  }

  public void save(){
    try {
      f.SaveShapeToFile(surface.shapes);
    } catch (Exception ex) {
      Logger.getLogger(ShapeActions.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public void load(){
    try {
      f.LoadShapeFromFile();
    } catch (Exception ex) {
      Logger.getLogger(ShapeActions.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public void redo() {
    if (redo.size() <= 0)
      return;

    undo.add(clone(surface.shapes));
    surface.shapes = clone(redo.get(redo.size() - 1));
    redo.remove(redo.size() - 1);
    surface.repaint();
  }

  public void clear() {
    saveAction();
    surface.shapes.clear();
  }

  public void addShapeToArray(BaseShape r) {
    saveAction();
    surface.shapes.add(r);
  }

  public ArrayList<BaseShape> clone(ArrayList<BaseShape> list) {
    ArrayList<BaseShape> clonedList = new ArrayList<BaseShape>(list.size());
    for (BaseShape shape : list) {
      clonedList.add(shape.clone());
    }
    return clonedList;
  }
}