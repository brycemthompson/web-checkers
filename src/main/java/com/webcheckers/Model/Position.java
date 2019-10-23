package com.webcheckers.Model;

public class Position {

    private int row;
    private int cell;

    public Position(int row, int cell){
        this.row = row;
        this.cell = cell;
    }

    public int getRow(){
        return this.row;
    }

    public int getCell(){
        return this.cell;
    }

}
