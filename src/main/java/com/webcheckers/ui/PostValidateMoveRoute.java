package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.Model.Move;
import com.webcheckers.Model.PlayerLobby;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

public class PostValidateMoveRoute implements Route {

    // Various objects the route needs to track.
    private final TemplateEngine templateEngine;

    PostValidateMoveRoute(TemplateEngine templateEngine)
    {
        this.templateEngine = templateEngine;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        // get the Move to validate from the Request
        String json = request.queryParams(ConstsUI.ACTION_DATA_PARAM);
        Move move = new Gson().fromJson(json, Move.class);

        // get validation names
        Message validationMessage = move.validityMessage();

        return new Gson().toJson(validationMessage);
    }
}
