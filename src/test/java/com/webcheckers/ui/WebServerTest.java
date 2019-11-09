package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.Model.PlayerLobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-Tier")
public class WebServerTest {

    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private WebServer CuT;
    private Response response;
    private Session session;
    private Gson gson = new Gson();
    private Request request;

    @BeforeEach
    public void setup(){

        request = mock(Request.class);
        response = mock(Response.class);
        templateEngine  = mock(TemplateEngine.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);

        playerLobby = new PlayerLobby();
        CuT = new WebServer(templateEngine,gson, playerLobby);
    }

    @Test
    public void intializeTest(){

        try{
            WebServer webServer = new WebServer(templateEngine, gson, playerLobby);
            assertNotNull(webServer);
            webServer.initialize();
        }
        catch(Exception e){
            fail("Should not have thrown any exception");
        }
    }

}
