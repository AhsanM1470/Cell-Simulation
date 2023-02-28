import java.awt.Color;
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
    private double randResult = 0;

    /**
     * Constructor for objects of class WhiteBloodCell
     */
    public WhiteBloodCell(Field field, Location location, Color col) {
        super(field, location, col);
        setSpawnRate(0.55);
        Random rand = new Random();
        double randResult = rand.nextDouble();
    }

    /**
     *
     */
    public void act(){
        int mycoCount = getCellCount(Mycoplasma.class);
        int whiteCount = getCellCount(WhiteBloodCell.class);
        setNextState(true);
        
        if(isAlive()){
            //Diseased.
            //This changes the cell's behaviour to spread disease and
            // kill other cells
            if(isDiseased){
                setColor(Color.RED);
                if(getAge() > 2){
                    setNextState(false);
                    isDiseased = false;
                    setAgeZero();
                    setColor(Color.PINK);
                    return;
                }
            }

            //This causes diseased cells to possibly infect normal White Blood Cells.
            else{
                for(Cell neighbour : getNeighbours()){
                    if(neighbour instanceof WhiteBloodCell && ((WhiteBloodCell)neighbour).isDiseased()){
                        setMayBeDiseased();
                    }
                }
            }

            //Kills White Blood Cells if enough Mycoplasma are nearby. Random chance.
            if(mycoCount >= 2 && randResult >= getSpawnRate()){
                //tk Mycoplasma spawn rate
                //Random rand = new Random();
                //double randResult = rand.nextDouble();
//                if(randResult <= ){
                setNextState(false);
                setAgeZero();
                setColor(Color.PINK);
                //}
            }

            //White Blood Cells die if they are surrounded by more White Blood Cells.
            else if(whiteCount > 3){
                setNextState(false);
                setAgeZero();
                setColor(Color.PINK);
            }

            //Diseased cells kill normal White Blood Cells. Random chance.
            else if(getDiseasedCount() > 2 && randResult >= getSpawnRate()){
                setNextState(false);
                setAgeZero();
                setColor(Color.PINK);
            }
        }

        //Revival clause. Random chance.
        else{
            if(whiteCount == 2 && randResult < getSpawnRate()){
                setNextState(true);
                setAgeZero();
                setColor(Color.PINK);
            }
        }
    }

    /**
     * @return true if the cell is diseased or not
     */
    public boolean isDiseased(){
        return isDiseased;
    }

    /**
     * When called, the cell this is acting on may become diseased.
     *  If this happens, the cell's age resets to 0.
     */
    public void setMayBeDiseased(){
        Random rand = new Random();
        double randResult = rand.nextDouble();
        if(randResult <= 0.01){
            isDiseased = true;
            setAgeZero();
        }
    }

    /**
     * @return number of diseased White Blood Cell neighbours.
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
