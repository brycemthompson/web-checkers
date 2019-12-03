package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.Model.Board;
import com.webcheckers.Model.Piece;
import com.webcheckers.Model.Player;
import spark.Request;
import spark.Response;

import java.util.HashMap;
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
        b.setActiveColor(Piece.Color.RED);
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
    }

/*
     * Build the given view-model to reflect Game View.
     * Build the given view-model to reflext Game View
     * @param currentPlayer: The player
     * @param opponentPlayer: The opponent
     * @param currentPlayerBoard: The board
     * @param vm: view-model to build
     */
    public static void buildGameViewModel(Player currentPlayer, Player opponentPlayer,
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
     * Updates the given view-model to update that Game View to reflect that the game has been won.
     */
    public static void buildGameOverView(Player currentPlayer, Player opponentPlayer,
                                         Board currentPlayerBoard,
                                         Map<String, Object> vm){
        // determine who won the game
        Player winner;
        switch (currentPlayerBoard.getWinningColor()){
            case WHITE:
                if (currentPlayer.getColor() == Piece.Color.WHITE){
                    winner = currentPlayer;
                } else {
                    winner = opponentPlayer;
                }
                break;
            case RED:
                if (currentPlayer.getColor() == Piece.Color.RED){
                    winner = currentPlayer;
                } else {
                    winner = opponentPlayer;
                }
                break;
            default:
                return;
        }

        // build the game over message
        String gameOverMessage;
        if (winner == currentPlayer){
            gameOverMessage = "You have captured all of your opponent's pieces!";
        } else {
            gameOverMessage = winner.getName() + " has captured all of your pieces.";
        }

        // build the modeOptionsAsJSON map and put it into the view-model
        final Map<String, Object> modeOptions = new HashMap<>(2);
        modeOptions.put("isGameOver", true);
        modeOptions.put("gameOverMessage", gameOverMessage);
        vm.put("modeOptionsAsJSON", new Gson().toJson(modeOptions));
    }

    /**
     * Updates the given view-model to update that Game View to reflect that the opponent has resigned.
     */
    public static void buildOpponentResignedView(Player opponentPlayer, Map<String, Object> vm){

        // build the game over message
        String gameOverMessage = opponentPlayer.getName() + " has resigned.";

        // build the modeOptionsAsJSON map and put it into the view-model
        final Map<String, Object> modeOptions = new HashMap<>(2);
        modeOptions.put("isGameOver", true);
        modeOptions.put("gameOverMessage", gameOverMessage);
        vm.put("modeOptionsAsJSON", new Gson().toJson(modeOptions));
    }

    public static void buildWeResignedView(Request request, Map<String, Object> vm){
        vm.put(ConstsUI.TITLE_PARAM, ConstsUI.GAME_WELCOME_MSG);
        vm.put(ConstsUI.MESSAGE_PARAM, ConstsUI.RESIGNATION_SUCCESSFUL_MSG);
        request.session().attribute(ConstsUI.MESSAGE_PARAM, ConstsUI.RESIGNATION_SUCCESSFUL_MSG);
    }
    /*
     * Build the given view-model to reflect a Game Error for the opponent
     * @param request: the session request object
     * @param response: the session response object
     * @param vm: the view-model to build
     */
    public static void buildOpponentInGameErrorView(Request request, Response response, Map<String, Object> vm){
        vm.put(ConstsUI.TITLE_PARAM, ConstsUI.GAME_WELCOME_MSG);
        vm.put(ConstsUI.MESSAGE_PARAM, ConstsUI.PLAYER_IN_GAME_ERROR_MSG);
        request.session().attribute(ConstsUI.MESSAGE_PARAM, ConstsUI.PLAYER_IN_GAME_ERROR_MSG);
    }

}
