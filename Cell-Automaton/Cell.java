import java.awt.Color;
import java.util.List;

/**
 * A class representing the shared characteristics of all forms of life
 *
 * @author Muhammad Ahsan Mahfuz, Saihan Marshall, David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2023.02.28 (1)
 */

public abstract class Cell {
    // Whether the cell is alive or not.
    private boolean alive;

    // Whether the cell will be alive in the next generation.
    private boolean nextAlive;
    
    // How many generations the cell has lived for
    // Starts at 1 to not cause errors with modulo operator
    private int age = 1;
    
    // The probability that a new cell spawns after its conditions have been fulfilled
    protected double spawnRate;

    // The cell's field.
    private Field field;

    // The cell's position in the field.
    private Location location;

    // The cell's color
    private Color color = Color.white;


    /**
     * Create a new cell at location in field.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Cell(Field field, Location location, Color col) {
        alive = true;
        nextAlive = false;
        this.field = field;
        setLocation(location);
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
     * Return the cell's age.
     * @return The cell's age.
     */
    protected int getAge(){
        return age;
    }
    
    /**
     * Reset the cell's age to 1
     */
    protected void resetAge(){
        age = 1;
    }
    
    /**
     * Increments the cell's age by 1
     */
    protected void incrementAge(){
        age++;
    }

    /**
     * Return the cell's spawn rate
     * @return The cell's spawn rate
     */
    protected double getSpawnRate(){
        return spawnRate;
    }
    
    /**
     * Set the cell's spawn rate
     * @param newRate The new spawn rate
     */
    protected void setSpawnRate(double newRate){
        spawnRate = newRate;
    }
    
    /**
     * Return all the cell's living neighbours
     * @return A list of the cell's living neighbours
     */
    protected List<Cell> getNeighbours(){
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        return neighbours;
    }
    
    /**
     * This returns the number of neighbours of a particular Cell subclass.
     * Takes a class as a parameter (for example "Mycoplasma.class") and checks how many neighbours
     * of that class the cell using this method has.
     * 
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
     * A new white blood cell is considered "young"
     * Check if the white blood cell is "mature" (returns 1) or "elder" (returns 2).
     * Mature white blood cells can attack Mycoplasma cells.
     * Elder white blood cells can attack Mycoplasma and Cancer cells.
     * 
     * @return 1 if the white blood cell is >= 6 generations old
     * @return 2 if the white blood cell is >= 11 generations old
    */
    protected int getNearbyWhiteMaturity(){
        for(Cell neighbour : getNeighbours()){
            //what if I just did age? probably no works
            if(neighbour instanceof WhiteBloodCell && !((WhiteBloodCell)neighbour).isDiseased()){
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
     * Checks if a replicable cancer cell is nearby
     * Cancer cells can replicate once evert 5 generations.
     * 
     * @return true if there is a cancer cell neighbour with an age divisible by 5
     * @return false otherwise
     */
    protected boolean replicableCancerNearby(){
        for(Cell neighbour : getNeighbours()){
            if(neighbour instanceof CancerCell && neighbour.getAge()%5 == 0){
                return true;
            }
        }
        return false;
    }
}
