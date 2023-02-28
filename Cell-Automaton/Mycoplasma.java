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
            if (mycoCount == 2 || mycoCount == 3){
                setNextState(true);
                return;
            }
            
            if(getNearbyWhiteMaturity() > 0){
                Random rand = new Random();
                double randResult = rand.nextDouble();
                if(randResult <= 0.4){
                    setNextState(false);
                }else{
                    setNextState(true);
                }
            }
        }else{
            if(mycoCount == 3 || cancerCount > 1){
                Random rand = new Random();
                double randResult = rand.nextDouble();
                if(randResult <= getSpawnRate()){
                    setNextState(true);   
                }
            }
        }
    }
    
    public void alterMycoSpawnRate(){
        for(Cell neighbour : getNeighbours()){
            if(neighbour instanceof CancerCell){
                setSpawnRate(1);
                return;
            }
        }
    }
}
