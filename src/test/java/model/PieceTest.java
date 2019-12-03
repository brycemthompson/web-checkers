package model;

import com.webcheckers.Model.Piece;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit Test for the Piece Class
 */
@Tag("Model-Tier")
public class PieceTest {

    /**
     * Test function to test that a SINGLE Type piece is created correctly
     */
    @Test
    public void create_piece_test_type(){

        Piece piece = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        assertEquals("SINGLE", piece.getType().name());
    }

    /**
     * Test function to test that an instantiated Piece when created is not null
     */
    @Test
    public void create_piece_not_null(){

        Piece piece = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        assertNotNull(piece);
    }

    /**
     * Test function that tests that the Red Piece color is held correctly when defined during instantiation
     */

    @Test
    public void create_piece_test_color_red(){

        Piece piece = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        assertEquals("RED", piece.getColor().name());
    }

    /**
     * Test function that tests that the White Piece color is held correctly when defined during instantiation
     */
    @Test
    public void create_piece_test_color_white(){

        Piece piece = new Piece(Piece.Type.SINGLE, Piece.Color.WHITE);
        assertEquals("WHITE", piece.getColor().name());
    }

}
