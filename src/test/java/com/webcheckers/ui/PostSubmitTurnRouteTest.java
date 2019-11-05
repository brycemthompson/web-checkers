package com.webcheckers.ui;

import com.webcheckers.Model.Board;
import com.webcheckers.Model.Piece;
import com.webcheckers.Model.Player;
import com.webcheckers.Model.PlayerLobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostSubmitTurnRouteTest {

    private TemplateEngineTester templateEngineTester = new TemplateEngineTester();
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private Request request;
    private PostSubmitTurnRoute CuT;
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
        CuT = new PostSubmitTurnRoute(templateEngine, playerLobby);
        templateEngineTester = new TemplateEngineTester();
    }

    @Test
    public void test_game_board()
    {
        Player currentUser = new Player("p1");
        Player opponent = new Player("p2");
        Board currentPlayerBoard = new Board();

        currentPlayerBoard.setRedPlayer(currentUser);
        currentPlayerBoard.setWhitePlayer(opponent);

        when(session.attribute(ConstsUI.CURRENT_USER_BOARD_PARAM)).thenReturn(currentPlayerBoard);
        playerLobby.addBoard(currentPlayerBoard);

        Piece.Color activeColor = currentPlayerBoard.getActiveColor();

        currentPlayerBoard.flipActiveColor();
        Piece.Color flippedColor = currentPlayerBoard.getActiveColor();
        assert(flippedColor != activeColor);

    }
}