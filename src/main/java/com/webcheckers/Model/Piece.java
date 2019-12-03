package com.webcheckers.Model;

/**
 * Java class object representing a piece on a board of checkers.
 * @author Clayton Pruitt : chp4145@rit.edu
 * @author Isaias Villalobos
 * @contributor Bryce Thompson : bxt6698@rit.edu
 */

public class Piece {

    /**
     * Enums
     */
    public enum Type {
        SINGLE,
        KING
    }

    public enum Color {
        RED,
        WHITE
    }


    /**
     * Piece information
     */
    private Type type;
    private Color color;
    // the direction that is considered "forward" for this piece (either 1 or -1)
    private int forwardDirection;

    /**
     * Constructor for Piece
     * @param type: type of piece (normal or king)
     * @param color: color of piece (red or white)
     */
    public Piece(Type type, Color color){
        this.type = type;
        this.color = color;

        if (color == Color.RED){
            this.forwardDirection = 1;
        } else {
            this.forwardDirection = -1;
        }

    }

    /**
     * Kings this piece.
     */
    public void kingMe(){
        this.type = Type.KING;
    }

    /**
     * Check if this Piece is a King.
     * @return boolean for whether this Piece's Type is King
     */
    public boolean isKing() { return this.type == Type.KING; }

    /**
     * Getter for type
     * @return this.type: the type of the piece
     */
    public Type getType(){
        return this.type;
    }

    /**
     * Getter for color
     * @return this.color: the color of the piece
     */
    public Color getColor(){
        return this.color;
    }

    /**
     * Getter for forward direction.
     * @return this.forwardDirection: the direction this Piece considers to be "forward"
     */
    public int getForwardDirection(){
        return this.forwardDirection;
    }


    /**
     * Returns the Color opposite of what was given.
     * @param color a given Color
     * @return the Color opposite the given Color
     */
    public static Color getOtherColor(Color color) {
        if (color == Color.RED){
            return Color.WHITE;
        } else {
            return Color.RED;
        }
    }

}
