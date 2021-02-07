package main.States;

import main.Graphics.ImageHandler;
import main.Players.FirstPlayer;
import main.Players.SecondPlayer;
import main.Util.KeyHandler;
import main.Util.MouseHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameOverStateForOnePlayer extends GameState {

    private final BufferedImage image;
    private Font font;
    private boolean menu = false;
    GameStateManager gameStateManager;

    public GameOverStateForOnePlayer(GameStateManager gameStateManager) {
        super(gameStateManager);
        this.gameStateManager = gameStateManager;
        this.font = gameStateManager.getFont();
        ImageHandler imageHandler = new ImageHandler("main/Graphics/gameOver/gameover.png", 512, 449);
        this.image = imageHandler.getImage(0,0);
    }

    @Override
    public void update() {

    }

    @Override
    public void input(KeyHandler keyHandler, MouseHandler mouseHandler) {
        if(keyHandler.menu.down){
            this.gameStateManager.changeState(1, null);
        }
    }

    @Override
    public void render(Graphics2D graphics2D) {
        graphics2D.drawImage(image, 384, 100, 512, 449, null);
        graphics2D.setFont(this.font.deriveFont(40f));
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString("Press E to main menu", 440, 520);
    }
}
