import java.awt.Color;
import java.util.Random;

/**
 * Attacks and spreads diseases to white blood cels
 * Symbiotic relationship with mycoplasma
 *
 * @author Muhammad Ahsan Mahfuz & Saihan Marshall
 * @version 2023.02.28 (1)
 */
public class CancerCell extends Cell
{
    
    /**
     * Constructor for objects of class CancerCell
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public CancerCell(Field field, Location location, Color col) {
        super(field, location, col);
        setSpawnRate(0.7);
    }

    /**
     * How it is decided if cancer cells live or not
     * And how they spread diseases
     */
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
            // Every 5 generations there is a chance for the cancer cell to
            // come back alive if there is a replicable cancer cell nearby
            if(replicableCancerNearby()){
                Random rand = new Random();
                double randResult = rand.nextDouble();
                if(randResult <= getSpawnRate()){
                    setNextState(true);
                    return;
                }
            }
        }
    }
    
    /**
     * Permanently lowers the cancer cell's spawnrate if it is near a mycoplasma
     */
    public void alterCancerSpawnRate(){
        for(Cell neighbour : getNeighbours()){
            if(neighbour instanceof Mycoplasma){
                setSpawnRate(0.4);
                return;
            }
        }
    }
    
    /**
     * Gives all nearby white blood cells the chance to be diseased
     */
    public void causeDisease(){
        for(Cell neighbour : getNeighbours()){
            if(neighbour instanceof WhiteBloodCell){
                ((WhiteBloodCell)neighbour).setMayBeDiseased();
            }
        }
    }
}
