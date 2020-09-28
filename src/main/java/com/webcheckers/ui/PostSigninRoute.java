package com.webcheckers.ui;

/*
  The Route to handle POSTing signin information.

  @author Jack Thomas
 */

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

import com.webcheckers.util.Message;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;
import static spark.Spark.halt;

public class PostSigninRoute implements Route{
    private final TemplateEngine templateEngine;
    private final String ERROR = "That is not valid, try again";

    /**
     *Constructor for POST signin
     *
     * Sets up template engine
     */
    public PostSigninRoute(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }

    /**
     * Operates on the given playerName by delegating the relevant portion of the data to a method in
     * PlayerLobby. Puts the resulting Player object in the session or prompts for a valid name
     *
     * Parameters: HTTP request and response
     * Returns:templateEngine.render
     */
    @Override
    public Object handle(Request request, Response response){
        final Session session = request.session();
        final Map<String, Object> vm = new HashMap<>();
        vm.put("title","Sign In");
        Message message = Message.error(ERROR);
        vm.put("message",message);
        ModelAndView mv = new ModelAndView(vm,"signin.ftl");
        //playerlobby functionality here
        //session.attribute(GetHomeRoute.PLAYER_KEY,PLAYER)
        //if (player == null){vm.put(MESSAGE_ATTR, message);}
        //response.redirect(WebServer.HOME_URL);
        //halt();
        return templateEngine.render(mv);
    }
}
