package com.webcheckers.Model;

/**
 * Packet for transferring Moves between the server and client. Holds information such as Move type and position
 * for Pieces jumped.
 *
 * @author Clayton Pruitt : chp4145@g.rit.edu
 */

public class MovePacket {

    public static enum Type{
        SIMPLE, SIMPLE_JUMP;
    }

    // private fields
    private Move move;
    private Type type;
    private Piece jumpedPiece;
    private Position jumpedPiecePosition;

    public MovePacket(Move move, Type type, Piece jumpedPiece, Position jumpedPiecePosition){
        this.move = move;
        this.type = type;
        this.jumpedPiece = jumpedPiece;
        this.jumpedPiecePosition = jumpedPiecePosition;
    }


    public MovePacket(Move move, Type type){
        this(move, type, null, null);
    }


    public Move getMove(){
        return this.move;
    }


    public Type getType(){
        return this.type;
    }


    public Piece getJumpedPiece(){
        return this.jumpedPiece;
    }

    public Position getJumpedPiecePosition() { return this.jumpedPiecePosition; }


}
