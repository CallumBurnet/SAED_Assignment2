package org.example.environment;

import org.example.GamePanel;
import java.awt.*;
public class EnvironmentManager {
    GamePanel gp;
    Lighting lighting;
    public EnvironmentManager(GamePanel gp){
        this.gp = gp;
    }
    public void setup(){
        lighting = new Lighting(gp, 200);
    }
    public void draw(Graphics2D g2, boolean visible){
        System.out.println(visible);
        lighting.draw(g2, visible);
    }
}
