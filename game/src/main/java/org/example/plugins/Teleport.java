package org.example.plugins;

import org.example.pluginengine.GameAPI;
import org.example.pluginengine.*;
import java.util.Random;

public class Teleport implements GamePlugin {
    private GameAPI gameAPI;
    private boolean hasTeleported = false; // To limit teleport to one use

    @Override
    public void initialize(GameAPI gameAPI) {
        this.gameAPI = gameAPI;

        // Register the "Teleport" menu option with an action
        gameAPI.registerMenuOption("Teleport", this::teleportPlayer);
    }

    private void teleportPlayer() {
        // Ensure the player can only teleport once
        if (!hasTeleported) {
            int gridSize = gameAPI.getGridSize();
            Random random = new Random();

            // Generate random coordinates within grid bounds
            int randomX = random.nextInt(gridSize);
            int randomY = random.nextInt(gridSize);

            // Set the player's new location
            gameAPI.setPlayerLocation(randomX, randomY);

            System.out.println("Player teleported to: (" + randomX + ", " + randomY + ")");
            hasTeleported = true; // Prevent further teleportation
        } else {
            System.out.println("Teleportation has already been used.");
        }
    }

    @Override
    public void execute() {
        // Optionally, any other actions or initialization needed for the plugin
        System.out.println("Teleport plugin loaded. Ready to teleport!");
    }
}
