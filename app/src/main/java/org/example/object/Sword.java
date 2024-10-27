package org.example.object;
import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.example.GamePanel;
import org.example.entity.Entity;
public class Sword extends Entity{
    public Sword(GamePanel gp){
        super(gp);

        name = "Sword";
        down1 = setup("/objects/diamondsword");
        description = "Long lost";
    }
}
