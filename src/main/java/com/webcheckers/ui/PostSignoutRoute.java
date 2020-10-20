package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.logging.Logger;

import static spark.Spark.halt;

public class PostSignoutRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostSignoutRoute.class.getName());

    private final PlayerLobby playerLobby;


    // --------------------------------- CONSTRUCTOR --------------------------------- //

    /**
     * Constructor for GetSignOutRoute
     *
     * Sets up playerLobby
     */
    public PostSignoutRoute(final PlayerLobby playerLobby) {
        this.playerLobby = playerLobby;
        LOG.config("PostSignoutRoute is initialized.");
    }

    // --------------------------------- METHODS --------------------------------- //

    /**
     * Signs the player out and redirects them to Home
     *
     * Parameters: HTTP request and response
     * Returns: null
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostSignoutRoute is invoked.");

        final Session session = request.session();

        // Get the current player
        Player player = session.attribute(GetHomeRoute.PLAYER_KEY);

        // Update the model
        player.donePlaying();
        player.removeFromGame();
        playerLobby.removePlayer(player);

        // Reset their local session attribute so they can play again if desired
        session.attribute(GetHomeRoute.PLAYER_KEY, null);

        // Redirect to Home
        response.redirect(WebServer.HOME_URL);
        halt();
        return null;
    }

}
