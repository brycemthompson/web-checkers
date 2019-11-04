package model;

import com.webcheckers.Model.Position;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Tag("Model-Tier")
public class PositionTest {

    // private fields
    public int row;
    public int cell;

    public PositionTest(int row1, int cell1){
        this.row = row1;
        this.cell = cell1;
    }

    public int getRow(){
        return this.row;
    }


    public int getCell(){
        return this.cell;
    }

    // Overriding equals() to compare if two Positions are equal by checking their coordinates.
    @Override
    public boolean equals(Object obj) {
        // this Position equals itself
        if (obj == this){
            return true;
        }

        // this Position cannot equal an object that is not a Position
        if (!(obj instanceof PositionTest)){
            return false;
        }

        // two Positions are equal if their coordinates are equal
        PositionTest p =  (PositionTest) obj;
        return (this.cell == p.cell && this.row == p.row);
    }

    @Override
    public String toString() {
        return "(" + row + ", " + cell + ")";
    }


    @Test
    public void getRow_test(){
        int row = 1;
        int cell = 2;
        PositionTest pos = new PositionTest(row, cell);

        assertEquals(1, pos.getRow());
    }

    @Test
    public void getCell_test(){
        int row = 1;
        int cell = 2;
        PositionTest pos = new PositionTest(row, cell);

        assertEquals(2, pos.getCell());
    }

}


