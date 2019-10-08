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

    /**
     * Player Constructor
     * @param name : Username of a newly logged player
     */
    public Player(String name)
    {
        this.name = name;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

}
