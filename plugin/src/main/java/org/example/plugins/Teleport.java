package org.example.plugins;

import org.engine.*;

import java.util.Random;

public class Teleport implements GamePlugin,GameEventCallback {
    private GameAPI gameAPI;
    private boolean hasTeleported = false; // To limit teleport to one use

    @Override
    public void initialize(GameAPI gameAPI) {
        this.gameAPI = gameAPI;
        gameAPI.registerEventCallback(this); // Register this as a callback
    }


    @Override
    public void execute() {
        // Optionally, any other actions or initialization needed for the plugin
        gameAPI.addItem("teleporter", "press_t", "technology", null);
        gameAPI.addCustomKeyListener('t');
    }

    @Override
    public void onGameEvent(GameEvent event) {
        switch (event.getEventType()) {
            case "CUSTOM_KEY":
                if(event.getEventData().toLowerCase().equals("t")){
                    if (!hasTeleported) {
                        int gridSize = gameAPI.getGridSize();
                        Random random = new Random();

                        // Generate random x and y positions within the grid bounds
                        int newX = random.nextInt(gridSize);
                        int newY = random.nextInt(gridSize);

                        // Teleport to the new position
                        gameAPI.setPlayerLocation(newX, newY);
                        System.out.println("Teleported to: (" + newX + ", " + newY + ")");

                        hasTeleported = true; // Mark teleport as used
                        gameAPI.removeItem("teleporter");
                    }


            }

            default:
                break;
        }
    }
}
