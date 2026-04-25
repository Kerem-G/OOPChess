package org.chess.observers;

import org.chess.ChessGame;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationObserverTest {

    @Test
    void testCheckEventSetsStatus() {
        ChessGame game = new ChessGame();
        String[] captured = {null};
        NotificationObserver observer = new NotificationObserver(game, s -> captured[0] = s);

        observer.onEvent("CHECK");

        assertEquals("White is in check", captured[0]);
    }

    @Test
    void testMoveEventClearsStatus() {
        ChessGame game = new ChessGame();
        String[] captured = {"stale"};
        NotificationObserver observer = new NotificationObserver(game, s -> captured[0] = s);

        observer.onEvent("MOVE");

        assertEquals("", captured[0]);
    }

    @Test
    void testUndoEventClearsStatus() {
        ChessGame game = new ChessGame();
        String[] captured = {"stale"};
        NotificationObserver observer = new NotificationObserver(game, s -> captured[0] = s);

        observer.onEvent("UNDO");

        assertEquals("", captured[0]);
    }
}
