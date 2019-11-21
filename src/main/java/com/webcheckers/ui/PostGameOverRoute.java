package com.webcheckers.ui;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import com.webcheckers.Model.Board;
import com.webcheckers.Model.Player;
import com.webcheckers.Model.PlayerLobby;
import com.webcheckers.util.Message;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PostGameOverRoute implements Route {

    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private final Logger LOG = Logger.getLogger(PostGameOverRoute.class.getName());

    public PostGameOverRoute(TemplateEngine templateEngine, PlayerLobby playerLobby)
    {
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        LOG.finer("PostGameOverRoute is invoked.");
        final Map<String, Object> vm = new HashMap<>();
        final String opponentUsername = request.queryParams("opponentUsername");
        ArrayList<String> playerNames = playerLobby.getPlayerNames();

        Board currentUserBoard = request.session().attribute(ConstsUI.CURRENT_USER_BOARD_PARAM);
        Player winningPlayer = currentUserBoard.getWinner();
        if(winningPlayer != null)
        {
            // sending the winning player back to the homepage upon winning the game
            vm.put(ConstsUI.TITLE_PARAM, ConstsUI.HOME_TITLE_DEFAULT_VALUE);
            vm.put(ConstsUI.MESSAGE_PARAM, Message.info("You won! You've captured all of " + opponentUsername +
                    "'s pieces."));
            vm.put(ConstsUI.PLAYER_LIST_PARAM, playerNames);
            return templateEngine.render(new ModelAndView(vm, ConstsUI.HOME_VIEW));
        }

        return null;
    }
}
