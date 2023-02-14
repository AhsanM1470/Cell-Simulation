import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
 * Simplest form of life.
 * Fun Fact: Mycoplasma are one of the simplest forms of life.  A type of
 * bacteria, they only have 500-1000 genes! For comparison, fruit flies have
 * about 14,000 genes.
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2022.01.06 (1)
 */

public class Mycoplasma extends Cell {

    /**
     * Create a new Mycoplasma.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Mycoplasma(Simulator simulator, Field field, Location location, Color col) {
        super(simulator, field, location, col);
    }

    /**
     * This is how the Mycoplasma decides if it's alive or not
     */
    public void act() {
        //List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        int mycoCount = getMycoCount();
        setNextState(false);
        if(isAlive()){
            if(mycoCount == 2 || mycoCount == 3) {
               setNextState(true);
            }
            else {
                //stays dead for next generation
//                getSimulator().addTemporaryDeadCell(this);
                  getSimulator().addTemporaryDeadCell(this);
//                EmptyCell newEmpty = new EmptyCell(getSimulator(), getField(), getLocation(), Color.GRAY);
//                getSimulator().addTemporaryCell(newEmpty);
                //setNewCell(newEmpty);
            }

        }
        else{
            getSimulator().addTemporaryDeadCell(this);
        }

    }
}
