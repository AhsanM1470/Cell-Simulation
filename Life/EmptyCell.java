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
    //    private ArrayList<CancerCell> arrayListOfCancerCells;
    private ArrayList<Cell> arrayListOfCancerCells;

    /**
     * Create a new EmptyCell.
     *
     * @param simulator The simulator used
     * @param field     The field currently occupied.
     * @param location  The location within the field.
     */
    public EmptyCell(Simulator simulator, Field field, Location location, Color col) {
        super(simulator, field, location, col);

        arrayListOfCancerCells = new ArrayList<>();
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

        arrayListOfCancerCells = new ArrayList<>();
    }

    /**
     * How it is decided if the empty cell continues to exist or be replaced by a specialised cell
     */
    public void act() {
        int mycoCount = getCellCount(Mycoplasma.class);
//        System.out.println(getCellCount(WhiteBloodCell.class));
        int whiteCount = getCellCount(WhiteBloodCell.class);
//        int whiteCount = getWhiteCount();
        setNextState(true);


        //If there is 3 mycoplasma around it, it will be replaced by a mycoplasma.
        if (mycoCount == 3) {
            Mycoplasma newMyco = new Mycoplasma(getSimulator(), getField(), Color.ORANGE);
            Random rand = new Random();
            randomNumber = rand.nextDouble();

            if (randomNumber<= newMyco.getProbabilityForSpawningNewCell()){
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
//                if(true){
            if(randomNumber<= newWhite.getProbabilityForSpawningNewCell()){
                setNextState(false);
                getSimulator().addTemporaryCell(newWhite);
            }

            return;
        }

//            if (getCancerCount() > 0) {
//                setNextState(false);
//                CancerCell newCancer = new CancerCell(getSimulator(), getField(), Color.RED);
//                getSimulator().addTemporaryCell(newCancer);
////                return;
//            }

        //Cancer cells spawn every ten generations
        if (getCellCount(CancerCell.class) > 0 && checkIfReplicableCancerCellIsNearby()) {
            Random rand = new Random();
            double randomNumber = rand.nextDouble();
            CancerCell newCancer = new CancerCell(getSimulator(), getField(), Color.RED);

            //This checks if there are any Cancer cells already made with special probabilities
            if(!getTheArrayListOfCancerCells().isEmpty()){
                setNextState(false);
                getSimulator().addTemporaryCell(getTheArrayListOfCancerCells().get(0));
                return;
            }

            //This checks for normal Cancer cell probabilities
            else if(randomNumber <= newCancer.getProbabilityForSpawningNewCell()) {
                setNextState(false);
                getSimulator().addTemporaryCell(newCancer);
                return;
            }
        }

    }

//    public ArrayList<CancerCell> getTheArrayListOfCancerCells(){
//        return arrayListOfCancerCells;
//    }

    /**
     * This is only used by CancerCell and EmptyCell classes to refer to Cancer cells
     *  that have had their individual probabilities of spawning affected.
     * @return ArrayList of Cancer cells with specific probabilities
     */
    protected ArrayList<Cell> getTheArrayListOfCancerCells(){
        return arrayListOfCancerCells;
    }

}
