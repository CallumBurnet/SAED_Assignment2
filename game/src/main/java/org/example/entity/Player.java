package org.example.entity;

import java.awt.Rectangle;
import java.util.ArrayList;
import org.example.GamePanel;
import org.example.InputHandler;

public class Player extends Entity {
    GamePanel gp;
    InputHandler keyH;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public Player(GamePanel gp, InputHandler keyH, int playerX, int playerY){
        super(gp);
        this.gp = gp;
        this.keyH = keyH;
        
        x = playerX;
        y = playerY;

        System.out.println("SHOULD BE SET");

        solidArea = new Rectangle(8,16,28,28);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDeafultValues();
        getPlayerImage();
    }
    public void setDeafultValues(){
        
        speed = 4;
        direction = "down";
        //inventory.add(new Sword());
    }
    public ArrayList<Entity> getInventory(){
        return inventory;
    }
    public void getPlayerImage(){
       
            up1 = setup("/player/up1");
            left1 = setup("/player/left1");

            right1 = setup("/player/right1");

            up2 = setup("/player/up2");

            down1 = setup("/player/down1");

            down2 = setup("/player/down2");

            left2 = setup("/player/left2");
            right2 = setup("/player/right2");

        
    }

   
    public void update(){
        if(keyH.upPressed ||keyH.downPressed || keyH.leftPressed||keyH.rightPressed){
            if(keyH.upPressed){
                direction = "up";
          }
            if(keyH.downPressed){
                direction = "down";
    
                
            }
            if(keyH.leftPressed){
                direction = "left";
    
                
            }if(keyH.rightPressed){
                direction = "right";
    
              
            }
            this.gp.gameAPI.notifyPlayerMove(direction);
            //Check tile collision
            collisionOn = false;
            gp.cDetector.checkTile(this);
            //Check object collision
            int objIndex = gp.cDetector.checkObject(this, true);
            pickUpItem(objIndex);
            //Check npc obstacle collision
            int npcIndex = gp.cDetector.checkEntity(this, gp.npc);
            interact(npcIndex, "npc");
            int obsIndex = gp.cDetector.checkEntity(this, gp.obstacles);
            interact(obsIndex, "obstacle");
            int goalIndex = gp.cDetector.checkEntity(this, gp.goals);
            interact(goalIndex, "goal");

            //If collision false
            if(collisionOn == false){
                gp.gameAPI.onPlayerMove(direction);

                switch (direction) {

                    case "up":
                        y -= speed;

                        break;
                    case "down":
                        y += speed;
                        break;
                    case "left":
                        x -= speed;
                        break;
                    case "right":
                        x += speed;
                        break;
                    
                }
            }
           spriteCounter++;
            if(spriteCounter > 20){
                if(spriteNum == 1){
                    spriteNum =2;
                }else if(spriteNum == 2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
       
    }
    
    public void pickUpItem(int i){
        if(i != 999 && collisionOn == false){ //999 means we didnt pick up[ anythign]
            inventory.add(gp.obj[i]);
            gp.gameAPI.notifyNewItemAcquired(gp.obj[i]);

            gp.obj[i] = null;

        }
    }
    public void addItem(Entity weapon){
        inventory.add(weapon);
    }
    public void interact(int i, String nature){
        if(i != 999 ){ //999 means we didnt pick up[ anythign]
           
                if(nature.equals("npc") && gp.keyH.enterPressed == true){
                    gp.gameState = gp.dialogueState;
                    gp.npc[i].displayDialogue();
                }else if(nature.equals("obstacle")){
                    System.out.println(gp.obstacles[i].unlocked == true);
                    if(hasItem(i) || gp.obstacles[i].unlocked == true){
                        System.out.println("sending to notify");
                        gp.gameAPI.notifyOnObstacleDefeated(gp.obstacles[i]);
                        gp.obstacles[i] = null;
                        

                        

                    }else{
                        gp.gameState = gp.dialogueState;
                        gp.obstacles[i].displayDialogue();
                    }
                }else if(nature.equals("goal")){
                    gp.gameState = gp.victoryState;
                }
            }
            gp.keyH.enterPressed = false;

        }
    
    public boolean hasItem(int i) {
    for (Entity entity : inventory) {
        if(entity.name.equals(gp.obstacles[i].requiredItem())){
            System.out.println("MATCHING ");
            return true;
        }
    }
    return false; // Return false if no matching item is found
    }
    public int[] getPosition() {
        // Example of initializing an array with two coordinates (e.g., x and y positions)
        int[] array = new int[] {x, y}; // Replace 0, 0 with actual values or variables
        return array;
    }
    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }
    public GamePanel getGamePanel(){
        return gp;
    }
    

    
}
