package com.webcheckers.Model;

import com.webcheckers.ui.PostHomeRoute;
import com.webcheckers.util.Message;

import static java.lang.Math.*;

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
     * Static function which reverses the given move.
     * @param move a Move to reverse
     * @return a new Move with the start and end flipped compared to the given Move
     */
    public static Move reverse(Move move){
        return new Move(move.getEnd(), move.getStart());
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
    public Position getEnd(){ return this.end; }

    /**
     * Returns the distance covered by this Move.
     * @return distance between the starting and ending Positions
     */
    public double getDistance(){
        return sqrt(
                pow((end.getCell() - start.getCell()), 2) + pow(end.getRow() - start.getRow(), 2)
        );
    }

    /**
     * Returns the direction of this Move.
     * @return direction of this Move
     */
    public double getDirection(){
        double diff = this.start.getRow() - this.end.getRow();
        return diff/abs(diff);
    }

    /**
     * Helper function that returns whether this Move is too large.
     * @return whether start Position and end Position are too far
     */
    public boolean isMoveTooLarge(){
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
        return start.toString() + " -> " + end.toString();
    }

    // Overriding equals() to check if two Moves are equal by checking if their starting and ending positions are the same.
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
