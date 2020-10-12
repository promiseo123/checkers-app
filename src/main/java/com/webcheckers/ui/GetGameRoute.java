package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to GET the /game route.
 *
 * @author Anthony DelPrincipe ajd6295
 */
public class GetGameRoute implements Route {

    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final PlayerLobby lobby;
    private final Gson gson;

    public static final String PLAYER_KEY = "player";

    // --------------------------------- CONSTRUCTORS --------------------------------- //

    /**
     * Constructor for GetGameGame route, sets up lobby and template engine for this route
     *
     * @param gson           Json conversion engine
     * @param lobby          The PlayerLobby of all players
     * @param templateEngine The template engine used in view/model interactions
     */
    public GetGameRoute(final Gson gson, final PlayerLobby lobby, final TemplateEngine templateEngine) {
        this.gson = gson;
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.lobby = lobby;
        //
        LOG.config("GetHomeRoute is initialized.");
    }

    /**
     * Handles any /game requests
     *
     * @param request  The HTTP request
     * @param response The HTTP response
     * @return The render of the game if successful, null otherwise
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {

        LOG.finer("GetGameRoute is invoked.");
        final Session session = request.session();

        Player currentUser = session.attribute(PLAYER_KEY);

        if (currentUser != null) {

            // Load the game screen, either for the first time or just for a game refresh
            // Game has been created and players are about to load the game screen/refresh game screen.
            if (currentUser.readyToPlay() || currentUser.isPlaying()) {

                Game game = GameCenter.getGameByID(currentUser.getGameID());

                // Populate the variables of the game.ftl render with pertinent information
                Map<String, Object> mv = new HashMap<>();
                mv.put("title", "New Game");
                mv.put("gameID", currentUser.getGameID());
                mv.put("currentUser", currentUser);
                mv.put("viewMode", "PLAY");

                // Put in the red/white player as the correct users depending on who requested
                if (currentUser.getColor() == Player.COLOR.RED) {
                    mv.put("redPlayer", currentUser);
                    mv.put("whitePlayer", game.getWhitePlayer());
                } else {
                    mv.put("whitePlayer", currentUser);
                    mv.put("redPlayer", game.getRedPlayer());
                }

                // "Start" the game by assigning the initial turn and Board setup
                mv.put("activeColor", game.getTurn().toString());
                mv.put("board", game.getBoardView(currentUser.getColor()));

                // Rendering the game for the first time? Do this stuff
                if (!currentUser.isPlaying()) {
                    // Mark the current user as playing in a game
                    // Each player, whether they requested the game or not, will go through this
                    lobby.markPlayerAsPlaying(currentUser.getName());

                    // We're in a game, so we're no longer waiting! Set this to false.
                    currentUser.waitingStatus(false);
                }
                else {
                    //check that opponent is still playing
                    Player opponent = game.getOpponent(currentUser);

                    if (opponent.isPlaying()) mv.put("modeOptionsAsJSON", null);
                        //send resign message if not
                    else {
                        final Map<String, Object> modeOptions = new HashMap<>(2);
                        modeOptions.put("isGameOver", true);
                        modeOptions.put("gameOverMessage", opponent.getName() + " has resigned.");
                        mv.put("modeOptionsAsJSON", gson.toJson(modeOptions));
                    }
                }

                // Render the game
                return templateEngine.render(new ModelAndView(mv, "game.ftl"));
            }
        }
        return null;
    }
}
