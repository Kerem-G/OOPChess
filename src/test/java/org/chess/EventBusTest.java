package org.chess;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventBusTest {

    private final List<GameObserver> attachedObservers = new ArrayList<>();

    private GameObserver attach(GameObserver observer) {
        EventBus.getInstance().attach(observer);
        attachedObservers.add(observer);
        return observer;
    }

    @AfterEach
    void detachAll() {
        for (GameObserver observer : attachedObservers) {
            EventBus.getInstance().detach(observer);
        }
        attachedObservers.clear();
    }

    @Test
    void testGetInstanceReturnsSameInstance() {
        assertSame(EventBus.getInstance(), EventBus.getInstance());
    }

    @Test
    void testAttachedObserverReceivesEvent() {
        List<String> received = new ArrayList<>();
        attach(received::add);

        EventBus.getInstance().postEvent("TEST");

        assertEquals(1, received.size());
        assertEquals("TEST", received.get(0));
    }

    @Test
    void testDetachedObserverDoesNotReceiveEvent() {
        List<String> received = new ArrayList<>();
        GameObserver observer = attach(received::add);

        EventBus.getInstance().detach(observer);
        attachedObservers.remove(observer);
        EventBus.getInstance().postEvent("TEST");

        assertTrue(received.isEmpty());
    }

    @Test
    void testMultipleObserversAllReceiveEvent() {
        List<String> received1 = new ArrayList<>();
        List<String> received2 = new ArrayList<>();
        attach(received1::add);
        attach(received2::add);

        EventBus.getInstance().postEvent("TEST");

        assertEquals(1, received1.size());
        assertEquals(1, received2.size());
    }
}
