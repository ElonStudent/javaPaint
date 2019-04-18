package app;
/**
 * 
 * @author Elon Gielink
 */
//Create text for the baseshape
public class BaseText {
    int x, y;
    String text;
    String position;

    //Constructor
    public BaseText(String text, String position) {
        this.text = text;
        this.position = position;
    }
    
    //Set position of the text
    public void setPos(int shapeX, int shapeY, int shapeWidth, int shapeHeight) {
        switch (position) {
        case "Top":
            x = (shapeX + (shapeWidth / 3));
            y = (int) shapeY;
            break;
        case "Bottom":
            x = (int) (shapeX + (shapeWidth / 3));
            y = (int) (shapeY + (shapeHeight + 15));
            break;
        case "Left":
            x = (int) (shapeX - (shapeWidth / 4));
            y = (int) (shapeY + (shapeHeight / 2));
            break;
        case "Right":
            x = (int) (shapeX + shapeWidth);
            y = (int) (shapeY + (shapeHeight / 2));
            break;
        default:
            break;
        }
    }
}