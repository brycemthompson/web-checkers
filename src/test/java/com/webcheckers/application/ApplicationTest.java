package com.webcheckers.application;
import com.google.gson.Gson;
import com.webcheckers.Application;
import com.webcheckers.Model.PlayerLobby;
import com.webcheckers.ui.WebServer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.TemplateEngine;
import spark.template.freemarker.FreeMarkerEngine;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("Application-Tier")
public class ApplicationTest {
    public WebServer webServer;
    final TemplateEngine templateEngine = new FreeMarkerEngine();
    final Gson gson = new Gson();
    PlayerLobby lobby = new PlayerLobby();

    @Test
    public void initialize_Test() {
        WebServer webServer = new WebServer(templateEngine, gson, lobby);
        assertNotNull(webServer);

    }

    @Test
    public void applicationTest(){
        WebServer webServer = new WebServer(templateEngine, gson, lobby);
        Application app = new Application(webServer);
        assertNotNull(app);
    }


}