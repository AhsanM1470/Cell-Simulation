import java.awt.Color;
import java.util.Random;

/**
 * Attacks mycoplasma and cancer cells
 *
 * @author Muhammad Ahsan Mahfuz & Saihan Marshall
 * @version 2023.02.28 (1)
 */
public class WhiteBloodCell extends Cell
{
    private boolean isDiseased = false;

    /**
     * Constructor for objects of class WhiteBloodCell
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public WhiteBloodCell(Field field, Location location, Color col) {
        super(field, location, col);
        setSpawnRate(0.75);
    }
    
    /**
     * How it is decided if white blood cells live or not
     */
    public void act(){
        int mycoCount = getCellCount(Mycoplasma.class);
        int whiteCount = getCellCount(WhiteBloodCell.class);
        int cancerCount = getCellCount(CancerCell.class);
        setNextState(true);
        
        if(isAlive()){
            if(isDiseased){
                // Diseased white blood cells turn dark red and kill themselves
                // if they are older than 6 generations
                setColor(Color.decode("#b50000"));
                if(getAge() > 6){
                    isDiseased = false;
                    whiteDeath();
                    return;
                }
            }else{
                // Diseased white blood cells can cause other healthy white blood cells to become diseased
                for(Cell neighbour : getNeighbours()){
                    if(neighbour instanceof WhiteBloodCell && ((WhiteBloodCell)neighbour).isDiseased()){
                        setMayBeDiseased();
                    }
                }
            }
            
            //getDiseasedCount() checks if there are more than 2 diseased cells near the healthy white blood cell
            if(mycoCount > 2 || cancerCount > 1 || getDiseasedCount() > 2){
                whiteDeath();
            }
        }else{
            //Reviving white blood cell
            Random rand = new Random();
            double randResult = rand.nextDouble();
            if(whiteCount == 2 && randResult <= getSpawnRate()){
                setNextState(true);
                resetAge();
                setColor(Color.PINK);
            }
        }
    }
    
    /**
     * Whenever white dies, set its nextState to false, its age back to zero, and its colour back to pink
     */
    public void whiteDeath(){
        setNextState(false);
        resetAge();
        setColor(Color.PINK);
    }
    
    /**
     * Returns the boolean value of if the white blood cell is diseased or not
     * @return isDiseased
     */
    public boolean isDiseased(){
        return isDiseased;
    }
    
    /**
     * There is a 5% chance for the disease to spread to nearby healthy white blood cells
     */
    public void setMayBeDiseased(){
        Random rand = new Random();
        double randResult = rand.nextDouble();
        if(randResult <= 0.05){
            isDiseased = true;
            resetAge();
        }
    }
    
    /**
     * Returns the number of diseased white blood cells nearby
     * @return Number of nearby diseased cells
     */
    protected int getDiseasedCount(){
        int diseasedCount = 0;
        for(Cell neighbour : getNeighbours()){
            if(neighbour instanceof WhiteBloodCell && ((WhiteBloodCell)neighbour).isDiseased()){
                diseasedCount++;
            }
        }
        return diseasedCount;
    }
}
