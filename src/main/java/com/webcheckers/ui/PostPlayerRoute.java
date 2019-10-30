package com.webcheckers.ui;

import com.webcheckers.Model.Player;
import com.webcheckers.Model.PlayerLobby;
import com.webcheckers.util.Message;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * The PostPlayerRoute Class Handling Sending the Players into a Game
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 * @author <a href='mailto:jrv@se.rit.edu'>Jim Vallino</a>
 * @contributor Bryce Thompson : bxt6698@rit.edu
 */
public class PostPlayerRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostPlayerRoute.class.getName());


    // Values used in the view-model map for rendering the game view after a guess.
    static final String PLAYER_PARAM = "player";
    static final String CURRENTUSER_PARAM = "currentUser";
    static final String OPPONENT_PARAM = "opponent";

    // Messages
    static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

    // Various objects the route needs to track.
    private final TemplateEngine templateEngine;
    private PlayerLobby playerLobby;

    PostPlayerRoute(TemplateEngine templateEngine, PlayerLobby playerLobby)
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
        LOG.finer("PostPlayerRoute is invoked.");

        // initialize view-model
        final Map<String, Object> vm = new HashMap<>();

        // Get the current user and the opponent
        Player currentPlayer = request.session().attribute(CURRENTUSER_PARAM);

        // Finding the opponent in the playerList
        final String opponentUsername = request.queryParams(PLAYER_PARAM);
        ArrayList<Player> players = playerLobby.getPlayers();
        Player opponent = null;
        for(Player player: players)
        {
            System.out.println(player.getName());
            if(player.getName().equals(opponentUsername))
            {
                System.out.println("Player is in game status: " + player.isInGame());
                if(player.isInGame()) // The player is in a game and we are sending an error message
                {
                    System.out.println("Player found in game. Trying to go home.");
                    vm.put(ConstsUI.TITLE_PARAM, WELCOME_MSG);
                    vm.put(ConstsUI.MESSAGE_PARAM, ConstsUI.PLAYER_IN_GAME_ERROR_MSG);
                    request.session().attribute(ConstsUI.MESSAGE_PARAM, ConstsUI.PLAYER_IN_GAME_ERROR_MSG);
                    response.redirect(ConstsUI.HOME_URL);
                    return templateEngine.render(new ModelAndView(vm, ConstsUI.HOME_VIEW));
                }
                else // The player is not in a game and therefore will start the game
                {
                    opponent = player;
                    request.session().attribute(OPPONENT_PARAM, opponent);
                }
            }
        }

        vm.put(ConstsUI.CURRENT_USER_PARAM, currentPlayer);
        vm.put(ConstsUI.VIEW_MODE_PARAM, "PLAY");
        vm.put(ConstsUI.MESSAGE_PARAM, WELCOME_MSG);
        vm.put("redPlayer", currentPlayer);
        vm.put("whitePlayer", opponent);
        vm.put("activeColor", "red");

        return templateEngine.render(new ModelAndView(vm, ConstsUI.GAME_VIEW));

    }
}