package com.webcheckers.ui;

import com.webcheckers.Model.Board;
import com.webcheckers.Model.Piece;
import com.webcheckers.Model.Player;
import com.webcheckers.Model.PlayerLobby;
import com.webcheckers.util.Message;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 *  @contributor Clayton Pruitt : chp4145@rit.edu
 *  @contributor Bryce Thompson : bxt6698@rit.edu
 */

public class GetGameRoute implements Route {

    private enum GameState {
        CHALLENGING, CHALLENGED, INGAME;
    }

    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    // Constants for the ViewModel
    public static final String VIEW_NAME = "game.ftl";
    public static final String CURRENTPLAYERBOARD_PARAM = "currentPlayerBoard";

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    /**
     * Constructor for GetGameRoute
     * @param templateEngine: holds the ViewModel
     * @param lobby: The PlayerLobby containing all the signed in players
     */
    public GetGameRoute(final TemplateEngine templateEngine, PlayerLobby lobby) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        //
        this.playerLobby = lobby;
        LOG.config("GetGameRoute is initialized.");
    }

    /**
     * Determines the state of the current Player's game.
     * @param currentPlayer the Player for whom to the determine the game state
     * @param currentPlayerBoard the Board of the Player for whom to determine the game state
     * @return current state of the game (CHALLENGING, CHALLENGED, INGAME)
     */
    private GameState determineGameState(Player currentPlayer, Board currentPlayerBoard){
        if ((currentPlayerBoard == null) && !currentPlayer.isInGame()){
            return GameState.CHALLENGING;
        } else if ((currentPlayerBoard == null)){
            return GameState.CHALLENGED;
        } else {
            return GameState.INGAME;
        }
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
     * Starts a new game between the current user and opponent user from the Home view.
     * @param request an HTTP request
     * @param lobby the PlayerLobby both Players belong to
     * @param currentUser the current user for whom the game is being started for
     * @param opponentUser the opponent user who challenged the current user
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
     * Fetches an ongoing game between the current user and opponent user then stores the game data in the current
     * user's session.
     * @param request an HTTP request
     * @param currentUser the current user for whom the game is being fetched
     * @param opponentUser the opponent to the current user
     */
    private void fetchGame(Request request, Player currentUser, Player opponentUser){
        Board board = playerLobby.getBoard(currentUser, opponentUser);
        request.session().attribute(ConstsUI.CURRENT_USER_BOARD_PARAM, board);
    }

    /**
     * Refreshes the view for an ongoing game between the current user and opponent user.
     * @param request an HTTP request that the game will be stored in
     * @param currentUser the current user who should currently be in a game
     * @param opponentUser the opponent to the current user
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
     * Handles all the happenings of GetGameRoute
     * @param request
     * @param response
     * @return templateEngine
     */
    public Object handle(Request request, Response response) {

        LOG.finer("GetGameRoute is invoked.");

        // initialize view-model
        final Map<String, Object> vm = new HashMap<>();

        // get the current user
        Player currentPlayer = request.session().attribute(ConstsUI.CURRENTUSER_PARAM);
        // check if a board currently exists for this user
        Board currentPlayerBoard = request.session().attribute(ConstsUI.CURRENT_USER_BOARD_PARAM);
        // declare opponent user
        Player opponent = null;

        /*
        There are three cases to consider.
        1) There is no board for the current player and they are not currently in a game.
            => This implies that they have just challenged an opponent and a game needs to start.
        2) There is no board for the current player and they are currently in a game.
            => This implies that they have just been challenged to a game.
        3) There is a board.
            => This implies a game is already in progress.
         */
        switch(determineGameState(currentPlayer, currentPlayerBoard)){
            case CHALLENGING:
                // Find the opponent in the PlayerLobby
                opponent = playerLobby.getPlayer(request.queryParams("opponentUsername"));
                // return an error message if the opponent is already in a game
                if(opponent.isInGame())
                {
                    GameView.buildOpponentInGameErrorView(request, response, vm);
                    response.redirect(ConstsUI.HOME_URL);
                    return templateEngine.render(new ModelAndView(vm, ConstsUI.HOME_VIEW));
                }

                // start a new game
               startNewGame(request,
                       currentPlayer,
                       opponent);

                // build the game view
                currentPlayerBoard = request.session().attribute(ConstsUI.CURRENT_USER_BOARD_PARAM);
                GameView.buildGameViewModel(
                        currentPlayer,
                        opponent,
                        currentPlayerBoard,
                        vm
                );

                return templateEngine.render(new ModelAndView(vm, ConstsUI.GAME_VIEW));

            case CHALLENGED:
                // find the Player who challenged us
                opponent = currentPlayer.getOpponent();

                // fetch the game between us and our opponent
                fetchGame(request,
                        currentPlayer,
                        opponent);

                // build the game view
                currentPlayerBoard = request.session().attribute(ConstsUI.CURRENT_USER_BOARD_PARAM);
                GameView.buildGameViewModel(
                        currentPlayer,
                        opponent,
                        currentPlayerBoard,
                        vm
                );

                return templateEngine.render(new ModelAndView(vm, ConstsUI.GAME_VIEW));
            case INGAME:
                // find the Player who we are in a game with
                opponent = currentPlayer.getOpponent();

                // refresh the game
                refreshGame(request,
                        currentPlayer,
                        opponent);

                // populate our view model
                currentPlayerBoard = request.session().attribute(ConstsUI.CURRENT_USER_BOARD_PARAM);
                GameView.buildGameViewModel(
                        currentPlayer,
                        opponent,
                        currentPlayerBoard,
                        vm
                );

                return templateEngine.render(new ModelAndView(vm, ConstsUI.GAME_VIEW));

        }

        return templateEngine.render(new ModelAndView(vm, ConstsUI.GAME_VIEW));
    }


}
