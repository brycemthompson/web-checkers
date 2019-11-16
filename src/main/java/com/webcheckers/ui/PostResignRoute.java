package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.Model.Board;
import com.webcheckers.Model.Player;
import com.webcheckers.Model.PlayerLobby;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * The PostResignRoute Class Handling the Resign Button
 *
 * @author Daniel Kitchen : djk9755@rit.edu
 */
public class PostResignRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostResignRoute.class.getName());
    private String resignMessage = "";
    private PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;
    private boolean p1Game, p2Game;


    PostResignRoute(TemplateEngine templateEngine, PlayerLobby playerLobby)
    {
        this.playerLobby = playerLobby;
        this.p1Game = false;
        this.p2Game = false;
        this.templateEngine = templateEngine;
    }

    /**
     * Helper function that resigns the given Player from a game.
     * @param player Player
     */
    public static void resignPlayer(Player player, PlayerLobby playerLobby){
        player.removeFromGame();
        playerLobby.removeBoard(player);

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

        //TODO: Resignation right now is, theoretically, always successful. When is it supposed to fail?
        final Map<String, Object> vm = new HashMap<>();

        // Get the current user and resign them from the game
        Player currentPlayer = request.session().attribute(ConstsUI.CURRENT_USER_PARAM);
        Player opponent = request.session().attribute(ConstsUI.OPPONENT_PARAM);


        // Flip the active user on the board
        Board board = request.session().attribute(ConstsUI.CURRENT_USER_BOARD_PARAM);
        board.flipActiveColor();
        System.out.println("test: " + opponent);
        resignPlayer(currentPlayer, playerLobby);
        opponent.removeFromGame();

        playerLobby.removeBoard(board);

//        opponent.removeFromGame();

        if (p1Game && p2Game){
            return new Gson().toJson(ConstsUI.RESIGN_SUCCESSFUL);
        }
        else{
            return new Gson().toJson(ConstsUI.RESIGN_UNSUCCESSFUL);
        }
        /*
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
         */

    }
}
