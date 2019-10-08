package com.webcheckers.ui;

import java.util.*;
import java.util.logging.Logger;

import com.webcheckers.Model.PlayerLobby;
import spark.*;

import com.webcheckers.util.Message;

/**
 * The UI Controller to GET the Sign In page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 * @contributor Bryce Thompson
 * @contributor Clayton Pruitt
 */
public class GetSignInRoute implements Route {

  private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

  private static final Message WELCOME_MSG = Message.info("Sign In to Play!");

  private final TemplateEngine templateEngine;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetSignInRoute(final TemplateEngine templateEngine) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    //
    LOG.config("GetSignInRoute is initialized.");
  }

  /**
   * Render the WebCheckers Sign In page.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Sign In page
   */
  @Override
  public Object handle(Request request, Response response) {

    LOG.finer("GetSignInRoute is invoked.");

    // retrieve the HTTP session
    final Session session = request.session();

    // start building the view model
    final Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Welcome!");
    vm.put("message", WELCOME_MSG);

    // retrieve the player lobby we initialized in the GetHomeRoute
    final PlayerLobby playerLobby = session.attribute(PlayerLobby.PLAYERLOBBY_KEY);

    if (playerLobby != null){
      String username = request.queryParams("username");
      playerLobby.addPlayer(username);
      LOG.finer("Added " + username + " to lobby.");
    }

    // render the View
    return templateEngine.render(new ModelAndView(vm , "signin.ftl"));

  }
}
