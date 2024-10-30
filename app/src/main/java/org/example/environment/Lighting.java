package org.example.environment;

import org.example.GamePanel;

import com.google.common.primitives.Floats;

import java.awt.geom.*;
import java.awt.*;
import java.awt.image.*;

public class Lighting {
    GamePanel gp;
    BufferedImage darknessFilter;
    int circleSize;
    Area screenArea;
    public Lighting(GamePanel gp, int circleSize){
        this.gp = gp;
        this.circleSize = circleSize;
        this.darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();
        Area screenArea = new Area(new Rectangle2D.Double(0,0, gp.screenWidth, gp.screenHeight));
        this.screenArea = screenArea;
        


    }
    public void draw(Graphics2D g2) {
        // Reset the darkness filter
        this.darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gFilter = (Graphics2D) darknessFilter.getGraphics();
        
        // Reset screenArea to cover the full screen
        screenArea = new Area(new Rectangle2D.Double(0, 0, gp.screenWidth, gp.screenHeight));
        
        // Get character's current center
        int centreX = gp.player.x + (gp.tileSize) / 2;
        int centreY = gp.player.y + (gp.tileSize) / 2;
        
        // Define light circle based on the character's center
        double x = centreX - (circleSize / 2);
        double y = centreY - (circleSize / 2);
        Shape circleShape = new Ellipse2D.Double(x, y, circleSize, circleSize);
        Area lightArea = new Area(circleShape);
        
        // Subtract the light area from the screen area
        screenArea.subtract(lightArea);
        
        // Set up gradient for the light circle
        Color[] colors = { new Color(0, 0, 0, 0f), new Color(0, 0, 0, 0.25f), new Color(0, 0, 0, 0.5f), new Color(0, 0, 0, 0.75f), new Color(0, 0, 0, 0.93f) };
        float[] fractions = { 0f, 0.25f, 0.5f, 0.75f, 1f };
        RadialGradientPaint gPaint = new RadialGradientPaint(centreX, centreY, circleSize / 2, fractions, colors);
        
        // Apply the gradient to the light area
        gFilter.setPaint(gPaint);
        gFilter.fill(lightArea);
        
        // Fill the remaining area with a solid black color for darkness
        gFilter.setColor(new Color(0, 0, 0, 0.95f));
        gFilter.fill(screenArea);
        gFilter.dispose();
        
        // Draw the darkness filter on the screen
        g2.drawImage(darknessFilter, 0, 0, null);
    }
    
    
}
