package app;
/**
 * 
 * @author Daan Eekhof
 */
//<<command pattern>>
//Redo shape command
public class ShapeRedo implements ICommand{
    private ShapeActions ShapeActions;

    //Constructor
    public ShapeRedo(ShapeActions ShapeActions){
        this.ShapeActions = ShapeActions;
    }
    
    //Execute the command
    public void execute(){
        ShapeActions.redo();
    }
}