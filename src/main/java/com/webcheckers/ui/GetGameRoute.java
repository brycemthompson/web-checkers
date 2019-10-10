package com.webcheckers.ui;

import com.webcheckers.Model.Player;
import com.webcheckers.Model.PlayerLobby;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class GetGameRoute implements Route {

    // idk what these are
    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    private static final Message WELCOME_MSG = Message.info("Welcome to the game page.");

    private PlayerLobby players;

    private Player redPlayer = players.getPlayers().get(0);

    private Player whitePlayer = players.getPlayers().get(1);

    private String currentUser = redPlayer.toString();

    private String activeColor = "RED";

    public static final String VIEW_NAME = "game.ftl";

    private final TemplateEngine templateEngine;


    public GetGameRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        //
        LOG.config("GetGameRoute is initialized.");
    }


    public Object handle(Request request, Response response) {

        LOG.finer("GetGameRoute is invoked.");

        // start building the view model
        final Map<String, Object> vm = new HashMap<>();
        vm.put("title", "welcome");
        vm.put("currentUser.name", currentUser);
        vm.put("viewMode", "PLAY");
        vm.put("message", WELCOME_MSG);
        vm.put("redPlayer.name", redPlayer);
        vm.put("whitePlayer.name", whitePlayer);
        vm.put("activeColor", "RED");
        // render the View
        return templateEngine.render(new ModelAndView(vm , VIEW_NAME));

    }


}
