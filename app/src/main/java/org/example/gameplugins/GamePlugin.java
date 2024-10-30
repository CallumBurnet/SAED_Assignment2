package org.example.gameplugins;

public interface GamePlugin {
    void initialize(GameAPI gameAPI);
    void execute();
}
