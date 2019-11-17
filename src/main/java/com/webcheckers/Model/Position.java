package com.webcheckers.Model;

/**
 * Represents the (row, cell) coordinate of some Space on the Board.
 * @author Clayton Pruitt : chp4145@g.rit.edu
 */

public class Position {

    // private fields
    private int row;
    private int cell;


    /**
     * Constructor.
     * @param row the row this Position is located in
     * @param cell the cell this Position is located in
     */
    public Position(int row, int cell){
        this.row = row;
        this.cell = cell;
    }

    /**
     * Getter for row.
     */
    public int getRow(){
        return this.row;
    }

    /**
     * Getter for cell.
     */
    public int getCell(){
        return this.cell;
    }

    // Overriding equals() to compare if two Positions are equal by checking their coordinates.
    @Override
    public boolean equals(Object obj) {
        // this Position equals itself
        if (obj == this){
            return true;
        }

        // this Position cannot equal an object that is not a Position
        if (!(obj instanceof Position)){
            return false;
        }

        // two Positions are equal if their coordinates are equal
        Position p = (Position) obj;
        return (this.cell == p.cell && this.row == p.row);
    }

    @Override
    public String toString() {
        return "(cell: " + cell + ", row: " + row + ")";
    }
}
