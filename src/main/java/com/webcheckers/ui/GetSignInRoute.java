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
 * @contributor Bryce Thompson : bxt6698@rit.edu
 * @contributor Clayton Pruitt : chp4145@rit.edu
 */
public class GetSignInRoute implements Route {

  private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

  // Various objects the route needs to keep track of.
  private final TemplateEngine templateEngine;
  private PlayerLobby playerLobby;


  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   * @param playerLobby
   */
  public GetSignInRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.playerLobby = playerLobby;
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

    // start building the view model
    final Map<String, Object> vm = new HashMap<>();
    vm.put(ConstsUI.TITLE_PARAM, ConstsUI.SIGN_IN_TITLE_DEFAULT_VALUE);
    vm.put(ConstsUI.MESSAGE_PARAM, ConstsUI.SIGN_IN_MSG);

    // push any message (i.e. error message) to the sign in form to be displayed
    Message msg = request.session().attribute(ConstsUI.MESSAGE_PARAM);
    if(msg != null)
    {
        vm.put(ConstsUI.MESSAGE_PARAM, msg);
    }

    // render the View
    return templateEngine.render(new ModelAndView(vm , ConstsUI.SIGNIN_VIEW));

  }
}
