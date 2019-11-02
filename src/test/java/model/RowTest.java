package model;

import com.webcheckers.Model.Piece;
import com.webcheckers.Model.Row;
import com.webcheckers.Model.Space;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("Model-Tier")
public class RowTest {


    @Test
    public void getIndex_test(){
        Row row = new Row(5);
        assertEquals(5, row.getIndex());
    }

    @Test
    public void getSpace_test(){
        int indexOfSpace = 5;

        Row row = new Row(indexOfSpace);
        Space sp = row.getSpace(indexOfSpace);
        assertEquals(5, sp.getCellIdx());
        assertEquals(5,sp.getCellIdy());
    }

}

