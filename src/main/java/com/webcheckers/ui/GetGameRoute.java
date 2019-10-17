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

public class GetGameRoute implements Route {

    // idk what these are
    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    private static final Message WELCOME_MSG = Message.info("Welcome to the game page.");

    /*
    private PlayerLobby players;

    // These two need to be passed in forming their own "game"
    private Player redPlayer = players.getPlayers().get(0);
    private Player whitePlayer = players.getPlayers().get(1);

    private String currentUser = redPlayer.toString();

    private String activeColor = "RED";

     */

    public static final String VIEW_NAME = "game.ftl";

    public static final String CURRENTPLAYERBOARD_PARAM = "currentPlayerBoard";

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;


    public GetGameRoute(final TemplateEngine templateEngine, PlayerLobby lobby) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        //
        this.playerLobby = lobby;
        LOG.config("GetGameRoute is initialized.");
    }

    /**
     * @return new Board
     */
    private Board createBoard(){
        return new Board();
    }

    /**
     * Draws the Pieces belonging to the opponent on the given Board.
     */
    public static void drawOpponentPieces(Board board, Piece.Color color){
        for (int r = 0; r < 2; r++){
            for (int c = r%2; c < Board.rowsPerBoard; c += 2){
                Piece piece = new Piece(Piece.Type.SINGLE, color);
                board.addPieceToSpace(piece, c, r);
            }
        }
    }

    /**
     * Draws the Pieces belonging to the current user on the given Board.
     */
    public static void drawCurrentUserPieces(Board board, Piece.Color color){
        for (int r = Board.rowsPerBoard - 2; r < Board.rowsPerBoard; r++){
            for (int c = r%2; c < Board.rowsPerBoard; c += 2){
                Piece piece = new Piece(Piece.Type.SINGLE, color);
                board.addPieceToSpace(piece, c, r);
            }
        }
    }

    /**
     * Draws the board so that the current user's Pieces are on the bottom and the opponent's are on the top on the
     * given Board.
     */
    public static void drawBoard(Board board, Piece.Color currentUserColor, Piece.Color opponentColor){
        drawOpponentPieces(board, opponentColor);
        drawCurrentUserPieces(board, currentUserColor);
    }

    /**
     * Appropriately populates the view model's player data given the current Player and their opponent.
     */
    public static void populateViewModelPlayerData(Map<String, Object> vm, Player currentPlayer, Player opponent){
        if (currentPlayer.getColor() == Piece.Color.RED){
            vm.put("redPlayer", currentPlayer);
            vm.put("whitePlayer", opponent);
        } else {
            vm.put("redPlayer", opponent);
            vm.put("whitePlayer", currentPlayer);
        }
        vm.put("activeColor", "red");
    }

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
        if (currentPlayerBoard == null && !currentPlayer.isInGame()){

            // Finding the opponent in the playerList
            final String opponentUsername = request.queryParams("opponentUsername");
            Player opponent = null;
            for(Player player: playerLobby.getPlayers())
            {
                if(player.getName().equals(opponentUsername))
                {
                    if(player.isInGame()) // The player is in a game and we are sending an error message
                    {
                        vm.put("title", WELCOME_MSG);
                        vm.put("message", PostPlayerRoute.PLAYER_IN_GAME_ERROR_MSG);
                        return templateEngine.render(new ModelAndView(vm, "home.ftl"));
                    }
                    else // The player is not in a game and therefore will start the game
                    {
                        opponent = player;
                        opponent.putInGame(currentPlayer, Piece.Color.WHITE);
                        currentPlayer.putInGame(opponent, Piece.Color.RED);
                        request.session().attribute(PostPlayerRoute.OPPONENT_PARAM, opponent);
                    }
                }
            }

            currentPlayerBoard = new Board();
            drawBoard(currentPlayerBoard, currentPlayer.getColor(), opponent.getColor());

            vm.put("title", WELCOME_MSG);
            vm.put("currentUser", currentPlayer);
            vm.put("viewMode", "PLAY");
            populateViewModelPlayerData(vm, currentPlayer, opponent);
            vm.put("board", currentPlayerBoard);
            vm.put(CURRENTPLAYERBOARD_PARAM, currentPlayerBoard);

            return templateEngine.render(new ModelAndView(vm, "game.ftl"));
        } else if (currentPlayerBoard == null && currentPlayer.isInGame()){
            // find the Player who challenged us
            Player opponent = currentPlayer.getOpponent();
            // create our Board
            currentPlayerBoard = new Board();
            drawBoard(currentPlayerBoard, currentPlayer.getColor(), opponent.getColor());
            // populate our view model
            vm.put("title", WELCOME_MSG);
            vm.put("currentUser", currentPlayer);
            vm.put("viewMode", "PLAY");
            populateViewModelPlayerData(vm, currentPlayer, opponent);
            vm.put("board", currentPlayerBoard);
            vm.put(CURRENTPLAYERBOARD_PARAM, currentPlayerBoard);

            return templateEngine.render(new ModelAndView(vm, "game.ftl"));
        } else {
            // find the Player who challenged us
            Player opponent = currentPlayer.getOpponent();
            // populate the view model
            vm.put("title", WELCOME_MSG);
            vm.put("currentUser", currentPlayer);
            vm.put("viewMode", "PLAY");
            populateViewModelPlayerData(vm, currentPlayer, opponent);
            vm.put("board", currentPlayerBoard);
            vm.put(CURRENTPLAYERBOARD_PARAM, currentPlayerBoard);

            return templateEngine.render(new ModelAndView(vm, "game.ftl"));
        }
    }


}
