package com.webcheckers.ui;

import com.webcheckers.Model.PlayerLobby;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The PostSignInRoute Class Handling Current Player Input
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 * @author <a href='mailto:jrv@se.rit.edu'>Jim Vallino</a>
 * @contributor Clayton Pruitt : chp4145@rit.edue
 * @contributor Isaias Villalobos
 */
public class PostHomeRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostHomeRoute.class.getName());

    //
    // Attributes
    //

    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;
    private final String signedInPlayer;

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
        // TODO the following line is terrible but we're working with it for now
        this.signedInPlayer = playerLobby.getPlayers().get(0).getUserName();
    }

    //
    // TemplateViewRoute method
    //

    /**
     * {@inheritDoc}
     */
    @Override
    public String handle(Request request, Response response){
        LOG.finer("GetSignInRoute is invoked.");
        // start building our View-Model
        final Map<String, Object> viewmodel = new HashMap<>();
        viewmodel.put(GetHomeRoute.SIGNED_IN_PLAYER_ATTR, signedInPlayer);

        return null;
    }
}
