package org.example.gameplugins;

public class PrintPlugin implements GamePlugin {
    private GameAPI gameAPI;

    @Override
    public void initialize(GameAPI gameAPI) {
        this.gameAPI = gameAPI;
        System.out.println("PrintPlugin initialized!");
    }

    @Override
    public void execute() {
        System.out.println("PrintPlugin message: Player is at location " 
            + gameAPI.getPlayerLocation()[0] + ", " + gameAPI.getPlayerLocation()[1]);
    }
}

