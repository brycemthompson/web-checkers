package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.Model.Player;
import com.webcheckers.Model.PlayerLobby;
import com.webcheckers.util.Message;
import spark.*;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * The PostResignRoute Class Handling the Resign Button
 *
 * @author Daniel Kitchen : djk9755@rit.edu
 */
public class PostResignRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostResignRoute.class.getName());

    //messages
    private static final Message RESIGN_SUCCESSFUL = Message.info("Resigned Successfully!");
    private static final Message OTHER_PLAYER_RESIGNED = Message.info("Other player resigned from the game!");
    private static final Message RESIGN_UNSUCCESSFUL = Message.info("Resign Unsuccessful.  Either backup from your " +
            "move or wait until your next turn and try again.");

    private final Gson gson;
    private PlayerLobby playerLobby;

    PostResignRoute(Gson gson, PlayerLobby playerLobby)
    {
        this.gson = gson;
        this.playerLobby = playerLobby;
    }

    /**
     * Handle the Resign button request and try to send the player back to home.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Sign In page
     */
    @Override
    public Object handle(Request request, Response response) throws Exception
    {
        LOG.finer("PostResignRoute is invoked.");


        // Get the current user and the opponent
        Player currentPlayer = request.session().attribute(PostPlayerRoute.CURRENTUSER_PARAM);

        // Finding the opponent in the playerList
        final String opponentUsername = request.queryParams("opponentUsername");

        ArrayList<Player> players = playerLobby.getPlayers();

        for(Player player: players)
        {
            System.out.println(opponentUsername);
            System.out.println(currentPlayer);
            if(player.getName().equals(opponentUsername))
            {
                player.removeFromGame();
                System.out.println("Got Here");
            }
            else if(player.getName().equals(currentPlayer.getName()))
            {
                player.removeFromGame();
                System.out.println("Got here too");
            }
        }

        return gson.toJson(RESIGN_SUCCESSFUL);
    }
}
