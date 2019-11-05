package com.webcheckers.ui;

import com.webcheckers.util.Message;

/**
 * @author Clayton Pruitt : chp4145@rit.edu
 */

public final class ConstsUI {

    /**
     * Parameters
     */
    public static final String BOARD_PARAM = "board";
    public static final String CURRENT_USER_PARAM = "currentUser";
    public static final String CURRENT_USER_BOARD_PARAM = "currentUserBoard";
    public static final String MESSAGE_PARAM = "message";
    public static final String PLAYER_LIST_PARAM = "allPlayers";
    public static final String TITLE_PARAM = "title";
    public static final String USERNAME_PARAM = "username";
    public static final String VIEW_MODE_PARAM = "viewMode";
    public static final String DISPLAYING_ERROR_PARAM = "displayingError";
    public static final String ACTION_DATA_PARAM = "actionData";
    public static final String CURRENTUSER_PARAM = "currentUser";
    public static final String OPPONENT_PARAM = "opponent";
    public static final String PROPOSED_MOVES_PARAM = "proposedMoves";
    public static final String BACKUP_MOVE_PARAM = "backupMove";

    /**
     * Default Values
     */
    public static final String DEFAULT_WELCOME_FOR_TITLE = "Welcome!";
    public static final String HOME_TITLE_DEFAULT_VALUE = "Welcome to Home Page!";
    public static final String SIGN_IN_TITLE_DEFAULT_VALUE = "Sign in to play!";
    public static final String VIEW_MODEL_DEFAULT_VALUE = "PLAY";

    /**
     * Messages
     */
    public static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");
    public static final Message GAME_WELCOME_MSG = Message.info("Welcome to the game page!");
    public static final Message SIGN_IN_MSG = Message.info("Sign in to play!");
    public static final Message SUBMIT_SUCCESSFUL_MSG = Message.info("Submission Successful!");
    public static final Message BACKUPMOVE_SUCCESSFUL_MSG = Message.info("The move has been reversed.");
    public static final Message PLAYER_IN_GAME_ERROR_MSG = Message.error("The chosen player is already in a game.");
    public static final Message BACKUPMOVE_FAIL_ERROR_MSG = Message.error("The backup move failed.");
    public static final Message JUMP_REQUIRED_ERROR_MSG = Message.error("Invalid Turn. Jump move available.");
    public static final Message SIGNIN_FAILED_INVALID_MSG = Message.info("Username must contain only alphanumeric character.");
    public static final Message SIGNIN_FAILED_NAME_TAKEN_MSG = Message.info("Username taken. Please enter a unique username.");
    public static final Message SIGNIN_FAILED_UNKNOWN_MSG = Message.info("Unknown error. Please try another username.");

    /**
     * VIEWS
     */
    public static final String GAME_VIEW = "game.ftl";
    public static final String HOME_VIEW = "home.ftl";
    public static final String SIGNIN_VIEW = "signin.ftl";
    public static final String SIGNOUT_VIEW = "signout.ftl";

    /**
     * URLS
     */
    public static final String GAME_URL = "/game";
    public static final String HOME_URL = "/";
    public static final String SIGNIN_URL = "/signin";
    public static final String SIGNOUT_URL = "/signout";
    public static final String VALIDATEMOVE_URL = "/validateMove";
    public static final String SUBMITTURN_URL = "/submitTurn";
    public static final String CHECKTURN_URL = "/checkTurn";
    public static final String BACKUPMOVE_URL = "/backupMove";
}
