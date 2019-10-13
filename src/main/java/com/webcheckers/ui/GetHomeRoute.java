package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

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

  // Values used in the view-model map for rendering the home view.
  static final String SIGNED_IN_PLAYER_ATTR = "username";
  static final String PLAYERSIGNEDIN_PARAM = "playerIsSignedIn";

  // idk what these are
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

  private static final String PLAYERSPLAYING_PARAM = "amountOfPlayersPlaying";

  public static final String VIEW_NAME = "home.ftl";

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
    vm.put("title", "Welcome!");
    vm.put("message", WELCOME_MSG);
    //The name  in the ftl is "name"
      if(request.queryParams("username") != null ){
      Player player = new Player(request.queryParams("username") );
      playerLobby.addPlayer(player);
      vm.put("currentUser", player);
    }
    int amountOfPlayersPlaying = playerLobby.size();
    vm.put(PLAYERSPLAYING_PARAM, amountOfPlayersPlaying);

    // render the View
    return templateEngine.render(new ModelAndView(vm, "home.ftl"));
  }
}
