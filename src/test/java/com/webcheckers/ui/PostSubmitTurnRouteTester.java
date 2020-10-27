package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.*;
import com.webcheckers.ui.BoardView.Piece;
import com.webcheckers.ui.BoardView.Space;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    private Player testPlayer2;
    private Board board;

    @BeforeEach
    void setup() {
        mockSession = mock(Session.class);
        mockRequest = mock(Request.class);
        mockResponse = mock(Response.class);

        mockTemplate = mock(TemplateEngine.class);
        mockLobby = mock(PlayerLobby.class);

        when(mockLobby.getPlayer(playerName)).thenReturn(testPlayer);
        when(mockRequest.session()).thenReturn(mockSession);

        CuT = new PostSubmitTurnRoute(mockLobby, mockTemplate);
        testPlayer = new Player(playerName);
        testPlayer2 = new Player(playerName);

        when(mockSession.attribute("player")).thenReturn(testPlayer);

        String gameID = Game.generateRandomGameID();
        GameCenter.newGame(gameID, testPlayer, testPlayer2);
        testPlayer.assignToGame(gameID);

        board = new Board();  // the board will be used to check the model in CuT vs ours.
    }

    @Test
    void SubmitSuccess() throws Exception {
        board.makeMove(new Move(new Position(2, 3), new Position(3, 4)));
        CuT.handle(mockRequest, mockResponse);
        assertNotNull(board.getSpaceAt(3, 4).getPiece());
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
            assertNotNull(board.getSpaceAt(3, 4).getPiece());
        }
    }


}
