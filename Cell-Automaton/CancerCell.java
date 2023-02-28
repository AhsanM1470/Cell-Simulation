import java.awt.Color;
import java.util.Random;

/**
 * Write a description of class CancerCell here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class CancerCell extends Cell
{
    
    /**
     * Constructor for objects of class CancerCell
     */
    public CancerCell(Field field, Location location, Color col) {
        super(field, location, col);
        setSpawnRate(0.3);
    }

    public void act(){
        int mycoCount = getCellCount(Mycoplasma.class);
        setNextState(true);
        alterCancerSpawnRate();

        //Disease.
        //Has a chance to cause disease in White Blood Cell neighbours.
        causeDisease();
        
        if(isAlive()){
            //Dies if nearby to elder White Blood Cell or to Mycoplasma cell.
            if(getNearbyWhiteMaturity() == 2 || mycoCount > 1){
                setNextState(false);
            }
        }

        else{
            //Revival clause.
            for(Cell neighbour : getNeighbours()){
                if(neighbour.replicableCancerNearby()){
                    Random rand = new Random();
                    double randResult = rand.nextDouble();
                    if(randResult <= getSpawnRate()){
                        setNextState(true);
                        return;
                    }
                }
            }
        }
    }

    /**
     * Parasitic Symbiosis.
     * If Cancer cell is nearby a Mycoplasma cell, the Cancer cell will
     *  have its spawn rate reduced.
     */
    public void alterCancerSpawnRate(){
        for(Cell neighbour : getNeighbours()){
            //Added condition that mycoplasma needs to be alive to affect cancer
            if(neighbour instanceof Mycoplasma && neighbour.isAlive()){
                setSpawnRate(0.2);
                return;
            }
        }
    }

    /**
     * Disease.
     * This checks for White Blood Cell neighbours and uses a method
     *  which gives a chance of causing disease.
     */
    public void causeDisease(){
        for(Cell neighbour : getNeighbours()){
            if(neighbour instanceof WhiteBloodCell){
                ((WhiteBloodCell)neighbour).setMayBeDiseased();
            }
        }
    }
}
