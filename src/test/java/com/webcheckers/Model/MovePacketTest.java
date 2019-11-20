package com.webcheckers.Model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-Tier")
public class MovePacketTest {

    @Test
    public void testGetMove()
    {
        Move move = new Move(new Position(0,0), new Position(0,1));
        MovePacket.Type type = MovePacket.Type.SIMPLE;
        Position pos = new Position(2, 3);
        Piece piece = new Piece(Piece.Type.SINGLE, Piece.Color.WHITE);
        PieceWithPosition p = new PieceWithPosition(piece, pos);
        MovePacket mp = new MovePacket(move,p);
        assertEquals(move, mp.getMove());
    }

    @Test
    public void testGetType()
    {
        Move move = new Move(new Position(0,0), new Position(0,1));
        MovePacket.Type type = MovePacket.Type.SIMPLE;
        PieceWithPosition pieceWithPosition = new PieceWithPosition(
                                                new Piece(Piece.Type.SINGLE, Piece.Color.RED),
                                                    new Position(3, 3) );
        MovePacket mp = new MovePacket(move, pieceWithPosition);
        assertEquals(type, mp.getType());
    }

    @Test
    public void testGetJumpedPiece()
    {
        Move move = new Move(new Position(0,0), new Position(0,1));
        MovePacket.Type type = MovePacket.Type.SIMPLE;
        Piece jumpedPiece = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        Position jumpedPiecePosition = new Position(1,1);
        PieceWithPosition pieceWithPosition = new PieceWithPosition(new Piece(Piece.Type.SINGLE, Piece.Color.WHITE), jumpedPiecePosition);
        MovePacket mp = new MovePacket(move, pieceWithPosition);
        assertEquals(jumpedPiece, mp.getJumpedPiece());
    }
}