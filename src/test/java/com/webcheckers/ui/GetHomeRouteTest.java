package com.webcheckers.ui;

import com.webcheckers.Model.Board;
import com.webcheckers.Model.Piece;
import com.webcheckers.Model.Player;
import com.webcheckers.Model.PlayerLobby;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

public class GetHomeRouteTest {

    private TemplateEngineTester templateEngineTester = new TemplateEngineTester();
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private Request request;
    private GetHomeRoute CuT;
    private Response response;
    private Session session;

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


    //I need to rename these tests !!!!!
    @Test
     public void test_something(){
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());

        Object something  = CuT.handle(request, response);
        templateEngineTester.assertViewModelAttribute("title", "Welcome to Home Page!");
    }

    @Test
    public void test_something2(){
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());

        Message msg = new Message("Test1", Message.Type.INFO);
        when(session.attribute(ConstsUI.MESSAGE_PARAM)).thenReturn(msg);

        //session.attribute(ConstsUI.MESSAGE_PARAM, msg );

        CuT.handle(request, response);
        templateEngineTester.assertViewModelAttribute(ConstsUI.MESSAGE_PARAM, msg);
    }

    @Test
    public void test_something3(){
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());

        CuT.handle(request, response);
        templateEngineTester.assertViewModelAttribute(ConstsUI.MESSAGE_PARAM, ConstsUI.WELCOME_MSG);


    }

    @Test
    public void test_something4(){
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

    @Test
    public void test_something_5(){
        Player pl = new Player("David2");
        playerLobby.addPlayer(pl);
        ArrayList<String> playerNames = playerLobby.getPlayerNames();

//        when(session.attribute(ConstsUI.CURRENT_USER_PARAM)).thenReturn(pl);

        Board currentUserBoard ;
        Player opponent = pl.getOpponent();

        pl.color = Piece.Color.RED;
        currentUserBoard = playerLobby.getBoard(pl, opponent);

        when(session.attribute(ConstsUI.CURRENT_USER_BOARD_PARAM)).thenReturn(currentUserBoard);

//        GetGameRoute.drawBoard(currentUserBoard, pl.getColor(), opponent.getColor());

        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());


        CuT.handle(request, response);
        templateEngineTester.assertViewModelAttribute(ConstsUI.CURRENT_USER_PARAM, pl);
        templateEngineTester.assertViewModelAttribute(ConstsUI.CURRENT_USER_BOARD_PARAM, currentUserBoard);

    }

    @Test
    public void test_something_6(){

        Player pl = null;
        String PLAYERSPLAYING_PARAM = "amountOfPlayersPlaying";

        int amountOfPlayersPlaying = playerLobby.size();
        System.out.println(amountOfPlayersPlaying);
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());

        CuT.handle(request, response);
        templateEngineTester.assertViewModelAttribute(PLAYERSPLAYING_PARAM, 0);

    }

}
