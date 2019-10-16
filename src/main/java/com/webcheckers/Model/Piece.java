package com.webcheckers.Model;

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
     * Constructor.
     */
    public Piece(Type type, Color color){
        this.type = type;
        this.color = color;
    }

    /**
     * Getter for type.
     */
    public Type getType(){
        return this.type;
    }

    /**
     * Getter for color.
     */
    public Color getColor(){
        return this.color;
    }
    
}
