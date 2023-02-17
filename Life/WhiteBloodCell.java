import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
 * Attacks mycoplasma and cancer cells
 */
public class WhiteBloodCell extends Cell
{
    private double randomNumber;

    /**
     * Create a new WhiteBloodVell.
     *
     * @param simulator The simulator used
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public WhiteBloodCell(Simulator simulator, Field field, Location location, Color col) {
        super(simulator, field, location, col);

        changeProbabilityForSpawningNewCell(0.8);
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
        changeProbabilityForSpawningNewCell(0.1);
        Random rand = new Random();
        randomNumber = rand.nextDouble();
    }

    /**
     * How it is decided if white blood cells live/spread or not
     */
    public void act()
    {
        int mycoCount = getMycoCount();
        setNextState(true);

        //If they are surrounded by at least 3 mycolplasma, they are killed and replaced by a new mycoplasma
        if(mycoCount >= 3){
            Mycoplasma newMyco = new Mycoplasma(getSimulator(), getField(), Color.ORANGE);
            if(randomNumber <= newMyco.getProbabilityForSpawningNewCell()) {
                setNextState(false);
                getSimulator().addTemporaryCell(newMyco);
            }

        }

    }
    //}
}
