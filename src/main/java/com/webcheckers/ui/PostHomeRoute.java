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
 * @constributor Daniel Kitchen : dkj9755@rit.edu
 * @contributor Clayton Pruitt : chp4145@rit.edu
 */
public class PostHomeRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostHomeRoute.class.getName());

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

        final String username = request.queryParams(ConstsUI.USERNAME_PARAM);
        Authentication authResult = playerLobby.authenticateSignIn(username);

        switch (authResult) {
            case FAIL_INVALID_USERNAME:
                vm.put(ConstsUI.TITLE_PARAM, ConstsUI.DEFAULT_WELCOME_FOR_TITLE);
                vm.put(ConstsUI.MESSAGE_PARAM, ConstsUI.SIGNIN_FAILED_INVALID_MSG);
                request.session().attribute(ConstsUI.MESSAGE_PARAM, ConstsUI.SIGNIN_FAILED_INVALID_MSG);
                response.redirect(ConstsUI.SIGNIN_URL);
                return templateEngine.render(new ModelAndView(vm, ConstsUI.SIGNIN_VIEW));
            case FAIL_NAME_TAKEN:
                vm.put(ConstsUI.TITLE_PARAM, ConstsUI.DEFAULT_WELCOME_FOR_TITLE);
                vm.put(ConstsUI.MESSAGE_PARAM, ConstsUI.SIGNIN_FAILED_NAME_TAKEN_MSG);
                request.session().attribute(ConstsUI.MESSAGE_PARAM, ConstsUI.SIGNIN_FAILED_NAME_TAKEN_MSG);
                response.redirect(ConstsUI.SIGNIN_URL);
                return templateEngine.render(new ModelAndView(vm, ConstsUI.SIGNIN_VIEW));
            case SUCCESS:
                // populate view model
                Player currentUser = new Player(username);
                playerLobby.addPlayer(currentUser);
                vm.put(ConstsUI.CURRENT_USER_PARAM, currentUser);
                vm.put(ConstsUI.USERNAME_PARAM, username);
                ArrayList<String> playerNames = playerLobby.getPlayerNames();
                playerNames.remove(username); // do not want to play the current user
                vm.put(ConstsUI.PLAYER_LIST_PARAM, playerNames);

                // put that there is a current user into the session
                request.session().attribute(ConstsUI.CURRENT_USER_PARAM, currentUser);

                // put the display messages for the home page in the view-model
                vm.put(ConstsUI.TITLE_PARAM, ConstsUI.DEFAULT_WELCOME_FOR_TITLE);
                vm.put(ConstsUI.MESSAGE_PARAM, ConstsUI.WELCOME_MSG);

                // render the view
                return templateEngine.render(new ModelAndView(vm, ConstsUI.HOME_VIEW));
            default:
                vm.put(ConstsUI.TITLE_PARAM, ConstsUI.WELCOME_MSG);
                vm.put(ConstsUI.MESSAGE_PARAM, ConstsUI.SIGNIN_FAILED_UNKNOWN_MSG);
                request.session().attribute(ConstsUI.MESSAGE_PARAM, ConstsUI.SIGNIN_FAILED_UNKNOWN_MSG);
                response.redirect(ConstsUI.SIGNIN_URL);
                return templateEngine.render(new ModelAndView(vm, ConstsUI.SIGNIN_VIEW));
        }

    }
}