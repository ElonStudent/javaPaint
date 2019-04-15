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
  private static PaintSurface instance = new PaintSurface();

  private Point startDrag, endDrag, clickedPoint;
  private int selectedShapeVal = -1;
  private Shape draggingShape;
  private boolean dragging = false;
  private ShapesFrame frame = ShapesFrame.getInstance();
  private ShapeActions actions = ShapeActions.getInstance();

  public String shapeType = frame.getShapeType();
  public ArrayList<BaseShape> shapes = new ArrayList<BaseShape>();

  ArrayList<ArrayList<BaseShape>> groupShapes = new ArrayList<ArrayList<BaseShape>>();
  ArrayList<BaseShape> availableShapes = new ArrayList<BaseShape>();

  // used for the singleton design pattern
  public static PaintSurface getInstance() {
    return instance;
  }

  public PaintSurface() {
    this.addMouseListener(new MouseAdapter() {

      public void mousePressed(MouseEvent e) {
        shapeType = frame.getShapeType();

        startDrag = new Point(e.getX(), e.getY());
        clickedPoint = new Point(e.getX(), e.getY());
        clickedPoint.x = e.getPoint().x;
        clickedPoint.y = e.getPoint().y;
        endDrag = startDrag;

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

      public void mouseReleased(MouseEvent e) {
        availableShapes.clear();
        if (shapeType == "Rectangle") {
          Shapes rectangle = new TestDecorator(new Rectangle());
          rectangle.draw(startDrag.x, startDrag.y, e.getX(), e.getY());
        } else if (shapeType == "Oval") {
          Shapes oval = new TestDecorator(new Circle());
          oval.draw(startDrag.x, startDrag.y, e.getX(), e.getY());
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

    this.addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        endDrag = new Point(e.getX(), e.getY());
        repaint();
        if (selectedShapeVal != -1) {
          dragging = true;
          // System.out.println(shapeType);
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

  private void DragObject(int val, Point e) {
    actions.saveAction();
    availableShapes.clear();
    BaseShape s = shapes.get(val);

    s.move(e.x, e.y);
  }

  private void ResizeObject(int val, Point point) {
    availableShapes.clear();
    BaseShape s = shapes.get(selectedShapeVal);

    actions.saveAction();
    s.resize(endDrag.x, endDrag.y);
  }

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
      }
      g2.draw(r);
    }
  }

}