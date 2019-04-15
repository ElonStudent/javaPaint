package app;

import java.awt.Shape;
/**
 * 
 * @author Elon Gielink
 */
interface Visitor {

	void visit(Circle circle);
	void visit(Rectangle rectangle);
    
}