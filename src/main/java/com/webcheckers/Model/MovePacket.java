package com.webcheckers.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Packet for transferring Moves between the server and client. Holds information such as Move type and position
 * for Pieces jumped.
 *
 * @author Clayton Pruitt : chp4145@g.rit.edu
 */

public class MovePacket {

    public static enum Type{
        SIMPLE, SIMPLE_JUMP, MULTIPLE_JUMP;
    }

    /*
    Private fields.
     */

    // the type of the Move contained in this MovePacket
    private Type type;

    // the Move contained in this MovePacket
    private Move move;

    // list of all Pieces jumped in this MovePacket (if any)
    private List<PieceWithPosition> jumpedPieces;


    /**
     * Constructor for a multiple jump move.
     * @param move the Move being made
     * @param jumpedPieces all jumped Pieces and their associated Positions
     */
    public MovePacket(Move move, List<PieceWithPosition> jumpedPieces){
        this.move = move;
        this.jumpedPieces = jumpedPieces;
        this.type = Type.MULTIPLE_JUMP;
    }


    /**
     * Constructor for a simple jump move.
     * @param move the Move being made
     * @param jumpedPiece the jumped Piece and its Position
     */
    public MovePacket(Move move, PieceWithPosition jumpedPiece){

        this.move = move;

        this.jumpedPieces = new ArrayList<PieceWithPosition>();
        this.jumpedPieces.add(jumpedPiece);

        this.type = Type.SIMPLE_JUMP;

    }


    /**
     * Constructor for a simple move.
     * @param move the Move being made
     */
    public MovePacket(Move move){

        this.move = move;

        this.jumpedPieces = null;

        this.type = Type.SIMPLE;

    }

    /**
     * Get the Move contained in this MovePacket.
     * @return a Move object
     */
    public Move getMove(){
        return this.move;
    }


    /**
     * Get the Type of this MovePacket.
     * @return enum Type evaluating to either SIMPLE, SIMPLE_JUMP, or MULTIPLE_JUMP
     */
    public Type getType(){
        return this.type;
    }

    /**
     * Get the first jumped Piece contained in the jumped Pieces list for this MovePacket. Used for simple jumps.
     * @return a PieceWithPosition object
     */
    public PieceWithPosition getJumpedPiece()
    {
        if (this.jumpedPieces == null){
            return null;
        }
        return this.jumpedPieces.get(0);
    }

    /**
     * Get the Pieces jumped by this move as well as their associated Positions.
     * @return List of PieceWithPositions
     */
    public List<PieceWithPosition> getJumpedPieces(){
        return this.jumpedPieces;
    }


}
