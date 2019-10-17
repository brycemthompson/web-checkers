package com.webcheckers.Model;

/**
 * Java class object representing a space on a board of checkers.
 * @author Clayton Pruitt : chp4145@rit.edu
 * @author Isaias Villalobos : ixv8608@rit.edu
 * @contributor Bryce Thompson : bxt6698@rit.edu
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
     * Constructor for Space
     * @param cellIdx: The x value of the cell
     * @param cellIdy: The y value of the cell
     * @param piece: The Piece in the cell
     */
    public Space(int cellIdx, int cellIdy, Piece piece){
        this.cellIdx = cellIdx;
        this.cellIdy = cellIdy;
        this.piece = piece;
    }

    /**
     * Getter for the cellIdx
     * @return cellIdx
     */
    public int getCellIdx() {
        return cellIdx;
    }

    /**
     * Getter for Piece
     * @return this.piece
     */
    public Piece getPiece(){
        return this.piece;
    }

    /**
     * Adds a Piece to this space
     * @param piece: The Piece to add
     */
    public void addPieceToSpace(Piece piece){
        this.piece = piece;
    }

    /**
     * Function to check validity of the Space
     * @return boolean value based on validity
     */
    public boolean isValid(){
        return ((cellIdx % 2 != cellIdy % 2) && this.piece == null);
    }
}
