package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag ("Model-Tier")
public class PlayerTester {

    @Test
    void assignToGame() {
        //create CuT
        final Player Cut = new Player("Test");
        String testid = "12345abcd";
        Cut.assignToGame(testid);
        assertEquals(testid,Cut.getGameID());
    }

    @Test
    void readyToPlayNotPlaying() {
        final Player Cut = new Player("Test");
        assertFalse(Cut.readyToPlay(),"Not In Game: Failure in readyToPlay with no active ID");
        String testid = "12345abcd";
        Cut.assignToGame(testid);
        assertTrue(Cut.readyToPlay(),"Not In Game: Failure in readyToPlay with active ID");
    }

    @Test
    void readyToPlayPlaying() {
        final Player Cut = new Player("Test");
        Cut.playing();
        assertFalse(Cut.readyToPlay(),"In Game: Failure in readyToPlay with no active ID");
        String testid = "12345abcd";
        Cut.assignToGame(testid);
        assertFalse(Cut.readyToPlay(),"In Game: Failure in readyToPlay with active ID");
    }

    @Test
    void waitingStatus() {
        final Player Cut = new Player("Test");
        Cut.waitingStatus(true);
        assertTrue(Cut.isWaiting(),"Failure is waitingStatus(True)");
        Cut.waitingStatus(false);
        assertFalse(Cut.isWaiting(),"Failure is waitingStatus(False)");
    }

    @Test
    void nameEquals() {
        final Player Cut = new Player("Test");
        assertTrue(Cut.nameEquals("Test"),"Failure in nameEquals(true)");
        assertFalse(Cut.nameEquals("Lorem Ipsum"),"Failure in nameEquals(true)");
    }

    @Test
    void testToString() {
        final Player Cut = new Player("Test");
        String expected = "Test with currentGameID ";
        assertEquals(Cut.toString(),expected);
        String testid = "12345abcd";
        Cut.assignToGame(testid);
        expected = "Test with currentGameID 12345abcd";
        assertEquals(Cut.toString(),expected);
    }

    @Test
    void testEquals() {
        final Player Cut = new Player("Test");
        final Player CutOtherEqual = new Player("Test");
        final Player CutOtherNotEqual = new Player("NotTest");
        assertNotEquals(Cut, CutOtherNotEqual, "Failure in equals(false)");
        assertEquals(Cut, CutOtherEqual, "Failure in equals(True)");
    }
}
