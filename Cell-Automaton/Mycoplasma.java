import java.awt.Color;
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
    public Mycoplasma(Field field, Location location, Color col) {
        super(field, location, col);
        setSpawnRate(0.95);
    }

    /**
     * This is how the Mycoplasma decides if it's alive or not
     */
    public void act() {
        int mycoCount = getCellCount(Mycoplasma.class);
        int cancerCount = getCellCount(CancerCell.class);
        setNextState(false);
        alterMycoSpawnRate();

        if (isAlive()){
            //Base task requirement for death.
            if (mycoCount == 2 || mycoCount == 3){
                setNextState(true);
                return;
            }

            //This gives a chance dying next generation if
            // any mature or elder White Blood Cells are nearby.
            if(getNearbyWhiteMaturity() > 0){
                Random rand = new Random();
                double randResult = rand.nextDouble();

                //This gives a 40% chance that Mycoplasma will die to White Blood Cell.
                //Death would be false in this case.
                setNextState(!(randResult <= 0.4));
            }
        }else{
            //Base task requirement for revival.
            if(mycoCount == 3 || cancerCount > 1){
                Random rand = new Random();
                double randResult = rand.nextDouble();
                if(randResult <= getSpawnRate()){
                    setNextState(true);   
                }
            }
        }
    }

    /**
     * Parasitic Symbiosis.
     * This checks for Cancer neighbours.
     * If there is at least one Cancer neighbour then the Mycoplasma
     *  cell spawn rate increases.
     */
    public void alterMycoSpawnRate(){
        for(Cell neighbour : getNeighbours()){
            if(neighbour instanceof CancerCell){
                setSpawnRate(1);
                return;
            }
        }
    }
}
