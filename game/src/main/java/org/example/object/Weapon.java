package org.example.object;
import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.example.GamePanel;
import org.example.entity.Entity;
public class Weapon extends Entity{
    
    public Weapon(GamePanel gp, String name, String des, String type){
        super(gp);

        this.name = name;
        switch(type.toLowerCase()){
            case "sword":
                System.out.println("ITS A SWORD");
                down1 = setup("/objects/diamondsword");  
                break;
            case "dagger":
                down1 = setup("/objects/copperDagger");
                break;
            case "axe":
                down1 = setup("/objects/wooden_axe");
                break;
            case "key":
                down1 = setup("/objects/key");
                break;
            case "jacket":
                down1 = setup("/objects/shirt");
                break;
            case "map":
                down1 = setup("/objects/map");
                break;
            case "ring":
                down1 = setup("/objects/ring");
                break;
            case "technology":
                down1 = setup("/objects/technology_symbol");
                break;

            
                
        }
        this.description = des;
    }
}
