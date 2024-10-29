package org.example.gameplugins;

public class PrintPlugin implements GamePlugin {
    GameAPI game;
    public PrintPlugin(GameAPI game) {
        this.game = game;
    }
    @Override
    public void printRetard(){
        System.out.println("REATRD");
    }
}
