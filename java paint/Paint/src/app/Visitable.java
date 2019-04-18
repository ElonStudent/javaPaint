package app;

import java.awt.Shape;

//<<Visitor Pattern>>
//Define an interface to repressent element
interface Visitable {
    public void accept(Visitor visitor);
}