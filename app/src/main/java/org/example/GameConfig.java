package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.example.object.Obstacle;

public class GameConfig {
    private int[] size;
    private int[] start;
    private int[] goal;
    private Map<String, ArrayList<int[]>> obstacles = new HashMap<>();
    private ArrayList<Item> itemList = new ArrayList<>();
    ArrayList<String> plugins = new ArrayList<>();
    ArrayList<String> scripts = new ArrayList<>();


    // Getters and setters
    public void setSize(int[] size) { this.size = size; }
    public int[] getSize() { return size; }

    public void setStart(int[] start) { this.start = start; }
    public int[] getStart() { return start; }

    public void setGoal(int[] goal) { this.goal = goal; }
    public int[] getGoal() { return goal; }

    public void addObstacle(String name, ArrayList<int[]> coords) {
        System.out.println("ADDED : " + name);
        obstacles.put(name,coords);

    }
    public Map<String, ArrayList<int[]>> getObstacles() { return obstacles; }

    
    public void addPlugin(String plugin){
        plugins.add(plugin);
    }
    public void addScript(String script){
        scripts.add(script);
    }
    public void addItem(Item item){
        itemList.add(item);
    }
    public ArrayList<Item> getItems() { return itemList; }
}



