import java.awt.Color;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

/**
 * Used when there is no specialised cell at the location on the field.
 * Specialised cells: Mycoplasma, white blood cell, cancer cell
 */
public class EmptyCell extends Cell {
    private double randomNumber;

    //Each EmptyCell can hold one special cell of each subcell with
    // a different probability. This is used for the parasitic symbiosis
    // between cancer and mycoplasma but can also be used for other properties.
    // Index 0 is for cancer, index 1 is for mycoplasma
    private ArrayList<Object> flaggedSpecialCells;

    /**
     * Create a new EmptyCell.
     *
     * @param simulator The simulator used
     * @param field     The field currently occupied.
     * @param location  The location within the field.
     */
    public EmptyCell(Simulator simulator, Field field, Location location, Color col) {
        super(simulator, field, location, col);
        flaggedSpecialCells = new ArrayList<>();
        flaggedSpecialCells.add("cancerPlaceholder");
        flaggedSpecialCells.add("mycoplasmaPlaceholder");
    }

    /**
     * Create a new EmptyCell without a location on the field.
     *
     * @param simulator The simulator used
     * @param field     The field currently occupied.
     */
    public EmptyCell(Simulator simulator, Field field, Color col) {
        super(simulator, field, col);
        Random rand = new Random();
        double randomNumber = rand.nextDouble();
        flaggedSpecialCells = new ArrayList<>();
        flaggedSpecialCells.add("cancerPlaceholder");
        flaggedSpecialCells.add("mycoplasmaPlaceholder");
    }

    /**
     * How it is decided if the empty cell continues to exist or be replaced by a specialised cell (life form)
     */
    public void act() {
        int mycoCount = getCellCount(Mycoplasma.class);
        int whiteCount = getCellCount(WhiteBloodCell.class);
        setNextState(true);

        //If there is 3 mycoplasma around it, it will be replaced by a mycoplasma.
        if (mycoCount == 3) {

            if ((flaggedSpecialCells.get(1) instanceof Mycoplasma)) {
                //don't need to set up probability with random number because new
                // probability is 1 so guaranteed to spawn
                setNextState(false);
                getSimulator().addTemporaryCell((Cell) flaggedSpecialCells.get(1));
                flaggedSpecialCells.add(1, "mycoplasmaPlaceholder");
                return;
            }

            Mycoplasma newMyco = new Mycoplasma(getSimulator(), getField(), Color.ORANGE);
            Random rand = new Random();
            randomNumber = rand.nextDouble();
            if (randomNumber <= newMyco.getSpawnRate()) {
                setNextState(false);
                getSimulator().addTemporaryCell(newMyco);
            }
            return;
        }
        //Otherwise if there are at least 2 white blood cells around it, it will be replaced by a white blood cell.
        if (whiteCount > 2) {
            WhiteBloodCell newWhite = new WhiteBloodCell(getSimulator(), getField(), Color.PINK);
            Random rand = new Random();
            randomNumber = rand.nextDouble();
            if (randomNumber <= newWhite.getSpawnRate()) {
                setNextState(false);
                getSimulator().addTemporaryCell(newWhite);
            }
            return;
        }
        //Cancer cells spawn every ten generations
        if (getCellCount(CancerCell.class) > 0 && checkIfReplicableCancerCellIsNearby()) {
            Random rand = new Random();
            double randomNumber = rand.nextDouble();
            CancerCell newCancer = new CancerCell(getSimulator(), getField(), Color.RED);

            //This checks if there are any Cancer cells already made with special probabilities
            if (flaggedSpecialCells.get(0) instanceof CancerCell) {
                //This uses less method calls than without introducing this variable
                CancerCell flaggedCancerCell = (CancerCell) flaggedSpecialCells.get(0);

                if (randomNumber < flaggedCancerCell.getSpawnRate()) {
                    setNextState(false);
                    getSimulator().addTemporaryCell(flaggedCancerCell);
                    return;
                }
            }


            //This checks for normal Cancer cell probabilities
            else if (randomNumber <= newCancer.getSpawnRate()) {
                setNextState(false);
                getSimulator().addTemporaryCell(newCancer);
                return;
            }
        }
    }

        /**
         * This is only used by CancerCell and EmptyCell classes to refer to Cancer cells
         *  that have had their individual probabilities of spawning affected.
         * @return ArrayList of Cancer cells with specific probabilities
         */
        protected ArrayList<Object> getFlaggedSpecialCells() {
            return flaggedSpecialCells;
        }

}
