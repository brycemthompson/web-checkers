package com.webcheckers.ui;

import com.webcheckers.Model.Piece;
import com.webcheckers.Model.Player;
import com.webcheckers.Model.PlayerLobby;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-Tier")
public class PostGameRouteTest {

    private TemplateEngineTester templateEngineTester = new TemplateEngineTester();
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private Request request;
    private PostGameRoute CuT;
    private Response response;
    private Session session;

    @BeforeEach
    public void setup() {

        request = mock(Request.class);
        response = mock(Response.class);
        templateEngine = mock(TemplateEngine.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);

        playerLobby = new PlayerLobby();
        CuT = new PostGameRoute(templateEngine, playerLobby);
        templateEngineTester = new TemplateEngineTester();
    }

    @Test
    public void test(){
        final Map<String, Object> vm = new HashMap<>();
        Player currentPlayer = new Player("Killian");
        currentPlayer.color = Piece.Color.RED;

        Player opponent = new Player("Boi");
        currentPlayer.putInGame(opponent, Piece.Color.WHITE);

        Player testSamePlayer = new Player("Killian");


        playerLobby.addPlayer(currentPlayer);
        playerLobby.addPlayer(testSamePlayer);
//        assertEquals(currentPlayer.getName(), testSamePlayer.getName());

        when(request.queryParams("opponentUsername")).thenReturn("Killian");

        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());

        CuT.handle(request, response);

        when(session.attribute(ConstsUI.MESSAGE_PARAM)).thenReturn(ConstsUI.PLAYER_IN_GAME_ERROR_MSG);
        templateEngineTester.assertViewModelAttribute(ConstsUI.MESSAGE_PARAM, ConstsUI.PLAYER_IN_GAME_ERROR_MSG);

        templateEngineTester.assertViewName("home.ftl");
    }


}

