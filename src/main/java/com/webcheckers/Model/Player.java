package com.webcheckers.Model;

/**
 * The Player Model of a newly logged in User
 * @author Bryce Thompson : bxt6698@rit.edu
 * @contributor Clayton Pruitt : chp4145@rit.edu
 */
public class Player
{

    // Player Fields
    private String name;
    private boolean inGame;

    // the current opponent for this Player
    private Player opponent;

    // the current color for this Player
    private Piece.Color color;

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
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @return opponent
     */
    public Player getOpponent() { return opponent; }

    /**
     * @return color
     */
    public Piece.Color getColor(){ return this.color; }

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
     */
    public void putInGame(Player opponent, Piece.Color color){
        System.out.println(this.name + " has been put in a game.");
        this.opponent = opponent;
        this.color = color;
        this.inGame = true;
    }

    /**
     * Overriding equals() to compare two Player objects.
     */
    @Override
    public boolean equals(Object o){
        if (!(o instanceof Player)){
            return false;
        }

        Player p = (Player) o;

        return this.getName().equals(p.getName());
    }

}
