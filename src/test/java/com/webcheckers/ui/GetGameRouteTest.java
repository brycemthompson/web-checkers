package com.webcheckers.ui;

import com.webcheckers.Model.Board;
import com.webcheckers.Model.Piece;
import com.webcheckers.Model.Player;
import com.webcheckers.Model.PlayerLobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-Tier")
/**
 * Unit Test for the GetGameRoute class
 */
public class GetGameRouteTest {

    /**
     * Private fields
     */
    private TemplateEngineTester templateEngineTester = new TemplateEngineTester();
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private Request request;
    private GetGameRoute CuT;
    private Response response;
    private Session session;

    /**
     * unit test setup function to initialize all of the items of the class
     */
    @BeforeEach
    public void setup() {

        request = mock(Request.class);
        response = mock(Response.class);
        templateEngine = mock(TemplateEngine.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);

        playerLobby = new PlayerLobby();
        CuT = new GetGameRoute(templateEngine, playerLobby);
        templateEngineTester = new TemplateEngineTester();
    }

    /**
     * test function that currently checks for the session attributes and checks for the correct
     * view name
     */
    @Test
    public void challengingGameState_test() {

        Map<String, Object> vm = new HashMap<>();

        Player player = new Player("P1");
        Board currentPlayerBoard = null;

        when(session.attribute(ConstsUI.CURRENTUSER_PARAM)).thenReturn(player);

        when(session.attribute(ConstsUI.CURRENT_USER_BOARD_PARAM)).thenReturn(currentPlayerBoard);

        Player opponent = new Player("P2");

        Player player3 = new Player("P3");
        player3.putInGame(opponent, Piece.Color.RED);
        playerLobby.addPlayer(player);
        playerLobby.addPlayer(opponent);
        playerLobby.addPlayer(player3);

        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());
        when(request.queryParams("opponentUsername")).thenReturn("P3");

        CuT.handle(request, response);

        templateEngineTester.assertViewName("home.ftl");

    }

    /**
     * test function that currently creates a board and a new game, testing the session attributes and game view name
     */
    @Test
    public void challengingGameState_test_2() {

        Map<String, Object> vm = new HashMap<>();

        Player currentPlayer = new Player("Isaias");
        Board currentPlayerBoard = new Board();

        when(session.attribute(ConstsUI.CURRENTUSER_PARAM)).thenReturn(currentPlayer);

        when(session.attribute(ConstsUI.CURRENT_USER_BOARD_PARAM)).thenReturn(currentPlayerBoard);

        Player opponent = null;

        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());

        CuT.handle(request, response);

        opponent = playerLobby.getPlayer(request.queryParams("opponentUsername"));

       when(session.attribute(ConstsUI.CURRENT_USER_BOARD_PARAM)).thenReturn(currentPlayerBoard);

       GameView.buildGameViewModel(
               currentPlayer,
               opponent,
               currentPlayerBoard,
               vm
       );
        templateEngineTester.assertViewName("game.ftl");

    }
}
