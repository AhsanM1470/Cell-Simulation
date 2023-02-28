import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
 * Attacks white blood cells and cancer cells
 * Symbiotic relatioship with cancer cells.
 *
 * @author Muhammad Ahsan Mahfuz, Saihan Marshall, David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2023.02.28 (1)
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
     * How it is decided if mycoplasma live or not
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
            // If a mature white blood cell is nearby, there is a 40% chance it will die
            if(getNearbyWhiteMaturity() > 0){
                Random rand = new Random();
                double randResult = rand.nextDouble();
                // Next state is the opposite of the boolean operation
                setNextState(!(randResult <= 0.5));
            }
        }else{
            // Reviving mycoplasma
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
     * If there is at least one cancer cell nearby, the mycoplasma's
     * spawn rate increases
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
