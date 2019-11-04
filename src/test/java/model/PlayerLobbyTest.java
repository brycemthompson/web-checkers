package model;

import com.webcheckers.Model.Authentication;
import com.webcheckers.Model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-Tier")
public class PlayerLobbyTest {

    ArrayList<Player> players;

    public PlayerLobbyTest()
    {
        players = new ArrayList<>();
    }

    @Test
    public void addPlayer_test(Player player)
    {
        boolean isInList = players.contains(player);
        if(!isInList)
            players.add(player);
        assertTrue(Arrays.asList(players).contains(player));
    }


    @Test
    public void addPlayer_test(){
        String playerName = "Isaias";
        assertNotNull(new Player(playerName));
        addPlayer_test(new Player(playerName));

    }

    /**
     * Gets a Player by their username.
     * @param playerName the username of the Player to get
     * @return the Player with the matching username
     */
    @Test
    public void getPlayer_test(String playerName){
        for (Player player : players){

            String pName = player.getName();
            assertNotNull(player);
            assertTrue(pName instanceof String);

            if (player.getName().equals(playerName)){
                assertEquals(pName, playerName);
            }

        }
    }

    /**
     * removePlayer function to remove a Player from the lobby
     * @param player : Player to remove from the lobby
     */
    @Test
    public void removePlayer_test(Player player){
        assertEquals(1, players.size());
        players.remove(player);
        assertEquals(0, players.size());
    }

    /**
     * Getter for Players
     * @return players: Players in the PlayerLobby
     */
    @Test
    public void getPlayers_test(){
        ArrayList<Player> mockArrayList = new ArrayList<>();
        mockArrayList.add(new Player("Isaias"));
        assertEquals(mockArrayList, players);
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

    //THIS TEST IS FAILING FIX LATER
    @Test
    public void size_test(){
        ArrayList<Player> mockArrayList = new ArrayList<>();
        mockArrayList.add(new Player("Isaias"));
        assertEquals(1, players.size());
    }

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
