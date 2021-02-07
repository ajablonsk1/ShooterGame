package main.States;

import main.Util.KeyHandler;
import main.Util.MouseHandler;

import java.awt.*;

public class PauseState extends GameState {

    GameStateManager gameStateManager;

    public PauseState(GameStateManager gameStateManager) {
        super(gameStateManager);
        this.gameStateManager = gameStateManager;
    }

    @Override
    public void update() {

    }

    @Override
    public void input(KeyHandler keyHandler, MouseHandler mouseHandler) {
        if(keyHandler.menu.down){
            this.gameStateManager.unpause();
        }
    }

    @Override
    public void render(Graphics2D graphics2D) {
        graphics2D.setColor(Color.BLACK);
        graphics2D.setFont(this.gameStateManager.getFont());
        String text = "PAUSED";
        FontMetrics metrics = graphics2D.getFontMetrics();
        int x = (1280 - metrics.stringWidth(text)) / 2;
        graphics2D.drawString(text, x, 150);
    }
}
