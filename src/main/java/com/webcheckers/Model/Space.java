package com.webcheckers.Model;

/**
 * Java class object representing a space on a board of checkers.
 * @author Clayton Pruitt
 * @author Isaias Villalobos
 */

public class Space {

    // x coordinate of this space, from 0 to 7 (inclusive)
    private int cellIdx;
    // y coordinate of this space, from 0 to 7 (inclusive)
    // inherited from the index of the Row that holds this Space
    private int cellIdy;
    // current piece on this space
    private Piece piece;

    /**
     * Constructor.
     */
    public Space(int cellIdx, int cellIdy, Piece piece){
        this.cellIdx = cellIdx;
        this.cellIdy = cellIdy;
        this.piece = piece;
    }

    /**
     * Getter for cellIdX.
     */
    public int getCellIdx() {
        return cellIdx;
    }

    /**
     * Getter for piece.
     */
    public Piece getPiece(){
        return this.piece;
    }

    /**
     * Adds a Piece to this Space.
     */
    public void addPieceToSpace(Piece piece){
        this.piece = piece;
    }

    public boolean isValid(){
        return ((cellIdx % 2 != cellIdy % 2) && this.piece == null);
    }
}
