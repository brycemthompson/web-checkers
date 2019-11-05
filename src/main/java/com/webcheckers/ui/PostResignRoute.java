package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.Model.Board;
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
    private boolean p1Game, p2Game;

    PostResignRoute(Gson gson, PlayerLobby playerLobby)
    {
        this.gson = gson;
        this.playerLobby = playerLobby;
        this.p1Game = false;
        this.p2Game = false;
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
        Player opponent = request.session().attribute(PostPlayerRoute.OPPONENT_PARAM);

        ArrayList<Player> players = playerLobby.getPlayers();
        //System.out.println(playerLobby.boards);
        Board currentPlayerBoard = request.session().attribute(ConstsUI.CURRENT_USER_BOARD_PARAM);

        //System.out.println(playerLobby.boards);
        for(Player player: players)
        {
            if(player.getName().equals(opponent.getName()))
            {
                player.removeFromGame();
                p2Game = true;
            }
            else if(player.getName().equals(currentPlayer.getName()))
            {
                player.removeFromGame();
                p1Game = true;
            }
        }

        currentPlayerBoard.flipActiveColor();
        playerLobby.removeBoard(currentPlayerBoard);

        if (p1Game && p2Game){
            System.out.println("succ");
            return gson.toJson(RESIGN_SUCCESSFUL);
        }
        else{
            System.out.println("fucc");
            return gson.toJson(RESIGN_UNSUCCESSFUL);
        }

    }
}
