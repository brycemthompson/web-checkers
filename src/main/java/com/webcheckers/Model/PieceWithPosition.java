package com.webcheckers.Model;

/**
 * A class that pairs a Piece with a Position.
 * @author Clayton Pruitt : chp4145
 */

public class PieceWithPosition {

    // private fields
    private Piece piece;
    private Position position;

    /**
     * Constructor for PieceWithPosition.
     * @param piece Piece object
     * @param position Position object
     */
    public PieceWithPosition(Piece piece, Position position){
        this.piece = piece;
        this.position = position;
    }

    /**
     * Get the Piece.
     * @return Piece
     */
    public Piece getPiece(){
        return this.piece;
    }

    /**
     * Get the Position.
     * @return Position
     */
    public Position getPosition(){
        return this.position;
    }

}
