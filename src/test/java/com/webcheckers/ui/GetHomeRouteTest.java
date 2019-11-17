package com.webcheckers.ui;

import com.webcheckers.Model.Board;
import com.webcheckers.Model.Piece;
import com.webcheckers.Model.Player;
import com.webcheckers.Model.PlayerLobby;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

/**
 * Unit Test for GetHomeRoute Class
 */
@Tag("UI-Tier")
public class GetHomeRouteTest {

    /**
     * Private fields
     */
    private TemplateEngineTester templateEngineTester = new TemplateEngineTester();
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private Request request;
    private GetHomeRoute CuT;
    private Response response;
    private Session session;

    /**
     * setup function to initialize the items for the session and game
     */
    @BeforeEach
    public void setup(){

        request = mock(Request.class);
        response = mock(Response.class);
        templateEngine  = mock(TemplateEngine.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);

        playerLobby = new PlayerLobby();
        CuT = new GetHomeRoute(templateEngine, playerLobby);
        templateEngineTester = new TemplateEngineTester();
    }

    /**
     * test function to test the view model components
     */
    @Test
     public void test_view(){
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());

        Object something  = CuT.handle(request, response);
        templateEngineTester.assertViewModelAttribute("title", "Welcome to Home Page!");
    }

    /**
     * test function to check that the session message parameter is outputting the correct message
     */
    @Test
    public void test_correct_message(){
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());

        Message msg = new Message("Test1", Message.Type.INFO);
        when(session.attribute(ConstsUI.MESSAGE_PARAM)).thenReturn(msg);

        //session.attribute(ConstsUI.MESSAGE_PARAM, msg );

        CuT.handle(request, response);
        templateEngineTester.assertViewModelAttribute(ConstsUI.MESSAGE_PARAM, msg);
    }

    /**
     * test function that tests that the welcome message is placed appropriately in the view model
     */
    @Test
    public void test_welcome_message(){
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());

        CuT.handle(request, response);
        templateEngineTester.assertViewModelAttribute(ConstsUI.MESSAGE_PARAM, ConstsUI.WELCOME_MSG);


    }

    /**
     * test function that tests the the view model holds the appropriate playerLobby, current user, and username
     * parameters
     */
    @Test
    public void test_username_currentUser_playerList(){
        Player pl = new Player("David");
        playerLobby.addPlayer(pl);
        ArrayList<String> playerNames = playerLobby.getPlayerNames();
        playerNames.remove(pl.getName());

        when(session.attribute(ConstsUI.CURRENT_USER_PARAM)).thenReturn(pl);

        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());


        CuT.handle(request, response);
        templateEngineTester.assertViewModelAttribute(ConstsUI.CURRENT_USER_PARAM, pl);
        templateEngineTester.assertViewModelAttribute(ConstsUI.USERNAME_PARAM, pl.getName());
        templateEngineTester.assertViewModelAttribute(ConstsUI.PLAYER_LIST_PARAM, playerNames);

    }

    /**
     * test function to check that the current board is correctly placed in the session and that the view model holds
     * the appropriate current user param and user board param
     */
    @Test
    public void test_board(){
        Player pl = new Player("David2");
        playerLobby.addPlayer(pl);
        ArrayList<String> playerNames = playerLobby.getPlayerNames();

        Board currentUserBoard ;
        Player opponent = pl.getOpponent();

        pl.color = Piece.Color.RED;
        currentUserBoard = playerLobby.getBoard(pl, opponent);

        when(session.attribute(ConstsUI.CURRENT_USER_BOARD_PARAM)).thenReturn(currentUserBoard);

        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());

        CuT.handle(request, response);
        templateEngineTester.assertViewModelAttribute(ConstsUI.CURRENT_USER_PARAM, pl);
        templateEngineTester.assertViewModelAttribute(ConstsUI.CURRENT_USER_BOARD_PARAM, currentUserBoard);

    }

    /**
     * test function that checks for the correct size of the player lobby
     */
    @Test
    public void test_player_lobby_size(){

        Player pl = null;
        String PLAYERSPLAYING_PARAM = "amountOfPlayersPlaying";

        int amountOfPlayersPlaying = playerLobby.size();
        System.out.println(amountOfPlayersPlaying);
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());

        CuT.handle(request, response);
        templateEngineTester.assertViewModelAttribute(PLAYERSPLAYING_PARAM, 0);

    }

}
