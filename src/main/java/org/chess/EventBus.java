package org.chess;

import java.util.ArrayList;
import java.util.List;

public class EventBus {
    private static EventBus instance;
    private final List<GameObserver> observers = new ArrayList<>();

    private EventBus() {}

    public static EventBus getInstance() {
        if (instance == null) {
            instance = new EventBus();
        }
        return instance;
    }

    public void attach(GameObserver observer) {
        observers.add(observer);
    }

    public void detach(GameObserver observer) {
        observers.remove(observer);
    }

    public void postEvent(String event) {
        for (GameObserver observer : observers) {
            observer.onEvent(event);
        }
    }
}
