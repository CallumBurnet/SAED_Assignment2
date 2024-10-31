package org.example;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.example.environment.GameLocalization;
import org.example.object.Weapon;

public class UI {
    GamePanel gp;
    Font arial_40;
    Image uiBox;
    InputHandler keyH;
    BufferedImage swordImage;
    int uiX, uiY;
    Graphics2D g2;
    public int slotCol = 0;
    public int slotRow = 0;
    public int commandNum =0;
    public String currentDialogue;
    private GameLocalization gameLocal;
    public String userInput = "";
    public UI(GamePanel gp, InputHandler keyH){
        this.gp = gp;
        this.keyH = keyH;
        getUIImage();
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        
        this.uiY = gp.screenWidth-250;
        this.gameLocal = gp.gameLocal;
        
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
        this.g2 =g2;
        int frameX = 10;
        int frameY = gp.screenHeight/2;
        int frameWidth = gp.screenWidth/3;
        int frameHeight = gp.tileSize*5;
        //Title Screen
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }else if(gp.gameState == gp.playState){
            if(keyH.tabPressed){
                //g2.drawImage(uiBox, 0,gp.screenWidth-250, gp.tileSize*10 , gp.tileSize*5, null);
                //g2.drawImage(swordImage,gp.tileSize/2, gp.screenWidth-230, gp.tileSize, gp.tileSize, null);
                drawSubWindow(frameX, frameY, frameWidth, frameHeight);
                final int slotXstart = frameX +20;
                final int slotYstart = frameY + 20;
                //SLOTS
                int slotX = slotXstart;
                int slotY = slotYstart;
                //Draw the inv
                for(int i = 0; i < gp.player.inventory.size(); i++){
                    g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
                    slotX += gp.tileSize;
    
                }
                //Cursor
                int cursorX = slotXstart + (gp.tileSize * slotCol);
                int cursorY = slotYstart+  (gp.tileSize * slotRow);
                int cursorWidth = gp.tileSize;
                int cursorHeight = gp.tileSize ;
                //DrawAing cursor
                g2.setColor(Color.white);
                g2.setStroke(new BasicStroke(3));
                g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
    
                //Description window
               int dFrameX = frameX;
               int dFrameY = frameY + frameHeight;
               int dFrameWidth = frameWidth;
               int dFrameHeight = gp.tileSize*3;
               //Draw Description
               int textX = dFrameX + 20;
               int textY = dFrameY + gp.tileSize;
               
               int itemIndex = getItemIndex();
               if(itemIndex < gp.player.inventory.size()){
    
                    drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
                    g2.setFont(g2.getFont().deriveFont(29F));
                    g2.setColor(Color.white);

                    g2.drawString(gameLocal.getText(gp.player.inventory.get(itemIndex).name), textX, textY);
                    g2.setFont(g2.getFont().deriveFont(20F));
                    g2.drawString(gameLocal.getText(gp.player.inventory.get(itemIndex).description), textX, textY+ 20);

               }
                drawSubWindow(gp.screenWidth- 6* gp.tileSize, gp.screenHeight- 3* gp.tileSize, 6*gp.tileSize, 3*gp.tileSize);
                g2.setFont(g2.getFont().deriveFont(20F));
                g2.setColor(Color.white);
                textX = gp.screenWidth - 5* gp.tileSize ;
                textY = gp.screenHeight -  1* gp.tileSize;
                g2.drawString(gp.gameDate.getFormattedDate(), textX, textY);



            }
        }else if(gp.gameState == gp.dialogueState){
            
            drawDialogueScreen();
        }else if(gp.gameState == gp.deathState){
            drawDeathScreen();
        }else if(gp.gameState == gp.languageState){
            drawLanuageScreen();
        }
        
        
    }
    public int getItemIndex(){
        int itemIndex = slotCol + (slotRow*6);
        return itemIndex;

    }
    public void drawLanuageScreen(){
        //Locale
//Name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
        g2.setColor(Color.WHITE);
        String text = gameLocal.getText("enter_language_tag");
        //function to find centre for the text
        int x = getXforCenterScreen(text);
        int y = gp.tileSize*3;
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        //Name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

        y += gp.tileSize*4;
        if(userInput != null){
            text = userInput;
            g2.drawString(text, x, y);

        }
    }
    public void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(0,0,0,210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }
    public void drawTitleScreen(){
        //Locale

        //Name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = gameLocal.getText("game_name");
        //function to find centre for the text
        int x = getXforCenterScreen(text);
        int y = gp.tileSize*3;
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
        //-------MENU-----//
        //--NEW GAME--//
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = gameLocal.getText("new_game");
        y += gp.tileSize*4;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">", x-gp.tileSize, y);
        }
        //-Languages-/
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = gameLocal.getText("choose_language");
        y += gp.tileSize*5;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x-gp.tileSize, y);
        }

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "Exit";
        y += gp.tileSize*6;
        g2.drawString(text, x, y);
        if(commandNum == 2){
            g2.drawString(">", x-gp.tileSize, y);
        }
    }
    public void drawDeathScreen(){
        //Name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        g2.setColor(Color.WHITE);
        String text = gameLocal.getText("died");
        //function to find centre for the text
        int x = getXforCenterScreen(text);
        int y = gp.tileSize*3;
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
        //-------MENU-----//
        //--NEW GAME--//
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = gameLocal.getText("new_game");
        y += gp.tileSize*4; 
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">", x-gp.tileSize, y);
        }
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = gameLocal.getText("exit_game");
        y += gp.tileSize*6; 
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x-gp.tileSize, y);
        }
    }
    public void drawDialogueScreen(){
        int x = gp.tileSize * 2;
        int y = gp.screenHeight/2;
        int width = gp.screenWidth - (gp.tileSize * 5);
        int height = gp.tileSize *5;
        g2.setFont(arial_40);
        drawSubWindow(x, y, width, height);
        x+= gp.tileSize;
        y+=gp.tileSize;
        g2.setColor(Color.white);
        g2.drawString(gameLocal.getText(currentDialogue), x, y);
    }
    public int getXforCenterScreen(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x = gp.screenWidth/2 -length/2;
        return x;
    }
}
