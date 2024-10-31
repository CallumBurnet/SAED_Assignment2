package org.example;

import java.util.List;
import java.util.Map;

import org.example.entity.Entity;
import org.example.entity.TrappedGuy;
import org.example.object.Goal;
import org.example.object.Obstacle;
import org.example.object.Weapon;
import org.engine.GamePlugin;

import java.util.ArrayList;

public class AssetSetter {
    Map<String, ArrayList<int[]>> obstacles;
    GamePanel gp;
    GameConfig cfg;
    List<Item> items;
    int itemCounter = 0;
    public Entity preOb[] = new Entity[10];


    public AssetSetter(GamePanel gp, GameConfig cfg){
        this.gp = gp;
        this.cfg = cfg;
        this.items = cfg.getItems();
        this.obstacles = cfg.getObstacles();

    }
    public void setObject(){

        gp.obj[0] = new Weapon(gp, "melted_blade","use_carefully",  "sword");
        gp.obj[0].x =  6* gp.tileSize;
        gp.obj[0].y =  6 *gp.tileSize;
        itemCounter ++;
        setItemToEntity();
    }
    public void setGoal(){
        int[] goal = cfg.getGoal();
        // Ensure the list has enough capacity, or add a new element at index 0
        

        gp.goals[0]=new Goal(gp);
        gp.goals[0].x = goal[0] * gp.tileSize;
        gp.goals[0].y = goal[1] * gp.tileSize;
        

      

    }

    public void setObstacles(){
        setObstacleToEntity();
    }
    public void setTimedObstacle(String name, int time){

        System.out.println("NEW OBJ");
        gp.obstacles[9] = new Obstacle(gp,"", true, time);
        gp.obstacles[9].x = 8 * gp.tileSize;
        gp.obstacles[9].y = 10 * gp.tileSize;
   
    }
    public void setNPC(){
        gp.npc[0] = new TrappedGuy(gp);
        gp.npc[0].x = gp.tileSize*10;
        gp.npc[0].y = gp.tileSize*10;


    }
    public void addItem(String name, String des, String type, boolean isAction, GamePlugin plugin){

       Item item = new Item("teleporter",6* gp.tileSize, 7* gp.tileSize , "Press t to teleport", plugin);
    }


    public void setItemToEntity() {
        // Assuming `items` is a list of parsed items with names
        for (Item item : items) {
            String name = item.getName().toLowerCase();
    
            if (isWeapon(name)) {
                System.out.println(item.getName() + " categorized as weapon.");
                // Add the item to the weapon list or take any appropriate action
                makeWeapon(item);
            } else if (isJewelry(name)) {
                System.out.println(item.getName() + " categorized as jewelry.");
                // Add the item to the jewelry list or take any appropriate action
                makeJewelry(item);
            } else {
                System.out.println(item.getName() + " categorized as other.");
                // Handle other types of items, if necessary
                System.out.println("HALLLT UNHANDLED");
            }
        }
    }
    public void setObstacleToEntity() {
        // Assuming `items` is a list of parsed items with names
        int i = 0;
        
        for (Map.Entry<String, ArrayList<int[]>> entry : obstacles.entrySet()) {
            String requiredItem = entry.getKey();
            ArrayList<int[]> coordinatesList = entry.getValue();

            
            System.out.println("Obstacle Type: " + requiredItem);
            
            for (int[] coordinates : coordinatesList) {
                System.out.println("Showing: " +i);
                gp.obstacles[i] = new Obstacle(gp, requiredItem,false, 0);
                gp.obstacles[i].x = coordinates[0] * gp.tileSize;
                gp.obstacles[i].y = coordinates[1] * gp.tileSize;
                i ++;
               
            }
            System.out.println(); // Line break between different obstacle types
        }
     

    
       
    }
    
    private boolean isWeapon(String name) {
        return name.contains("sword") || name.contains("dagger") || name.contains("axe") || name.contains("bow");
    }
    
    private boolean isJewelry(String name) {
        return name.contains("pendant") || name.contains("ring") || name.contains("necklace") || name.contains("bracelet");
    }
    private void makeWeapon(Item item){
        String name = item.getName().toLowerCase();
        String type = "";
        if(name.contains("sword")){
            type = "sword";
        }else if(name.contains("dagger")){
            type = "dagger";
        }else if(name.contains("axe")){
            type = "axe";

        }else if(name.contains("bow")){
            type = "bow";
        }else{
            System.out.println("IDK what you are  "+ name);
        }
        gp.obj[itemCounter] = new Weapon(gp, name, item.getMessage(),  type);
        gp.obj[itemCounter].x =  item.getX()* gp.tileSize;
        gp.obj[itemCounter].y =  item.getY() *gp.tileSize;
        itemCounter ++;

    }
    private void makeJewelry(Item item){

    }
    public void remove(String name, int index){
        switch(name){
            case "obstacle":
                gp.obstacles[index] = null;

        }
    }
    

}
