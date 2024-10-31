package org.pluginengine;

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
    void addItem(String name, String description, String itemImage);  // Accepts an item to add
    void removeItem(String itemName); // Accepts an item to remove

    int getGridSize();
    void visibility(boolean visible);
    void registerMenuOption(String optionName, Runnable action);

    void createTimedObstacle(String name, int time);

}
