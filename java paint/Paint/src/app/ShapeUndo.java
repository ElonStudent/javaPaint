package app;
/**
 * 
 * @author Daan Eekhof
 */
public class ShapeUndo implements ICommand{
    private ShapeActions ShapeActions;

    public ShapeUndo(ShapeActions ShapeActions){
        this.ShapeActions = ShapeActions;
    }

    public void execute(){
        ShapeActions.undo();
    }
}