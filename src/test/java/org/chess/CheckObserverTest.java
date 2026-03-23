package org.chess;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckObserverTest {

    private CheckObserver observer;

    @AfterEach
    void detach() {
        EventBus.getInstance().detach(observer);
    }

    @Test
    void testObserverReceivesCheckEvent() {
        observer = new CheckObserver();
        EventBus.getInstance().attach(observer);

        EventBus.getInstance().postEvent("CHECK");

        assertEquals("CHECK", observer.getLastEvent());
    }

    @Test
    void testObserverReceivesCheckmateEvent() {
        observer = new CheckObserver();
        EventBus.getInstance().attach(observer);

        EventBus.getInstance().postEvent("CHECKMATE");

        assertEquals("CHECKMATE", observer.getLastEvent());
    }

    @Test
    void testObserverReceivesStalemateEvent() {
        observer = new CheckObserver();
        EventBus.getInstance().attach(observer);

        EventBus.getInstance().postEvent("STALEMATE");

        assertEquals("STALEMATE", observer.getLastEvent());
    }

    @Test
    void testGetLastEventStartsEmpty() {
        observer = new CheckObserver();
        assertEquals("", observer.getLastEvent());
    }
}
