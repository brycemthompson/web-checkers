package com.webcheckers.ui;

// THIS CLASS STILL NEED TO BE COMPLETED

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
 * The PostHomeRoute Class Handling Username Input
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 * @author <a href='mailto:jrv@se.rit.edu'>Jim Vallino</a>
 * @contributor Bryce Thompson : bxt6698@rit.edu
 * @constributor Daniel Kitchen
 * @contributor Clayton Pruitt : chp4145@rit.edu
 */
public class PostHomeRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostHomeRoute.class.getName());


    // Values used in the view-model map for rendering the game view after a guess.
    static final String USERNAME_PARAM = "username";
    static final String CURRENTUSER_PARAM = "currentUser";
    static final String PLAYERLIST_PARAM = "allPlayers";

    // Messages
    private static final Message WELCOME_MSG = Message.info("Sign In to Play!");
    private static final Message SIGNIN_FAILED_INVALID_MSG = Message.info("Username must contain only alphanumeric character.");
    private static final Message SIGNIN_FAILED_NAME_TAKEN_MSG = Message.info("Username taken. Please enter a unique username.");
    private static final Message SIGNIN_FAILED_UNKNOWN_MSG = Message.info("Unknown error. Please try another username.");

    // Various objects the route needs to track.
    private final TemplateEngine templateEngine;
    private PlayerLobby playerLobby;

    PostHomeRoute(TemplateEngine templateEngine, PlayerLobby playerLobby)
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
        LOG.finer("PostHomeRoute is invoked.");

        // initialize view-model
        final Map<String, Object> vm = new HashMap<>();

        // authenticate player log in. if successful, store Player in session

        final String username = request.queryParams(USERNAME_PARAM);
        Authentication authResult = playerLobby.authenticateSignIn(username);

        System.out.println("Above switch");
        switch (authResult) {
            case FAIL_INVALID_USERNAME:
                System.out.println("Here1");
                vm.put("title", "Welcome!");
                vm.put("message", SIGNIN_FAILED_INVALID_MSG);
                request.session().attribute("message", SIGNIN_FAILED_INVALID_MSG);
                response.redirect("/signin");
                return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
            case FAIL_NAME_TAKEN:
                vm.put("title", "Welcome!");
                vm.put("message", SIGNIN_FAILED_NAME_TAKEN_MSG);
                request.session().attribute("message", SIGNIN_FAILED_NAME_TAKEN_MSG);
                response.redirect("/signin");
                return templateEngine.render(new ModelAndView(vm, "singin.ftl"));
            case SUCCESS:
                // populate view model
                Player currentUser = new Player(username);
                vm.put(CURRENTUSER_PARAM, currentUser);
                vm.put(USERNAME_PARAM, username);
                ArrayList<String> playerNames = playerLobby.getPlayerNames();
                playerNames.remove(username); // do not want to play the current user
                vm.put(PLAYERLIST_PARAM, playerNames);

                // put that there is a current user into the session
                request.session().attribute(CURRENTUSER_PARAM, currentUser);

                // put the display messages for the home page in the view-model
                vm.put("title", "Welcome!");
                vm.put("message", GetHomeRoute.WELCOME_MSG);

                // render the view
                return templateEngine.render(new ModelAndView(vm, GetHomeRoute.VIEW_NAME));
            default:
                vm.put("title", "Welcome!");
                vm.put("message", SIGNIN_FAILED_UNKNOWN_MSG);
                request.session().attribute("message", SIGNIN_FAILED_UNKNOWN_MSG);
                response.redirect("/signin");
                return templateEngine.render(new ModelAndView(vm, GetHomeRoute.VIEW_NAME));
        }

    }
}