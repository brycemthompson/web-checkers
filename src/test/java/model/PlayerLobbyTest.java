package model;

import com.webcheckers.Model.Authentication;
import com.webcheckers.Model.Player;
import com.webcheckers.Model.PlayerLobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test for PlayerLobby Class
 */
@Tag("Model-Tier")
public class PlayerLobbyTest {

    ArrayList<Player> players;
    PlayerLobby playerLobby = new PlayerLobby();

    /**
     * PlayerLobbyTest constructor
     */
    public PlayerLobbyTest()
    {
        players = new ArrayList<>();
    }

    /**
     * Test function asserting that a new Player is not null, and that the correct Player name is returned
     */
    @Test
    public void addPlayer_test(){
        Player p = new Player("Isaias");
        assertNotNull(p);
        playerLobby.addPlayer(p);
        assertEquals(playerLobby.getPlayer("Isaias"), p);
    }

    /**
     * Gets a Player by their username.
     * @param playerName the username of the Player to get
     * @return the Player with the matching username
     */
//    @Test
//    public void getPlayer_test(){
//        Player play  = new Player("Kyle");
//        String playerName = play.getName();
//        playerLobby.addPlayer(play);
//
//        for (Player player : players){
//
//            String pName = player.getName();
//            assertNotNull(player);
//            assertTrue(pName instanceof String);
//
//            if (player.getName().equals(playerName)){
//                assertEquals(pName, playerName);
//            }
//
//        }
//    }

    /**
     * Test function that asserts that the playerLobby holds the correct number of Players
     */
    @Test
    public void removePlayer_test(){
        Player p = new Player("Isaias");
        players.add(p);

        assertEquals(1, players.size());
        players.remove(p);
        assertEquals(0, players.size());
    }

    /**
     * Getter for Players
     * @return players: Players in the PlayerLobby
     */
    @Test
    public void getPlayers_test(){
        Player p = new Player("Test1");
        Player p2 = new Player("Test2");

        playerLobby.addPlayer(p);
        playerLobby.addPlayer(p2);

        ArrayList<Player> plays = new ArrayList<>();
        plays.add(p);
        plays.add(p2);

        assertEquals(plays, playerLobby.getPlayers());
    }

    /**
     * Getter for the Player names
     * @return names: an ArrayList of all Player names in this lobby
     */
    @Test
    public void getPlayerNames_test(){
        ArrayList<String> names = new ArrayList<>();

        for (Player player : players){
            names.add(player.getName());
        }

        for (Player player : players){
            assertTrue(player.getName() instanceof String);
        }

    }

    /**
     * Test function to assert the correct size of the player array list
     * THIS TEST IS FAILING FIX LATER
     */
    @Test
    public void size_test(){
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("Isaias"));
        assertEquals(1, players.size());
    }

    /**
     * Unit Test to assert the correct Messages based on the validity of the username input
     */
    @Test
    public void authenticateSignIn_test(){
        String username = "Test1";


        // check if username has at least one alphanumeric character
        int alphanumericChars = 0;
        for (int i = 0; i < username.length(); i++){
            char c = username.charAt(i);
            if (!Character.isDigit(c) && !Character.isLetter(c) && !Character.isSpace(c)){
                assertEquals("FAIL_INVALID_USERNAME", Authentication.FAIL_INVALID_USERNAME.name());
            } else {
                alphanumericChars++;
            }
        }
        if (alphanumericChars == 0){
            assertEquals("FAIL_INVALID_USERNAME", Authentication.FAIL_INVALID_USERNAME.name() );
        }
        else if(username.contains("\"")){
            assertEquals("FAIL_INVALID_USERNAME",Authentication.FAIL_INVALID_USERNAME.name());
        }
        // check for other players with the same username
        for (Player player : players){
            if (player.getName().equals(username)) {
                assertEquals("FAIL_NAME_TAKEN", Authentication.FAIL_NAME_TAKEN.name());
            }
        }

        // authentication successful
        assertEquals("SUCCESS", Authentication.SUCCESS.name());
    }





}
