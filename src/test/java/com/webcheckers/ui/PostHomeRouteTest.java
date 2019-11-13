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

/**
 * Unit Test for the PostHomeRoute Class
 */
@Tag("UI-Tier")
public class PostHomeRouteTest {

    /**
     * Private fields
     */
    private TemplateEngineTester templateEngineTester = new TemplateEngineTester();
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private Request request;
    private PostHomeRoute CuT;
    private Response response;
    private Session session;

    /**
     * setup function to initialize and mock the session items used for testing
     */
    @BeforeEach
    public void setup() {

        request = mock(Request.class);
        response = mock(Response.class);
        templateEngine = mock(TemplateEngine.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);

        playerLobby = new PlayerLobby();
        CuT = new PostHomeRoute(templateEngine, playerLobby);
        templateEngineTester = new TemplateEngineTester();
    }

    /**
     * test function to check for the correct view model title attribute
     */
    @Test
    public void test_view(){
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());
        System.out.println(playerLobby.size());

        CuT.handle(request, response);
        templateEngineTester.assertViewModelAttribute("title", "Welcome to Home Page!");
    }

    /**
     * test function to check for an invalid username error message on an invalid username, and checking for the
     * appropriate view model attributes being held and displayed
     */
    @Test
    public void test_invalid_username() {
        final Map<String, Object> vm = new HashMap<>();

        // Testing an invalid username
        final String username = "_122g";
        playerLobby.addPlayer(username);
        Authentication authResult = playerLobby.authenticateSignIn(username);

        assertEquals(authResult, Authentication.FAIL_INVALID_USERNAME);

        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());
        CuT.handle(request, response);

        templateEngineTester.assertViewModelAttribute(ConstsUI.TITLE_PARAM, ConstsUI.DEFAULT_WELCOME_FOR_TITLE);
        templateEngineTester.assertViewModelAttribute(ConstsUI.MESSAGE_PARAM, ConstsUI.SIGNIN_FAILED_INVALID_MSG);
        templateEngineTester.assertViewName(ConstsUI.SIGNIN_VIEW);
    }

    /**
     * test function to test for the name taken error message when a user tries to enter a username that is already
     * being used by another user in addition to checking the appropriate view model attributes
     */
    @Test
    public void test_username_taken() {
        final Map<String, Object> vm = new HashMap<>();

        // Testing a taken name
        final String username = "bob";
        playerLobby.addPlayer(username);

        String second_username = "bob";
        Authentication authResult = playerLobby.authenticateSignIn(second_username);

        assertEquals(authResult, Authentication.FAIL_NAME_TAKEN);

        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());
        CuT.handle(request, response);

        templateEngineTester.assertViewModelAttribute(ConstsUI.TITLE_PARAM, ConstsUI.DEFAULT_WELCOME_FOR_TITLE);
        templateEngineTester.assertViewModelAttribute(ConstsUI.MESSAGE_PARAM, ConstsUI.SIGNIN_FAILED_NAME_TAKEN_MSG);
        templateEngineTester.assertViewName(ConstsUI.SIGNIN_VIEW);
    }

    /**
     * test function to assert a successful login message along with a successful authentication when the user
     * enters an valid and unused username to signin
     */
    @Test
    public void test_successful_username()
    {
        final Map<String, Object> vm = new HashMap<>();

        // Testing successful username
        final String username = "bob";
        Authentication authResult = playerLobby.authenticateSignIn(username);

        Player currentUser = new Player(username);
        assertEquals(authResult, Authentication.SUCCESS);
        playerLobby.addPlayer(currentUser);

        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());
        CuT.handle(request, response);

        templateEngineTester.assertViewModelAttribute(ConstsUI.CURRENT_USER_PARAM, currentUser);
        templateEngineTester.assertViewModelAttribute(ConstsUI.USERNAME_PARAM, username);

        ArrayList<String> playerNames = playerLobby.getPlayerNames();
        templateEngineTester.assertViewModelAttribute(ConstsUI.PLAYER_LIST_PARAM, playerNames);

        templateEngineTester.assertViewModelAttribute(ConstsUI.TITLE_PARAM, ConstsUI.DEFAULT_WELCOME_FOR_TITLE);
        templateEngineTester.assertViewModelAttribute(ConstsUI.MESSAGE_PARAM, ConstsUI.WELCOME_MSG);
        templateEngineTester.assertViewName(ConstsUI.HOME_VIEW);
    }
}