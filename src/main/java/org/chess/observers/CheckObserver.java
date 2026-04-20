package org.chess.observers;

public class CheckObserver implements GameObserver {
    private String lastEvent = "";

    @Override
    public void onEvent(String event) {
        lastEvent = event;
    }

    public String getLastEvent() {
        return lastEvent;
    }
}
