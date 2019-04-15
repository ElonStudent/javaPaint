package app;
/**
 * 
 * @author Daan Eekhof
 */
public class ShapeSave implements ICommand{
    private ShapeActions ShapeActions;

    public ShapeSave(ShapeActions ShapeActions){
        this.ShapeActions = ShapeActions;
    }

    public void execute(){
        ShapeActions.save();
    }
}