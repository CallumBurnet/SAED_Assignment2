package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameConfig {
    private int[] size;
    private int[] start;
    private int[] goal;
    private Map<String, ArrayList<int[]>> obstacles = new HashMap<>();
    private List<Item> items = new ArrayList<>();

    // Getters and setters
    public void setSize(int[] size) { this.size = size; }
    public int[] getSize() { return size; }

    public void setStart(int[] start) { this.start = start; }
    public int[] getStart() { return start; }

    public void setGoal(int[] goal) { this.goal = goal; }
    public int[] getGoal() { return goal; }

    public void addObstacle(String requirement, ArrayList<int[]> coords) {
        this.obstacles.put(requirement, coords);
    }
    public Map<String, ArrayList<int[]>> getObstacles() { return obstacles; }

    public void addItem(Item item) {
        this.items.add(item);
    }
    public List<Item> getItems() { return items; }
}

