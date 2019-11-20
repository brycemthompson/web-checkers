package com.webcheckers.ui;

import com.webcheckers.Model.Board;
import com.webcheckers.Model.Piece;
import com.webcheckers.Model.Player;
import com.webcheckers.Model.PlayerLobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit Test for the PostPlayerRoute Class
 */
@Tag("UI-Tier")
public class PostPlayerRouteTest {

    /**
     * Private fields
     */
    private TemplateEngineTester templateEngineTester = new TemplateEngineTester();
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private Request request;
    private PostPlayerRoute CuT;
    private Response response;
    private Session session;

    /**
     * setup function to initialize and mock the session items for testing
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        templateEngine  = mock(TemplateEngine.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);

        playerLobby = new PlayerLobby();
        CuT = new PostPlayerRoute(templateEngine, playerLobby);
        templateEngineTester = new TemplateEngineTester();
    }

    /**
     * test function to check the components of putting two players into a game.
     * this consists of checking that the current user and opponent are not null in the session,
     * the current user and the opponent are separate Player objects,
     * and that both players are truthfully and successfully put into a game together.
     */
    @Test
    public void test_players_in_game()
    {
        Player currentUser = new Player("p1");
        when(session.attribute(ConstsUI.CURRENT_USER_PARAM)).thenReturn(currentUser);
        assertNotNull(currentUser);
        playerLobby.addPlayer(currentUser);

        Player opponent = new Player("p2");
        when(session.attribute(ConstsUI.OPPONENT_PARAM)).thenReturn(opponent);
        assertNotNull(opponent);
        playerLobby.addPlayer(opponent);

        assertNotEquals(currentUser, opponent);

        // Putting the current user in game to test the conditional
        currentUser.putInGame(opponent, Piece.Color.RED);
        assert(currentUser.isInGame());

        // Putting the opponent in game to test the conditional
        opponent.putInGame(currentUser, Piece.Color.WHITE);
        assert(opponent.isInGame());
    }

    /**
     * test function that checks all of the components that are to be displayed and held within the view model as an
     * attribute or the current view name.
     */
    @Test
    public void test_view_model_messages()
    {
        Board currentUserBoard = new Board();

        when(session.attribute(ConstsUI.CURRENT_USER_BOARD_PARAM)).thenReturn(currentUserBoard);

        Player currentUser = new Player("p1");
        when(session.attribute(ConstsUI.CURRENT_USER_PARAM)).thenReturn(currentUser);

        Player opponent = null;

        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());
        CuT.handle(request, response);

        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());

        opponent = playerLobby.getPlayer(request.queryParams("opponentUsername"));
        when(session.attribute(ConstsUI.OPPONENT_PARAM)).thenReturn(opponent);

        Object o  = CuT.handle(request, response);
        templateEngineTester.assertViewModelAttribute(ConstsUI.CURRENT_USER_PARAM, currentUser);
        templateEngineTester.assertViewModelAttribute(ConstsUI.VIEW_MODE_PARAM, "PLAY");
        templateEngineTester.assertViewModelAttribute(ConstsUI.MESSAGE_PARAM, ConstsUI.WELCOME_MSG);
        templateEngineTester.assertViewModelAttribute("redPlayer", currentUser);
        templateEngineTester.assertViewModelAttribute("whitePlayer", opponent);
        templateEngineTester.assertViewModelAttribute("activeColor", "red");

        templateEngineTester.assertViewName(ConstsUI.GAME_VIEW);
    }
}