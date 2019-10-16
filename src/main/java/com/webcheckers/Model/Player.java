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
     * Player accessor for their game availability status
     * @return this.inGame: boolean attribute for their availability status
     */
    public boolean isInGame()
    {
        return this.inGame;
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
