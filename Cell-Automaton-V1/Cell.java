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
    protected double spawnRate;
    
    // Boolean flag that checks if the cell's spawn rate will be changed or not
    protected boolean changeProbabilityFlag = false;

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
    }

    /**
     * Cell constructor but no location is given, so the cell is
     * not placed on the field.
     *
     * @param simulator The simulator used
     * @param field The field currently occupied.
     */
    public Cell(Simulator simulator, Field field, Color col) {
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
     * @return The cell's colour
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

    /**
     * Return the cell's age (in generations)
     * @return The cell's age (measured in generations)
     */
    protected int getAge() {
        return age;
    }
    
    protected void setAgeZero(){
        age = 0;
    }

    /**
     * @return the cell's spawn probability
     */
    protected double getSpawnRate(){
        return spawnRate;
    }

    /**
     * Changes the cell's spawn probability
     */
    protected void setSpawnRate(double newRate){
        spawnRate = newRate;
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
     * This returns the number of neighbours of a particular Cell subclass.
     * Takes a class as a parameter (for example "Mycoplasma.class") and checks how many neighbours
     * of that class the cell using this method has.
     * @param c
     * @return Number of neighbours of Class c.
     */
    protected int getCellCount(Class c){
        int cellCount = 0;
        //Class<? extends Cell> classOfCell = cell.getClass();
        List<Cell> neighbours = getNeighbours();
        for(Cell neighbour : neighbours){
            if(neighbour.getClass().equals(c)){
                cellCount++;
            }
        }
        return cellCount;
    }

    /**
     * @return The boolean value of the flag that determines if probability of spawning should change
     */
    protected boolean getChangeProbabilityFlag(){
        return changeProbabilityFlag;
    }

    /**
     * Changes the value of the flag for a given cell
     */
    protected void setChangeProbabilityFlag(boolean flagValue){
        changeProbabilityFlag = flagValue;
    }

    /**
     * Return the cell's simulator
     * @return Return the cell's simulator
     */
    protected Simulator getSimulator(){
        return simulator;
    }
    
    /**
     * Replace the cell with an Empty Cell
     */
    protected void replaceWithEmpty(){
        EmptyCell newEmpty = new EmptyCell(getSimulator(), getField(), Color.GRAY);
        getSimulator().addTemporaryCell(newEmpty);
    }
    
    /**
     * A new white blood cell is considered "young"
     * Check if the white blood cell is "mature" (returns 1) or "elder" (returns 2).
     * Mature white blood cells can attack Mycoplasma cells.
     * Elder white blood cells can attack Mycoplasma and Cancer cells.
     * Age starts at 1 to prevent issues with the modulo operator when looking for a zero value
     * @return 1 if the white blood cell is >= 6 generations old
     * @return 2 if the white blood cell is >= 11 generations old
     */
    protected int getNearbyWhiteMaturity(){
        List<Cell> neighbours = getNeighbours();
        for(Cell neighbour : neighbours){
            //what if I just did age? probably no works
            if(neighbour instanceof WhiteBloodCell){
                if(neighbour.getAge() >= 6){
                    return 1;
                }
                else if(neighbour.getAge() >= 11){
                    return 2;
                }
            }
        }
        return 0;
    }

    /**
     * Checks if a cancer cell that can replicate itself is nearby
     * Cancer cells can only multiply once every 10 generations
     * @return true if there is a cancer cell neighbour with an age in the multiples of 10
     * @return false otherwise
     */
    protected Boolean checkIfReplicableCancerCellIsNearby() {
        for (Cell neighbour : getNeighbours()) {
            if (neighbour instanceof CancerCell && neighbour.getAge()%10 == 0) {
                return true;
            }
        }
        return false;
    }

}