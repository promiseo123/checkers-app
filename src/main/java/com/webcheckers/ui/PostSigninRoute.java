package com.webcheckers.ui;

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

    public PostSigninRoute(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }

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
