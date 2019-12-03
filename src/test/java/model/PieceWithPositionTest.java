package model;
import com.webcheckers.Model.Piece;
import com.webcheckers.Model.PieceWithPosition;
import com.webcheckers.Model.Position;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 This class is going to test whether the function created in the original code was properly
 created and returns the correct values
 */
@Tag("Model")
public class PieceWithPositionTest {

    /**
        This test is going to test whether or not the piece object
        created matches the object that gets returned
        when the getPiece function is called from the original code.
     */
   @Test
    public void get_piece_tes(){
        Piece p  = new Piece(Piece.Type.SINGLE, Piece.Color.WHITE);
        PieceWithPosition piece = new PieceWithPosition(p, new Position(2,3));
        assertTrue(piece instanceof PieceWithPosition);
        assertEquals(p, piece.getPiece());
    }

    /**
     This test is going to test against the original code. It will use a sample created object such as
     pos, which will be compared to a PieceWithPositionn's getPosition Piece object.
     */
    @Test
    public void get_piece_position(){
        Piece p  = new Piece(Piece.Type.SINGLE, Piece.Color.WHITE);
        Position pos =  new Position(2,3);
        PieceWithPosition piece = new PieceWithPosition(p, pos);
        assertEquals(pos, piece.getPosition());
    }
    /**
     This test test the original code's getRow function. A sample position object was created and
     it being tested against the a PieceWithPosition Object's getRow function
     */
    @Test
    public void get_row_test(){
        Piece p  = new Piece(Piece.Type.SINGLE, Piece.Color.WHITE);
        Position pos =  new Position(2,3);

        PieceWithPosition piece = new PieceWithPosition(p, pos);

        assertEquals(pos.getRow(), piece.getRow());
    }
    /**
     This test test the original code's getCell function. A sample position object was created and
     it being tested against the a PieceWithPosition Object's getCell function
     */
    @Test
    public void get_cell_test(){
        Piece p  = new Piece(Piece.Type.SINGLE, Piece.Color.WHITE);
        Position pos =  new Position(2,3);

        PieceWithPosition piece = new PieceWithPosition(p, pos);

        assertEquals(pos.getCell(), piece.getCell());
    }
    /**
     This test is simply testing whether or not the two objects, in this case,
     two PieceWithPosition objects are the same or not
     */
    @Test
    public void equal_test(){
        Piece p  = new Piece(Piece.Type.SINGLE, Piece.Color.WHITE);
        Position pos =  new Position(2,3);

        PieceWithPosition piece = new PieceWithPosition(p, pos);
        PieceWithPosition newPiece= new PieceWithPosition(p, pos);

        assertTrue(piece.equals(newPiece));
    }

}
