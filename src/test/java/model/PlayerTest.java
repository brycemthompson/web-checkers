package model;

/**
 * PlayerTest: Unit Test for Player Class
 * @author Bryce Thompson : bxt6698@rit.edu
 */

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.Model.Piece;
import com.webcheckers.Model.Player;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class PlayerTest
{
    public static final String name = "test-name";
    public static final boolean inGame = false;
    public static final Player opponent = new Player("opponent-test-name");
    public static final Piece.Color color = Piece.Color.RED;

    /**
     * Test that the main constructor works without failure.
     */
//    @Test
//    public void ctor_Arg()
//    {
//        final Player CuT = new Player(name);
//        assertEquals("{Player " + name + "}", CuT.toString());
//    }

    /**
     * Test that the returned name is correct.
     */
    @Test
    public void testGetName()
    {
        final Player CuT = new Player(name);
        assertEquals(name, CuT.getName());
    }

    /**
     * Test that the returned opponent Player is correct.
     */
//    @Test
//    public void testGetOpponent()
//    {
//        Player CuT = opponent;
//        assertEquals(opponent, CuT.getOpponent());
//    }

    /**
     * Test that the returned Player color is correct.
     */
//    @Test
//    public void testGetColor()
//    {
//        final Player CuT = new Player(name);
//        CuT.color.RED;
//        assertEquals(color.RED.toString(), CuT.getColor());
//    }

    /**
     * Test that the initialized player is not in a game to start
     */
    @Test
    public void isInGame()
    {
        final Player CuT = new Player(name);
        assertEquals(inGame, CuT.isInGame());
    }

    /**
     * Test that the equals function properly compares two Player objects
     */
    @Test
    public void equals()
    {
        final Player CuT = new Player(name);
        final Player test_player = new Player(name);
        assertEquals(true, CuT.equals(test_player));
    }


}
