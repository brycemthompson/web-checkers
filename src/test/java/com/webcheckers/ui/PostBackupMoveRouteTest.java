package com.webcheckers.ui;

import com.webcheckers.Model.*;
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

/**
 * Unit Test for the PostBackupMoveRoute Class
 */
@Tag("UI-Tier")
public class PostBackupMoveRouteTest {
    private TemplateEngineTester templateEngineTester = new TemplateEngineTester();
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private Request request;
    private PostBackupMoveRoute CuT;
    private Response response;
    private Session session;

    /**
     * the setup function to initialize the session attributes
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        templateEngine  = mock(TemplateEngine.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);

        playerLobby = new PlayerLobby();
        CuT = new PostBackupMoveRoute(templateEngine, playerLobby);
        templateEngineTester = new TemplateEngineTester();
    }

    /**
     * test function to test the functionality of the backup move, checking for a null value, and checking to see
     * if the backup move is populated once a move is created
     */
    @Test
    public void test_backup_move()
    {
        Board currentBoard = new Board();
        when(request.attribute(ConstsUI.CURRENT_USER_BOARD_PARAM)).thenReturn(currentBoard);

        Player currentUser = new Player("p1");
        when(request.attribute(ConstsUI.CURRENT_USER_PARAM)).thenReturn(currentUser);

        Player opponent = new Player("p2");
        when(session.attribute(ConstsUI.OPPONENT_PARAM)).thenReturn(opponent);

        MovePacket backupMove = currentBoard.getBackupMove();
        assertNull(backupMove);

        Move move = new Move(new Position(1,2), new Position(2,2));
        PieceWithPosition pieceWithPosition = new PieceWithPosition(new Piece(Piece.Type.SINGLE, Piece.Color.RED ), new Position(2,2));
        currentBoard.movePiece(new MovePacket(move, pieceWithPosition));
        backupMove = currentBoard.getBackupMove();
        assertNotNull(backupMove);
        when(request.attribute(ConstsUI.BACKUP_MOVE_PARAM)).thenReturn(backupMove);
    }
}