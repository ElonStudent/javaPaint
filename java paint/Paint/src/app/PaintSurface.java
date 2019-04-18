package app;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Dimension2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaintSurface extends JComponent {
  //Create new instance of PaintSurface <<Singleton Pattern>>
  private static PaintSurface instance = new PaintSurface();

  private Point startDrag, endDrag, clickedPoint;
  private int selectedShapeVal = -1;
  private Shape draggingShape;
  private boolean dragging = false;
  //Sets a vairable set to the frame instance <<Singleton Pattern>>
  private ShapesFrame frame = ShapesFrame.getInstance();
  //Sets a vairable set to the ShapeActions instance <<Singleton Pattern>>
  private ShapeActions actions = ShapeActions.getInstance();

  private int oldSX;
  private int oldSY;

  public String shapeType = frame.getShapeType();
  public ArrayList<BaseShape> shapes = new ArrayList<BaseShape>();

  private ArrayList<BaseShape> availableShapes = new ArrayList<BaseShape>();

  //Returns the paintsurface instance <<Singleton Pattern>>
  public static PaintSurface getInstance() {
    return instance;
  }
  public PaintSurface() {
    //Checks mouse actions
    this.addMouseListener(new MouseAdapter() {
      //Define position of the mouse by mousePressed location
      public void mousePressed(MouseEvent e) {
        shapeType = frame.getShapeType();

        startDrag = new Point(e.getX(), e.getY());
        clickedPoint = new Point(e.getX(), e.getY());
        clickedPoint.x = e.getPoint().x;
        clickedPoint.y = e.getPoint().y;
        endDrag = startDrag;

        //Add text to a shape
        if (shapeType == "Top" || shapeType == "Bottom" || shapeType == "Left" || shapeType == "Right") {
          for (BaseShape s : shapes) {
            if (s.shape.contains(clickedPoint.x, clickedPoint.y)) {
              actions.saveAction();
              String text = JOptionPane.showInputDialog("Enter text");
              s.addText(shapeType, text);
            }
          }
        }
        repaint();
      }

      //Draw or change the shapes based on selected option
      public void mouseReleased(MouseEvent e) {
        availableShapes.clear();
        if (shapeType == "Rectangle") {
          Shapes rectangle = new TestDecorator(new Rectangle());
          rectangle.draw(startDrag.x, startDrag.y, e.getX(), e.getY());
        } else if (shapeType == "Oval") {
          Shapes oval = new TestDecorator(new Circle());
          oval.draw(startDrag.x, startDrag.y, e.getX(), e.getY());
        }else if(shapeType == "Group"){
          int scount = -1;
          List<BaseShape> test = new ArrayList<BaseShape>();
          for (BaseShape s : shapes){
            scount++;
            if (s.shape.contains(clickedPoint.x, clickedPoint.y)){
              for (BaseShape x : shapes){
                if(x.getX() >= startDrag.x && x.getY() >= startDrag.y && x.getWidth() <= endDrag.x && x.getHeight() <= endDrag.y){
                  if(s != x)
                    test.add(x);
                }
                else{
                  System.out.print("no shapes");
                }
              }
              group(scount, test);
              actions.saveAction();
            }
          }
        }else if(shapeType == "Ungroup"){
          int scount = -1;
          for (BaseShape s : shapes){
            scount++;
            if(s.shape.contains(clickedPoint.x, clickedPoint.y) && s.GetList() != null){
              ungroup(scount);
            }else{
              System.out.println("No group found in this shape/ not  parent of a group");
            }
          }
        }
        if (selectedShapeVal != -1) {
          if (shapeType == "Select") {
            if (dragging == true) {
              DragObject(selectedShapeVal, e.getPoint());
            }
          } else if (shapeType == "Resize") {
            if (dragging == true) {
              ResizeObject(selectedShapeVal, e.getPoint());
            }
          }
        }

        startDrag = null;
        endDrag = null;
        selectedShapeVal = -1;
        dragging = false;
        repaint();
      }

    });

    //Resize or move the shape to a new location based on the position the mouse is dragged to
    this.addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        endDrag = new Point(e.getX(), e.getY());
        repaint();
        if (selectedShapeVal != -1) {
          dragging = true;
          if (shapeType == "Resize") {
            BaseShape s = shapes.get(selectedShapeVal);

            startDrag.x = s.getWidth();
            startDrag.y = s.getHeight();
          } else if (shapeType == "Select") {
            startDrag.x = e.getPoint().x;
            startDrag.y = e.getPoint().y;
          }
        }
      }
    });
  }

  //Create a group based on the selected shape
  public void group(int ShapeID, List<BaseShape> shape){
    BaseShape s = shapes.get(ShapeID);
    for (BaseShape b : shape)
      s.CreateList(b);
    actions.saveAction();
  }

  //Removes the shapes from the group
  public void ungroup(int ShapeID){
    BaseShape s = shapes.get(ShapeID);
    if(s.GetList() != null){
      for (BaseShape b : s.GetList())
        s.GetList().remove(b);
    }
  }

  //Move shape or group of shapes to a new location
  private void DragObject(int val, Point e) {
    actions.saveAction();
    availableShapes.clear();
    BaseShape s = shapes.get(val);

    oldSX = s.getX();
    oldSY = s.getY();
    if(s.GetList() != null){
      s.move(e.x, e.y);
      for(BaseShape b : s.GetList()){
        b.move(b.getX() + (e.x - oldSX), b.getY() + (e.y - oldSY));
      }
    }else if (s.GetList() == null){
      for (BaseShape x : shapes){
        if(x.GetList() != null){
          if(x.GetList().contains(s) && x.GetList() != null){
            x.move(x.getX() + (e.x - oldSX), x.getY() + (e.y - oldSY));
            for(BaseShape t : x.GetList())
              t.move(t.getX() + (e.x - oldSX), t.getY() + (e.y - oldSY));
          }
        } else {
          s.move(e.x, e.y);
        }
      }
    }
  }

  //Resize a shape or a group of shapes
  private void ResizeObject(int val, Point point) {
    availableShapes.clear();
    BaseShape s = shapes.get(selectedShapeVal);

    actions.saveAction();
    oldSX = s.getX();
    oldSY = s.getY();
    if(s.GetList() != null){
      s.resize(endDrag.x, endDrag.y);
      for(BaseShape b : s.GetList()){
        b.resize(b.getX() + (endDrag.x - oldSX), b.getY() + (endDrag.y - oldSY));
      }
    }else if (s.GetList() == null){
      for (BaseShape x : shapes){
        if(x.GetList() != null){
          if(x.GetList().contains(s) && x.GetList() != null){
            x.resize(x.getX() + (endDrag.x - oldSX), x.getY() + (endDrag.y - oldSY));
            for(BaseShape t : x.GetList())
              t.resize(t.getX() + (endDrag.x - oldSX), t.getY() + (endDrag.y - oldSY));
          }
        } else {
          s.resize(endDrag.x, endDrag.y);
        }
      }
    }
  }

  //Gives color to the shapes
  private void paintBackground(Graphics2D g2) {
    g2.setPaint(Color.LIGHT_GRAY);
    for (int i = 0; i < getSize().width; i += 10) {
      Shape line = new Line2D.Float(i, 0, i, getSize().height);
      g2.draw(line);
    }

    for (int i = 0; i < getSize().height; i += 10) {
      Shape line = new Line2D.Float(0, i, getSize().width, i);
      g2.draw(line);
    }
  }

  //visualizes all the shapes in the frame
  public void paint(Graphics g) {
    if (shapeType != "Resize" || shapeType == "Select") {
      availableShapes.clear();
    }

    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    paintBackground(g2);
    Color[] colors = { Color.YELLOW, Color.MAGENTA, Color.CYAN, Color.RED, Color.BLUE, Color.PINK };
    int colorIndex = 0;

    g2.setStroke(new BasicStroke(2));
    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f));
    for (BaseShape s : shapes) {
      if (startDrag != null && s != null && shapeType == "Resize" || shapeType == "Select") {
        if (s.shape.contains(clickedPoint.x, clickedPoint.y)) {
          if (!availableShapes.contains(s)) {
            availableShapes.add(s);
          }
          selectedShapeVal = shapes.indexOf(s);
          g2.setStroke(new BasicStroke(10));
        }
      }
      g2.setFont(new Font("Serif", Font.BOLD, 18));
      g2.setPaint(Color.BLACK);
      if (s.textList.size() != 0) {
        for (BaseText text : s.textList) {
          g2.drawString(text.text, text.x, text.y);
        }
      }

      g2.setPaint(Color.BLACK);
      g2.draw(s.shape);
      g2.setPaint(colors[(colorIndex++) % 6]);
      g2.fill(s.shape);
    }

    if (startDrag != null && endDrag != null) {
      g2.setPaint(Color.LIGHT_GRAY);
      g2.setStroke(new BasicStroke(10));
      int x = startDrag.x;
      int y = startDrag.y;
      int w = endDrag.x;
      int h = endDrag.y;
      Shape r = new Rectangle2D.Float();

      if (shapeType == "Select" && selectedShapeVal != -1) {
        BaseShape s = shapes.get(selectedShapeVal);
        x = endDrag.x;
        y = endDrag.y;
        w = s.getWidth();
        h = s.getHeight();
        if (s instanceof Rectangle) {
          r = new Rectangle2D.Float(x, y, w, h);
        } else if (s instanceof Circle) {
          r = new Ellipse2D.Float(x, y, w, h);
        }
      }
      if (shapeType == "Resize" && selectedShapeVal != -1) {
        BaseShape s = shapes.get(selectedShapeVal);
        x = s.getX();
        y = s.getY();
        if (s instanceof Rectangle) {
          r = new Rectangle2D.Float(Math.min(x, w), Math.min(y, h), Math.abs(x - w), Math.abs(y - h));
        } else if (s instanceof Circle) {
          r = new Ellipse2D.Float(Math.min(x, w), Math.min(y, h), Math.abs(x - w), Math.abs(y - h));
        }
      }
      if (shapeType == "Oval") {
        r = new Ellipse2D.Float(Math.min(x, w), Math.min(y, h), Math.abs(x - w), Math.abs(y - h));
      } else if (shapeType == "Rectangle") {
        r = new Rectangle2D.Float(Math.min(x, w), Math.min(y, h), Math.abs(x - w), Math.abs(y - h));
      } else if (shapeType == "Group") {
        r = new Rectangle2D.Float(Math.min(x, w), Math.min(y, h), Math.abs(x - w), Math.abs(y - h));
      } else if (shapeType == "Ungroup") {
        r = new Rectangle2D.Float(Math.min(x, w), Math.min(y, h), Math.abs(x - w), Math.abs(y - h));
      }
      g2.draw(r);
    }
  }

}