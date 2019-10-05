package com.webcheckers;

import com.webcheckers.Model.Player;

import java.util.ArrayList;

/**
 * The PlayerLobby to handle Sign-In actions
 * @author Bryce Thompson : bxt6698@rit.edu
 */
public class PlayerLobby
{
    //Player Lobby Fields
    ArrayList<Player> players = new ArrayList<>();

    /**
     * PlayerLobby constructor, initialized on application start
     */
    public PlayerLobby()
    {

    }

    /**
     * addPlayer function to add a Player into the lobby
     * @param player : new Player to join the lobby
     */
    public void addPlayer(Player player)
    {
        players.add(player);
    }
}
