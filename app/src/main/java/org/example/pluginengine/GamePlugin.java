package org.example.pluginengine;

public interface GamePlugin {
    void initialize(GameAPI gameAPI);
    void execute();
}
