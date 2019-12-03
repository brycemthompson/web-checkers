package com.webcheckers.ui;

import com.webcheckers.Model.Player;
import com.webcheckers.Model.PlayerLobby;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-Tier")
public class PostSignOutRouteTest {
    private TemplateEngineTester templateEngineTester = new TemplateEngineTester();
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private Request request;
    private PostSignOutRoute CuT;
    private Response response;
    private Session session;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        templateEngine  = mock(TemplateEngine.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);

        playerLobby = new PlayerLobby();
        CuT = new PostSignOutRoute(templateEngine, playerLobby);
        templateEngineTester = new TemplateEngineTester();
    }

    @Test
    public void test_signin_title_and_msg(){
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());

        CuT.handle(request, response);
        templateEngineTester.assertViewModelAttribute("title", ConstsUI.HOME_TITLE_DEFAULT_VALUE);
    }

    @Test
    public void test_message_display(){
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());

        Message msg = new Message("Sign In to Play!", Message.Type.INFO);
        when(session.attribute(ConstsUI.MESSAGE_PARAM)).thenReturn(msg);
        CuT.handle(request, response);
        templateEngineTester.assertViewModelAttribute(ConstsUI.MESSAGE_PARAM, msg);
    }

    @Test
    public void test_playerlobby_size(){

        playerLobby.addPlayer("testplayer1");
        playerLobby.addPlayer("testplayer2");

        playerLobby.removePlayer(playerLobby.getPlayer("testplayer1"));

        String PLAYERSPLAYING_PARAM = "amountOfPlayersPlaying";

        int amountOfPlayersPlaying = playerLobby.size();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());

        CuT.handle(request, response);
        templateEngineTester.assertViewModelAttribute(PLAYERSPLAYING_PARAM, 0);

    }


}