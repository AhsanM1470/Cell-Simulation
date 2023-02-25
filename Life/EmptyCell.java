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

    //This is an arraylist of all cells with special probabilities
    //Each index is for a particular cell type (0 for cancer, 1 for mycoplasma)
    //Initialised values are integers 1 as placeholders so if condition to check
    // if arraylist is empty is not required.
    private ArrayList<Object> arrayListOfSpecialCells;

    /**
     * Create a new EmptyCell.
     *
     * @param simulator The simulator used
     * @param field     The field currently occupied.
     * @param location  The location within the field.
     */
    public EmptyCell(Simulator simulator, Field field, Location location, Color col) {
        super(simulator, field, location, col);

        arrayListOfSpecialCells = new ArrayList<>();
        arrayListOfSpecialCells.add(1);
        arrayListOfSpecialCells.add(1);
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

        arrayListOfSpecialCells = new ArrayList<>();
        arrayListOfSpecialCells.add(1);
        arrayListOfSpecialCells.add(1);

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
//            if (!getTheArrayListOfMycoplasmaCells().isEmpty()){
            if (getTheArrayListOfSpecialCells().get(1) instanceof Mycoplasma){
                setNextState(false);
                getSimulator().addTemporaryCell((Cell) getTheArrayListOfSpecialCells().get(1));

                //this replaces this special cell with an integer so the same one isn't twice added
                getTheArrayListOfSpecialCells().add(1, 1);
                return;
            }

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
//            if(!getTheArrayListOfCancerCells().isEmpty()){
            if(getTheArrayListOfSpecialCells().get(0) instanceof CancerCell){
                Cell specialCancerCell = (Cell) arrayListOfSpecialCells.get(0);

                //this uses the new special probability value.
                //not necessary for special Mycoplasma because their special value is 1.
                if(randomNumber <= specialCancerCell.getProbabilityForSpawningNewCell()) {
                    setNextState(false);
                    getSimulator().addTemporaryCell(specialCancerCell);
                }
                arrayListOfSpecialCells.add(0,1);
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

    /**
     * This is only used by Mycoplasma and EmptyCell classes to refer to Mycoplasma cells
     *  that have had their individual probabilities of spawning affected.
     * @return ArrayList of Cancer cells with specific probabilities
     */
    protected ArrayList<Object> getTheArrayListOfSpecialCells(){
        return arrayListOfSpecialCells;
    }

}
