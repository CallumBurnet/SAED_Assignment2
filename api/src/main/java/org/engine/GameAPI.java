package org.engine;

import org.entity.APIEntity;


public interface GameAPI {
    void onPlayerMove(String direction);
    void onNewItemAcquired(APIEntity item);
    void onMenuOptionSelected();

    APIEntity getPlayer();
    void setPlayerLocation(int x, int y);
    int[] getPlayerLocation();

    APIEntity[] getInventory();
    void addItem(String name, String description, GamePlugin action);  // Accepts an item to add
    void removeItem(String itemName); // Accepts an item to remove

    int getGridSize();
    void visible(boolean visible);
    void registerMenuOption(String optionName, Runnable action);
    void addCustomKeyListener(Character letter);
    void createTimedObstacle(String name, int time);
    void registerEventCallback(GameEventCallback callback);
    void triggerEvent(GameEvent event); // Used to simulate an event trigger

}
