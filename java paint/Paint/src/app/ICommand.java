package app;
/**
 * 
 * @author Daan Eekhof
 */

 // Interface serves as the "Order" for the Command Pattern
@FunctionalInterface
public interface ICommand{
    void execute();
}