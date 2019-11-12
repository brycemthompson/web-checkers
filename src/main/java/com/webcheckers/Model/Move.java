package com.webcheckers.Model;

import com.webcheckers.util.Message;
import static java.lang.Math.abs;

/**
 * Represents a Move on the Board by tracking the start Position and the end Position.
 * @author Clayton Pruitt : chp4145@g.rit.edu
 */

public class Move {

    /**
     *  Private Fields
     */
    private Position start;
    private Position end;

    /**
     * What is defined as too-far of a move
     */
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
     * return: the start Position
     */
    public Position getStart(){
        return this.start;
    }

    /**
     * Getter for end.
     * return: the end Position
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
     * Gets the Move validity message
     * return: a Message describing the validity of the move.
     */
    public Message validityMessage(){
        // check if distance is too large
        if (isMoveTooLarge()){
            return Message.error("Piece has been moved too far.");
        }
        return Message.info("Good move!");
    }

    /**
     * Puts the start and end position into an assembled String
     * @return: the start and end position in a String
     */
    @Override
    public String toString() {
        return start.toString() + " -> " + end.toString();
    }

    /**
     * Overriding equals() to check if two Moves are equal by checking if their starting and ending positions are the
     * same.
     * @param obj: The object to compare to the move.
     * @return: boolean value based on the truth of the comparison
     */
    @Override
    public boolean equals(Object obj) {
        // this Move equals itself
        if (obj == this){
            return true;
        }

        // this Move cannot equal an object that is not a Move
        if (!(obj instanceof Move)){
            return false;
        }

        // two Move are equal if their starting and ending positions are the same
        Move m = (Move) obj;
        return (this.start.equals(m.start) && this.end.equals(m.end));
    }
}
