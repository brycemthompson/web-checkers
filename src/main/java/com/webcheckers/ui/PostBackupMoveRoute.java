package com.webcheckers.ui;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

public class PostBackupMoveRoute implements Route {

    private final TemplateEngine templateEngine;

    public PostBackupMoveRoute(TemplateEngine templateEngine)
    {
        this.templateEngine = templateEngine;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return null;
    }
}
