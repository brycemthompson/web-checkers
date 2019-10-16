package com.webcheckers.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.Model.Authentication;
import com.webcheckers.Model.Player;
import com.webcheckers.Model.PlayerLobby;
import spark.*;

import com.webcheckers.util.Message;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 * @contributor Bryce Thompson : bxt6698@rit.edu
 * @contributor Clayton Pruitt: chp4145@ritledu
 */
public class GetHomeRoute implements Route {

  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  // Values used in the view-model map for rendering the home view.
  static final String SIGNED_IN_PLAYER_ATTR = "username";
  static final String PLAYERSIGNEDIN_PARAM = "playerIsSignedIn";
  private static final String PLAYERSPLAYING_PARAM = "amountOfPlayersPlaying";
  public static final String VIEW_NAME = "home.ftl";

  // Messages
  private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");


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
    vm.put("title", "Welcome to Home Page!");
    vm.put("message", WELCOME_MSG);

    // get current user (null if none exists)
    Player currentUser = request.session().attribute(PostHomeRoute.CURRENTUSER_PARAM);

    if (currentUser != null){
        // populate view model
        vm.put(PostHomeRoute.CURRENTUSER_PARAM, currentUser);
        vm.put(PostHomeRoute.USERNAME_PARAM, currentUser.getName());
        ArrayList<String> playerNames = playerLobby.getPlayerNames();
        playerNames.remove(currentUser.getName()); // do not want to play the current user
        vm.put(PostHomeRoute.PLAYERLIST_PARAM, playerNames);
        return templateEngine.render(new ModelAndView(vm, "home.ftl"));
    } else {
        int amountOfPlayersPlaying = playerLobby.size();
        vm.put(PLAYERSPLAYING_PARAM, amountOfPlayersPlaying);
        return templateEngine.render(new ModelAndView(vm, "home.ftl"));
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
