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
        System.out.println("HERE");
        switch(type.toLowerCase()){
            case "sword":
                System.out.println("ITS A SWORD");
                down1 = setup("/objects/diamondsword");  
                break;
            case "dagger":
                down1 = setup("/objects/diamondsword");
                break;
            case "axe":
                down1 = setup("/objects/diamondsword");
                break;
                
        }
        this.description = des;
    }
}
