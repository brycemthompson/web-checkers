package com.webcheckers.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.Model.Board;
import com.webcheckers.Model.Player;
import com.webcheckers.Model.PlayerLobby;
import spark.*;

import com.webcheckers.util.Message;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 * @contributor Bryce Thompson : bxt6698@rit.edu
 * @contributor Clayton Pruitt : chp4145@rit.edu
 * @contributor Daniel Kitchen : djk9755@rit.edu
 */
public class GetHomeRoute implements Route {

  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  // Values used in the view-model map for rendering the home view.
  public static final String PLAYERSPLAYING_PARAM = "amountOfPlayersPlaying";

  // Various objects the route needs to track.
  private final TemplateEngine templateEngine;
  private final PlayerLobby playerLobby;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   * @param playerLobby
   */
  public GetHomeRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    //
    this.playerLobby = playerLobby;
    LOG.config("GetHomeRoute is initialized.");
  }

  /**
   * Render the WebCheckers Home page.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Home page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("GetHomeRoute is invoked.");

    // retrieve the HTTP session
    final Session session = request.session();

    // start building the view model
    Map<String, Object> vm = new HashMap<>();
    vm.put(ConstsUI.TITLE_PARAM, ConstsUI.HOME_TITLE_DEFAULT_VALUE);

    // display default welcome message only if we have nothing else to display
      Message msg = request.session().attribute(ConstsUI.MESSAGE_PARAM);
      if(msg != null)
      {
          vm.put(ConstsUI.MESSAGE_PARAM, msg);
      } else {
          vm.put(ConstsUI.MESSAGE_PARAM, ConstsUI.WELCOME_MSG);
      }


    // get current user (null if none exists)
    Player currentUser = request.session().attribute(ConstsUI.CURRENT_USER_PARAM);

    /*
    There are three cases to consider.
    1) There is a current user signed in and they are not in a game.
        => then we need to display a list of currently signed in players
    2) There is a current user signed in and they're in a game.
        => then we need to yeet them to the game view
    3) There is no current user signed in.
        => then we need to display the amount of players currently playing
     */

    /*
      // push any message (i.e. error message) to the sign in form to be displayed
      Message msg = request.session().attribute(ConstsUI.MESSAGE_PARAM);
      if(msg != null)
      {
          vm.put(ConstsUI.MESSAGE_PARAM, msg);
      }

     */
      if (currentUser != null && !currentUser.isInGame()) { // current user is not in a game

        // populate view model with current user's info
        vm.put(ConstsUI.CURRENT_USER_PARAM, currentUser);
        vm.put(ConstsUI.USERNAME_PARAM, currentUser.getName());

        // populate view model with list of players that excludes the current player
        ArrayList<String> playerNames = playerLobby.getPlayerNames();
        playerNames.remove(currentUser.getName());
        vm.put(ConstsUI.PLAYER_LIST_PARAM, playerNames);

        return templateEngine.render(new ModelAndView(vm, ConstsUI.HOME_VIEW));

    } else if (currentUser != null){ // current user is supposed to be in a game

        // find the Player who challenged us
        Player opponent = currentUser.getOpponent();

        // create our Board
        Board currentUserBoard = new Board();
        GetGameRoute.drawBoard(currentUserBoard, currentUser.getColor(), opponent.getColor());
        request.session().attribute(ConstsUI.CURRENT_USER_BOARD_PARAM, currentUserBoard);

        // populate our view model
        vm.put(ConstsUI.TITLE_PARAM, ConstsUI.WELCOME_MSG);
        vm.put(ConstsUI.CURRENT_USER_PARAM, currentUser);
        vm.put(ConstsUI.VIEW_MODE_PARAM, ConstsUI.VIEW_MODEL_DEFAULT_VALUE);
        GetGameRoute.populateViewModelPlayerData(vm, currentUser, opponent);
        vm.put(ConstsUI.BOARD_PARAM, currentUserBoard);
        vm.put(GetGameRoute.CURRENTPLAYERBOARD_PARAM, currentUserBoard);

        return templateEngine.render(new ModelAndView(vm, ConstsUI.GAME_VIEW));
    } else { // there is no current user
        int amountOfPlayersPlaying = playerLobby.size();
        vm.put(PLAYERSPLAYING_PARAM, amountOfPlayersPlaying);
        return templateEngine.render(new ModelAndView(vm, ConstsUI.HOME_VIEW));
    }

    /*
    //The name in the ftl is "username"
      if(request.queryParams("username") != null){
        Player player = new Player(request.queryParams("username") );

        //This will make sure to not have duplicates in the list, this may still be buggy, need QA to check
        if(!playerLobby.getPlayerNames().contains(player.getName())) {
            playerLobby.addPlayer(player);
            vm.put("currentUser", player);
        }else{
          String failMessage = "\nUser already exists, please choose different name";
          vm.put("failUserNameMessage", failMessage);
        }
    }
     */


    //This works but there is a bug
//    //This should display all the users that signed in to play a game, on homepage.
//    for(int i = 0; i < playerLobby.getPlayers().size(); i++){
//      String auth = playerLobby.authenticateSignIn(playerLobby.getPlayerNames().get(i)).toString();
//      if(auth.equals("SUCESSS") ){
//        String playerIsSignedIn = "playerIsSignedIn";
//        vm.put(playerIsSignedIn, playerIsSignedIn);
//        vm.put("list", names);
//      }
//      else{
//        String playerIsSignedIn = "playerIsSignedIn";
//        vm.put(playerIsSignedIn, playerIsSignedIn);
//        vm.put("list", names);
//      }
//
//    }

  }
}
