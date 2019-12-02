package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.Model.Player;
import com.webcheckers.Model.PlayerLobby;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PostResignGameRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostResignGameRoute.class.getName());

    // Various objects the route needs to track.
    private final TemplateEngine templateEngine;
    private PlayerLobby playerLobby;

    PostResignGameRoute(TemplateEngine templateEngine, PlayerLobby playerLobby)
    {
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        LOG.finer("PostResignGameRoute is invoked.");

        // initialize view-model
        final Map<String, Object> vm = new HashMap<>();
        // get the current user
        Player currentPlayer = request.session().attribute(ConstsUI.CURRENT_USER_PARAM);

        // attempt to resign the Player
        Message msg;
        if (currentPlayer.isInGame()){
            currentPlayer.removeFromGame();
            msg = Message.info("Resignation successful.");
        } else {
            msg = Message.info("Resignation failed.");
        }

        return new Gson().toJson(msg);

    }
}
