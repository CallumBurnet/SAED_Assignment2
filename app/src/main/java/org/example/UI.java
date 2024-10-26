package org.example;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.example.object.Sword;

public class UI {
    GamePanel gp;
    Font arial_40;
    Image uiBox;
    InputHandler keyH;
    BufferedImage swordImage;
    int uiX, uiY;
    public UI(GamePanel gp, InputHandler keyH){
        this.gp = gp;
        this.keyH = keyH;
        getUIImage();
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        Sword sword = new Sword();
        swordImage = sword.image;
        this.uiY = gp.screenWidth-250;
        
    }
    public void getUIImage(){
        try{
            System.out.println("r");
            uiBox = ImageIO.read(getClass().getResourceAsStream("/UI/ui.png"));

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2){
        if(keyH.tabPressed){
            g2.drawImage(uiBox, 0,gp.screenWidth-250, gp.tileSize*10 , gp.tileSize*5, null);
            g2.drawImage(swordImage,gp.tileSize/2, gp.screenWidth-230, gp.tileSize, gp.tileSize, null);


        }
        
    }
}
