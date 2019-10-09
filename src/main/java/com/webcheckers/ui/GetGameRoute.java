package com.webcheckers.ui;

import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class GetGameRoute implements Route{
    // Values used in the view-model map for rendering the home view.

    // idk what these are
    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());
    private static final Message WELCOME_MSG = Message.info("Welcome to the Game Page.");
    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetGameRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        LOG.config("GetGameRoute is initialized.");
    }


    public Object handle(Request request, Response response) {
        LOG.finer("GetGameRoute is invoked.");

        // retrieve the HTTP session
        final Session session = request.session();

        // start building the view model
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Welcome to Game Page!");
        vm.put("message", WELCOME_MSG);

        // render the View
        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}

