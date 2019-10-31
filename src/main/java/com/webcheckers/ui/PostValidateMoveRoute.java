package com.webcheckers.ui;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.webcheckers.Model.*;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.ArrayList;

public class PostValidateMoveRoute implements Route {

    // Various objects the route needs to track.
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    PostValidateMoveRoute(TemplateEngine templateEngine, PlayerLobby playerLobby)
    {
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        // get the Move to validate from the Request
        String json = request.queryParams(ConstsUI.ACTION_DATA_PARAM);
        Move move = new Gson().fromJson(json, Move.class);
        System.out.println("The move: " + move);

        // get current user's board and color
        Board currentBoard = request.session().attribute(ConstsUI.CURRENT_USER_BOARD_PARAM);
        Player currentPlayer = request.session().attribute(ConstsUI.CURRENTUSER_PARAM);
        Piece.Color currentPlayerColor = currentPlayer.getColor();

        // get list of valid moves
        ArrayList<Move> validMoves = currentBoard.getAllValidMoves(currentPlayerColor);

        // check if the Move given is in the list of valid moves
        Message validationMessage = null;
        if (validMoves.contains(move)){
            // if a move is valid, add it to the list of proposed moves
            ArrayList<Move> proposedMoves = request.session().attribute(ConstsUI.PROPOSED_MOVES_PARAM);
            if (proposedMoves == null){
                proposedMoves = new ArrayList<Move>();
            }
            proposedMoves.add(move);
            request.session().attribute(ConstsUI.PROPOSED_MOVES_PARAM, proposedMoves);

            // set validation message
            validationMessage = Message.info("Good move!");
        } else {
            validationMessage = Message.error("Piece has been moved too far.");
        }

        return new Gson().toJson(validationMessage);
    }
}
