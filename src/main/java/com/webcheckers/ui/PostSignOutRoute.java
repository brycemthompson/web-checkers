package com.webcheckers.ui;

import com.webcheckers.Model.Authentication;
import com.webcheckers.Model.Player;
import com.webcheckers.Model.PlayerLobby;
import com.webcheckers.util.Message;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * The PostSignInRoute Class Handling Username Input
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 * @author <a href='mailto:jrv@se.rit.edu'>Jim Vallino</a>
 * @contributor Clayton Pruitt : chp4145@rit.edu
 * @contributor Bryce Thompson : bxt6698@rit.edu
 */
public class PostSignOutRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostSignOutRoute.class.getName());

    // Messages
    private static final Message WELCOME_MSG = Message.info("Sign In to Play!");
    private static final Message SIGNIN_FAILED_INVALID_MSG = Message.info("Username must contain only alphanumeric character.");
    private static final Message SIGNIN_FAILED_NAME_TAKEN_MSG = Message.info("Username taken. Please enter a unique username.");

    // Various objects the route needs to track.
    private final TemplateEngine templateEngine;
    private PlayerLobby playerLobby;

    PostSignOutRoute(TemplateEngine templateEngine, PlayerLobby playerLobby)
    {
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
    }

    /**
     * Render the WebCheckers Sign In page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Sign In page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostSignOutRoute is invoked.");

        // initialize view-model
        final Map<String, Object> vm = new HashMap<>();

        // set currentUser to null => the current user is effectively "signed out"
        playerLobby.removePlayer(request.session().attribute(ConstsUI.CURRENT_USER_PARAM));
        request.session().attribute(ConstsUI.CURRENT_USER_PARAM, null);

        // re-render the default home page
        vm.put(ConstsUI.TITLE_PARAM, "Welcome to Home Page!");
        vm.put(ConstsUI.MESSAGE_PARAM, WELCOME_MSG);
        int amountOfPlayersPlaying = playerLobby.size();
        vm.put(GetHomeRoute.PLAYERSPLAYING_PARAM, amountOfPlayersPlaying);
        return templateEngine.render(new ModelAndView(vm, ConstsUI.HOME_VIEW));

    }
}