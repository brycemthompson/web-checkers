package model;

import com.webcheckers.Model.Move;
import com.webcheckers.Model.Position;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static java.lang.Math.*;
import static java.lang.Math.pow;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test for the Move Class
 */
@Tag("Model-Tier")
public class MoveTest {

    /**
     * Private fields
     */
    @Test
    public void test_is_move_too_large_valid(){
        Position start = new Position(1,1);
        Position end = new Position(2,2);
        Move newMove = new Move(start, end);
        boolean isTooLarge = newMove.isMoveTooLarge();
        assertEquals(false, isTooLarge);

    }

    /**
     * Helper function to check if the given Move is invalid (too many spaces away)
     * @return result: whether the Move is valid or not
     */
    @Test
    public void test_is_move_too_large_not_valid(){
        Position start = new Position(1,1);
        Position end = new Position(4,4);
        Move newMove = new Move(start, end);
        boolean isTooLarge = newMove.isMoveTooLarge();
        assertEquals(true, isTooLarge);

    }




    @Test
    public void validity_message_test_too_large(){
        Position start = new Position(1,1);
        Position end = new Position(4,4);
        Move newMove = new Move(start, end);
        Message msg = new Message("Piece has been moved too far.", Message.Type.ERROR);
        //The move is too large and it should be display an error message;
        assertEquals(msg.toString(), newMove.validityMessage().toString());

    }

    /**
     * Test function that tests for the appropriate Too Large Message is being displayed when the validity returns that
     * the Move IS too large
     */
    @Test
    public void validity_message_test_is_not_too_large(){
        Position start = new Position(1,1);
        Position end = new Position(2,2);
        Move newMove = new Move(start, end);
        Message msg = new Message("Good move!", Message.Type.INFO);

        //The move is too large and it should be display an valid move message;
        assertEquals(msg.toString(), newMove.validityMessage().toString());

    }

    @Test
    public void get_start_test(){
        Position start = new Position(1,1);
        Position end = new Position(2,2);
        Move newMove = new Move(start, end);
        //The move is too large and it should be display an valid move message;
        assertEquals(start, newMove.getStart());

    }

    @Test
    public void get_end_test(){
        Position start = new Position(1,1);
        Position end = new Position(2,2);
        Move newMove = new Move(start, end);
        //The move is too large and it should be display an valid move message;
        assertEquals(end, newMove.getEnd());
    }


    @Test
    public void get_distance_test(){
        Position start = new Position(4,4);
        Position end = new Position(2,2);
        Move newMove = new Move(start, end);
        double val = sqrt(pow((end.getCell() - start.getCell()), 2) + pow(end.getRow() - start.getRow(), 2));
        assertEquals(val, newMove.getDistance());
    }

    @Test
    public void get_direction_test(){
        Position start = new Position(4,4);
        Position end = new Position(2,2);
        Move newMove = new Move(start, end);

        int diff = start.getRow() - end.getRow();
        double val = diff/abs(diff);
        System.out.println(diff);
        //The move is too large and it should be display an valid move message;
        assertEquals(val, newMove.getDirection());
    }

}

