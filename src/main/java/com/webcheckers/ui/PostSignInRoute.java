package com.webcheckers.ui;

// THIS CLASS STILL NEED TO BE COMPLETED

import spark.*;

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
    private final TemplateEngine templateEngine;

    PostSignInRoute(TemplateEngine templateEngine)
    {
        this.templateEngine = templateEngine;
    }

    @Override
    public String handle(Request request, Response response)
    {
        final String getUsername = request.queryParams(USERNAME_PARAM);
        return getUsername;
    }
}