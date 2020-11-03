package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to GET the /spectator/game route.
 *
 * @author Promise Omiponle poo9724
 */
public class GetSpectatorGameRoute implements Route {

    private static final Logger LOG = Logger.getLogger(GetSpectatorGameRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final PlayerLobby lobby;
    private final Gson gson;
    public static final String PLAYER_KEY = "player";
    private final String GAME_ID_PARAM = "GameID";

    public GetSpectatorGameRoute(final Gson gson, final PlayerLobby lobby, final TemplateEngine templateEngine) {
        this.gson = gson;
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.lobby = lobby;
        LOG.config("GetHomeRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.finer("GetSpectatorGameRoute is invoked.");
        final Session session = request.session();
        Player currentUser = session.attribute(PLAYER_KEY);
        lobby.markPlayerAsSpectating(currentUser.getName());
        // If there's no current user, or they shouldn't be loading the game page, return null
//        if (currentUser == null ||
//                !(currentUser.readyToPlay() || currentUser.isPlaying())) {
//            return null;
//        }
        String gameID=request.queryParams(GAME_ID_PARAM);
        Game game = GameCenter.getGameByID(gameID);
        assert game != null;


        // Populate the variables of the game.ftl render with pertinent information
        Map<String, Object> mv = new HashMap<>();
        mv.put("title", "New Game");
        mv.put("gameID", gameID);
        mv.put("currentUser", currentUser);
        mv.put("viewMode", "SPECTATOR");

        mv.put("redPlayer", game.getRedPlayer());
        mv.put("whitePlayer", game.getWhitePlayer());

        mv.put("activeColor", game.getTurn().toString());
        mv.put("board", game.getBoardView(currentUser.getColor()));

        Player currentTurn;
        Player opponent;
        // Rendering the game for the first time? Do this stuff
        if (game.getTurn()== Game.TURN.RED) {
            currentTurn=game.getRedPlayer();
            opponent=game.getWhitePlayer();
        } else {
            currentTurn=game.getWhitePlayer();
            opponent=game.getRedPlayer();
        }
        if (!currentTurn.isPlaying()) {
            lobby.markPlayerAsPlaying(currentTurn.getName());
            // We're in a game, so we're no longer waiting! Set this to false.
            currentTurn.waitingStatus(false);
        } else {
            //check that opponent is still playing
            if (opponent.isPlaying()) {
                mv.put("modeOptionsAsJSON", null);
                //send resign message if not
            } else {
                final Map<String, Object> modeOptions = new HashMap<>(2);
                modeOptions.put("isGameOver", true);
                modeOptions.put("gameOverMessage", opponent.getName() + " has resigned.");
                mv.put("modeOptionsAsJSON", gson.toJson(modeOptions));
            }
        }

        if (game.hasEnded()) {
            final Map<String, Object> modeOptions = new HashMap<>(2);
            modeOptions.put("isGameOver", true);

            if (currentTurn.stateEquals(Player.STATE.WON)) {
                modeOptions.put("gameOverMessage", currentTurn.getName()+" won! He captured all of the opponent's pieces.");
            } else {
                modeOptions.put("gameOverMessage", opponent.getName()+" won! He captured all of the opponent's pieces.");
            }

            mv.put("modeOptionsAsJSON", gson.toJson(modeOptions));
            lobby.markPlayerAsDonePlaying(currentUser.getName());
        }
        return templateEngine.render(new ModelAndView(mv, "game.ftl"));
    }
}
