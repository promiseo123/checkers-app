package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.halt;

/**
 * The UI Controller to GET the Game render page.
 */
public class GetGameRoute implements Route {

    // --------------------------------- VARIABLES --------------------------------- //

    private PlayerLobby lobby;
    private TemplateEngine templateEngine;

    // --------------------------------- CONSTRUCTORS --------------------------------- //

    /**
     * Constructor for GetStartGame route, sets up lobby and template engine
     *
     * @param lobby     The PlayerLobby of all players
     * @param engine    The template engine used in view/model interactions
     */
    public GetGameRoute(PlayerLobby lobby, TemplateEngine engine) {
        this.lobby = lobby;
        this.templateEngine = engine;
    }

    // --------------------------------- METHODS --------------------------------- //

    /**
     * handle: Begins the build process for the game page, determines whether a game can/will actually be started or not
     *         and then defers to the GetHomeRoute
     *
     * @param request       HTTP Request
     * @param response      HTTP Response
     * @return              Either null or the template engine render
     */
    @Override
    public Object handle(Request request, Response response){
        final Session session = request.session();

        return null;
    }
}
