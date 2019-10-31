package com.webcheckers.Model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Java class object representing the board.
 * @author Clayton Pruitt : chp4145@rit.edu
 * @contributor Bryce Thompson : bxt6698@rit.edu
 */

public class Board implements Iterable<Row> {

    // all rows in the board
    ArrayList<Row> rows;
    private Move backupMove;

    // amount of rows in a board
    public static int rowsPerBoard = 8;

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

    /**
     * Adds the given Piece to the given coordinates.
     */
    public  void addPieceToSpace(Piece piece, int cellIdx, int cellIdy){
        this.rows.get(cellIdy).addPieceToSpace(piece, cellIdx);
    }

    /**
     * Gets the Space with the given coordinates.
     * @param row the row the Space is in
     * @param cell the cell the Space is in
     * @return Space instance
     */
    public Space getSpace(int row, int cell){
        return this.rows.get(row).getSpace(cell);
    }

    /**
     * Calculates all possible and valid Moves that can be made between both Players.
     * @return array list containing all valid Moves
     */
    public ArrayList<Move> getAllValidMoves(){

        // scan each row and collect all spaces with pieces
        ArrayList<Space> allStartingSpaces = new ArrayList<Space>();
        for (Row row : rows){
            for (Space space : row){
                if (space.hasPiece()){
                    allStartingSpaces.add(space);
                }
            }
        }

        // for all valid starting spaces, find all valid end spaces

        

        return null;
    }

    /**
     * Setter for the creation of a backup move for the purposes of undoing a move before a submission
     * @param move: The move to put in backupMove
     */
    public void setBackupMove(Move move)
    {
        this.backupMove = move;
    }

    /**
     * Getter for the backupMove to return the backupMove for the purpose to returning the moved piece to the original
     * location
     * @return backupMove: The Move to move the Piece back to before a submission is sent
     */
    public Move getBackupMove()
    {
        return this.backupMove;
    }

    /**
     * Override iterator function. Contains other functions for the iterator
     * @return it: The new iterator
     */
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
