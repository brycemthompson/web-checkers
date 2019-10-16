package com.webcheckers.ui;

// THIS CLASS STILL NEED TO BE COMPLETED

import com.webcheckers.Model.Authentication;
import com.webcheckers.Model.Player;
import com.webcheckers.Model.PlayerLobby;
import com.webcheckers.util.Message;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * The PostSignInRoute Class Handling Username Input
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 * @author <a href='mailto:jrv@se.rit.edu'>Jim Vallino</a>
 * @contributor Bryce Thompson : bxt6698@rit.edu
 * @constributor Daniel Kitchen
 * @contributor Clayton Pruitt : chp4145@rit.edu
 */
public class PostSignInRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());


    // Values used in the view-model map for rendering the game view after a guess.
    static final String USERNAME_PARAM = "username";
    static final String CURRENTUSER_PARAM = "currentUser";
    static final String PLAYERLIST_PARAM = "users";

    // Messages
    private static final Message WELCOME_MSG = Message.info("Sign In to Play!");
    private static final Message SIGNIN_FAILED_INVALID_MSG = Message.info("Username must contain only alphanumeric character.");
    private static final Message SIGNIN_FAILED_NAME_TAKEN_MSG = Message.info("Username taken. Please enter a unique username.");

    // Various objects the route needs to track.
    private final TemplateEngine templateEngine;
    private PlayerLobby playerLobby;

    PostSignInRoute(TemplateEngine templateEngine, PlayerLobby playerLobby)
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
        LOG.finer("GetSignInRoute is invoked.");

        // initialize view-model
        final Map<String, Object> vm = new HashMap<>();

        // retrieve player lobby
        final Session session = request.session();
        final PlayerLobby playerLobby = session.attribute(PlayerLobby.PLAYERLOBBY_KEY);

        // authenticate player log in. if successful, store Player in session

        final String username = request.queryParams(USERNAME_PARAM);
        Authentication authResult = playerLobby.authenticateSignIn(username);

        switch (authResult) {
            case FAIL_INVALID_USERNAME:
                vm.put("title", "Welcome!");
                vm.put("message", SIGNIN_FAILED_INVALID_MSG);
                return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
            case FAIL_NAME_TAKEN:
                vm.put("title", "Welcome!");
                vm.put("message", SIGNIN_FAILED_NAME_TAKEN_MSG);
                return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
            case SUCCESS:
                vm.put(CURRENTUSER_PARAM, new Player(username));
                vm.put(USERNAME_PARAM, username);
                ArrayList<String> playerNames = playerLobby.getPlayerNames();
                playerNames.remove(username); // do not want to play the current user
                vm.put(PLAYERLIST_PARAM, playerNames);

                // put the display messages for the home page in the view-model
                vm.put("title", "Welcome!");
                vm.put("message", WELCOME_MSG);

                // render the view
                return templateEngine.render(new ModelAndView(vm, GetHomeRoute.VIEW_NAME));
            default:
                vm.put("title", "Welcome!");
                vm.put("message", "Unknown error.");
                return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
        }

    }
}