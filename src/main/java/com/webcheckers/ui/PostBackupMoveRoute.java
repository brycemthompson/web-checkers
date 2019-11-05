package com.webcheckers.ui;
import com.google.gson.Gson;
import com.webcheckers.Model.*;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

/**
 * PostBackupMoveRoute reverses the currentPlayer's move.
 * @author Bryce Thompson : bxt6698@rit.edu
 */

public class PostBackupMoveRoute implements Route {

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    public PostBackupMoveRoute(TemplateEngine templateEngine, PlayerLobby playerLobby)
    {
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        // get current user's board
        Board currentBoard = request.session().attribute(ConstsUI.CURRENT_USER_BOARD_PARAM);

        // Backing up the move
        MovePacket backupMove = currentBoard.getBackupMove();
        System.out.println("backup move: " + backupMove);
        Message backupMoveMessage;

        // Checking if the backup move exists
        if(backupMove == null) // If there is no move backed up, send an error message
        {
            backupMoveMessage = ConstsUI.BACKUPMOVE_FAIL_ERROR_MSG;
        }
        else // Otherwise, reverse the move and send the success message
        {
            backupMoveMessage = ConstsUI.BACKUPMOVE_SUCCESSFUL_MSG;
            request.session().attribute(ConstsUI.BACKUP_MOVE_PARAM, backupMove);
            currentBoard.movePiece(backupMove);
            // if a Piece was jumped, add it back
            if (backupMove.getJumpedPiece() != null){
                Piece jumpedPiece = backupMove.getJumpedPiece();
                Position jumpedPiecePosition = backupMove.getJumpedPiecePosition();
                currentBoard.addPieceToSpace(jumpedPiece, jumpedPiecePosition.getCell(), jumpedPiecePosition.getRow());
            }
        }

        return new Gson().toJson(backupMoveMessage);
    }
}
