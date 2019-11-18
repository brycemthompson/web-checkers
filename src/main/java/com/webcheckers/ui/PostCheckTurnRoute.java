package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.Model.Board;
import com.webcheckers.Model.Player;
import com.webcheckers.Model.PlayerLobby;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;

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
        Board currentUserBoard = request.session().attribute(ConstsUI.CURRENT_USER_BOARD_PARAM);
        Player opponent = request.session().attribute(ConstsUI.OPPONENT_PARAM);


        if(opponent.isInGame()){

        }
        // create a Message depending on whether or not it is this current user's turn
        Message msg = null;
        if(currentUser.opponentHasResigned()){

            // opponent has resigned
            msg = Message.info("true");
        }
        else if (currentUserBoard.getActiveColor() == currentUser.getColor()){
            msg = Message.info("true");
        } else {
            msg = Message.info("false");
        }

        return new Gson().toJson(msg);
    }
}
