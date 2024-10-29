package org.example;

import java.util.List;

import org.checkerframework.checker.units.qual.s;
import org.example.object.Weapon;

public class AssetSetter {
    GamePanel gp;
    GameConfig cfg;
    List<Item> items;
    int itemCounter = 0;
    public AssetSetter(GamePanel gp, GameConfig cfg){
        this.gp = gp;
        this.cfg = cfg;
        this.items = cfg.getItems();

    }
    public void setObject(){

        gp.obj[0] = new Weapon(gp, "White Sword","Use carefully",  "sword");
        gp.obj[0].x =  6* gp.tileSize;
        gp.obj[0].y =  6 *gp.tileSize;
        itemCounter ++;
        setItemToEntity();
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
    

}
