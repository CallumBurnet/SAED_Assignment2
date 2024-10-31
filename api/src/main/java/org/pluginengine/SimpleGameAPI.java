package org.pluginengine;

import org.example.entity.Entity;
import org.example.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SimpleGameAPI implements GameAPI {
    private Player player;
    private List<Entity> inventory = new ArrayList<>();
    private boolean gridVisible = true;
    private int gridSize = 10; // Example grid size

    public SimpleGameAPI(Player player) {
        this.player = player;
    }

    @Override
    public void onPlayerMove(String direction) {
        System.out.println("Player moved " + direction);
    }

    @Override
    public void onNewItemAcquired(Entity item) {
       // addItem(item);
        System.out.println("New item acquired: " + item);
    }

    @Override
    public void onMenuOptionSelected() {
        System.out.println("Menu option selected!");
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public void setPlayerLocation(int x, int y) {
        player.setPosition(x, y);
    }

    @Override
    public int[] getPlayerLocation() {
        return player.getPosition();
    }

    @Override
    public Entity[] getInventory() {
        return inventory.toArray(new Entity[0]);
    }

    @Override
    public void addItem(String name, String description, String itemImage) {
        //inventory.add(item);
    }

    @Override
    public void removeItem(String item) {
        inventory.remove(item);
    }

    @Override
    public int getGridSize() {
        return gridSize;
    }

    @Override
    public void visibility(boolean visible) {
        this.gridVisible = visible;
        System.out.println("Grid visibility set to: " + visible);
    }

    @Override
    public void registerMenuOption(String optionName, Runnable action) {
        player.getGamePanel().ui.drawSubWindow(gridSize, gridSize, gridSize, gridSize);
    }
    @Override
    
    public void createTimedObstacle(String name, int time){
        player.getGamePanel().assetSetter.setTimedObstacle(name, time);

    }

}
