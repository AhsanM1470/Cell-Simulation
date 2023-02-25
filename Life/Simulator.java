import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

/**
 * A Life (Game of Life) simulator, first described by British mathematician
 * John Horton Conway in 1970.
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2022.01.06 (1)
 */

public class Simulator {
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 100;

    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;

    // List of cells in the field.
    private List<Cell> cells;

    //Newly generated cells that will be added to the cells ArrayList
    private List<Cell> temporaryCells;

    //The locations on the field for the temporary cells
    private List<Location> temporaryLocations;

    // The current state of the field.
    private Field field;

    // The current generation of the simulation.
    private int generation;

    // A graphical view of the simulation.
    private SimulatorView view;

    /**
     * Execute simulation
     */
    public static void main(String[] args) {
        Simulator sim = new Simulator();
        sim.simulate(1000);
//        sim.simulate(30);
    }

    /**
     * Construct a simulation field with default size.
     */
    public Simulator() {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width) {
        if (width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }

        cells = new ArrayList<>();
        temporaryCells = new ArrayList<>();
        field = new Field(depth, width);
        temporaryLocations = new ArrayList<>();

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width, this
        );

        // Setup a valid starting point.
        reset();
    }

    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 generations).
     */
    public void runLongSimulation() {
        simulate(4000);
    }

    /**
     * Run the simulation from its current state for the given number of
     * generations.  Stop before the given number of generations if the
     * simulation ceases to be viable.
     * @param numGenerations The number of generations to run for.
     */
    public void simulate(int numGenerations) {
        for (int gen = 1; gen <= numGenerations && view.isViable(field); gen++) {
            simOneGeneration();
//            delay(100);   // comment out to run simulation faster
        }
    }

    /**
     * Run the simulation from its current state for a single generation.
     * Iterate over the whole field updating the state of each life form.
     */
    public void simOneGeneration() {
        generation++;
        for (Iterator<Cell> it = cells.iterator(); it.hasNext(); ) {
            Cell cell = it.next();
            cell.act();
        }

        for (Cell cell : cells) {
            cell.updateState();
            cell.incrementAge();
            if(cell instanceof WhiteBloodCell && cell.getAge() > 6 && !((WhiteBloodCell) cell).isDiseased()){
                cell.setColor(Color.WHITE);
            }
        }

        //Remove all dead cells and replace them with the newly generated cells in temporaryCells
        Iterator<Cell> it = cells.iterator();
        while(it.hasNext()){
            Cell t = it.next();
            if (!t.isAlive()){
                temporaryLocations.add(t.getLocation());
                it.remove();
            }
        }

        //Place the temporary cells on the field
        for(Cell temporaryCell : temporaryCells){
            temporaryCell.setLocation(temporaryLocations.get(0));
            temporaryLocations.remove(0);
            cells.add(temporaryCell);
        }

        view.showStatus(generation, field);
        temporaryCells.clear();
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        generation = 0;
        cells.clear();
        field.clear();
        populate();

        // Show the starting state in the view.
        view.showStatus(generation, field);
    }

    /**
     * Randomly populate the field live/dead life forms
     */
    private void populate() {
        Random rand = Randomizer.getRandom();

        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Location location = new Location(row, col);
                double randResult = rand.nextDouble();
                if (randResult <= 0.2) {
                    Mycoplasma myco = new Mycoplasma(this, field, location, Color.ORANGE);
                    cells.add(myco);
                }

                else if(randResult > 0.2 && randResult <= 0.22){
                    WhiteBloodCell white = new WhiteBloodCell(this, field, location, Color.PINK);
                    cells.add(white);
                }

                else if (randResult > 0.22 && randResult <= 0.221){
                    CancerCell cancer = new CancerCell(this, field, location, Color.RED);
                    cells.add(cancer);
                }

                else{
                    EmptyCell empty = new EmptyCell(this, field, location, Color.GRAY);
                    cells.add(empty);
                }

            }
        }
    }

    /**
     * Add a cell to the temporaryCells ArrayList
     */
    public void addTemporaryCell(Cell cell){
        temporaryCells.add(cell);
    }

    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec) {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }

    /**
     * Returns the current generation as an integer.
     * @return generation
     */
    public int getGeneration(){
        return generation;
    }
}
