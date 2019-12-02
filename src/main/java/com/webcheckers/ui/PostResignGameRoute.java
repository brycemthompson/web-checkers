package com.webcheckers.ui;

import com.webcheckers.Model.PlayerLobby;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

public class PostResignGameRoute implements Route {

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
        return null;
    }
}
