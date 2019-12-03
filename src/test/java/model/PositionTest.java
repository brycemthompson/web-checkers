package model;
import com.webcheckers.Model.Piece;
import com.webcheckers.Model.Position;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    /**
     * Test function that will test equality of two object if they are the same
     */
    @Test
    public void equals_test() {

        Position obj = new Position(4,4);
        if (obj instanceof Position){
            assertTrue(obj instanceof Position);
        }

    }

    /**
     * Test function that will test equality of two object if they are not the same
     */
    @Test
    public void equals_fail_test() {

        Piece obj = new Piece(Piece.Type.SINGLE, Piece.Color.WHITE);
        boolean isSame = obj.equals(new Position(4,4));
        assertFalse(isSame);
    }

}


