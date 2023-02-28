import java.awt.Color;
import java.util.List;
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
        
        causeDisease();
        
        if(isAlive()){
            if(getNearbyWhiteMaturity() == 2 || mycoCount > 1){
                setNextState(false);
            }
        }else{
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
    
    public void alterCancerSpawnRate(){
        for(Cell neighbour : getNeighbours()){
            if(neighbour instanceof Mycoplasma){
                setSpawnRate(0.2);
                return;
            }
        }
    }
    
    public void causeDisease(){
        for(Cell neighbour : getNeighbours()){
            if(neighbour instanceof WhiteBloodCell){
                ((WhiteBloodCell)neighbour).setMayBeDiseased();
            }
        }
    }
}
