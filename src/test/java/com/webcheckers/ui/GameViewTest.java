package com.webcheckers.ui;

import com.webcheckers.Model.Board;
import com.webcheckers.Model.Piece;
import com.webcheckers.Model.Player;
import com.webcheckers.Model.PlayerLobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-Tier")
/**
 * Unit Test Class for GameView Class
 */
public class GameViewTest {

    /**
     * Private Fields
     */
    private TemplateEngineTester templateEngineTester = new TemplateEngineTester();
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private Request request;
    private GameView CuT;
    private Response response;
    private Session session;

    /**
     * Initialization for the Board
     * @param redPlayer: Red Player object
     * @param whitePlayer: White Player object
     * @return: new Board containing the two players
     */
    public Board initializeBoard(Player redPlayer, Player whitePlayer){
        Board b = new Board();
        b.setRedPlayer(redPlayer);
        b.setWhitePlayer(whitePlayer);
        b.setActiveColor(Piece.Color.RED);
        return b;
    }

    /**
     * Helper function to populate the view model
     * @param vm: the view model to populate
     * @param currentPlayer: the current player item
     * @param opponent: the opponent item
     */
    public void populateViewModelPlayerData(Map<String, Object> vm, Player currentPlayer, Player opponent){
        if (currentPlayer.getColor() == Piece.Color.RED){
            vm.put("redPlayer", currentPlayer);
            vm.put("whitePlayer", opponent);
        } else {
            vm.put("redPlayer", opponent);
            vm.put("whitePlayer", currentPlayer);
        }
    }

    /**
     * Helper function to construct the view model
     * @param currentPlayer: the current player item
     * @param opponentPlayer: the opponent item
     * @param currentPlayerBoard: the board item
     * @param vm: the view model to build
     */
    public void buildGameViewModel(Player currentPlayer, Player opponentPlayer,
                                          Board currentPlayerBoard,
                                          Map<String, Object> vm){
        vm.put(ConstsUI.TITLE_PARAM, ConstsUI.GAME_WELCOME_MSG);
        vm.put(ConstsUI.CURRENT_USER_PARAM, currentPlayer);
        vm.put(ConstsUI.VIEW_MODE_PARAM, "PLAY");
        vm.put(ConstsUI.BOARD_PARAM, currentPlayerBoard);
        vm.put(ConstsUI.CURRENT_USER_BOARD_PARAM, currentPlayerBoard);
        vm.put("activeColor", currentPlayerBoard.getActiveColor());
        populateViewModelPlayerData(vm, currentPlayer, opponentPlayer);
    }

    /**
     * Helper function to construct a game error view for the opponent
     * @param request: the view model's request item
     * @param response: the view model's response item
     * @param vm: the view model to construct
     */
    public void buildOpponentInGameErrorView(Request request, Response response, Map<String, Object> vm){
        vm.put(ConstsUI.TITLE_PARAM, ConstsUI.GAME_WELCOME_MSG);
        vm.put(ConstsUI.MESSAGE_PARAM, ConstsUI.PLAYER_IN_GAME_ERROR_MSG);
        request.session().attribute(ConstsUI.MESSAGE_PARAM, ConstsUI.PLAYER_IN_GAME_ERROR_MSG);
    }

    /**
     * Unit Test setup function to instantiate the items of the view model and to prepare a board
     */
    @BeforeEach
    public void setup() {

        request = mock(Request.class);
        response = mock(Response.class);
        templateEngine = mock(TemplateEngine.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);

        playerLobby = new PlayerLobby();
        templateEngineTester = new TemplateEngineTester();
    }

    /**
     * Test function building the components for the board, and using those components, instantiating the board
     * item
     */
    @Test
    public void test_initializeBoard(){
        Player redPlayer = new Player("redPlayer");
        redPlayer.color = Piece.Color.RED;
        Player whitePlayer = new Player("whitePlayer");
        whitePlayer.color = Piece.Color.WHITE;

        Board board = initializeBoard(redPlayer, whitePlayer);

        assertEquals(redPlayer.color, board.getActiveColor());
        assertEquals(whitePlayer.color, board.getWhitePlayer().color);
        assertTrue(board instanceof Board);
    }

    /**
     * Test function to test for the population of the view model containing the player data
     */
    @Test
    public void test_populateViewModelPlayerData(){
        Map<String, Object> vm = new HashMap<>();

        Player redPlayer = new Player("redPlayer");
        redPlayer.color = Piece.Color.RED;
        Player whitePlayer = new Player("whitePlayer");
        whitePlayer.color = Piece.Color.WHITE;

        populateViewModelPlayerData(vm, redPlayer, whitePlayer);

//        templateEngineTester.assertViewModelAttribute();

//        redPlayer.color, board.getActiveColor());
    }

}
