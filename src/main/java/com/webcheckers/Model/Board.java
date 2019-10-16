package com.webcheckers.Model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Java class object representing the board.
 * @author Clayton Pruitt
 */

public class Board implements Iterable<Row> {

    // all rows in the board
    ArrayList<Row> rows;

    // amount of rows in a board
    int rowsPerBoard = 8;

    /**
     * Constructor. Automatically populates the board with 8 rows.
     */
    public Board(){
        // populate board with rows
        rows = new ArrayList<Row>();
        for (int i = 0; i < rowsPerBoard; i++){
            rows.add(new Row(i));
        }
    }

    @Override
    public Iterator<Row> iterator() {
        Iterator<Row> it = new Iterator<Row>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext(){
                return (currentIndex < rows.size() && rows.get(currentIndex) != null);
            }

            @Override
            public Row next(){
                return rows.get(currentIndex++);
            }

            @Override
            public void remove(){
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }
}
