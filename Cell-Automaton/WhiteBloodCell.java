import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
 * Write a description of class WhiteBloodCell here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class WhiteBloodCell extends Cell
{
    private boolean isDiseased = false;

    /**
     * Constructor for objects of class WhiteBloodCell
     */
    public WhiteBloodCell(Field field, Location location, Color col) {
        super(field, location, col);
        setSpawnRate(0.55);
    }
    
    public void act(){
        int mycoCount = getCellCount(Mycoplasma.class);
        int whiteCount = getCellCount(WhiteBloodCell.class);
        setNextState(true);
        
        if(isAlive()){
            if(isDiseased){
                setColor(Color.MAGENTA);
                if(getAge() > 2){
                    setNextState(false);
                    isDiseased = false;
                    setAgeZero();
                    setColor(Color.PINK);
                    return;
                }
            }else{
                for(Cell neighbour : getNeighbours()){
                    if(neighbour instanceof WhiteBloodCell && ((WhiteBloodCell)neighbour).isDiseased()){
                        setMayBeDiseased();
                    }
                }
            }
            
            if(mycoCount >= 2){
                //tk Mycoplasma spawn rate
                //Random rand = new Random();
                //double randResult = rand.nextDouble();
                //if(randResult <= ){
                setNextState(false);
                setAgeZero();
                setColor(Color.PINK);
                //}
            }else if(whiteCount > 3){
                setNextState(false);
                setAgeZero();
                setColor(Color.PINK);
            }else if(getDiseasedCount() > 2){
                setNextState(false);
                setAgeZero();
                setColor(Color.PINK);
            }
        }else{
            if(whiteCount == 2){
                setNextState(true);
                setAgeZero();
                setColor(Color.PINK);
            }
        }
    }
    
    public boolean isDiseased(){
        return isDiseased;
    }
    
    public void setMayBeDiseased(){
        Random rand = new Random();
        double randResult = rand.nextDouble();
        if(randResult <= 0.01){
            isDiseased = true;
            setAgeZero();
        }
    }
    
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
