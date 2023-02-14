import java.awt.Color;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

/**
 * Write a description of class EmptyCell here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class EmptyCell extends Cell
{
    // instance variables - replace the example below with your own
    
    /**
     * Constructor for objects of class EmptyCell
     */
    public EmptyCell(Simulator simulator, Field field, Location location, Color col)
    {
        super(simulator, field, location, col);
    }
    
    public EmptyCell(Simulator simulator, Field field, Color col)
    {
        super(simulator, field, col);
    }
    
    public void act()
    {
        int mycoCount = getMycoCount();
        setNextState(true);
        if(isAlive() && mycoCount == 3){
            //setNextState(false);
            setNextState(false);
            Mycoplasma newMyco = new Mycoplasma(getSimulator(), getField(), Color.ORANGE);
            getSimulator().addTemporaryCell(newMyco);
            //setNewCell(newMyco);
            //getField().place(newMyco, getLocation());
        }
    }

}
