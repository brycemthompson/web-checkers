package model;

import com.webcheckers.Model.Position;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit Test for the Position Class
 */
@Tag("Model-Tier")
public class PositionTest {

    /**
     * Test function to test that the getRow function returns the correct row in the Position
     */
    @Test
    public void getRow_test(){
        int row = 1;
        int cell = 2;
        Position pos = new Position(row, cell);

        assertEquals(1, pos.getRow());
    }

    /**
     * Test function to test that the getCell function returns the correct cell in the Position
     */
    @Test
    public void getCell_test(){
        int row = 1;
        int cell = 2;
        Position pos = new Position(row, cell);

        assertEquals(2, pos.getCell());
    }

}


