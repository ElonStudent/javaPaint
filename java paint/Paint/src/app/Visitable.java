package app;

import java.awt.Shape;

interface Visitable {
    public void accept(Visitor visitor);
}