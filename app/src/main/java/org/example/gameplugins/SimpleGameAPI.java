package org.example.gameplugins;

import org.example.entity.Player;

public class SimpleGameAPI implements GameAPI{
    private Player player;

    public SimpleGameAPI(Player player) {
        this.player = player;
    }

    @Override
    public Player getPlayer() {
        return player;
    }
}
