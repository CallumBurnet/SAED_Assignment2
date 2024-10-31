package org.example.plugins;

import org.engine.GameAPI;
import org.engine.GameEvent;
import org.engine.GameEventCallback;
import org.entity.APIEntity;
import org.engine.GamePlugin;

public class Reveal implements GamePlugin, GameEventCallback {
    private GameAPI gameAPI;

    @Override
    public void initialize(GameAPI gameAPI) {
        this.gameAPI = gameAPI;
    }

    @Override
    public void execute() {
        gameAPI.registerEventCallback(this); // Register this as a callback
    }

    @Override
    public void onGameEvent(GameEvent event) {
        // Check event type and handle accordingly
        switch (event.getEventType()) {
            case "PLAYER_MOVE":
                break;
            case "NEW_ITEM_ACQUIRED":
                System.out.println("New item acquired: " + event.getEventData());
                if(event.getEventData().contains("map")) {
                    gameAPI.visible(true);
                }
                break;
            default:
                break;
        }
    }
}
