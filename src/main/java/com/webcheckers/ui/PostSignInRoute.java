package com.webcheckers.ui;

// THIS CLASS STILL NEED TO BE COMPLETED

import com.webcheckers.util.Message;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The PostSignInRoute Class Handling Username Input
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 * @author <a href='mailto:jrv@se.rit.edu'>Jim Vallino</a>
 * @contributor Bryce Thompson : bxt6698@rit.edu
 * @constributor Daniel Kitchen
 */
public class PostSignInRoute implements Route {
    // Constants
    // Values used in the view-model map for rendering the game view after a guess.
    static final String USERNAME_PARAM = "username";
    private static final Message WELCOME_MSG = Message.info("Sign In to Play!");
    private final TemplateEngine templateEngine;

    PostSignInRoute(TemplateEngine templateEngine)
    {
        this.templateEngine = templateEngine;
    }

    /**
     * Stores a username.
     * @param username
     *    the username of the signed in Player
     * @param session
     *    i have no idea what this is
     */
    private void storeUsername(String username, Session session){
        List<String> usernames = session.attribute("usernames");
        if (usernames == null){
            usernames = new ArrayList<>();
        }
        usernames.add(username);
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
        /*
        LOG.finer("GetSignInRoute is invoked.");
        //
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Welcome!");

        // display a user message in the Home page
        vm.put("message", WELCOME_MSG);

        // render the View
        return templateEngine.render(new ModelAndView(vm , "signin.ftl"));
         */

        // get username
        final String username = request.queryParams(USERNAME_PARAM);
        storeUsername(username, request.session());

        // display message on Sign In page
        final Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Welcome!");
        vm.put("message", WELCOME_MSG);

        // render the view
        return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
    }
}