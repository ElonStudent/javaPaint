package app;
/**
 * 
 * @author Daan Eekhof
 */
//<<command pattern>>
//Clear shape command
public class ShapeClear implements ICommand{
    private ShapeActions ShapeActions;

    //Constructor
    public ShapeClear(ShapeActions ShapeActions){
        this.ShapeActions = ShapeActions;
    }
    
    //Execute the clear command
    public void execute(){
        ShapeActions.clear();
    }
}