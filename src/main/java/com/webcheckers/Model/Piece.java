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

    /**
     * Constructor for Piece
     * @param type: type of piece (normal or king)
     * @param color: color of piece (red or white)
     */
    public Piece(Type type, Color color){
        this.type = type;
        this.color = color;
    }

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
