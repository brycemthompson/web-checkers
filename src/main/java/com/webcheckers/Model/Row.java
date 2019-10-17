package com.webcheckers.Model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Java class object representing a row of a board of checkers.
 * @author Clayton Pruitt : chp4145@rit.edu
 * @contributor Bryce Thompson : bxt6698@rit.edu
 */

public class Row implements  Iterable<Space> {

    // all spaces in this given row
    ArrayList<Space> spaces;
    // y coordinate of this row
    private int index;

    // amount of spaces in a row
    private int spacesPerRow = 8;

    /**
     * Constructor. Automatically populates the Row with 8 spaces.
     * @param index: Index of the row on the board
     */
    public Row(int index){
        this.index = index;
        // populate the row
        this.spaces = new ArrayList<Space>();
        for (int i = 0; i < spacesPerRow; i++){
            spaces.add(new Space(i, index, null));
        }
    }

    /**
     * Getter for the index
     * @return index
     */
    public int getIndex(){
        return this.index;
    }

    /**
     * Adds a given Piece to the Space with the given cellIdx.
     * @param piece: The Piece to be added to the Space
     * @param cellIdx: The cellIdx
     */
    public void addPieceToSpace(Piece piece, int cellIdx){
        this.spaces.get(cellIdx).addPieceToSpace(piece);
    }

    /**
     * Override function for iterator
     * @return it
     */
    @Override
    public Iterator<Space> iterator() {
        Iterator<Space> it = new Iterator<Space>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext(){
                return (currentIndex < spaces.size() && spaces.get(currentIndex) != null);
            }

            @Override
            public Space next(){
                return spaces.get(currentIndex++);
            }

            @Override
            public void remove(){
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }
}
