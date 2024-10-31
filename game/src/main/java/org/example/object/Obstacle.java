package org.example.object;

import org.example.GamePanel;
import org.example.entity.Entity;

public class Obstacle extends Entity{
    String requires;
    public Boolean unlocked;
    public int timer;
    public boolean timed;
    public Obstacle(GamePanel gp, String requires,Boolean timed, int time){
            super(gp);
            collision = true;
            this.requires = requires;
            this.timed = timed;
            this.timer = time;
            down1 = setup("/objects/diamondsword");  
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
        dialogue[0] = requireString ;
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
    
    
}
