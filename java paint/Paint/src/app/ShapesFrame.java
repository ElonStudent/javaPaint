package app;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShapesFrame extends JFrame implements ActionListener {
  private static ShapesFrame instance = new ShapesFrame();

  private PaintSurface surface;
  private Command command = Command.getInstance();
  private ShapeActions actions = ShapeActions.getInstance();
  private ShapeUndo sUndo = new ShapeUndo(actions);
  private ShapeRedo sRedo = new ShapeRedo(actions);
  private ShapeClear sClear = new ShapeClear(actions);
  private ShapeSave sSave = new ShapeSave(actions);
  private ShapeLoad sOpen = new ShapeLoad(actions);

  public String shapeType = "Rectangle";
  public ArrayList<Shape> shapes = new ArrayList<Shape>();

  String[] textPos = new String[] { "Top", "Bottom", "Left", "Right" };
  JComboBox<String> textList = new JComboBox<>(textPos);

  public static ShapesFrame getInstance() {
    return instance;
  }

  public void createDrawObj() {
    surface = PaintSurface.getInstance();
    actions.surface = surface;


    ButtonGroup cbg = new ButtonGroup();
    JButton openButton = new JButton("Open");
    JButton saveButton = new JButton("Save");
    JRadioButton groupButton = new JRadioButton("Group");
    JRadioButton ungroupButton = new JRadioButton("Ungroup");
    JRadioButton ovalButton = new JRadioButton("Oval");
    JRadioButton rectangleButton = new JRadioButton("Rectangle");
    JRadioButton selectButton = new JRadioButton("Select");
    JRadioButton resizeButton = new JRadioButton("Resize");
    JButton clearButton = new JButton("Clear");
    JButton undoButton = new JButton("Undo");
    JButton redoButton = new JButton("Redo");
    JPanel radioPanel = new JPanel(new FlowLayout());

    CreateObjectInFrame(cbg, radioPanel, openButton);
    CreateObjectInFrame(cbg, radioPanel, saveButton);
    CreateObjectInFrame(cbg, radioPanel, groupButton);
    CreateObjectInFrame(cbg, radioPanel, ungroupButton);
    CreateObjectInFrame(cbg, radioPanel, ovalButton);
    CreateObjectInFrame(cbg, radioPanel, rectangleButton);
    CreateObjectInFrame(cbg, radioPanel, selectButton);
    CreateObjectInFrame(cbg, radioPanel, resizeButton);
    CreateObjectInFrame(cbg, radioPanel, clearButton);
    CreateObjectInFrame(cbg, radioPanel, undoButton);
    CreateObjectInFrame(cbg, radioPanel, redoButton);
    CreateObjectInFrame(cbg, radioPanel, textList);

    this.setLayout(new BorderLayout());
    this.add(radioPanel, BorderLayout.NORTH);
    this.setSize(900, 600);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.add(surface, BorderLayout.CENTER);
    this.setVisible(true);
  }

  private void CreateObjectInFrame(ButtonGroup group, JPanel panel, Component o) {
    panel.add(o);

    if (o instanceof JButton) {
      JButton b = (JButton) o;
      group.add(b);
      b.addActionListener(this);
    } else if (o instanceof JRadioButton) {
      JRadioButton b = (JRadioButton) o;
      group.add(b);
      b.addActionListener(this);
    } else if (o instanceof JComboBox) {
      JComboBox<String> b = (JComboBox) o;
      b.addActionListener(this);
    }
  }

  public void actionPerformed(ActionEvent ae) {
    if (ae.getActionCommand().toString() == "Clear") {
      command.addCommand(sClear);
      command.executeCommand();
    } else if (ae.getActionCommand().toString() == "Undo") {
      command.addCommand(sUndo);
      command.executeCommand();
    } else if (ae.getActionCommand().toString() == "Redo") {
      command.addCommand(sRedo);
      command.executeCommand();
    } else if (ae.getActionCommand().toString() == "Save") {
      command.addCommand(sSave);
      command.executeCommand();
    } else if (ae.getActionCommand().toString() == "Open") {
      command.addCommand(sOpen);
      command.executeCommand();
      surface.repaint();
    }else if(ae.getActionCommand().toString() == "comboBoxChanged"){
      shapeType = (String) textList.getSelectedItem();
    } else {
      System.out.println(ae.getActionCommand().toString());
      shapeType = ae.getActionCommand().toString();
    }
    repaint();
  }

  public String getShapeType() {
    return shapeType;
  }

  public ArrayList<Shape> getShapes() {
    return shapes;
  }
}