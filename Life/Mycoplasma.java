import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
 * A type of bacteria
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 
 */

public class Mycoplasma extends Cell {

    private double randomNumber;
    /**
     * Create a new Mycoplasma.
     *
     * @param simulator The simulator used
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Mycoplasma(Simulator simulator, Field field, Location location, Color col) {
        super(simulator, field, location, col);
    }
    
    /**
     * Create a new Mycoplasma without a location on the field.
     *
     * @param simulator The simulator used
     * @param field The field currently occupied.
     */
    public Mycoplasma(Simulator simulator, Field field, Color col)
    {
        super(simulator, field, col);
        changeProbabilityForSpawningNewCell(0.5);
        Random rand = new Random();
        randomNumber = rand.nextDouble();
    }

    /**
     * This is how the Mycoplasma decides if it's alive or not
     */
    public void act() {
        int mycoCount = getMycoCount();
        //int whiteCount = getWhiteCount();
        setNextState(false);

        affectMycoSpawnRate();

        //if(isAlive()){
            //If there are exactly 2 or 3 mycoplasma they will continue to live
            //Otherwise they are killed and replaced by an empty cell
            if(mycoCount == 2 || mycoCount == 3) {
               setNextState(true);
               return;
            }else if(mycoCount < 2 || mycoCount > 3){
                EmptyCell newEmpty = new EmptyCell(getSimulator(), getField(), Color.GRAY);
                getSimulator().addTemporaryCell(newEmpty);
                //setNewCell(newEmpty);
                return;
            }
            //If there is at least 1 mature white blood cell around them, they are killed.
            //80% of the time they are replaced by an empty cell. 20% of the time with another white blood cell.
            if(matureWhiteNearby() > 0){
//                Random rand = new Random();
//                double randResult = rand.nextDouble();
                if(randomNumber <= 0.8){
                    EmptyCell newEmpty = new EmptyCell(getSimulator(), getField(), Color.GRAY);
                    getSimulator().addTemporaryCell(newEmpty);
                }else {
                    WhiteBloodCell newWhite = new WhiteBloodCell(getSimulator(), getField(), Color.PINK);
                    if (randomNumber <= -1) {
                        getSimulator().addTemporaryCell(newWhite);
                    }
                }
            }
    }

    /**
     * This method with the method affectCancerSpawnRate form the parasitic relationship.
     */
    public void affectMycoSpawnRate(){
        if (cancerCount()>0){
            changeProbabilityForSpawningNewCell(1.0);
        }
    }

}
