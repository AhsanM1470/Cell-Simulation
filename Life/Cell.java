import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

/**
 * A class representing the shared characteristics of all forms of life
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2022.01.06 (1)
 */

public abstract class Cell {
    // Whether the cell is alive or not.
    private boolean alive;

    // Whether the cell will be alive in the next generation.
    private boolean nextAlive;
    
    // How many generations the cell has lived for
    private int age = 1;

    // The probability that a new cell spawns on top of its normal conditions.
    private double probabilityForSpawningNewCell;

    // The cell's field.
    private Field field;

    // The cell's position in the field.
    private Location location;

    // The cell's color
    private Color color = Color.white;
    
    //**private List<Cell> neighbours;
    
    //The cell's simulator
    private Simulator simulator;

    /**
     * Create a new cell at location in field.
     *
     * @param simulator The simulator used
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Cell(Simulator simulator, Field field, Location location, Color col) {
        alive = true;
        nextAlive = false;
        this.simulator = simulator;
        this.field = field;
        setLocation(location);
        setColor(col);
        
        // neighbours = new ArrayList<>();
        // neighbours = getField().getLivingNeighbours(getLocation());
    }
    
    /**
     * Cell constructor but no location is given, so the cell is
     * not placed on the field.
     * 
     * @param simulator The simulator used
     * @param field The field currently occupied.
     */
    public Cell(Simulator simulator, Field field, Color col)
    {
        alive = true;
        nextAlive = false;
        this.simulator = simulator;
        this.field = field;
        setColor(col);
    }

    /**
     * Make this cell act - that is: the cell decides it's status in the
     * next generation.
     */
    abstract public void act();

    /**
     * Check whether the cell is alive or not.
     * @return true if the cell is still alive.
     */
    protected boolean isAlive() {
        return alive;
    }

    /**
     * Indicate that the cell is no longer alive.
     */
    protected void setDead() {
        alive = false;
    }

    /**
     * Indicate that the cell will be alive or dead in the next generation.
     */
    public void setNextState(boolean value) {
      nextAlive = value;
    }

    /**
     * Changes the state of the cell
     */
    public void updateState() {
      alive = nextAlive;
    }

    /**
     * Changes the color of the cell
     */
    public void setColor(Color col) {
      color = col;
    }

    /**
     * Returns the cell's color
     */
    public Color getColor() {
      return color;
    }

    /**
     * Return the cell's location.
     * @return The cell's location.
     */
    protected Location getLocation() {
        return location;
    }

    /**
     * Place the cell at the new location in the given field.
     * @param location The cell's location.
     */
    protected void setLocation(Location location) {
        this.location = location;
        field.place(this, location);
    }

    /**
     * Return the cell's field.
     * @return The cell's field.
     */
    protected Field getField() {
        return field;
    }
    
    // protected List<Cell> getNeighbours(){
        // return neighbours;
    // }
    
    /**
     * Return the cell's age (in generations)
     * @return The cell's age (measured in generations)
     */
    protected int getAge() {
        return age;
    }

    /**
     * @retyrn the cell's spawn probability
     */
    protected double getProbabilityForSpawningNewCell(){
        return probabilityForSpawningNewCell;
    }

    /**
     * Changes the cell's spawn probability
     */
    protected void changeProbabilityForSpawningNewCell(double n){
        probabilityForSpawningNewCell = n;
    }

    /**
     * Increases the cell's age by 1
     */
    protected void incrementAge(){
        age++;
    }
    
    /**
     * Return all alive cells around the cell
     * @return List of the cells
     */
    protected List<Cell> getNeighbours(){
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        return neighbours;
    }
    
    /**
     * Return the number of mycoplasma neighbours around the cell
     * @return The number of mycoplasma neighbours
     */
    protected int getMycoCount(){
        int mycoCount = 0;
        List<Cell> neighbours = getNeighbours();
        for(Cell neighbour : neighbours){
            if(neighbour instanceof Mycoplasma){
                mycoCount++;
            }
        }
        return mycoCount;
    }
    
    /**
     * Return the number of white blood cells neighbours around the cell
     * @return The number of white blood cells neighbours
     */
    protected int getWhiteCount(){
        int whiteCount = 0;
        List<Cell> neighbours = getNeighbours();
        for(Cell neighbour : neighbours){
            if(neighbour instanceof WhiteBloodCell){
                whiteCount++;
            }
        }
        return whiteCount;
    }
    
        /**
     * Returns the number of cancer cell neighbours around a given cell
     * @return The number of cancer cell neighbours
     */
    protected int cancerCount(){
        int cancerCount = 0;
        List<Cell> neighbours = getNeighbours();
        for(Cell neighbour : neighbours){
            if(neighbour instanceof CancerCell){
                cancerCount++;
            }
        }
        return cancerCount;
    }
    
    /**
     * Check if the white blood cell is "mature".
     * Only mature white blood cells can attack other cells.
     * @return True if the white blood cell is equal to or older than 5 (generations)
     * It says 6 generations because counting starts at 1
     * Counting starts at 1 to prevent issues with the modulo operator
     */
    protected int matureWhiteNearby(){
        List<Cell> neighbours = getNeighbours();
        for(Cell neighbour : neighbours){
            //what if I just did age? probably no works
            if(neighbour instanceof WhiteBloodCell){
                if(neighbour.getAge() >= 6){
                    return 1;
                }
                else if(neighbour.getAge() >= 6){
                    return 2;
                }
            }
        }
        return 0;
    }

    /**
     *
     */
    protected Cell cancerCellNearby() {
        for (Cell neighbour : getNeighbours()) {
            if (neighbour instanceof CancerCell) {
                return neighbour;

            }
        }
        return null;
    }

            /**
             *
             */
            // protected boolean elderWhiteNearby(){
            // List<Cell> neighbours = getNeighbours();
            // for(Cell neighbour : neighbours){
            // //what if I just did age? probably no works
            // if(neighbour instanceof WhiteBloodCell && neighbour.getAge() >= 10){
            // return true;
            // }
            // }
            // return false;
            // }

            /**
             * Return the cell's simulator
             * @return Return the cell's simulator
             */
            protected Simulator getSimulator(){
                return simulator;
            }

}