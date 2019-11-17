package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit Test for PostValidateMoveRoute
 */
@Tag("UI-Tier")
public class PostValidateMoveRouteTest {

    /**
     * Private fields
     */
    private TemplateEngineTester templateEngineTester = new TemplateEngineTester();
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private Request request;
    private PostValidateMoveRoute CuT;
    private Response response;
    private Session session;

    /**
     * setup function to initialize and mock the session items for testing
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        templateEngine  = mock(TemplateEngine.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);

        playerLobby = new PlayerLobby();
        CuT = new PostValidateMoveRoute(templateEngine, playerLobby);
        templateEngineTester = new TemplateEngineTester();
    }

    /**
     * test function to test the validity of the proposed moves. This happens through checking for a null move, and
     * by checking that the populated proposed moves are correctly placed in the session.
     */
    @Test
    public void test_valid_moves()
    {
        String json = new String();
        when(request.queryParams(ConstsUI.ACTION_DATA_PARAM)).thenReturn(json);

        Move move = new Gson().fromJson(json, Move.class);

        Board currentUserBoard = new Board();
        when(session.attribute(ConstsUI.CURRENT_USER_BOARD_PARAM)).thenReturn(currentUserBoard);

        Player currentPlayer = new Player("p1");
        when(session.attribute(ConstsUI.CURRENT_USER_PARAM)).thenReturn(currentPlayer);

        Piece.Color currentPlayerColor = currentPlayer.getColor();
        ArrayList<MovePacket> validMoves = currentUserBoard.getAllValidMoves(currentPlayerColor);

        assertFalse(validMoves.contains(move));

        ArrayList<Move> proposedMoves = null;
        when(session.attribute(ConstsUI.PROPOSED_MOVES_PARAM)).thenReturn(proposedMoves);
        assertNull(proposedMoves);

        proposedMoves = new ArrayList<>();
        proposedMoves.add(move);
        when(session.attribute(ConstsUI.PROPOSED_MOVES_PARAM)).thenReturn(proposedMoves);

    }
}