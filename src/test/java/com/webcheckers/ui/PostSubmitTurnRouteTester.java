package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;
import com.webcheckers.ui.BoardView.Space;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests the submitting turn feature, universally adapted per move type.
 *
 * @author: Ferdous Z.
 */

@Tag("UI-tier")
public class PostSubmitTurnRouteTester {
    private PostSubmitTurnRoute CuT;
    private PlayerLobby playerLobby;

    private Request mockRequest;
    private Response mockResponse;
    private Session mockSession;
    private TemplateEngine mockTemplate;
    private PlayerLobby mockLobby;
    private String playerName;
    private Player testPlayer;
    private Board board;

    @BeforeEach
    void setup() {
        mockSession = mock(Session.class);
        mockRequest = mock(Request.class);
        mockResponse = mock(Response.class);

        mockTemplate = mock(TemplateEngine.class);
        mockLobby = mock(PlayerLobby.class);

        CuT = new PostSubmitTurnRoute(mockLobby, mockTemplate);
        testPlayer = new Player(playerName);

        board = new Board();  // the board will be used to check the model in CuT vs ours.

        when(mockLobby.getPlayer(playerName)).thenReturn(testPlayer);
        when(mockRequest.session()).thenReturn(mockSession);
        when(mockRequest.attribute("PLAYER_KEY")).thenReturn(testPlayer);
    }

    @Test
    void SubmitSuccess() throws Exception {
        board.makeMove(new Move(new Position(2, 3), new Position(3, 4)));
        CuT.handle(mockRequest, mockResponse);
        assertEquals(new Space(board, 3, 4, Space.COLOR.BLACK), board.getSpaceAt(3, 4));
    }

    @Test
    void SubmitFinished() throws Exception {
        for (int r = 5; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                board.getSpaceAt(r, c).setPiece(null);  // effectively discards the piece.
            }
        }

        {  // the difference here between submission is conquering under different circumstances.
            board.makeMove(new Move(new Position(2, 3), new Position(3, 4)));
            CuT.handle(mockRequest, mockResponse);
            assertEquals(new Space(board, 3, 4, Space.COLOR.BLACK), board.getSpaceAt(3, 4));
        }
    }


}
