import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
 * Attacks mycoplasma and cancer cells
 */
public class WhiteBloodCell extends Cell
{
    private boolean isDiseased = false;

    /**
     * Create a new WhiteBloodVell.
     *
     * @param simulator The simulator used
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public WhiteBloodCell(Simulator simulator, Field field, Location location, Color col) {
        super(simulator, field, location, col);
        setSpawnRate(1);
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
        setSpawnRate(0.55);
    }

    /**
     * How it is decided if white blood cells live/spread or not
     */
    public void act()
    {
        int mycoCount = getCellCount(Mycoplasma.class);
        setNextState(true);

        //Actions based on whether this cell is diseased.
        if(isDiseased){
            setColor(Color.MAGENTA);
            
            for(Cell neighbour : getNeighbours()){
                if (neighbour instanceof WhiteBloodCell){
                    ((WhiteBloodCell) neighbour).setMayBeDiseased();
                }
            }
            //
            if (getAge() > 2){
                setNextState(false);
                EmptyCell newEmpty = new EmptyCell(getSimulator(), getField(), Color.GRAY);
                getSimulator().addTemporaryCell(newEmpty);
                return;
            }
        }

        //If they are surrounded by at least 3 mycolplasma, they are killed and replaced by a new mycoplasma
        if(mycoCount >= 3){
            Mycoplasma newMyco = new Mycoplasma(getSimulator(), getField(), Color.ORANGE);
            Random rand = new Random();
            double randomNumber = rand.nextDouble();

            if(randomNumber <= newMyco.getSpawnRate()) {
                setNextState(false);
                getSimulator().addTemporaryCell(newMyco);
            }

        }

        //If they are surrounded by at least 3 diseased cells, they are killed
        else if (getDiseasedCount() > 2){
            setNextState(false);
            EmptyCell newEmpty = new EmptyCell(getSimulator(), getField(), Color.GRAY);
            getSimulator().addTemporaryCell(newEmpty);
        }

    }
    //}

    /**
     * Makes a White Blood Cell have a 1% chance to become disease
     * Diseased White Blood Cells attack any cell, even other White Blood Cells.
     */
    public void setMayBeDiseased() {
        Random rand = new Random();
        double randomNumber = rand.nextDouble();
        if (randomNumber < 0.01) {
            isDiseased = true;
            setAgeZero();
        }

    }

    /**
     * @return Whether this White Blood Cell is diseased.
     */
    public boolean isDiseased(){
        return isDiseased;
    }

    /**
     * Return the number of mycoplasma neighbours around the cell
     * @return The number of mycoplasma neighbours
     */
    protected int getDiseasedCount(){
        int diseasedCount = 0;
        for(Cell neighbour : getNeighbours()){
            if(neighbour instanceof WhiteBloodCell && ((WhiteBloodCell) neighbour).isDiseased()){
                diseasedCount++;
            }
        }
        return diseasedCount;
    }

}
