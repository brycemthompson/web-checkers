package com.webcheckers.Model;

/**
 * The Player Model of a newly logged in User
 * @author Bryce Thompson : bxt6698@rit.edu
 */
public class Player
{

    // Player Fields
    private String userName;

    /**
     * Player Constructor
     * @param userName : Username of a newly logged player
     */
    public Player(String userName)
    {
        this.userName = userName;
    }

    /**
     * @return username
     */
    public String getUserName() {
        return userName;
    }

}
