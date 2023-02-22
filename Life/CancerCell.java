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
        changeProbabilityForSpawningNewCell(0.3);

        if(flagForDeterminingWhenProbabilityShouldChange){
            changeProbabilityForSpawningNewCell(0);
        }

    }

    /**
     * How it is decided if the cancer cell lives/spreads or not
     */
    public void act()
    {
        System.out.println(getCellCount(Mycoplasma.class));



        affectProbabilityForPossibleCancerNeighbours();
        int mycoCount = getMycoCount();
        int whiteCount = getWhiteCount();
        List<Cell> neighbours = getNeighbours();
        setNextState(true);

        //All the code for introducing new cancer cells is in the EmptyCell class.
        //This is to avoid confusion inside the List containing temporary cells.


        //This ensures that the same flagged Cancer cell does not spawn
        // multiple cells without the Myco parasitic condition
        setFlagForDeterminingWhenProbabilityShouldChange(false);


        //Causes disease in White Blood Cells
        causeDisease();

        //Elder WBCs kill Cancer cells
        if (matureWhiteNearby() == 2){
            setNextState(false);
            EmptyCell newEmpty = new EmptyCell(getSimulator(), getField(), Color.GRAY);
            getSimulator().addTemporaryCell(newEmpty);
        }
    }



    /**
     * This method is pretty beautiful icl
     * This method with the method affectMycoSpawnRate() form the parasitic relationship.
     */
    public void affectProbabilityForPossibleCancerNeighbours(){
        //Creates a list of neighbours and makes a separate one of new cancer cells
        List<Cell> neighbours = getNeighbours();

        //Modulo 9 means the generation before every tenth is checked to see if there
        // is a nearby Mycoplasma
        if(getMycoCount() > 0 && getAge()%10 == 9) {
            for (Cell neighbour : neighbours) {
                if (neighbour instanceof EmptyCell) {
                    ((EmptyCell) neighbour).getTheArrayListOfCancerCells().clear();
                    CancerCell cancer = new CancerCell(neighbour.getSimulator(), neighbour.getField(), Color.RED);
                    cancer.setFlagForDeterminingWhenProbabilityShouldChange(true);

                    //Casting here is fine because there is a check above for if neighbour is an EmptyCell
                    //Adds the cancer cells to the ArrayList, so they have specific probabilities
                    ((EmptyCell) neighbour).getTheArrayListOfCancerCells().add(cancer);

                }

            }
        }
    }

    /**
     *
     */
    protected void causeDisease(){
        for(Cell neighbour : getNeighbours()){
            if (neighbour instanceof WhiteBloodCell){
                ((WhiteBloodCell) neighbour).setMayBeDiseased(true);
            }
        }
    }

}
