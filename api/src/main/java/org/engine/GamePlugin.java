package org.engine;

public interface GamePlugin {
    void initialize(GameAPI gameAPI);
    void execute();
}
