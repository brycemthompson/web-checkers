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

        MovePacket mp = new MovePacket(move, type);
        assertEquals(move, mp.getMove());
    }

    @Test
    public void testGetType()
    {
        Move move = new Move(new Position(0,0), new Position(0,1));
        MovePacket.Type type = MovePacket.Type.SIMPLE;

        MovePacket mp = new MovePacket(move, type);
        assertEquals(type, mp.getType());
    }

    @Test
    public void testGetJumpedPiece()
    {
        Move move = new Move(new Position(0,0), new Position(0,1));
        MovePacket.Type type = MovePacket.Type.SIMPLE;
        Piece jumpedPiece = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        Position jumpedPiecePosition = new Position(1,1);

        MovePacket mp = new MovePacket(move, type, jumpedPiece, jumpedPiecePosition);
        assertEquals(jumpedPiece, mp.getJumpedPiece());
    }

    @Test
    public void testGetJumpedPiecePosition()
    {
        Move move = new Move(new Position(0,0), new Position(0,1));
        MovePacket.Type type = MovePacket.Type.SIMPLE;
        Piece jumpedPiece = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        Position jumpedPiecePosition = new Position(1,1);

        MovePacket mp = new MovePacket(move, type, jumpedPiece, jumpedPiecePosition);
        assertEquals(jumpedPiecePosition, mp.getJumpedPiecePosition());
    }
}