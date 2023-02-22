import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
 * Attacks mycoplasma and cancer cells
 */
public class WhiteBloodCell extends Cell
{
    // instance variables - replace the example below with your own


    /**
     * Create a new WhiteBloodVell.
     *
     * @param simulator The simulator used
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public WhiteBloodCell(Simulator simulator, Field field, Location location, Color col) {
        super(simulator, field, location, col);
        //these were 1 in saihan's code
        setSpawnProbability(0.95);
    }

    /**
     * Create a new WhiteBloodCell without a location on the field
     *
     * @param simulator The simulator used
     * @param field The field currently occupied.
     */
    public WhiteBloodCell(Simulator simulator, Field field, Color col)
    {
        super(simulator, field, col);
        setSpawnProbability(0.95);
    }

    /**
     * How it is decided if white blood cells live/spread or not
     */
    public void act()
    {
        int mycoCount = getMycoCount();
        setNextState(true);
        //if(isAlive()){
            //If they are surrounded by at least 3 mycolplasma, they are killed and replaced by a new mycoplasma
            if(mycoCount >= 3){
                Mycoplasma newMyco = new Mycoplasma(getSimulator(), getField(), Color.ORANGE);
                Random rand = new Random();
                double randomNumber = rand.nextDouble();

                if(randomNumber <= newMyco.getSpawnProbability()){
                    setNextState(false);
                    getSimulator().addTemporaryCell(newMyco);
                }
            }
        //}
    }
}
