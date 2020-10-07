package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.*;

import com.webcheckers.util.Message;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {

  // --------------------------------- VARIABLES --------------------------------- //

  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  private final TemplateEngine templateEngine;
  private final PlayerLobby playerLobby;

  public static final String PLAYER_KEY = "player";
  public static final String CURRENT_USER_KEY = "currentUser";
  private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

  // --------------------------------- CONSTRUCTORS --------------------------------- //

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetHomeRoute(final PlayerLobby playerLobby, final TemplateEngine templateEngine) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.playerLobby = playerLobby;
    //
    LOG.config("GetHomeRoute is initialized.");
  }

  // --------------------------------- METHODS --------------------------------- //

  /**
   * Render the WebCheckers Home page.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Home page
   */
  @Override
  public Object handle(Request request, Response response) {

    // Yeah, this is a thing
    LOG.finer("GetHomeRoute is invoked.");
    final Session session = request.session();

    // Make the vm to display
    Map<String, Object> vm = new HashMap<>();

    // display the title/message in the Home page
    vm.put("title", "Welcome!");
    vm.put("message", WELCOME_MSG);

    Player currentUser = session.attribute(PLAYER_KEY);

    // If the current User is null, something has gone catastrophically wrong
    if (currentUser != null){

      vm.put(CURRENT_USER_KEY, currentUser);


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
        mv.put("modeOptionsAsJSON", null);

        // Put in the red/white player as the correct users depending on who requested
        if (currentUser.getColor() == Player.COLOR.RED) {
          mv.put("redPlayer", currentUser);
          mv.put("whitePlayer",  game.getWhitePlayer());
        }
        else {
          mv.put("whitePlayer", currentUser);
          mv.put("redPlayer", game.getRedPlayer());
        }

        // "Start" the game by assigning the initial turn and Board setup
        mv.put("activeColor", game.getTurn().toString());
        mv.put("board", game.getBoardView(currentUser.getColor()));


        if (!currentUser.isPlaying()) {
          // Mark the current user as playing in a game
          // Each player, whether they requested the game or not, will go through this
          playerLobby.markPlayerAsPlaying(currentUser.getName());

          // We're in a game, so we're no longer waiting! Set this to false.
          currentUser.waitingStatus(false);
        }

        // Render the game
        return templateEngine.render(new ModelAndView(mv, "game.ftl"));
      }

      // Display a list of all other signed in Players
      vm.put("users", playerLobby.getOtherPlayers(currentUser.getName()));
    } else {
      //display a message of the number of signed-in, not-in-a-game players
      vm.put("Num", playerLobby.getPlayers().size() + " players are signed in.");
    }
    // render the View
    return templateEngine.render(new ModelAndView(vm , "home.ftl"));
  }
}
