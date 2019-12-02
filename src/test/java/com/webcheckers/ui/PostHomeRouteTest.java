package com.webcheckers.ui;

import com.webcheckers.Model.Authentication;
import com.webcheckers.Model.Player;
import com.webcheckers.Model.PlayerLobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-Tier")
public class PostHomeRouteTest {

    private TemplateEngineTester templateEngineTester = new TemplateEngineTester();
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private Request request;
    private PostHomeRoute CuT;
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
        CuT = new PostHomeRoute(templateEngine, playerLobby);
        templateEngineTester = new TemplateEngineTester();
    }

    @Test
    public void test_default_view(){
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());
        final String username = "Test";
        playerLobby.addPlayer(username);
        when(request.queryParams(ConstsUI.USERNAME_PARAM)).thenReturn(username);

        CuT.handle(request, response);

        templateEngineTester.assertViewName("signin.ftl");
    }

    @Test
    public void test_invalid_username() {
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());

        // Testing an invalid username
        final String username = "_122g";
        playerLobby.addPlayer(username);
        when(request.queryParams(ConstsUI.USERNAME_PARAM)).thenReturn(username);

//        assertEquals(authResult, Authentication.FAIL_INVALID_USERNAME);

        CuT.handle(request, response);

        templateEngineTester.assertViewModelAttribute(ConstsUI.TITLE_PARAM, ConstsUI.DEFAULT_WELCOME_FOR_TITLE);
        templateEngineTester.assertViewModelAttribute(ConstsUI.MESSAGE_PARAM, ConstsUI.SIGNIN_FAILED_INVALID_MSG);
        templateEngineTester.assertViewName(ConstsUI.SIGNIN_VIEW);
    }

    @Test
    public void test_username_taken() {
        final Map<String, Object> vm = new HashMap<>();

        // Testing a taken name
        final String username = "bob";
        playerLobby.addPlayer(username);

        String second_username = "bob";
        Authentication authResult = playerLobby.authenticateSignIn(second_username);
        when(request.queryParams(ConstsUI.USERNAME_PARAM)).thenReturn(username);

        assertEquals(authResult, Authentication.FAIL_NAME_TAKEN);

        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());
        CuT.handle(request, response);

        templateEngineTester.assertViewModelAttribute(ConstsUI.TITLE_PARAM, ConstsUI.DEFAULT_WELCOME_FOR_TITLE);
        templateEngineTester.assertViewModelAttribute(ConstsUI.MESSAGE_PARAM, ConstsUI.SIGNIN_FAILED_NAME_TAKEN_MSG);
        templateEngineTester.assertViewName(ConstsUI.SIGNIN_VIEW);
    }

    @Test
    public void test_successful_username()
    {
        final Map<String, Object> vm = new HashMap<>();

        // Testing successful username
        final String username = "bob";
        Authentication authResult = playerLobby.authenticateSignIn(username);

        Player currentUser = new Player(username);
        assertEquals(authResult, Authentication.SUCCESS);

//        playerLobby.addPlayer(currentUser);
        when(request.queryParams(ConstsUI.USERNAME_PARAM)).thenReturn(username);
//        when(playerLobby.authenticateSignIn(username)).thenReturn(authResult);

//        when(request.queryParams(ConstsUI.CURRENT_USER_PARAM)).thenReturn(currentUser);

        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());
        CuT.handle(request, response);
        templateEngineTester.assertViewModelAttribute(ConstsUI.CURRENT_USER_PARAM, currentUser);
        templateEngineTester.assertViewModelAttribute(ConstsUI.USERNAME_PARAM, username);
        playerLobby.addPlayer(currentUser);
        ArrayList<String> playerNames = playerLobby.getPlayerNames();
        //Player then is removed from playernames in original PostHomeRouteCode
        playerNames.remove(username);

        templateEngineTester.assertViewModelAttribute(ConstsUI.PLAYER_LIST_PARAM, playerNames);
        templateEngineTester.assertViewModelAttribute(ConstsUI.TITLE_PARAM, ConstsUI.DEFAULT_WELCOME_FOR_TITLE);
        templateEngineTester.assertViewModelAttribute(ConstsUI.MESSAGE_PARAM, ConstsUI.WELCOME_MSG);
        templateEngineTester.assertViewName(ConstsUI.HOME_VIEW);
    }
}