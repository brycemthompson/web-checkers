package model;

import com.webcheckers.Model.Piece;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@Tag("Model-Tier")
public class PieceTest {

    @Test
    public void create_piece_test_type(){

        Piece piece = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        assertEquals("SINGLE", piece.getType().name());
    }

    @Test
    public void create_piece_not_null(){

        Piece piece = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        assertNotNull(piece);
    }

    @Test
    public void create_piece_test_color_red(){

        Piece piece = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        assertEquals("RED", piece.getColor().name());
    }

    @Test
    public void create_piece_test_color_white(){

        Piece piece = new Piece(Piece.Type.SINGLE, Piece.Color.WHITE);
        assertEquals("WHITE", piece.getColor().name());
    }

}
