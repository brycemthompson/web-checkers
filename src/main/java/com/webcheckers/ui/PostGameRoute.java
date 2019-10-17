package com.webcheckers.ui;

import com.webcheckers.Model.Board;
import com.webcheckers.Model.Player;
import com.webcheckers.Model.PlayerLobby;
import com.webcheckers.util.Message;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PostGameRoute implements Route{
    private static final Logger LOG = Logger.getLogger(PostGameRoute.class.getName());

    // Messages
    public static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");
    public static final Message MESSAGE_MSG = Message.info("AAAAAAAAAA");

    // Values used in the view-model map for rendering the game view after a guess.
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    PostGameRoute(TemplateEngine templateEngine, PlayerLobby playerLobby)
    {
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
    }

    private Board createBoard(){
        return new Board();
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

        // Get the current user and the opponent
        Player currentPlayer = request.session().attribute(PostPlayerRoute.CURRENTUSER_PARAM);

        // Finding the opponent in the playerList
        final String opponentUsername = request.queryParams("opponentUsername");
        System.out.println(opponentUsername);
        ArrayList<Player> players = playerLobby.getPlayers();
        Player opponent = null;
        for(Player player: players)
        {
            if(player.getName().equals(opponentUsername))
            {
                if(player.isInGame()) // The player is in a game and we are sending an error message
                {
                    vm.put("title", WELCOME_MSG);
                    vm.put("message", PostPlayerRoute.PLAYER_IN_GAME_ERROR_MSG);
                    return templateEngine.render(new ModelAndView(vm, "home.ftl"));
                }
                else // The player is not in a game and therefore will start the game
                {
                    opponent = player;
                    request.session().attribute(PostPlayerRoute.OPPONENT_PARAM, opponent);
                }
            }
        }

        vm.put("title", WELCOME_MSG);
        vm.put("message", MESSAGE_MSG);
        vm.put("currentUser", currentPlayer);
        vm.put("viewMode", "PLAY");
        vm.put("message", WELCOME_MSG);
        vm.put("redPlayer", currentPlayer);
        vm.put("whitePlayer", opponent);
        vm.put("activeColor", "red");
        vm.put("board", createBoard());

        return templateEngine.render(new ModelAndView(vm, "game.ftl"));

    }
}
