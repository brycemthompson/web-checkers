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
     * Creates a new Board
     * @return new Board
     */
    private Board createBoard(){
        return new Board();
    }

    /**
     * Draws the Pieces belonging to the opponent on the given Board.
     * @param board: The Board to be drawn on
     * @param color: Color of the opponent's pieces
     */
    public static void drawOpponentPieces(Board board, Piece.Color color){
        for (int r = 0; r < 3; r++){
            for (int c = (r+1)%2; c < Board.rowsPerBoard; c += 2){
                Piece piece = new Piece(Piece.Type.SINGLE, color);
                board.addPieceToSpace(piece, c, r);
            }
        }
    }

    /**
     * Draws the Pieces belonging to the current user on the given Board.
     * @param board: The Board to be drawn on
     * @param color: Color of the current user's pieces
     */
    public static void drawCurrentUserPieces(Board board, Piece.Color color){
        for (int r = Board.rowsPerBoard - 3; r < Board.rowsPerBoard; r++){
            for (int c = (r+1)%2; c < Board.rowsPerBoard; c += 2){
                Piece piece = new Piece(Piece.Type.SINGLE, color);
                board.addPieceToSpace(piece, c, r);
            }
        }
    }

    /**
     * Draws the board so that the current user's Pieces are on the bottom and the opponent's are on the top on the
     * given Board.
     * @param board: The Board to be drawn on
     * @param currentUserColor: Color of the current user's pieces
     * @param opponentColor: Color of the opponent's pieces
     */
    public static void drawBoard(Board board, Piece.Color currentUserColor, Piece.Color opponentColor){
        drawOpponentPieces(board, opponentColor);
        drawCurrentUserPieces(board, currentUserColor);
    }

    /**
     * Appropriately populates the view model's player data given the current Player and their opponent.
     * @param vm: HashMap
     * @param currentPlayer: currentPlayer's Player object
     * @param opponent: opponent's Player object
     */
    public static void populateViewModelPlayerData(Map<String, Object> vm, Player currentPlayer, Player opponent){
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
        Player currentPlayer = request.session().attribute(PostPlayerRoute.CURRENTUSER_PARAM);
        // check if a board currently exists for this user
        Board currentPlayerBoard = request.session().attribute(CURRENTPLAYERBOARD_PARAM);

        /*
        There are three cases to consider.
        1) There is no board for the current player and they are not currently in a game.
            => This implies that they have just challenged an opponent and a game needs to start.
        2) There is no board for the current player and they are currently in a game.
            => This implies that they have just been challenged to a game.
        3) There is a board.
            => This implies a game is already in progress.
         */
        if (currentPlayerBoard == null && !currentPlayer.isInGame()){ // current user is challenging a player

            // Finding the opponent in the playerList
            final String opponentUsername = request.queryParams("opponentUsername");
            Player opponent = null;
            for(Player player: playerLobby.getPlayers())
            {
                if(player.getName().equals(opponentUsername))
                {
                    opponent = player;

                    if(opponent.isInGame()) // The player is in a game and we are sending an error message
                    {
                        vm.put(ConstsUI.TITLE_PARAM, ConstsUI.GAME_WELCOME_MSG);
                        vm.put(ConstsUI.MESSAGE_PARAM, ConstsUI.PLAYER_IN_GAME_ERROR_MSG);
                        request.session().attribute(ConstsUI.MESSAGE_PARAM, ConstsUI.PLAYER_IN_GAME_ERROR_MSG);
                        response.redirect(ConstsUI.HOME_URL);
                        return templateEngine.render(new ModelAndView(vm, ConstsUI.HOME_VIEW));
                    }
                    opponent.putInGame(currentPlayer, Piece.Color.WHITE);
                    currentPlayer.putInGame(opponent, Piece.Color.RED);
                    request.session().attribute(PostPlayerRoute.OPPONENT_PARAM, opponent);
                }
            }

            currentPlayerBoard = new Board();
            drawBoard(currentPlayerBoard, currentPlayer.getColor(), opponent.getColor());
            request.session().attribute(ConstsUI.CURRENT_USER_BOARD_PARAM, currentPlayerBoard);

            vm.put(ConstsUI.TITLE_PARAM, ConstsUI.GAME_WELCOME_MSG);
            vm.put(ConstsUI.CURRENT_USER_PARAM, currentPlayer);
            vm.put(ConstsUI.VIEW_MODE_PARAM, "PLAY");
            populateViewModelPlayerData(vm, currentPlayer, opponent);
            vm.put(ConstsUI.BOARD_PARAM, currentPlayerBoard);
            vm.put(CURRENTPLAYERBOARD_PARAM, currentPlayerBoard);
            response.redirect(ConstsUI.GAME_URL);

            return templateEngine.render(new ModelAndView(vm, ConstsUI.GAME_VIEW));
        } else if (currentPlayerBoard == null && currentPlayer.isInGame()){ // current user has been challenged
            // find the Player who challenged us
            Player opponent = currentPlayer.getOpponent();
            //System.out.println("Opponent is in game: " + opponent.isInGame());
            // create our Board
            currentPlayerBoard = new Board();
            drawBoard(currentPlayerBoard, currentPlayer.getColor(), opponent.getColor());
            request.session().attribute(ConstsUI.CURRENT_USER_BOARD_PARAM, currentPlayerBoard);

            // populate our view model
            vm.put(ConstsUI.TITLE_PARAM, ConstsUI.GAME_WELCOME_MSG);
            vm.put(ConstsUI.CURRENT_USER_PARAM, currentPlayer);
            vm.put(ConstsUI.VIEW_MODE_PARAM, "PLAY");
            populateViewModelPlayerData(vm, currentPlayer, opponent);
            vm.put(ConstsUI.BOARD_PARAM, currentPlayerBoard);
            vm.put(CURRENTPLAYERBOARD_PARAM, currentPlayerBoard);

            return templateEngine.render(new ModelAndView(vm, ConstsUI.GAME_VIEW));
        } else { // the current user and their opponent have a game in progress
            // find the Player who challenged us
            Player opponent = currentPlayer.getOpponent();
            // populate the view model
            vm.put(ConstsUI.TITLE_PARAM, ConstsUI.GAME_WELCOME_MSG);
            vm.put(ConstsUI.CURRENT_USER_PARAM, currentPlayer);
            vm.put(ConstsUI.VIEW_MODE_PARAM, "PLAY");
            populateViewModelPlayerData(vm, currentPlayer, opponent);
            vm.put(ConstsUI.BOARD_PARAM, currentPlayerBoard);
            vm.put(CURRENTPLAYERBOARD_PARAM, currentPlayerBoard);

            return templateEngine.render(new ModelAndView(vm, ConstsUI.GAME_VIEW));
        }
    }


}
