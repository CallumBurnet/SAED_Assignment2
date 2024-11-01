package org.example.api;

import org.engine.GameEvent;
import org.engine.GameEventCallback;
import org.entity.APIEntity;
import org.example.GamePanel;
import org.example.entity.Entity;
import org.example.entity.Player;
import org.engine.GameAPI;
import org.engine.GamePlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SimpleGameAPI implements GameAPI {
    private Player player;
    private GamePanel gamePanel;
    private List<Entity> inventory = new ArrayList<>();
    private List<GameEventCallback> eventCallbacks = new ArrayList<>();
    private int gridSize;
    private long lastMoveTime;
    private boolean penaltyAdded;
    private boolean idleCheck; // Flag to enable idle obstacle creation


    public SimpleGameAPI(GamePanel gp) {
        this.gamePanel = gp;
        this.player = gp.player;
        this.gridSize = gp.maxScreenCol;
        this.penaltyAdded =false;
        this.idleCheck = false; 

        this.lastMoveTime = System.currentTimeMillis();

    }
    @Override
    public void createIdleObstacleAfterDelay(String name, int time) {
        System.out.println("HERE MF");
        lastMoveTime = System.currentTimeMillis(); // Reset idle timer
        this.idleCheck = true; // Enable idle obstacle creation
        penaltyAdded = false; // Reset the penalty flag when enabling idle obstacle
    }
    public void createObstacle(String name, int time){
        gamePanel.assetSetter.setAPIObstacle(name, time);

    }

    @Override
    public void onPlayerMove(String direction) {
        lastMoveTime = System.currentTimeMillis(); // Update the last move time

    }
    @Override
    public void onSecondPassed() {
        long currentTime = System.currentTimeMillis();
        System.out.println(currentTime - lastMoveTime);
        System.out.println(idleCheck);
        if (idleCheck && !penaltyAdded && currentTime - lastMoveTime >= 5000) { // 5 seconds idle time
            System.out.println("Player has been idle for 5 seconds.");
            createObstacle("Death", 0); // Create the obstacle
            penaltyAdded = true; // Set penaltyAdded to true to prevent repeated creations
            idleCheck = false; // Disable the flag after obstacle creation
        }
    }
    @Override
    public void onNewItemAcquired(APIEntity item) {
       // addItem(item);
        System.out.println("New item acquired: " + item);
    }

    @Override
    public void onMenuOptionSelected() {
        System.out.println("Menu option selected!");
    }

    @Override
    public APIEntity getPlayer() {
        System.out.println("Player X: " + player.x);

        return player;
    }

    @Override
    public void setPlayerLocation(int x, int y) {
        player.setPosition(x * gamePanel.tileSize, y * gamePanel.tileSize);
    }

    @Override
    public int[] getPlayerLocation() {
        return player.getPosition();
    }

    @Override
    public APIEntity[] getInventory() {
        return inventory.toArray(new APIEntity[0]);
    }

    @Override
    public void addItem(String name, String description,String type, GamePlugin action) {
        System.out.println("ADDING ");
        gamePanel.assetSetter.addItem(name, description, type, false, action);
    }

    @Override
    public void removeItem(String item) {
        System.out.println("Deleting item");
        ArrayList<Entity> inventory =  gamePanel.player.getInventory();
        for (int i = 0; i < inventory.size(); i++) {
            Entity entity = inventory.get(i);
    
            // Check if the entity's name matches the specified item
            if (entity.name.equals(item)) { // Use .equals() to compare strings
                System.out.println("Found item at index: " + i);
                // Use the index (i) for your next call or any other operation
                // Remove the item, if necessary
                inventory.remove(i);
                break; // Exit loop after finding and removing the item
            }
        }

    }

    @Override
    public int getGridSize() {
        return gamePanel.maxScreenCol;
    }

    @Override
    public void visible(boolean visible) {
        gamePanel.visible = visible;
        System.out.println("Grid visibility set to: " + visible);
    }

    @Override
    public void registerMenuOption(String optionName, Runnable action) {
        player.getGamePanel().ui.drawSubWindow(gridSize, gridSize, gridSize, gridSize);
    }

    @Override
    public void addCustomKeyListener(Character letter) {
        gamePanel.keyH.addCustomKey(letter);
    }


    
    @Override
    public void registerEventCallback(GameEventCallback callback) {
        eventCallbacks.add(callback);
    }

    // Notify all callbacks about player movement
    public void notifyPlayerMove(String direction) {
        GameEvent event = new GameEvent("PLAYER_MOVE", direction);
        triggerEvent(event);
    }

    // Notify all callbacks about item acquisition
    public void notifyNewItemAcquired(APIEntity item) {
        GameEvent event = new GameEvent("NEW_ITEM_ACQUIRED", item.getName());
        triggerEvent(event);
    }
    public void notifyOnCustomKeyPressed(Character character) {
        GameEvent event = new GameEvent("CUSTOM_KEY", character.toString());
        triggerEvent(event);
    }
    public void notifyOnObstacleDefeated(APIEntity item) {

        GameEvent event = new GameEvent("OBSTACLE_DEFEATED", item.getName());
        triggerEvent(event);
    }


    @Override
    public void triggerEvent(GameEvent event) {
        for (GameEventCallback callback : eventCallbacks) {
            callback.onGameEvent(event);
        }
    }

}
