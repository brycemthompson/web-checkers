package com.webcheckers.ui;

import com.webcheckers.Model.Board;
import com.webcheckers.Model.Player;
import com.webcheckers.Model.PlayerLobby;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostCheckTurnRouteTest {
    private TemplateEngineTester templateEngineTester = new TemplateEngineTester();
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private Request request;
    private PostCheckTurnRoute CuT;
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
        CuT = new PostCheckTurnRoute(templateEngine, playerLobby);
        templateEngineTester = new TemplateEngineTester();
    }

    @Test
    public void test_player_turn()
    {
        Player currentUser = new Player("p1");
        when(session.attribute(ConstsUI.CURRENT_USER_PARAM)).thenReturn(currentUser);

        Board currentUserBoard = new Board();
        when(session.attribute(ConstsUI.CURRENT_USER_BOARD_PARAM)).thenReturn(currentUserBoard);

        Message msg = null;

        if(currentUserBoard.getActiveColor() == currentUser.getColor())
        {
            msg = CuT.COLOR_MATCH_MESSAGE;
            assertEquals(msg, CuT.COLOR_MATCH_MESSAGE);
        }
        else
        {
            msg = CuT.FAIL_COLOR_MATCH;
            assertEquals(msg, CuT.FAIL_COLOR_MATCH);
        }
    }
}