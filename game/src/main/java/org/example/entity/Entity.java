package org.example.entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.entity.APIEntity;
import org.example.GamePanel;
import org.example.UtilTool;
import java.awt.Graphics2D;
import java.io.Serializable;

public class Entity implements APIEntity {
    public GamePanel gp;
    public int x, y;
    public int speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public boolean unlocked = false;
    public String direction = "down";
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea = new Rectangle(0,0,48,48); // setting a better collision box (hitbox)
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    //Image stuff
    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;
    //Item specific
    public String description = " ";
    public String dialogue[] = new String[20];
    

    public Entity(GamePanel gp){
        this.gp = gp;
    }
    public void displayDialogue(){

    }
    public BufferedImage setup(String imagePath){
       UtilTool uTool = new UtilTool();
       BufferedImage image = null;
       try{
        image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
        image = uTool.scaledImage(image, gp.tileSize, gp.tileSize);
       }catch(IOException e){
        e.printStackTrace();
       }
       return image;
    }
    public void draw(Graphics2D g2){
        
        if(gp.gameState == gp.playState){
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
        image = down1;
        g2.drawImage(image, x,y, gp.tileSize, gp.tileSize, null);

       
       
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getSpeed() {
        return this.speed;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDirection() {
        return this.direction;
    }

    @Override
    public boolean isCollisionOn() {
        return this.collisionOn;
    }

    @Override
    public Rectangle getSolidArea() {
        return this.solidArea;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setAction(){
    }
    public void update(){ 
        setAction();
        collisionOn = false;
        gp.cDetector.checkTile(this);
        gp.cDetector.checkObject(this, false);
        gp.cDetector.checkPlayer(this);
        if(collisionOn == false && gp.gameState == gp.playState){
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
