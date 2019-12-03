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
/**
    This is the class that will test the WebServer class
 */
@Tag("UI-Tier")
public class WebServerTest {

    /**
     * These are the fields
     */
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private WebServer CuT;
    private Response response;
    private Session session;
    private Gson gson = new Gson();
    private Request request;

    /**
     * The setup for the test function
     */
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

    /**
     * This test will simply test if you can initialize an instance of the WebServer and not throw any errors.
     */
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
