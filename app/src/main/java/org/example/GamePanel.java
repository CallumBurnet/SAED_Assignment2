package org.example;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.text.PlainDocument;

import org.example.entity.Player;
import org.example.tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
    // SCREEN settings
    final int originalTileSize = 16; //16x16 characters
    final int scale = 3; // makes it 48 x 48 it scales
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; //768
    final int screenHeight= tileSize * maxScreenRow; //576
    //FPS
    int FPS = 60;
    TileManager tileManager = new TileManager(this);
    InputHandler keyH = new InputHandler();
    Thread gameThread;
    //PLAYER
    Player player = new Player(this, keyH );
    //default player pos
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override //THE GAME LOOP IS HERE
    public void run(){
        //DELTA ACCUMULATOR METHOD OF GAME LOOP
        double drawInterval = 1000000000/FPS;
        double delta =0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        while(gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if(delta >=1){
                update();
                repaint();
                delta --;
                drawCount ++;
            }
            if(timer >= 1000000000){
                System.out.println("FPS:" + drawCount);
                drawCount =0;
                timer = 0;
            }
            
        }
    }
    public void update(){
        player.update();
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g; //change graphics to 2d more functions
        tileManager.draw(g2);
        player.draw(g2);
        g2.dispose(); //mem saver
    }
}
