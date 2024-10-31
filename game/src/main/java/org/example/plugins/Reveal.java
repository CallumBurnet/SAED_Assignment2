package org.example.plugins;

import org.example.pluginengine.GameAPI;
import org.example.pluginengine.GamePlugin;

public class Reveal implements GamePlugin {
    private GameAPI gameAPI;

    @Override
    public void initialize(GameAPI gameAPI) {
        this.gameAPI = gameAPI;
        System.out.println("Teleport plugin initialized!");
    }

    @Override
    public void execute() {
        
    }
}

