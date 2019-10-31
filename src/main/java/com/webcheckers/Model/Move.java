package com.webcheckers.Model;

import com.webcheckers.ui.PostHomeRoute;
import com.webcheckers.util.Message;

import static java.lang.Math.abs;

/**
 * Represents a Move on the Board by tracking the start Position and the end Position.
 * @author Clayton Pruitt : chp4145@g.rit.edu
 */

public class Move {

    // private fields
    private Position start;
    private Position end;

    // what is defined as "too far" for a move
    private static int tooFarThreshold = 2;

    /**
     * Constructor.
     * @param start the Position this Move started in
     * @param end the Position this Move is trying to go to
     */
    public Move(Position start, Position end){
        this.start = start;
        this.end = end;
    }

    /**
     * Getter for start.
     */
    public Position getStart(){
        return this.start;
    }

    /**
     * Getter for end.
     */
    public Position getEnd(){
        return this.end;
    }

    /**
     * Helper function that returns whether this Move is too large.
     * @return whether start Position and end Position are too far
     */
    private boolean isMoveTooLarge(){
        int rowDifference = abs(start.getRow() - end.getRow());
        int cellDifference = abs(start.getCell() - end.getCell());
        boolean result = rowDifference > tooFarThreshold || cellDifference > tooFarThreshold;
        return result;
    }

    /**
     * Returns a Message describing the validity of the move.
     */
    public Message validityMessage(){
        // check if distance is too large
        if (isMoveTooLarge()){
            return Message.error("Piece has been moved too far.");
        }
        return Message.info("Good move!");
    }

    @Override
    public String toString() {
        return start.toString() + ", " + end.toString();
    }
}
