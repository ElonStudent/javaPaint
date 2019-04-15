package app;
/**
 * 
 * @author Daan Eekhof
 */
public class ShapeLoad implements ICommand{
    private ShapeActions ShapeActions;

    public ShapeLoad(ShapeActions ShapeActions){
        this.ShapeActions = ShapeActions;
    }

    public void execute(){
        ShapeActions.load();
    }
}