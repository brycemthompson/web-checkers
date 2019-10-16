package com.webcheckers.ui;

import com.webcheckers.Model.Player;
import com.webcheckers.Model.PlayerLobby;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PostGameRoute implements Route{
    private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());
    private static final String WELCOME_MSG = "Game Board";

    // Values used in the view-model map for rendering the game view after a guess.
    private final TemplateEngine templateEngine;

    PostGameRoute(TemplateEngine templateEngine)
    {
        this.templateEngine = templateEngine;
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
        LOG.finer("PostGameRoute is invoked.");

        // initialize view-model
        final Map<String, Object> vm = new HashMap<>();

        final Session session = request.session();

        // put the display messages for the home page in the view-model
        vm.put("title", "Game View");
        vm.put("message", WELCOME_MSG);

        // render the view
        return templateEngine.render(new ModelAndView(vm, "game.ftl"));
    }
}
