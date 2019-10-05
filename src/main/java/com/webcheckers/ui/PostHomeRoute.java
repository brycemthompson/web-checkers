package com.webcheckers.ui;

import com.webcheckers.PlayerLobby;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.Objects;

/**
 * The {@code POST /home} route handler.
 *
 * @author Clayton Pruitt
 * @author Isaias Villaloobos
 */
public class PostHomeRoute implements Route {

    //
    // Attributes
    //

    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;

    /**
     * The constructor for the {@code POST /home} route handler.
     *
     * @param playerLobby
     *      {@Link PlayerLobby} that holds player data.
     * @param templateEngine
     *      template engine to use for rendering HTML page
     *
     * @throws NullPointerException
     *      when the {@code gameCenter} or {@code templateEngine} is null
     */
    public PostHomeRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {
        // validation
        Objects.requireNonNull(playerLobby, "playerLobby can't be null.");
        Objects.requireNonNull(templateEngine, "templateEngine can't be null.");
        //
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    @Override
    public String handle(Request request, Response response){
        // TODO
        return null;
    }
}
