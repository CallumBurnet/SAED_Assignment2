package org.engine;

import org.entity.APIEntity;


public interface GameAPI {
    void onPlayerMove(String direction);
    void onNewItemAcquired(APIEntity item);
    void onMenuOptionSelected();
    void onSecondPassed();

    APIEntity getPlayer();
    void setPlayerLocation(int x, int y);
    int[] getPlayerLocation();

    APIEntity[] getInventory();
    void addItem(String name, String description,String type, GamePlugin action);  // Accepts an item to add
    void removeItem(String itemName); // Accepts an item to remove
    void createIdleObstacleAfterDelay(String name, int time);
    int getGridSize();
    void visible(boolean visible);
    void registerMenuOption(String optionName, Runnable action);
    void addCustomKeyListener(Character letter);
    void createObstacle(String name, int time);
    void registerEventCallback(GameEventCallback callback);
    void triggerEvent(GameEvent event); // Used to simulate an event trigger
}
