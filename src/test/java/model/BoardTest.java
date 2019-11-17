package model;

import com.webcheckers.Model.Piece;
import com.webcheckers.Model.Row;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test for the Board Class
 */
@Tag("Model-Tier")
public class BoardTest implements Iterable<Row> {

    /**
     * Fields
     */
    ArrayList<Row> rows;
    public static int rowsPerBoard = 8;

    /**
     * Board Constructor
     */
    public BoardTest(){
        // populate board with rows
        rows = new ArrayList<Row>();
        for (int i = 0; i < rowsPerBoard; i++){
            rows.add(new Row(i));
        }
    }

    /**
     * Helper function to add a Piece to a Space on the Board
     * @param piece: the Piece to add to the space
     * @param cellIdx: the cell x-coordinate location of the Space
     * @param cellIdy: the cell y-coordinate location of the Space
     * @return true: that the Piece has been successfully added to the Space
     * @throws IndexOutOfBoundsException
     */
    public boolean addPieceToSpace(Piece piece, int cellIdx, int cellIdy) throws IndexOutOfBoundsException{
        this.rows.get(cellIdy).addPieceToSpace(piece, cellIdx);
        return true;
    }

    /**
     * Override function to iterate through the Spaces on the Board
     * @return it: the iterator
     */
    @Override
    public Iterator<Row> iterator() {
        Iterator<Row> it = new Iterator<Row>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext(){
                return (currentIndex < rows.size() && rows.get(currentIndex) != null);
            }

            @Override
            public Row next(){
                return rows.get(currentIndex++);
            }

            @Override
            public void remove(){
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }

    /**
     * Assert that the Piece in a Space on the Board is legal
     */
    @Test
    public void testBoard_case_legal(){
        //Test for a piece in boundary of board, should be legal
        BoardTest board = new BoardTest();
        Piece pieceTest = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        board.addPieceToSpace(pieceTest, 1, 1);
        assertTrue(true);
    }

    /**
     * Assert that an illegally placed Piece on the Board will be false
     * This Test case should fail, the code is supposed to throw an error. LOOK INTO THIS MORE
     */
    @Test
    public void testBoard_case_illegal(){
        int celldx = 1;
        int celldy = 25;
        //Test for a piece not in boundary, should not be legal
        BoardTest board = new BoardTest();
        Piece pieceTest2= new Piece(Piece.Type.SINGLE, Piece.Color.RED);

        assertThrows(IndexOutOfBoundsException.class, ()->{
            board.addPieceToSpace(pieceTest2, celldx, celldy);
        });
    }

    /**
     * Test function to check whether the Space we are trying to add a Piece to is already occupied
     */
    @Test
    public void testBoard_case_occupied(){
        //Create a board that will hold pieces
        BoardTest board = new BoardTest();

        //Create two pieces to test if a space is occupied.
        Piece pieceTest3 = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        Piece pieceTest4 = new Piece(Piece.Type.SINGLE, Piece.Color.RED);

        //Add first piece to the board, then make the other piece try and occupy same space
        board.addPieceToSpace(pieceTest3, 1,1);
        board.addPieceToSpace(pieceTest4, 1,1);

    }
}



