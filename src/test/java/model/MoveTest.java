package model;

import com.webcheckers.Model.Position;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static java.lang.Math.abs;
import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-Tier")
public class MoveTest {

    // private fields
    private Position start;
    private Position end;

    // what is defined as "too far" for a move
    private static int tooFarThreshold = 2;

    public MoveTest(Position start, Position end){
        this.start = start;
        this.end = end;
    }

    public Position getStart(){
        return this.start;
    }


    public Position getEnd(){
        return this.end;
    }


    public boolean isMoveTooLarge(){
        int rowDifference = abs(start.getRow() - end.getRow());
        int cellDifference = abs(start.getCell() - end.getCell());
        boolean result = rowDifference > tooFarThreshold || cellDifference > tooFarThreshold;
        return result;
    }

    public Message validityMessage(){
        // check if distance is too large
        if (isMoveTooLarge()){
            return Message.error("Piece has been moved too far.");
        }
        return Message.info("Good move!");
    }


    @Test
    public void test_is_move_too_large_valid(){
        Position start = new Position(1,1);
        Position end = new Position(2,2);
        MoveTest newMove = new MoveTest(start, end);
        boolean isTooLarge = newMove.isMoveTooLarge();
        assertEquals(false, isTooLarge);

    }

    @Test
    public void test_is_move_too_large_not_valid(){
        Position start = new Position(1,1);
        Position end = new Position(4,4);
        MoveTest newMove = new MoveTest(start, end);
        boolean isTooLarge = newMove.isMoveTooLarge();
        assertEquals(true, isTooLarge);

    }

    @Test
    public void validity_message_test_too_large(){
        Position start = new Position(1,1);
        Position end = new Position(4,4);
        MoveTest newMove = new MoveTest(start, end);

        //The move is too large and it should be display an error message;
        assertEquals("Piece has been moved too far.", newMove.validityMessage());

    }

    @Test
    public void validity_message_test_is_not_too_large(){
        Position start = new Position(1,1);
        Position end = new Position(2,2);
        MoveTest newMove = new MoveTest(start, end);
        //The move is too large and it should be display an valid move message;
        assertEquals("Good move!", newMove.validityMessage());

    }


}

