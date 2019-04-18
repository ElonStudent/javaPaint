package app;

import java.awt.Shape;
/**
 * 
 * @author Elon Gielink
 */
//<<Visitor Pattern>>
//Create visitor interface
interface Visitor {

	void visit(Circle circle);
	void visit(Rectangle rectangle);
    
}