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
     * @return players
     */
    public ArrayList<Player> getPlayers() { return players; }

    /**
     * this is me trying to trick nav-bar.ftl into taking a username (clayton)
     */
    public String getUsername(){
        String username = players.get(0).getUserName();
        if (username == null){
            return "Harry";
        } else {
            return username;
        }
    }
}
