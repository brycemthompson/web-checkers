package com.webcheckers.Model;

import com.webcheckers.Model.Player;

import java.util.ArrayList;

/**
 * The PlayerLobby to handle Sign-In actions
 * @author Bryce Thompson : bxt6698@rit.edu
 */
public class PlayerLobby
{
    //Player Lobby Fields
    ArrayList<Player> players;

    // key for player lobby in the Session HashMap
    public static final String PLAYERLOBBY_KEY = "playerLobby";

    /**
     * PlayerLobby constructor, initialized on application start
     * initializes a new ArrayList<Player>
     */
    public PlayerLobby()
    {
        players = new ArrayList<>();
    }

    /**
     * addPlayer function to add a Player into the lobby
     * @param player : new Player to join the lobby
     */
    public void addPlayer(Player player)
    {
        players.add(player);
    }

    /**
     * addPlayer function overload to add a player by name.
     * @param playerName: the name of the new Player to initialize and put in the lobby
     */
    public void addPlayer(String playerName){ players.add(new Player(playerName)); }

    /**
     * Checks if any players exist with the given username. If not, add the given player to
     * the list of signed-in players.
     * @param username a username to authenticate
     * @return boolean for whether sign in was successful
     */
    public boolean authenticateSignIn(String username){
        // check for other players with the same username
        for (Player player : players){
            if (player.getName() == null){ //TODO: why are there null names?
                continue;
            }
            if (player.getName().equals(username)) {
                return false;
            }
        }
        // authentication successful; sign in the new player
        this.addPlayer(username);
        return true;
    }

}
