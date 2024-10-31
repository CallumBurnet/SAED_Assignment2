package org.example.plugins;

import org.example.pluginengine.GameAPI;
import org.example.pluginengine.GamePlugin;

public class Penalty implements GamePlugin {
    private GameAPI gameAPI;

    @Override
    public void initialize(GameAPI gameAPI) {
        this.gameAPI = gameAPI;
        System.out.println("PrintPlugin initialized!");
    }

    @Override
    public void execute() {
        gameAPI.createTimedObstacle("death", 5);
    }
}
