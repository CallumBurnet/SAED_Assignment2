package org.example.entity;
import java.util.Random;
import org.example.GamePanel;
import org.example.UtilTool;

public class TrappedGuy extends Entity{
    public int actionLock =0;
    public int dialogueIndex = 0;
    public TrappedGuy(GamePanel gp){
        super(gp);
        direction = "down";
        speed = 1;
        getImage();
        setDialogue();
    }
    public void getImage(){
       
        up1 = setup("/npc/oldman_up_1");
        left1 = setup("/npc/oldman_left_1");

        right1 = setup("/npc/oldman_right_1");


        up2 = setup("/npc/oldman_up_2");

        down1 = setup("/npc/oldman_down_1");

        down2 = setup("/npc/oldman_down_2");


        left2 = setup("/npc/oldman_left_2");

        right2 = setup("/npc/oldman_right_2");


    
    }   
    public void setDialogue(){
        dialogue[0] = "its_been_so_long";
        dialogue[1] = "since_ive_seen_a_face";
        dialogue[2] = "its_so_dark";
    }
    public void displayDialogue(){
        if(dialogue[dialogueIndex] == null){
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogue[dialogueIndex];
        dialogueIndex++;
        switch(gp.player.direction){
            case "up":
                direction = "down";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
            case "down":
                direction = "up";
                break;
        }
    }
    public void setAction(){
        actionLock ++;
        if(actionLock == 140){
            Random random = new Random();
            int i = random.nextInt(100); //rand num
            if(i <= 25){
                direction = "up";
            }
            else if(i <= 50){
                direction = "down";
            }else if(i <= 75){
                direction = "left";
            }else{
                direction = "right";
            }
            actionLock =0;
        }
      
    }
    
}
