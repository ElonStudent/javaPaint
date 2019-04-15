package app;
/**
 * 
 * @author Daan Eekhof
 */
public class ShapeRedo implements ICommand{
    private ShapeActions ShapeActions;

    public ShapeRedo(ShapeActions ShapeActions){
        this.ShapeActions = ShapeActions;
    }

    public void execute(){
        ShapeActions.redo();
    }
}