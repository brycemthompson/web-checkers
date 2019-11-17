package com.webcheckers.ui;

import com.webcheckers.Model.Board;
import com.webcheckers.Model.Piece;
import com.webcheckers.Model.Player;
import com.webcheckers.Model.PlayerLobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-Tier")
/**
 * Unit Test for the GetGameRoute class
 */
public class GetGameRouteTest {

    /**
     * Private fields
     */
    private TemplateEngineTester templateEngineTester = new TemplateEngineTester();
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private Request request;
    private GetGameRoute CuT;
    private Response response;
    private Session session;

    /**
     * Game state enumeration
     */
    public enum GameState {
        CHALLENGING, CHALLENGED, INGAME;
    }

    /**
     * Starts a new game between the current user and opponent user then stores the game data in the current user's
     * session.
     * @param request an HTTP request
     * @param currentUser the current user for whom the game is being built
     * @param opponentUser the opponent to the current user
     */
    private void startNewGame(Request request, Player currentUser, Player opponentUser){
        // set up players
        opponentUser.putInGame(currentUser, Piece.Color.WHITE);
        currentUser.putInGame(opponentUser, Piece.Color.RED);
        request.session().attribute(ConstsUI.OPPONENT_PARAM, opponentUser);

        // set up board
        Board newBoard = GameView.initializeBoard(currentUser, opponentUser);
        request.session().attribute(ConstsUI.CURRENT_USER_BOARD_PARAM, newBoard);
        playerLobby.addBoard(newBoard);
    }

    /**
     * Helper function to begin a new game
     * @param request: an HTTP request
     * @param lobby: the player lobby containing the players
     * @param currentUser: the current user used to build the game
     * @param opponentUser: the opponent user used to build the game
     */
    public static void startNewGameFromHome(Request request, PlayerLobby lobby, Player currentUser, Player opponentUser){
        // create our Board
        Board board;
        if (currentUser.getColor() == Piece.Color.RED){
            board = lobby.getBoard(currentUser, opponentUser);
        } else {
            board = lobby.getBoard(opponentUser, currentUser);
        }
        request.session().attribute(ConstsUI.CURRENT_USER_BOARD_PARAM, board);
    }

    /**
     * Helper function to get the game from the session
     * @param request: an HTTP request
     * @param currentUser: the current user of the game
     * @param opponentUser: the opponent of the current user of the game
     */
    private void fetchGame(Request request, Player currentUser, Player opponentUser){
        Board board = playerLobby.getBoard(currentUser, opponentUser);
        request.session().attribute(ConstsUI.CURRENT_USER_BOARD_PARAM, board);
    }

    /**
     * Helper function to refresh the current game
     * @param request: an HTTP request
     * @param currentUser: the current user of the game
     * @param opponentUser: the opponent of the current user of the game
     */
    private void refreshGame(Request request, Player currentUser, Player opponentUser){
        // find our Board
        Board board;
        if (currentUser.getColor() == Piece.Color.RED){
            board = playerLobby.getBoard(currentUser, opponentUser);
        } else {
            board = playerLobby.getBoard(opponentUser, currentUser);
        }
        request.session().attribute(ConstsUI.CURRENT_USER_BOARD_PARAM, board);
    }

    /**
     * unit test setup function to initialize all of the items of the class
     */
    @BeforeEach
    public void setup() {

        request = mock(Request.class);
        response = mock(Response.class);
        templateEngine = mock(TemplateEngine.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);

        playerLobby = new PlayerLobby();
        CuT = new GetGameRoute(templateEngine, playerLobby);
        templateEngineTester = new TemplateEngineTester();
    }

    /**
     * test function that currently checks for the session attributes and checks for the correct
     * view name
     */
    @Test
    public void challengingGameState_test() {

        Map<String, Object> vm = new HashMap<>();

        Player player = new Player("P1");
        Board currentPlayerBoard = null;

        when(session.attribute(ConstsUI.CURRENTUSER_PARAM)).thenReturn(player);

        when(session.attribute(ConstsUI.CURRENT_USER_BOARD_PARAM)).thenReturn(currentPlayerBoard);

        Player opponent = new Player("P2");

        Player player3 = new Player("P3");
        player3.putInGame(opponent, Piece.Color.RED);
        playerLobby.addPlayer(player);
        playerLobby.addPlayer(opponent);
        playerLobby.addPlayer(player3);

        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());
        when(request.queryParams("opponentUsername")).thenReturn("P3");

        CuT.handle(request, response);

        templateEngineTester.assertViewName("home.ftl");

        //Something is missing here idk David will help probably
    }

    /**
     * test function that currently creates a board and a new game, testing the session attributes and game view name
     */
    @Test
    public void challengingGameState_test_2() {

        Map<String, Object> vm = new HashMap<>();

        Player currentPlayer = new Player("Isaias");
        Board currentPlayerBoard = new Board();

        when(session.attribute(ConstsUI.CURRENTUSER_PARAM)).thenReturn(currentPlayer);

        when(session.attribute(ConstsUI.CURRENT_USER_BOARD_PARAM)).thenReturn(currentPlayerBoard);

        Player opponent = null;

        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());

        CuT.handle(request, response);

        opponent = playerLobby.getPlayer(request.queryParams("opponentUsername"));
        // start a new game
        startNewGame(request,
                currentPlayer,
                opponent);

       when(session.attribute(ConstsUI.CURRENT_USER_BOARD_PARAM)).thenReturn(currentPlayerBoard);

       GameView.buildGameViewModel(
               currentPlayer,
               opponent,
               currentPlayerBoard,
               vm
       );
        templateEngineTester.assertViewName("game.ftl");

    }
}
