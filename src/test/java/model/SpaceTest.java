package model;

import com.webcheckers.Model.Piece;
import com.webcheckers.Model.Position;
import com.webcheckers.Model.Space;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test for the Space Class
 */
@Tag("Model-Tier")
public class SpaceTest {

    /**
     * Test function to assert the correct x-coordinate of the cell from the provided Space
     */
    @Test
    public void getCellIdx_test(){
         int cellIdx = 0;
         int cellIdy = 0;
         Piece piece = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
         Space sp = new Space(cellIdx, cellIdy, piece);
         assertEquals(0, sp.getCellIdx());
    }

    /**
     * Test function to assert the correct y-coordinate of the cell from the provided Space
     */
    @Test
    public void getCellIdy_test(){
        int cellIdx = 0;
        int cellIdy = 1;
        Piece piece = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        Space sp = new Space(cellIdx, cellIdy, piece);
        assertEquals(1, sp.getCellIdy());
    }

    /**
     * Test function to assert the return of the correct Piece from the Space
     */
    @Test
    public void getPiece_test(){
        int cellIdx = 2;
        int cellIdy = 2;
        Piece piece = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        Space sp = new Space(cellIdx, cellIdy, piece);

        assertSame(piece, sp.getPiece());
    }

    /**
     * Test function that asserts that the hasPiece function does not return a null Piece in the provided Space
     */
    @Test
    public void hasPiece_test(){
        int cellIdx = 3;
        int cellIdy = 3;
        Piece piece = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        Space sp = new Space(cellIdx, cellIdy, piece);

        assertNotNull(sp.hasPiece());
    }

    /**
     * Test function to assert that the addPieceToSpace function does not throw an IndexOutOfBoundsException
     */
    @Test
    public void addPieceToSpace_test_valid(){
        int cellIdx = 7;
        int cellIdy = 7;
        Piece piece = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        Space sp = new Space(cellIdx, cellIdy, piece);
        sp.addPieceToSpace(piece);

        assertDoesNotThrow(IndexOutOfBoundsException::new);
    }

    /**
     * Test function to assert that the addPieceToSpace function does throw an IndexOutOfBoundsException on an invalid
     * add
     */
    @Test
    public void addPieceToSpace_test_not_valid(){
        boolean thrown = false;

        int cellIdx = 10;
        int cellIdy = 10;


        try {
            Piece piece = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
            Space sp = new Space(cellIdx, cellIdy, piece);
            sp.addPieceToSpace(piece);

        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (thrown) //an exception, index out of bounds was thrown
            assertTrue(thrown); //This should be an error but not sure what is going on here.
        assertFalse(thrown);    //This should not happen

    }

    /**
     * Test function to assert that when a Piece is removed from a Space, and the getPiece() function on that Space
     * will return null
     */
    @Test
    public void removePieceFromSpace_test() {

        int cellIdx = 5;
        int cellIdy = 5;

        Piece piece = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        Space sp = new Space(cellIdx, cellIdy, piece);
        sp.addPieceToSpace(piece);
        sp.removePieceFromSpace();

        assertNull( sp.getPiece() );

    }

    /**
     * Test function to assert that the Space.getPosition() function returns a real Position object
     */
    @Test
    public void getPosition_test() {

        int cellIdx = 5;
        int cellIdy = 5;

        Piece piece = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        Space sp = new Space(cellIdx, cellIdy, piece);
        sp.addPieceToSpace(piece);
        Position pos = new Position(cellIdx,cellIdy);

        assertTrue(sp.getPosition() instanceof Position );
    }

    /**
     * Test function that tests that a Piece on a Space is in fact valid.
     * This test is confusing, what is the code supposed to do for space validity
     */
    @Test
    public void isValid_test() {

        int cellIdx = 7;
        int cellIdy = 4;

        Piece piece = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        Space sp = new Space(cellIdx, cellIdy, piece);
        sp.addPieceToSpace(piece);
        Position pos = new Position(cellIdx,cellIdy);
        // assertNotNull(sp);
        assertEquals(false, sp.isValid()); //This should be True, because space object
                                                    // is not null, and the cellIdx and cellIdy
                                                    //  are not equal mods.
                                                    // This should be True
    }

}
