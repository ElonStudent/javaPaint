package app;
/**
 * 
 * @author Elon Gielink, Daan Eekhof
 */
//Command that will revert the previous action
public class ShapeUndo implements ICommand{
    private ShapeActions ShapeActions;
    //constructor
    public ShapeUndo(ShapeActions ShapeActions){
        this.ShapeActions = ShapeActions;
    }
    //executes the command
    public void execute(){
        ShapeActions.undo();
    }
}