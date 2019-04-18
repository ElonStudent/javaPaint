package app;
/**
 * 
 * @author Daan Eekhof
 */
//Write to file
public class ShapeSave implements ICommand{
    private ShapeActions ShapeActions;

    //constructor
    public ShapeSave(ShapeActions ShapeActions){
        this.ShapeActions = ShapeActions;
    }
    
    //execute the command
    public void execute(){
        ShapeActions.save();
    }
}