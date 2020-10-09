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

import static spark.Spark.halt;

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

      if (currentUser.readyToPlay()) {
        response.redirect(WebServer.GAME_URL);
        halt();
      }

      //if player navigates home then they automatically resign
      else if (currentUser.isPlaying()){
        playerLobby.markPlayerAsDonePlaying(currentUser.getName());
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
