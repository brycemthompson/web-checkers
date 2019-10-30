package com.webcheckers.ui;

import com.webcheckers.Model.PlayerLobby;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * The PostResignRoute Class Handling the Resign Button
 *
 * @author Daniel Kitchen : djk9755@rit.edu
 */
public class PostResignRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostResignRoute.class.getName());

    //messages
    private static final Message RESIGN_SUCCESSFUL = Message.info("Resigned Successfully!");
    private static final Message OTHER_PLAYER_RESIGNED = Message.info("Other player resigned from the game!");
    private static final Message RESIGN_UNSUCCESSFUL = Message.info("Resign Unsuccessful.  Either backup from your " +
            "move or wait until your next turn and try again.");

    private final TemplateEngine templateEngine;
    private PlayerLobby playerLobby;

    PostResignRoute(TemplateEngine templateEngine, PlayerLobby playerLobby)
    {
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
    }

    /**
     * Handle the Resign button request and try to send the player back to home.
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
    public Object handle(Request request, Response response) throws Exception {
        LOG.finer("PostResignRoute is invoked.");

        final Map<String, Object> vm = new HashMap<>();


        // re-render the default home page
        vm.put(ConstsUI.TITLE_PARAM, "Welcome to Home Page!");
        vm.put(ConstsUI.MESSAGE_PARAM, RESIGN_SUCCESSFUL);
        int amountOfPlayersPlaying = playerLobby.size();
        vm.put(GetHomeRoute.PLAYERSPLAYING_PARAM, amountOfPlayersPlaying);
        return templateEngine.render(new ModelAndView(vm, ConstsUI.HOME_VIEW));
    }
}
