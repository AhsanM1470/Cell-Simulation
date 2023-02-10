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
    // instance variables - replace the example below with your own

    /**
     * Constructor for objects of class WhiteBloodCell
     */
    public WhiteBloodCell(Field field, Location location, Color col) {
        super(field, location, col);
    }

    /**
     */
    public void act()
    {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        incrementAge();
        setNextState(false);
        
        if(isAlive()){
            setDead();
            //determines whether the WBC dies due to neighbouring bacteria
            int bacteriaCount = 0;
            for(Cell cell : neighbours){
//                cell.setDead(); //lol
                if(cell instanceof WhiteBloodCell){
                    bacteriaCount++;
                }
            }
            if(bacteriaCount < 3){
                setNextState(true);
            }




            else if (bacteriaCount > 2){
                setNextState(false);
            }



            //determines whether the WBC is old enough to kill pathogens
            if (getAge() > 5){
                //
            }



        }






    }
}
