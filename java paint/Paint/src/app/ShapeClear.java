package app;
/**
 * 
 * @author Daan Eekhof
 */
public class ShapeClear implements ICommand{
    private ShapeActions ShapeActions;

    public ShapeClear(ShapeActions ShapeActions){
        this.ShapeActions = ShapeActions;
    }

    public void execute(){
        ShapeActions.clear();
    }
}