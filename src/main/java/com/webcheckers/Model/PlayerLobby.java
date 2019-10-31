package com.webcheckers.Model;

import com.webcheckers.Model.Player;

import java.util.ArrayList;

/**
 * The PlayerLobby to handle Sign-In actions
 * @author Bryce Thompson : bxt6698@rit.edu
 * @contributor Clayton Pruitt : chp4145@rit.edu
 */
public class PlayerLobby
{
    // all players in this lobby
    ArrayList<Player> players;

    // all boards initialized in this lobby
    ArrayList<Board> boards;

    // key for player lobby in the Session HashMap
    public static final String PLAYERLOBBY_KEY = "playerLobby";

    /**
     * PlayerLobby constructor, initialized on application start
     * initializes a new ArrayList<Player>
     */
    public PlayerLobby()
    {
        players = new ArrayList<>();
        boards = new ArrayList<>();
    }

    /**
     * addPlayer function to add a Player into the lobby
     * @param player : new Player to join the lobby
     */
    public void addPlayer(Player player)
    {
        boolean isInList = players.contains(player);
        if(!isInList)
            players.add(player);
    }

    /**
     * addPlayer function overload to add a player by name.
     * @param playerName: the name of the new Player to initialize and put in the lobby
     */
    public void addPlayer(String playerName){
        addPlayer(new Player(playerName));
    }

    /**
     * Gets a Player by their username.
     * @param playerName the username of the Player to get
     * @return the Player with the matching username
     */
    public Player getPlayer(String playerName){
        for (Player player : players){
            if (player.getName().equals(playerName)){
                return player;
            }
        }
        return null;
    }

    /**
     * removePlayer function to remove a Player from the lobby
     * @param player : Player to remove from the lobby
     */
    public void removePlayer(Player player){
        players.remove(player);
    }

    /**
     * Getter for Players
     * @return players: Players in the PlayerLobby
     */
    public ArrayList<Player> getPlayers(){ return players; }

    /**
     * Getter for the Player names
     * @return names: an ArrayList of all Player names in this lobby
     */
    public ArrayList<String> getPlayerNames(){
        ArrayList<String> names = new ArrayList<>();
        for (Player player : players){
            names.add(player.getName());
        }
        return names;
    }

    /**
     * Adds a given Board to the list of Boars tracked by this PlayerLobby.
     * @param board a new Board to track
     */
    public void addBoard(Board board){
        this.boards.add(board);
    }

    /**
     * Finds the Board with the given Players and returns it.
     * @param redPlayer the Board's red Player
     * @param whitePlayer the Board's white Player
     * @return the Board with both the given red and white Players
     */
    public Board getBoard(Player redPlayer, Player whitePlayer){
        // create Board for comparing
        Board b = new Board();
        b.setRedPlayer(redPlayer);
        b.setWhitePlayer(whitePlayer);
        // find the Board we are looking for
        for (Board board : boards){
            if (board.equals(b)){
                return board;
            }
        }
        // no Board found
        return null;
    }

    /**
     * Gets the amount of players in the lobby.
     * @return size of players
     */
    public int size(){
        return players.size();
    }

    /**
     * Authenticates a player's sign-in to the application.
     * @param username a username to authenticate
     * @return Authentication enum for result of the authentication
     */
    public Authentication authenticateSignIn(String username){

        // check if username has at least one alphanumeric character
        int alphanumericChars = 0;
        for (int i = 0; i < username.length(); i++){
            char c = username.charAt(i);
            if (!Character.isDigit(c) && !Character.isLetter(c) && !Character.isSpace(c)){
                return Authentication.FAIL_INVALID_USERNAME;
            } else {
                alphanumericChars++;
            }
        }
        if (alphanumericChars == 0){
            return Authentication.FAIL_INVALID_USERNAME;
        }
        else if(username.contains("\"")){
            return Authentication.FAIL_INVALID_USERNAME;
        }
        // check for other players with the same username
        for (Player player : players){
            if (player.getName().equals(username)) {
                return Authentication.FAIL_NAME_TAKEN;
            }
        }

        // authentication successful
        return Authentication.SUCCESS;
    }

}
