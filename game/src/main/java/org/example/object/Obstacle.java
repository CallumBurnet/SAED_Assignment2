package org.example.object;

import org.checkerframework.checker.units.qual.t;
import org.example.GamePanel;
import org.example.entity.Entity;

public class Obstacle extends Entity{
    String requires;
    public Boolean unlocked;
    public int timer;
    public boolean timed;
    public Boolean collision;
    public String type;
    public Obstacle(GamePanel gp, String requires,Boolean timed, int time, String type){
            super(gp);
            this.collision = true;
            this.requires = requires;
            this.timed = timed;
            this.timer = time;
            this.type = type;
            if(type.contains("key")){
                down1 = setup("/objects/door");  

            }else if(type.contains("priest")){
                down1 = setup("/objects/water");
            

            }else if(type.contains("ring")){
                down1 = setup("/objects/hand");
            }else{
                down1 = setup("/objects/box");  
            }
            setDialogue();
            
    }
    public void setDialogue(){
        String requireString = requires.replace("\"", "");
        if(requires == ""){
            unlocked = true;
            System.out.println("Unlocked: " + unlocked);
        }else{
            unlocked = false;
        }
        dialogue[0] = "requires_"+requireString ;
    }
    public void displayDialogue(){
        gp.ui.currentDialogue = dialogue[0];
    }
    public String requiredItem(){
        return requires;
    }
    public int getTimer(){
        return timer;
    }
    public void decreaseTimer(){
        timer--;
    }
    public void setCollision(boolean coll){
        this.collision = coll;
    }
    
    
}
