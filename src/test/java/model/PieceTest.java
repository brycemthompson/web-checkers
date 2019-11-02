package model;

import com.webcheckers.Model.Piece;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@Tag("Model-Tier")
public class PieceTest {

    public enum Type {
        SINGLE,
        KING
    }

    public enum Color {
        RED,
        WHITE
    }

    /**
     * Piece information
     */
    private Piece.Type type;
    private Piece.Color color;

    /**
     * Constructor for Piece
     * @param type: type of piece (normal or king)
     * @param color: color of piece (red or white)
     */
    public PieceTest(Piece.Type type, Piece.Color color){
        this.type = type;
        this.color = color;
    }

    /**
     * Getter for type
     * @return this.type: the type of the piece
     */
    public Piece.Type getType(){
        return this.type;
    }

    /**
     * Getter for color
     * @return this.color: the color of the piece
     */
    public Piece.Color getColor(){
        return this.color;
    }

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
