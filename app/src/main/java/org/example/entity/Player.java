package org.example.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import org.example.GamePanel;
import org.example.InputHandler;
import org.example.UtilTool;
import org.example.object.Weapon;

import com.google.common.escape.ArrayBasedCharEscaper;

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
        System.out.println("Player x " + x + y);
        solidArea = new Rectangle(8,16,32,32);
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
    public void getPlayerImage(){
       
            up1 = setup("boy_up_1");
            left1 = setup("boy_left_1");

            right1 = setup("boy_right_1");

            up2 = setup("boy_up_2");

            down1 = setup("boy_down_1");

            down2 = setup("boy_down_2");

            left2 = setup("boy_left_2");
            right2 = setup("boy_right_2");

        
    }

    public BufferedImage setup(String imageName){
        UtilTool uTool = new UtilTool();
        BufferedImage scaledImage = null;
        try{
            scaledImage = ImageIO.read(getClass().getResourceAsStream("/player/" +imageName+".png"));
            scaledImage = uTool.scaledImage(scaledImage, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
        return scaledImage;
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
            //Check tile collision
            collisionOn = false;
            gp.cDetector.checkTile(this);
            //Check object collision
            int objIndex = gp.cDetector.checkObject(this, true);
            
            pickUpItem(objIndex);

            //If collision false
            if(collisionOn == false){
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
    public void draw(Graphics2D g2){
        BufferedImage image = null;
        switch(direction){
            case "up":
                if(spriteNum == 1){
                    image = up1;

                }
                if(spriteNum == 2){
                    image = up2;

                }
                break;
            case "down":
                 if(spriteNum == 1){
                    image = down1;

                }
                if(spriteNum == 2){
                    image = down2;

                }
                break;
            case "left":
                if(spriteNum == 1){
                    image = left1;

                }
                if(spriteNum == 2){
                    image = left2;

                }
                break;
            case "right":
                if(spriteNum == 1){
                    image = right1;

                }
                if(spriteNum == 2){
                    image = right2;

                }
                break;
        }
        g2.drawImage(image, x,y, gp.tileSize, gp.tileSize, null);
    }
    public void pickUpItem(int i){
        if(i != 999 && collisionOn == false){ //999 means we didnt pick up[ anythign]
            inventory.add(gp.obj[i]);
            gp.obj[i] = null;

        }
    }
}
