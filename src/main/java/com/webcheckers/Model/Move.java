package com.webcheckers.Model;

import com.webcheckers.ui.PostHomeRoute;

/**
 * Represents a Move on the Board by tracking the start Position and the end Position.
 * @author Clayton Pruitt : chp4145@g.rit.edu
 */

public class Move {

    // private fields
    private Position start;
    private Position end;

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

}
