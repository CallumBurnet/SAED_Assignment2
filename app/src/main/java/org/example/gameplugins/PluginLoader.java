package org.example.gameplugins;

import java.util.ArrayList;
import java.util.List;

public class PluginLoader {
    private List<GamePlugin> plugins = new ArrayList<>();

    public void loadPlugin(String className, GameAPI gameAPI) {
        try {
            Class<?> pluginClass = Class.forName(className);
            GamePlugin plugin = (GamePlugin) pluginClass.getDeclaredConstructor(GameAPI.class).newInstance(gameAPI);
            plugins.add(plugin);
            System.out.println("Loaded plugin: " + className);
        } catch (Exception e) {
            System.err.println("Error loading plugin: " + e.getMessage());
        }
    }

    public List<GamePlugin> getPlugins() {
        return plugins;
    }
}
