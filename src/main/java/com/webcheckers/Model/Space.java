package com.webcheckers.Model;

import java.util.ArrayList;

public class Space {
    public int xPos;
    public int yPos;
    public Piece color;
    public Piece typeOfPiece;

    public Space(int x, int y) {
        this.xPos = x;
        this.yPos = y;
    }

    //if the space is unoccupied then return true
    public boolean isValid(){
        return true;
    }

    //Return the coordinates
    public ArrayList<Integer> getCoordinates() {
        ArrayList<Integer> intList = new ArrayList();
        intList.add(xPos, yPos);
        return intList;
    }

}
