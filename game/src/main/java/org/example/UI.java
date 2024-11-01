package org.example;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.example.environment.GameLocalization;

public class UI {
    GamePanel gp;
    Font purisaB,Maru;
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
        try{
            InputStream  is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            Maru = Font.createFont(Font.TRUETYPE_FONT, is);

        }catch(FontFormatException e){
            System.out.println("FONT FAILURE");
        }catch(IOException e){
            System.out.println("FONT IO FAILURE");

        }
        this.uiY = gp.screenWidth-250;
        this.gameLocal = gp.gameLocal;
        
    }
    public void getUIImage(){
        try{
            uiBox = ImageIO.read(getClass().getResourceAsStream("/UI/ui.png"));

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2){
        this.g2 =g2;
        g2.setFont(Maru);
        int frameX = 10;
        int frameY = gp.screenHeight/2;
        int frameWidth = gp.screenWidth/3;
        int frameHeight = gp.tileSize*5;
        //Title Screen
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }else if(gp.gameState == gp.playState){
            if(keyH.tabPressed){
               
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
                    g2.setFont(g2.getFont().deriveFont(35F));
                    g2.setColor(Color.white);
                    
                    String name = gp.player.inventory.get(itemIndex).name.trim();
                    if (name.startsWith("\"") && name.endsWith("\"")) {
                        name = name.substring(1, name.length() - 1);  // Remove leading and trailing quotes
                    }
                    
                    g2.drawString(gameLocal.getText(name), textX, textY);
                    g2.setFont(g2.getFont().deriveFont(20F));
                    String description = gp.player.inventory.get(itemIndex).description.trim();
                    if (description.startsWith("\"") && description.endsWith("\"")) {
                        description = description.substring(1, description.length() - 1);  // Remove leading and trailing quotes
                    }
                    
                    g2.drawString(gameLocal.getText(description), textX, textY+ 30);

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
        }else if(gp.gameState == gp.victoryState){
            drawVictoryScreen();
        }
        
        
    }
    public int getItemIndex(){
        return slotCol + (slotRow*6);

    }
    public void drawLanuageScreen(){
        
        g2.setFont(Maru);

        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
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
        
        if(commandNum == 1){
            g2.drawString(">", x-gp.tileSize, y);
        }
        y += gp.tileSize*4;
        if(userInput != null){
            text = userInput;
            g2.drawString(text, x, y);

        }
        
        text = gameLocal.getText("escape");
        y += gp.tileSize*6; 
        g2.drawString(text, x, y);
        
    }
    public void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(0,0,0,210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);
        c = new Color(255,255,255);

        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }
    public void drawTitleScreen(){
        //Locale
        g2.setFont(Maru);
        g2.setColor(new Color(102,178,255));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        //Name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 70F));
        String text = gameLocal.getText("game_name");
        int x = getXforCenterScreen(text);
        int y = gp.tileSize*2;
        //Shadow drawing
        g2.setColor(Color.black);
        g2.drawString(text, x+8, y+8);
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
        //-------MENU-----//
        //--NEW GAME--//
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = gameLocal.getText("new_game");
        y += gp.tileSize*2;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">", x-gp.tileSize, y);
        }
        //-Languages-/
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
        text = gameLocal.getText("choose_language");
        y += gp.tileSize*3;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x-gp.tileSize, y);
        }

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
        text = "Exit";
        y += gp.tileSize*3;
        g2.drawString(text, x, y);
        if(commandNum == 2){
            g2.drawString(">", x-gp.tileSize, y);
        }
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
        text = "-----------------------------";
        y += gp.tileSize;
       
        g2.drawString(text, x, y);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
        text = gameLocal.getText("choose_language");

        y += gp.tileSize*2;
        g2.drawString(text, x, y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
        text = gameLocal.getText("inventory_instruction");
        y += gp.tileSize*2;
        g2.drawString(text, x, y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
        text = gameLocal.getText("wasd_instruction");

        y += gp.tileSize*2;
        g2.drawString(text, x, y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
        text = gameLocal.getText("dialogue_instruction");

        y += gp.tileSize*2;
        g2.drawString(text, x, y);

    }
    public void drawDeathScreen(){
        //Name
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
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
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        String text1 = gameLocal.getText("you_survived");
        text = text1 + " " + gp.days;
        y += gp.tileSize*3; 
        g2.drawString(text, x, y);
        
    }
    public void drawVictoryScreen(){
        //Name
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = gameLocal.getText("victory");
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
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        String text1 = gameLocal.getText("you_survived");
        String text2 = gameLocal.getText("days");
        text = text1 + " " + gp.days + " "+ text2;
        y += gp.tileSize*3; 
        g2.drawString(text, x, y);
        
    }
    public void drawDialogueScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));

        int x = gp.tileSize * 2;
        int y = gp.screenHeight/2;
        int width = gp.screenWidth - (gp.tileSize * 5);
        int height = gp.tileSize *5;
        
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
