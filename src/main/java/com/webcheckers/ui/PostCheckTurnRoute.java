package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.Model.Board;
import com.webcheckers.Model.Player;
import com.webcheckers.Model.PlayerLobby;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

public class PostCheckTurnRoute implements Route {

    // Various objects the route needs to track.
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    PostCheckTurnRoute(TemplateEngine templateEngine, PlayerLobby playerLobby)
    {
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        //TODO: add a check if the opponent is in a game or not

        // get the current user and their board
        Player currentUser = request.session().attribute(ConstsUI.CURRENT_USER_PARAM);
        Player opponent = currentUser.getOpponent();
        System.out.println(opponent);
        Board currentUserBoard = request.session().attribute(ConstsUI.CURRENT_USER_BOARD_PARAM);
        System.out.println(opponent.isInGame());
        // create a Message depending on whether or not it is this current user's turn
        Message msg = null;
        if(!opponent.isInGame()){
            msg = Message.info("Game Over");
        }
        else if (currentUserBoard.getActiveColor() == currentUser.getColor()){
            msg = Message.info("true");
        } else {
            msg = Message.info("false");
        }

        return new Gson().toJson(msg);
    }
}
