package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

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

  private final TemplateEngine templateEngine;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetHomeRoute(final TemplateEngine templateEngine) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    //
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

    // if this a brand new session, create a new lobby
    if (session.attribute(PlayerLobby.PLAYERLOBBY_KEY) == null){
      final PlayerLobby playerLobby = new PlayerLobby();
      session.attribute(PlayerLobby.PLAYERLOBBY_KEY, playerLobby);
      vm.put(PLAYERSIGNEDIN_PARAM, Boolean.FALSE);
    }

    // render the View
    return templateEngine.render(new ModelAndView(vm , "home.ftl"));
  }
}
