package com.webcheckers.Model;

/**
 * The Player Model of a newly logged in User
 * @author Bryce Thompson : bxt6698@rit.edu
 * @contributor Clayton Pruitt : chp4145@rit.edu
 */
public class Player
{

    /**
     * Player Fields
     */
    private String name;
    private boolean inGame;

    /**
     * Player opponent object
     */
    private Player opponent;

    /**
     * Piece Color
     */
    public Piece.Color color;

    /**
     * Player Constructor
     * @param name : Username of a newly logged player
     */
    public Player(String name)
    {
        this.name = name;
        this.inGame = false;
    }

    /**
     * Getter for name
     * @return name: name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for opponent
     * @return opponent: The opponent as a Player object
     */
    public Player getOpponent() { return opponent; }

    /**
     * Getter for color
     * @return this.color: The color of the Player
     */
    public Piece.Color getColor(){ return this.color; }

    /**
     * Checks whether this Player is RED. Used primarily in game.ftl for determining orientation of the board.
     * @return true if Player is RED, false otherwise
     */
    public boolean getIsRed(){ return (this.color == Piece.Color.RED); }

    /**
     * Player accessor for their game availability status
     * @return this.inGame: boolean attribute for their availability status
     */
    public boolean isInGame()
    {
        return this.inGame;
    }

    /**
     * Puts this Player into a game with a given opponent.
     * @param opponent: The current Player's opponent
     * @param color: The color of the current Player
     */
    public void putInGame(Player opponent, Piece.Color color){
        System.out.println(this.name + " has been put in a game.");
        this.opponent = opponent;
        this.color = color;
        this.inGame = true;
    }

    /**
     * toString override that returns Player's username.
     */
    @Override
    public String toString() {
        return ("Player(" + this.name + ")");
    }

    /**
     * Override for equals
     * @param o: Hopefully another Player object to compare to
     * @return boolean value based on the comparison
     */
    @Override
    public boolean equals(Object o){
        // A Player will always be equal to itself.
        if (o == this){
            return true;
        }

        // A Player can only be equal to another Player.
        if (!(o instanceof Player)){
            return false;
        }

        // Two Players are equal if they have the same name.
        Player p = (Player) o;
        return this.getName().equals(p.getName());
    }

}
