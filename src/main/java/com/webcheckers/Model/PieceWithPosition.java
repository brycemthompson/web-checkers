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

    /**
     * Gets the row of the Position.
     * @return the row as an int
     */
    public int getRow() { return this.position.getRow(); }

    /**
     * Gets the cell of the Position.
     * @return the cell as an int
     */
    public int getCell() { return this.position.getCell(); }

}
