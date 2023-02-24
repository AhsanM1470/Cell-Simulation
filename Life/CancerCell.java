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
        //this was 0 in Saihan's code
        setSpawnProbability(0.3);
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
        setSpawnProbability(0.3);
    }

    /**
     * How it is decided if the cancer cell lives/spreads or not
     */
    public void act()
    {
        int mycoCount = getMycoCount();
        int whiteCount = getWhiteCount();
        setNextState(true);

        if(mycoCount > 0){
            setSpawnProbability(0.25);
        }

        //if(isAlive()){

        //}
    }

}
