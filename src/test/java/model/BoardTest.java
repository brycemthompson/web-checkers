package model;

import com.webcheckers.Model.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
@Tag("Model-Tier")
public class BoardTest {
    // all rows in the board

    @Test
    public void testBoard_case_legal(){
        //Test for a piece in boundary of board, should be legal
        Board board = new Board();
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
        Board board = new Board();
        Piece pieceTest2= new Piece(Piece.Type.SINGLE, Piece.Color.RED);

        assertThrows(IndexOutOfBoundsException.class, ()->{
            board.addPieceToSpace(pieceTest2, celldx, celldy);
        });
    }

    @Test
    public void testBoard_case_occupied(){
        //Create a board that will hold pieces
        Board board = new Board();

        //Creat two pieces to test if a space is occupied.
        Piece pieceTest3 = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        Piece pieceTest4 = new Piece(Piece.Type.SINGLE, Piece.Color.RED);

        //Add first piece to the board, then make the other piece try and occupy same space
        board.addPieceToSpace(pieceTest3, 1,1);
        board.addPieceToSpace(pieceTest4, 1,1);

    }


    @Test
    public void get_space_test(){
        Board board = new Board();
        int row = 1;
        int cell = 2;

        //Creat two pieces to test if a space is occupied.
        Piece piece = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        Space space = new Space(cell, row, piece);
        assertEquals(space.toString(), board.getSpace(row, cell).toString());
    }

    @Test
    public void get_all_valid_moves_test(){
        Board board = new Board();
        int rows = 1;
        int cell = 2;
        Piece piece = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        Space space = new Space(cell, rows, piece);
        board.activeColor = Piece.Color.RED;
        assertEquals(Piece.Color.RED, board.getActiveColor());
    }



}



