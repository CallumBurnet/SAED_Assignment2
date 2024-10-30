package org.example.gameplugins;

import org.example.Item;
import org.example.entity.*;

public interface GameAPI {
    void onPlayerMove(String direction);
    void onNewItemAcquired(Entity item);
    void onMenuOptionSelected();

    Player getPlayer();
    void setPlayerLocation(int x, int y);
    int[] getPlayerLocation();

    Entity[] getInventory();
    void addItem(Entity item);  // Accepts an item to add
    void removeItem(Entity item); // Accepts an item to remove

    int getGridSize();
    void visibility(boolean visible);
}
