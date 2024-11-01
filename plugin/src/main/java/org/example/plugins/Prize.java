package org.example.plugins;

import org.engine.GameAPI;
import org.engine.GameEvent;
import org.engine.GameEventCallback;
import org.entity.APIEntity;
import org.engine.GamePlugin;

public class Prize implements GamePlugin, GameEventCallback {
    private GameAPI gameAPI;
    private int obstaclesDefeated = 0;
    private int itemsCollected = 0;
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
                //System.out.println("SS");
                break;
            case "NEW_ITEM_ACQUIRED":
                System.out.println("New item acquired: " + event.getEventData());
                itemsCollected++;

                break;
            case "OBSTACLE_DEFEATED":
                System.out.println("Obstacle defeated: ");
                obstaclesDefeated++;
                break;
            default:
                break;
        }
        if(obstaclesDefeated + itemsCollected >=5) {

            obstaclesDefeated = 0;
            itemsCollected = 0;
            gameAPI.addItem("great_sword", "great_sword_description","sword", null);

            
            //add item
        }
    }
}
