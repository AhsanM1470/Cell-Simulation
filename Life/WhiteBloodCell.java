import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
 * Write a description of class WhiteBloodCell here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class WhiteBloodCell extends Cell
{
    // instance variables - replace the example below with your own

    /**
     * Constructor for objects of class WhiteBloodCell
     */
    public WhiteBloodCell(Field field, Location location, Color col) {
        super(field, location, col);
    }

    /**
     */
    public void act()
    {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        incrementAge();
        setNextState(false);
        
        if(isAlive()){
            int bacteriaCount = 0;
            for(Cell cell : neighbours){
                if(cell instanceof Mycoplasma){
                    bacteriaCount++;
                }
            }
            if(bacteriaCount < 3){
                setNextState(true);
            }
        }
    }
}
