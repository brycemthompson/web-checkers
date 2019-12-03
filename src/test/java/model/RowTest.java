package model;

import com.webcheckers.Model.Piece;
import com.webcheckers.Model.Row;
import com.webcheckers.Model.Space;

import net.bytebuddy.build.ToStringPlugin;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
/**
 * Unit Test for the Row Class
 */
@Tag("Model-Tier")
public class RowTest {
    /**
     * Test function to check that the getIndex function returns the correct row index
     */
    @Test
    public void getIndex_test(){
        Row row = new Row(5);
        assertEquals(5, row.getIndex());
    }
    /**
     * Test function to check that the getSpace function returns the correct space in the row
     */
    @Test
    public void getSpace_test(){
        int indexOfSpace = 5;

        Row row = new Row(indexOfSpace);
        Space sp = row.getSpace(indexOfSpace);
        assertEquals(5, sp.getCellIdx());
        assertEquals(5,sp.getCellIdy());
    }
    /**
     * Test for constructor of Row
     */
    @Test
    public void Row_Test(){
        Row row = new Row(4);
        assertNotNull(row);
    }
    /**
     * Make sure that adding a piece to the space is done, and nothing breaks
     */
    @Test
    public void addPieceToSpace_test(){
        Row row = new Row(4);
        Piece p = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        row.addPieceToSpace(p, 4);
        assertNotNull(row);
    }



}

