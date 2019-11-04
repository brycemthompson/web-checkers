package com.webcheckers.ui;

import com.webcheckers.Model.PlayerLobby;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetSignInRouteTest {

    private TemplateEngineTester templateEngineTester = new TemplateEngineTester();
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private Request request;
    private GetHomeRoute CuT;
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
        CuT = new GetHomeRoute(templateEngine, playerLobby);
        templateEngineTester = new TemplateEngineTester();
    }

    @Test
    public void message_is_not_null_test(){
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());

        Message msg = new Message("I wanna die", Message.Type.INFO);
        when(session.attribute(ConstsUI.MESSAGE_PARAM)).thenReturn(msg);

        CuT.handle(request,response);
        templateEngineTester.assertViewModelAttribute(ConstsUI.MESSAGE_PARAM, msg);

    }
}