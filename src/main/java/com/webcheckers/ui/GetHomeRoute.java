package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import com.webcheckers.util.Message;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

  private final TemplateEngine templateEngine;
  private final PlayerLobby playerLobby;

  public static final String PLAYER_KEY = "player";

  public final String CURRENT_USER_KEY = "currentUser";

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
    LOG.finer("GetHomeRoute is invoked.");
    final Session session = request.session();
    //
    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Welcome!");

    // display a user message in the Home page
    vm.put("message", WELCOME_MSG);
    Player currentUser = session.attribute(PLAYER_KEY);
    if (currentUser != null){
      vm.put(CURRENT_USER_KEY,currentUser);
      this.playerLobby.getPlayers().remove(currentUser);
      vm.put("message", "Other players signed in:");
      // display a list of all other signed in Players
      vm.put("users", this.playerLobby.getPlayers());
      this.playerLobby.getPlayers().add(currentUser);
    } else {
      //display a message of the number of signed-in players
      vm.put("message", this.playerLobby.getPlayers().size()+" players are signed in.");
    }
    // render the View
    return templateEngine.render(new ModelAndView(vm , "home.ftl"));
  }
}
