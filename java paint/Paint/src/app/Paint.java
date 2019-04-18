package app;
public class Paint {

  public static void main(String[] args) {
    //Sets a vairable set to the frame instance <<Singleton Pattern>>
    ShapesFrame object = ShapesFrame.getInstance();
    object.createDrawObj();
  }
}