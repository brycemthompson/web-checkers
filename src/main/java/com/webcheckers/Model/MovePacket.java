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
    private Position jumpedPiece;

    private MovePacket(Move move, Type type, Position jumpedPiece){
        this.move = move;
        this.type = type;
        this.jumpedPiece = jumpedPiece;
    }


    private MovePacket(Move move, Type type){
        this(move, type, null);
    }


    public Move getMove(){
        return this.move;
    }


    public Type getType(){
        return this.type;
    }


    public Position getJumpedPiece(){
        return this.jumpedPiece;
    }


}
