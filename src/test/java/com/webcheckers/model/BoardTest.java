package com.webcheckers.model;

import com.webcheckers.Model.Piece;
import com.webcheckers.Model.Row;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest implements Iterable<Row> {

        ArrayList<Row> rows;

        public static int rowsPerBoard = 8;

        public BoardTest(){
            // populate board with rows
            rows = new ArrayList<Row>();
            for (int i = 0; i < rowsPerBoard; i++){
                rows.add(new Row(i));
            }
        }

        public boolean addPieceToSpace(Piece piece, int cellIdx, int cellIdy) throws IndexOutOfBoundsException{
            this.rows.get(cellIdy).addPieceToSpace(piece, cellIdx);
            return true;
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

    @Test
    public void testBoard_case_legal(){
        //Test for a piece in boundary of board, should be legal
        BoardTest board = new BoardTest();
        Piece pieceTest = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        board.addPieceToSpace(pieceTest, 1, 1);
        assertTrue(true);
    }

    //This Test case should fail, the code is supposed to throw an error. LOOK INTO THIS MORE
    @Test
    public void testBoard_case_illegal(){
        int celldx = 1;
        int celldy = 25;
        //Test for a piece not in boundary, should not be legal
        BoardTest board = new BoardTest();
        Piece pieceTest2= new Piece(Piece.Type.SINGLE, Piece.Color.RED);

        assertThrows(IndexOutOfBoundsException.class, ()->{
            board.addPieceToSpace(pieceTest2, celldx, celldy);
        });
    }

    @Test
    public void testBoard_case_occupied(){
        //Create a board that will hold pieces
        BoardTest board = new BoardTest();

        //Creat two pieces to test if a space is occupied.
        Piece pieceTest3 = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        Piece pieceTest4 = new Piece(Piece.Type.SINGLE, Piece.Color.RED);

        //Add first piece to the board, then make the other piece try and occupy same space
        board.addPieceToSpace(pieceTest3, 1,1);
        board.addPieceToSpace(pieceTest4, 1,1);

    }




}



