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

public class SimpleGameAPI implements GameAPI {
    private Player player;
    private GamePanel gamePanel;
    private List<Entity> inventory = new ArrayList<>();
    private List<GameEventCallback> eventCallbacks = new ArrayList<>();
    private int gridSize;

    private boolean gridVisible = true;

    public SimpleGameAPI(GamePanel gp) {
        this.gamePanel = gp;
        this.player = gp.player;
        this.gridSize = gp.maxScreenCol;
    }

    @Override
    public void onPlayerMove(String direction) {
        System.out.println("Player moved " + direction);
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
    public void addItem(String name, String description, GamePlugin action) {
        //inventory.add(item);
    }

    @Override
    public void removeItem(String item) {
        inventory.remove(item);
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

    public void createTimedObstacle(String name, int time){
        gamePanel.assetSetter.setTimedObstacle(name, time);

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
