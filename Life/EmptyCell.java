import java.awt.Color;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

/**
 * Used when there is no specialised cell at the location on the field.
 * Specialised cells: Mycoplasma, white blood cell, cancer cell
 */
public class EmptyCell extends Cell
{
    /**
     * Create a new EmptyCell.
     *
     * @param simulator The simulator used
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    
    //--
    private double randomNumber;
    
    public EmptyCell(Simulator simulator, Field field, Location location, Color col)
    {
        super(simulator, field, location, col);
    }
    
    /**
     * Create a new EmptyCell without a location on the field.
     *
     * @param simulator The simulator used
     * @param field The field currently occupied.
     */
    public EmptyCell(Simulator simulator, Field field, Color col)
    {
        super(simulator, field, col);
    }
    
    /**
     * How it is decided if the empty cell continues to exist or be replaced by a specialised cell
     */
    public void act()
    {
        setNextState(true);
        
            //If there is 3 mycoplasma around it, it will be replaced by a mycoplasma.
            if(getMycoCount() == 3){
                Mycoplasma newMyco = new Mycoplasma(getSimulator(), getField(), Color.ORANGE);
                Random rand = new Random();
                randomNumber = rand.nextDouble();
                
                if(randomNumber <= newMyco.getSpawnProbability()){
                    setNextState(false);
                    getSimulator().addTemporaryCell(newMyco);
                }
                return;
            }
            
            //Otherwise if there are at least 2 white blood cells around it, it will be replaced by a white blood cell.
            if(getWhiteCount() > 2){
                Random rand = new Random();
                randomNumber = rand.nextDouble();
                WhiteBloodCell newWhite = new WhiteBloodCell(getSimulator(), getField(), Color.PINK);
                
                if(randomNumber <= newWhite.getSpawnProbability()){
                    setNextState(false);
                    getSimulator().addTemporaryCell(newWhite);
                }
                return;
            }
            
            if(replicableCancerNearby()){
                Random rand = new Random();
                randomNumber = rand.nextDouble();
                CancerCell newCancer = new CancerCell(getSimulator(), getField(), Color.RED);
                
                if(randomNumber <= newCancer.getSpawnProbability()){
                    setNextState(false);
                    getSimulator().addTemporaryCell(newCancer);
                }
                return;
            }
    }

}
