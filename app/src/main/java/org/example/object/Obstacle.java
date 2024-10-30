package org.example.object;

import org.example.GamePanel;
import org.example.entity.Entity;

public class Obstacle extends Entity{
    String requires;
    public Obstacle(GamePanel gp, String requires){
            super(gp);
            collision = true;
            this.requires = requires;
            down1 = setup("/objects/diamondsword");  
            setDialogue();
            System.out.println("HERE");
            
    }
    public void setDialogue(){
        String requireString = requires.replace("\"", "");

        dialogue[0] = "Need a " + requireString ;
    }
    public void displayDialogue(){
        gp.ui.currentDialogue = dialogue[0];
    }
    public String requiredItem(){
        return requires;
    }
    
    
}
