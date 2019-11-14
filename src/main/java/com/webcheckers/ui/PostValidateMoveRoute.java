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
        ArrayList<MovePacket> validMoves = currentBoard.getAllValidMoves(currentPlayerColor);

        // find the move in the move packets
        Message validationMessage = null;
        MovePacket movePacket = null;
        for (MovePacket mp : validMoves){
            if (mp.getMove().equals(move)){
                movePacket = mp;
                validationMessage = Message.info("Move successful.");
            }
        }

        // if validationMessage is null, the move is invalid
        if (validationMessage == null){
            if (validMoves.get(0).getType() == MovePacket.Type.SIMPLE_JUMP) {
                validationMessage = Message.error("You must jump a piece.");
            } else if (validMoves.get(0).getType() == MovePacket.Type.MULTIPLE_JUMP){
                validationMessage = Message.error("You must jump multiple pieces.");
            } else {
                validationMessage = Message.error("Move is too far.");
            }
            return new Gson().toJson(validationMessage);
        }

        currentBoard.movePiece(movePacket);
        if (movePacket.getJumpedPiece() != null){
            Position jumpedPiecePosition = movePacket.getJumpedPiece().getPosition();
            System.out.println("Removing " + jumpedPiecePosition);
            currentBoard.removePieceFromSpace(jumpedPiecePosition.getCell(), jumpedPiecePosition.getRow());
        }

        /*
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

            // get list of proposed move sequence as well as our board
            Board board = request.session().attribute(ConstsUI.CURRENT_USER_BOARD_PARAM);

            // Move the piece here so we can backup if requested
            board.movePiece(move);
        } else {
            validationMessage = Message.error("Piece has been moved too far.");
        }
         */

        return new Gson().toJson(validationMessage);
    }
}
