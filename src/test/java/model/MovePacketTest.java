package model;

import com.webcheckers.Model.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit Test for the MovePacket Class
 */
@Tag("Model")
public class MovePacketTest {

    /**
     * Test function that tests whether the movePacket getMove function works
     */
    @Test
    public void testGetMove(){
        int row = 1, col = 1;
        Position start = new Position(row, col);
        Position end = new Position(row, col);
        Move move = new Move(start, end);
        PieceWithPosition p = new PieceWithPosition(new Piece(Piece.Type.SINGLE, Piece.Color.WHITE), new Position(row, col));
        MovePacket movePacket = new MovePacket(move, p);

        assertTrue(movePacket instanceof MovePacket);

        assertEquals(move, movePacket.getMove());
        assertEquals(1, move.getStart().getCell());
        assertEquals(1, move.getEnd().getRow());
    }

    /**
     * Test function that tests whether the movePacket getType function works
     */
    @Test
    public void testGetType(){
        int row = 1, col = 1;
        Position start = new Position(row, col);
        Position end = new Position(row, col);
        Move move = new Move(start, end);
        PieceWithPosition p = new PieceWithPosition(new Piece(Piece.Type.SINGLE, Piece.Color.WHITE), new Position(row, col));
        MovePacket movePacket = new MovePacket(move, p);

        assertEquals(MovePacket.Type.SIMPLE_JUMP, movePacket.getType());
    }

    /**
     * Test function that tests whether the movePacket getJumped function works
     */
    @Test
    public void testGetJumped(){
        int row = 1, col = 1;
        Position start = new Position(row, col);
        Position end = new Position(row, col);
        Move move = new Move(start, end);
        Piece piece = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        PieceWithPosition p = new PieceWithPosition(new Piece(Piece.Type.SINGLE, Piece.Color.WHITE), new Position(row, col));

        MovePacket movePacket = new MovePacket(move, p);

        assertEquals(MovePacket.Type.SIMPLE_JUMP, movePacket.getType());
    }
    /**
     * Test function that tests whether the movePacket getJumpedPiece function works
     */
    @Test
    public void getJumpedPieceTest(){
        int row = 1, col = 1;
        Position start = new Position(row, col);
        Position end = new Position(row, col);
        Move move = new Move(start, end);
        PieceWithPosition p = new PieceWithPosition(new Piece(Piece.Type.SINGLE, Piece.Color.WHITE), new Position(row, col));

        MovePacket movePacket = new MovePacket(move, p);

        assertEquals(p, movePacket.getJumpedPiece());
    }

    /**
     * Test function that tests whether the getJumpedPiecePosition works
     */
    @Test
    public void getJumpedPiecePosition(){
        int row = 1, col = 4;
        Position start = new Position(row, col);
        Position end = new Position(row, col);
        Move move = new Move(start, end);
        PieceWithPosition p = new PieceWithPosition(new Piece(Piece.Type.SINGLE, Piece.Color.WHITE), new Position(row, col));

        MovePacket movePacket = new MovePacket(move, p);

        assertEquals(start, movePacket.getMove().getStart());
    }
}