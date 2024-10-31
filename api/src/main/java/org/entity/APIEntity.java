package org.entity;

// Import relevant libraries for properties used
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.Graphics2D;

public interface APIEntity {
    int getX();
    int getY();
    int getSpeed();
    String getName();
    String getDirection();
    boolean isCollisionOn();
    Rectangle getSolidArea();

    void setX(int x);
    void setY(int y);
    void setDirection(String direction);
    void setAction();
    void update();
    void draw(Graphics2D g2);
    BufferedImage setup(String imagePath);
    void displayDialogue();
}
