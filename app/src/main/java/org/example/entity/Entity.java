package org.example.entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.example.GamePanel;
import org.example.UtilTool;
import java.awt.Graphics2D;

public class Entity {
    GamePanel gp;
    public int x, y;
    public int speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
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
    

    public Entity(GamePanel gp){
        this.gp = gp;
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
        BufferedImage image = null;
        image = down1;
        g2.drawImage(image, x,y, gp.tileSize, gp.tileSize, null);
    }
    
}
