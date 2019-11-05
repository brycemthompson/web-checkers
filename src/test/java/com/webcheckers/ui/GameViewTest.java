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

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameViewTest {

    private TemplateEngineTester templateEngineTester = new TemplateEngineTester();
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private Request request;
    private GameView CuT;
    private Response response;
    private Session session;

    public Board initializeBoard(Player redPlayer, Player whitePlayer){
        Board b = new Board();
        b.setRedPlayer(redPlayer);
        b.setWhitePlayer(whitePlayer);
        b.setActiveColor(Piece.Color.RED);
        return b;
    }


    public void populateViewModelPlayerData(Map<String, Object> vm, Player currentPlayer, Player opponent){
        if (currentPlayer.getColor() == Piece.Color.RED){
            vm.put("redPlayer", currentPlayer);
            vm.put("whitePlayer", opponent);
        } else {
            vm.put("redPlayer", opponent);
            vm.put("whitePlayer", currentPlayer);
        }
    }


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

    public void buildOpponentInGameErrorView(Request request, Response response, Map<String, Object> vm){
        vm.put(ConstsUI.TITLE_PARAM, ConstsUI.GAME_WELCOME_MSG);
        vm.put(ConstsUI.MESSAGE_PARAM, ConstsUI.PLAYER_IN_GAME_ERROR_MSG);
        request.session().attribute(ConstsUI.MESSAGE_PARAM, ConstsUI.PLAYER_IN_GAME_ERROR_MSG);
    }

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

    @Test
    public void test_populateViewModelPlayerData(){
        Map<String, Object> vm = new HashMap<>();

        Player redPlayer = new Player("redPlayer");
        redPlayer.color = Piece.Color.RED;
        Player whitePlayer = new Player("whitePlayer");
        whitePlayer.color = Piece.Color.WHITE;

        populateViewModelPlayerData(vm, redPlayer, whitePlayer);

        templateEngineTester.assertViewModelAttribute();

//        redPlayer.color, board.getActiveColor());
    }

}
