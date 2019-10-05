package com.webcheckers.ui;

import com.webcheckers.PlayerLobby;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.Objects;

/**
 * The PostSignInRoute Class Handling Current Player Input
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 * @author <a href='mailto:jrv@se.rit.edu'>Jim Vallino</a>
 * @contributor Clayton Pruitt : chp4145@rit.edue
 * @contributor Isaias Villalobos
 */
public class PostHomeRoute implements Route {

    //
    // Attributes
    //

    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;

    //
    // Constructor
    //

    public PostHomeRoute(PlayerLobby playerLobby, TemplateEngine templateEngine){
        // validation
        Objects.requireNonNull(playerLobby, "playerLobby must not be null, dumdum");
        Objects.requireNonNull(templateEngine, "templateEngine must not be null, dumdum");
        //
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    //
    // TemplateViewRoute method
    //

    /**
     * {@inheritDoc}
     */
    @Override
    public String handle(Request request, Response response){
        return null;
    }
}
