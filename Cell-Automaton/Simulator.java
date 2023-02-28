import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

/**
 * A Life (Game of Life) simulator, first described by British mathematician
 * John Horton Conway in 1970.
 *
 * @author Muhammad Ahsan Mahfuz, Saihan Marshall, David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2023.02.28 (1)
 */

public class Simulator {
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 100;

    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;

    // List of cells in the field.
    private List<Cell> cells;

    // The current state of the field.
    private Field field;

    // The current generation of the simulation.
    private int generation;

    // A graphical view of the simulation.
    private SimulatorView view;
    
    // The time between generation simulations in millisecs
    private int timer = 500;

    /**
     * Execute simulation
     */
    public static void main(String[] args) {
      Simulator sim = new Simulator();
      sim.simulate(100);
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
        field = new Field(depth, width);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width, this);

        // Setup a valid starting point.
        reset();
    }

    /**
     * Run the simulation from its current state for a long period of time
     * (2000 generations)
     */
    public void runLongSimulation() {
        simulate(2000);
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
            delay(timer);   // comment out to run simulation faster
        }
    }

    /**
     * Run the simulation from its current state for a single generation.
     * Iterate over the whole field updating the state of each life form.
     */
    public void simOneGeneration() {
        generation++;
        for(Cell cell : cells){
            cell.act();
        }

        for (Cell cell : cells) {
            cell.updateState();
            cell.incrementAge();
            if(cell instanceof WhiteBloodCell && cell.getAge() > 6){
                cell.setColor(Color.WHITE);
            }
        }
        view.showStatus(generation, field);
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        generation = 0;
        cells.clear();
        populate();

        // Show the starting state in the view.
        view.showStatus(generation, field);
    }

    /**
     * Randomly populate the field live/dead life forms
     */
    private void populate() {
        Random rand = Randomizer.getRandom();
        field.clear();
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Location location = new Location(row, col);
                double randResult = rand.nextDouble();

                if (randResult <= 0.2) {
                    Mycoplasma myco = new Mycoplasma(field, location, Color.ORANGE);
                    cells.add(myco);
                }else if (randResult <= 0.65){
                    Mycoplasma myco = new Mycoplasma(field, location, Color.ORANGE);
                    myco.setDead();
                    cells.add(myco);
                }else if(randResult <= 0.75){
                    WhiteBloodCell white = new WhiteBloodCell(field, location, Color.PINK);
                    cells.add(white);
                }else if(randResult <= 0.85){
                    WhiteBloodCell white = new WhiteBloodCell(field, location, Color.PINK);
                    white.setDead();
                    cells.add(white);
                }else if(randResult <= 0.95){
                    CancerCell cancer = new CancerCell(field, location, Color.RED);
                    cells.add(cancer);
                }else{
                    CancerCell cancer = new CancerCell(field, location, Color.RED);
                    cancer.setDead();
                    cells.add(cancer);
                }
            }
        }
    }

    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    public void delay(int millisec) {
        timer = millisec;
        try {
            Thread.sleep(timer);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
    
    /**
     * Return the current generation of the simulation
     * @return The current generation
     */
    public int getGeneration(){
        return generation;
    }
}
