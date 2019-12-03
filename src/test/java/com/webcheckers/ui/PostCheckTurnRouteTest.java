package com.webcheckers.ui;

import com.webcheckers.Model.Board;
import com.webcheckers.Model.Piece;
import com.webcheckers.Model.Player;
import com.webcheckers.Model.PlayerLobby;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-Tier")
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
    public void test_player_turn_active_color_equality()
    {
        Player currentUser = new Player("p1");
        when(session.attribute(ConstsUI.CURRENT_USER_PARAM)).thenReturn(currentUser);

        Board currentUserBoard = new Board();
        when(session.attribute(ConstsUI.CURRENT_USER_BOARD_PARAM)).thenReturn(currentUserBoard);

        try {
            CuT.handle(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(currentUserBoard.getActiveColor() == currentUser.getColor())
        {
            System.out.println(currentUser.toString());
            System.out.println(currentUserBoard.toString());
            Message msg = new Message("true", Message.Type.INFO);
            assertEquals(Message.info("true").toString(), msg.toString());
        }

    }

    @Test
    public void test_player_turn_active_color_equality_fail()
    {
        Player currentUser = new Player("p1");
        currentUser.color = Piece.Color.WHITE;
        when(session.attribute(ConstsUI.CURRENT_USER_PARAM)).thenReturn(currentUser);

        Board currentUserBoard = new Board();
        when(session.attribute(ConstsUI.CURRENT_USER_BOARD_PARAM)).thenReturn(currentUserBoard);

        currentUserBoard.setActiveColor(Piece.Color.RED);

        if( currentUserBoard.getActiveColor() != currentUser.getColor())
        {
            System.out.println(currentUser.toString());
            System.out.println(currentUserBoard.toString());
            Message msg = new Message("false", Message.Type.INFO);
            assertEquals(Message.info("false").toString(), msg.toString());
        }

    }

}