package com.webcheckers.ui;

import com.webcheckers.util.Message;

public final class ConstsUI {

    /**
     * Parameters
     */
    public static final String BOARD_PARAM = "board";
    public static final String CURRENT_USER_PARAM = "currentUser";
    public static final String MESSAGE_PARAM = "message";
    public static final String PLAYER_LIST_PARAM = "allPlayers";
    public static final String TITLE_PARAM = "title";
    public static final String USERNAME_PARAM = "username";
    public static final String VIEW_MODE_PARAM = "viewMode";

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
    public static final Message SIGN_IN_MSG = Message.info("Sign in to play!");

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
}
