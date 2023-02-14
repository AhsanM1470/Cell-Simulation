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

    // The probability that a Mycoplasma is alive
    private static final double MYCOPLASMA_ALIVE_PROB = 0.1;
    
    // The probability that a white blood cell is alive
    private static final double WHITE_BLOOD_CELL_ALIVE_PROB = 0.2;

    // List of cells in the field.
    private List<Cell> cells;
    
    private List<Cell> temporaryCells;







    private List<Cell> temporaryDeadCells;

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
      sim.simulate(1);
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
        temporaryDeadCells = new ArrayList<>();
        field = new Field(depth, width);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);

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
            delay(1000);   // comment out to run simulation faster
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
        }

        //removes dead cells from the list Cells
        Iterator<Cell> it = cells.iterator();
        while(it.hasNext()){
            Cell cell = it.next();
            if (!cell.isAlive()){
                it.remove();
            }
        }

        for (Cell cell : temporaryDeadCells){
            EmptyCell newEmpty = new EmptyCell(this, cell.getField(), cell.getLocation(), Color.GRAY);
            addTemporaryCell(newEmpty);
        }

        for(Cell temporaryCell : temporaryCells){
            cells.add(temporaryCell);
        }
        
        //**delete the other cells
        //either use iterator to remove the element from cells list
        //I think that's the best option for now
        //Wait...remove a cell if it is dead? In the iterator?
        
        view.showStatus(generation, field);
        temporaryCells.clear();
        temporaryDeadCells.clear();
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
          Mycoplasma myco = new Mycoplasma(this, field, location, Color.ORANGE);
          if (rand.nextDouble() <= MYCOPLASMA_ALIVE_PROB) {
            cells.add(myco);
          }
          else{
            EmptyCell empty = new EmptyCell(this, field, location, Color.GRAY);
            cells.add(empty);
            //myco.setDead();
            //cells.add(myco);
          }
        }
      }
    }
    
    public void addTemporaryCell(Cell cell){
        temporaryCells.add(cell);
    }

    public void addTemporaryDeadCell(Cell cell){
        temporaryDeadCells.add(cell);
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
}
