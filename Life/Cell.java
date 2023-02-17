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
    
    //The cell's probability to spawn on the field
    private double spawnProbability;
    
    //The cell's simulator
    private Simulator simulator;

    // The cell's field.
    private Field field;

    // The cell's position in the field.
    private Location location;

    // The cell's color
    private Color color = Color.white;
    
    //**private List<Cell> neighbours;

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
     * Return the cell's age (in generations)
     * @return The cell's age (measured in generations)
     */
    protected int getAge() {
        return age;
    }
    
    /**
     * Increases the cell's age by 1
     */
    protected void incrementAge(){
        age++;
    }
    
    protected double getSpawnProbability(){
        return spawnProbability;
    }
    
    protected void setSpawnProbability(double newProbability){
        spawnProbability = newProbability;
    }
    
    /**
     * Return the cell's simulator
     * @param Return the cell's simulator
     */
    protected Simulator getSimulator(){
        return simulator;
    }

    /**
     * Return the cell's field.
     * @return The cell's field.
     */
    protected Field getField() {
        return field;
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
     * Returns the cell's color
     */
    public Color getColor() {
      return color;
    }
    
    /**
     * Changes the color of the cell
     */
    public void setColor(Color col) {
      color = col;
    }
    
    // protected List<Cell> getNeighbours(){
        // return neighbours;
    // }
    
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
     * Return the number of cancer cells neighbours around the cell
     * @return The number of cancer cells neighbours
     */
    protected int getCancerCount(){
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
     * Check the white blood cell's maturity. Maturity levels affect the kind
     * of cells it can attack.
     * 0 = child. They cannot kill anything
     * 1 = mature. They can only kill mycoplasma
     * 2 = elder. They can kill mycoplasma and cancer cells
     */
    protected int matureWhiteNearby(){
        List<Cell> neighbours = getNeighbours();
        for(Cell neighbour : neighbours){
            int neighboursAge = neighbour.getAge();
            if(neighbour instanceof WhiteBloodCell){
                if(neighboursAge >= 6){
                    return 1;
                }else if(neighboursAge >= 11){
                    return 2;
                }
            }
        }
        return 0;
    }
    
    protected boolean replicableCancerNearby(){
        List<Cell> neighbours = getNeighbours();
        for(Cell neighbour : neighbours){
            if(neighbour instanceof CancerCell && neighbour.getAge()%10 == 0){
                return true;
            }
        }
        return false;
    }
    
}
