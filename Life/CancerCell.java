import java.awt.Color;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

/**
 * Cell type that starts of few in numbers but can spread very quickly.
 * Can only be killed by adult white blood cells.
 */
public class CancerCell extends Cell
{
    /**
     * Create a new CancerCell.
     *
     * @param simulator The simulator used
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public CancerCell(Simulator simulator, Field field, Location location, Color col)
    {
        super(simulator, field, location, col);
        changeProbabilityForSpawningNewCell(0);
    }
    
    /**
     * Create a new CancerCell without a location on the field.
     *
     * @param simulator The simulator used
     * @param field The field currently occupied.
     */
    public CancerCell(Simulator simulator, Field field, Color col)
    {
        super(simulator, field, col);
        changeProbabilityForSpawningNewCell(0.3);
    }
    
    /**
     * How it is decided if the cancer cell lives/spreads or not
     */
    public void act()
    {
        affectCancerCount();
    int mycoCount = getMycoCount();
    int whiteCount = getWhiteCount();
    List<Cell> neighbours = getNeighbours();
    setNextState(true);

    //All the code for introducing new cancer cells is in the EmptyCell class.
    //This is to avoid confusion inside the List containing temporary cells.




    }

    /**
     * This method with the method affectMycoSpawnRate() form the parasitic relationship.
     */
    public void affectCancerCount(){
        if(getMycoCount() > 0){
            changeProbabilityForSpawningNewCell(1);
        }
    }

    /**
     *
     */
    public void ahsefoihaliesufh(){
        if(matureWhiteNearby() == 2){

        }
    }



}
