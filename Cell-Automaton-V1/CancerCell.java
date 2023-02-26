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

        //This value is changed in the method that affects probability below
        setSpawnRate(0.3);
    }

    /**
     * How it is decided if the cancer cell lives/spreads or not
     */
    public void act()
    {
        affectProbabilityForPossibleCancerNeighbours();
        List<Cell> neighbours = getNeighbours();
        setNextState(true);

        //Causes disease in White Blood Cells
        causeDisease();

        //Elder White Blood Cells kill Cancer cells
        if (getNearbyWhiteMaturity() == 2){
            setNextState(false);
            EmptyCell newEmpty = new EmptyCell(getSimulator(), getField(), Color.GRAY);
            getSimulator().addTemporaryCell(newEmpty);
        }
    }

    /**
     * This method with the method affectMycoSpawnRate() form the parasitic relationship.
     */
    public void affectProbabilityForPossibleCancerNeighbours(){
        //Creates a list of neighbours and makes a separate one of new cancer cells
        List<Cell> neighbours = getNeighbours();

        //Modulo 9 means the generation before every tenth is checked to see if there is a nearby Mycoplasma
        if(getCellCount(Mycoplasma.class) > 0 && getAge()%10 == 9) {
            for (Cell neighbour : neighbours) {

                if (neighbour instanceof EmptyCell) {
                    CancerCell cancer = new CancerCell(neighbour.getSimulator(), neighbour.getField(), Color.RED);
                    cancer.setSpawnRate(0);

                    //Casting here is fine because there is a check above for if neighbour is an EmptyCell
                    //Adds the cancer cells to the ArrayList, so they have specific probabilities
                    ((EmptyCell) neighbour).getFlaggedSpecialCells().add(0, cancer);

                }

            }
        }
    }

    /**
     * Gives any white blood cells adjacent to the cancer cell the chance of becomming diseased
     */
    protected void causeDisease(){
        for(Cell neighbour : getNeighbours()){
            if (neighbour instanceof WhiteBloodCell){
                ((WhiteBloodCell) neighbour).setMayBeDiseased();
            }
        }
    }

}
