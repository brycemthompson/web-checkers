package com.webcheckers.Model;

/**
 * Packet for transferring Moves between the server and client. Holds information such as Move type and position
 * for Pieces jumped.
 *
 * @author Clayton Pruitt : chp4145@g.rit.edu
 */

public class MovePacket {

    /**
     * Enumeration for the Type of the Move
     */
    public static enum Type{
        SIMPLE, SIMPLE_JUMP;
    }

    /**
     * Private fields
     */
    private Move move;
    private Type type;
    private Piece jumpedPiece;
    private Position jumpedPiecePosition;

    /**
     * Full Constructor for the MovePacket
     * @param move: The move to be made
     * @param type: The type of the move
     * @param jumpedPiece: The piece to be jumped
     * @param jumpedPiecePosition: The position of the piece to be jumped
     */
    public MovePacket(Move move, Type type, Piece jumpedPiece, Position jumpedPiecePosition){
        this.move = move;
        this.type = type;
        this.jumpedPiece = jumpedPiece;
        this.jumpedPiecePosition = jumpedPiecePosition;
    }

    /**
     * Half-Constructor for MovePacket
     * @param move: The move to be made
     * @param type: The type of the move
     */
    public MovePacket(Move move, Type type){
        this(move, type, null, null);
    }

    /**
     * Getter for the Move
     * @return: the move
     */
    public Move getMove(){
        return this.move;
    }

    /**
     * Getter for the Move Type
     * @return: the move type
     */
    public Type getType(){
        return this.type;
    }

    /**
     * Getter for the piece to be jumped
     * @return: the piece to be jumped
     */
    public Piece getJumpedPiece(){
        return this.jumpedPiece;
    }

    /**
     * Getter for the Position of the piece to be jumped
     * @return: position of the piece to be jumped
     */
    public Position getJumpedPiecePosition() { return this.jumpedPiecePosition; }


}
