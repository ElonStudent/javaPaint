package app;
/**
 * 
 * @author Daan Eekhof
 */
//<<command pattern>>
//load shape command
public class ShapeLoad implements ICommand{
    private ShapeActions ShapeActions;

    //constructor
    public ShapeLoad(ShapeActions ShapeActions){
        this.ShapeActions = ShapeActions;
    }
    
    //Execute the command
    public void execute(){
        ShapeActions.load();
    }
}