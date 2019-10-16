package com.webcheckers.Model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Java class object representing a row of a board of checkers.
 * @author Clayton Pruitt
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
     * Getter for index.
     */
    public int getIndex(){
        return this.index;
    }

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
