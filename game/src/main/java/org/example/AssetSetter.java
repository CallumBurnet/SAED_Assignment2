package org.example;

import java.util.List;
import java.util.Map;
import org.checkerframework.checker.units.qual.s;
import org.example.entity.Entity;
import org.example.entity.TrappedGuy;
import org.example.object.Obstacle;
import org.example.object.Weapon;
import java.util.ArrayList;
import java.util.HashMap;

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

        gp.obj[0] = new Weapon(gp, "White Sword","Use carefully",  "sword");
        gp.obj[0].x =  6* gp.tileSize;
        gp.obj[0].y =  6 *gp.tileSize;
        itemCounter ++;
        setItemToEntity();
    }
    public void setObstacles(){
        setObstacleToEntity();
    }
    public void setTimedObstacle(String name, int time){
        for(int i  = 0; i <= gp.obstacles.length; i++){
            System.out.println("FOUND A SLOT" + i);
        }
            
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
