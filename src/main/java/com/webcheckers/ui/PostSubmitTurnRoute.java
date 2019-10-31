package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.Model.*;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PostSubmitTurnRoute implements Route {

    private final TemplateEngine templateEngine;
    private final PlayerLobby playLobby;
    private final Logger LOG = Logger.getLogger(PostSubmitTurnRoute.class.getName());

    public PostSubmitTurnRoute(TemplateEngine templateEngine, PlayerLobby playerLobby)
    {
        this.templateEngine = templateEngine;
        this.playLobby = playerLobby;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.finer("PostSubmitTurnRoute invoked");

        //TODO: In Feature-SimpleMove, submitTurn will always be valid. Not the case for future branches!

        // get list of proposed move sequence as well as our board
        ArrayList<Move> proposedMoves = request.session().attribute(ConstsUI.PROPOSED_MOVES_PARAM);
        Board board = request.session().attribute(ConstsUI.CURRENT_USER_BOARD_PARAM);

        // as we are assuming all moves are currently valid, process them all
        for (Move move : proposedMoves){
            board.movePiece(move);
        }

        return new Gson().toJson(Message.info("Turn submitted."));

        /*
        final Map<String, Object> vm = new HashMap<>();
        vm.put(ConstsUI.TITLE_PARAM, ConstsUI.GAME_WELCOME_MSG);

        // Here is where we call the functions to check the validity and determine whether we are going to
        // submit the turn or not
        Message msg = request.session().attribute(ConstsUI.MESSAGE_PARAM);
        if(msg != null)
        {
            if(msg == Message.info("Good move!"))
            {
                vm.put(ConstsUI.MESSAGE_PARAM, ConstsUI.SUBMIT_SUCCESSFUL_MSG);
                // Send the move here
            }
            else // Jump move required, thus error message
            {
                vm.put(ConstsUI.MESSAGE_PARAM, ConstsUI.JUMP_REQUIRED_ERROR_MSG);
                // The user must back up the move
            }
        }

        return templateEngine.render(new ModelAndView(vm, ConstsUI.GAME_VIEW));
         */
    }
}
