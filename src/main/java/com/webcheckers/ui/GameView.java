package com.webcheckers.ui;

import com.webcheckers.Model.Board;
import com.webcheckers.Model.Piece;
import com.webcheckers.Model.Player;
import spark.Request;
import spark.Response;

import java.util.Map;

/**
 * This class contains methods that handle building, updating, and generally managing the Game View.
 * @author Clayton Pruitt : chp415@g.rit.edu
 */

public class GameView {

    /**
     * Initializes a Board with the given red and white Players.
     * @param redPlayer the Board's red Player
     * @param whitePlayer the Board's white Player
     * @return a Board with the given white and red Players
     */
    public static Board initializeBoard(Player redPlayer, Player whitePlayer){
        Board b = new Board();
        b.setRedPlayer(redPlayer);
        b.setWhitePlayer(whitePlayer);
        return b;
    }

    /**
     * Appropriately populates the view model's player data given the current Player and their opponent.
     * @param vm: HashMap
     * @param currentPlayer: currentPlayer's Player object
     * @param opponent: opponent's Player object
     */
    private static void populateViewModelPlayerData(Map<String, Object> vm, Player currentPlayer, Player opponent){
        if (currentPlayer.getColor() == Piece.Color.RED){
            vm.put("redPlayer", currentPlayer);
            vm.put("whitePlayer", opponent);
        } else {
            vm.put("redPlayer", opponent);
            vm.put("whitePlayer", currentPlayer);
        }
        vm.put("activeColor", Piece.Color.RED);
    }

    /**
     * Build the given view-model to reflect Game View.
     * @param vm view-model to build
     */
    public static void buildGameViewModel(Player currentPlayer, Player opponentPlayer,
                                          Board currentPlayerBoard,
                                          Map<String, Object> vm){
        vm.put(ConstsUI.TITLE_PARAM, ConstsUI.GAME_WELCOME_MSG);
        vm.put(ConstsUI.CURRENT_USER_PARAM, currentPlayer);
        vm.put(ConstsUI.VIEW_MODE_PARAM, "PLAY");
        vm.put(ConstsUI.BOARD_PARAM, currentPlayerBoard);
        vm.put(ConstsUI.CURRENT_USER_BOARD_PARAM, currentPlayerBoard);
        populateViewModelPlayerData(vm, currentPlayer, opponentPlayer);
    }

    public static void buildOpponentInGameErrorView(Request request, Response response, Map<String, Object> vm){
        vm.put(ConstsUI.TITLE_PARAM, ConstsUI.GAME_WELCOME_MSG);
        vm.put(ConstsUI.MESSAGE_PARAM, ConstsUI.PLAYER_IN_GAME_ERROR_MSG);
        request.session().attribute(ConstsUI.MESSAGE_PARAM, ConstsUI.PLAYER_IN_GAME_ERROR_MSG);
        response.redirect(ConstsUI.HOME_URL);
    }

}
