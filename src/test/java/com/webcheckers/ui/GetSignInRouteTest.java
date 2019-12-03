package com.webcheckers.ui;

import com.webcheckers.Model.PlayerLobby;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
/**
 * Unit Test for the GetSignInRoute Class
 */
@Tag("UI-Tier")
public class GetSignInRouteTest {
    /**
     * Private fields
     */
    private TemplateEngineTester templateEngineTester = new TemplateEngineTester();
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private Request request;
    private GetSignInRoute CuT;
    private Response response;
    private Session session;


    /**
     * the setup function to initialize the items for the session and the signin route
     */
    @BeforeEach
    public void setup() {

        request = mock(Request.class);
        response = mock(Response.class);
        templateEngine = mock(TemplateEngine.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);

        playerLobby = new PlayerLobby();
        CuT = new GetSignInRoute(templateEngine, playerLobby);
        templateEngineTester = new TemplateEngineTester();
    }
    /**
     * test function to test that the session message parameter and view model is holding the correct message item
     */
    @Test
    public void message_is_not_null_test(){
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());

        Message msg = new Message("I wanna die", Message.Type.INFO);
        when(session.attribute(ConstsUI.MESSAGE_PARAM)).thenReturn(msg);

        CuT.handle(request,response);
        templateEngineTester.assertViewModelAttribute(ConstsUI.MESSAGE_PARAM, msg);

    }
}