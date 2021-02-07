package main.States;

import main.Players.FirstPlayer;
import main.Players.SecondPlayer;
import main.Util.KeyHandler;
import main.Util.MouseHandler;

import java.awt.*;

public class MenuState extends GameState {

    GameStateManager gameStateManager;
    String text = "Game for one player";
    String text1 = "Game for two player";
    String text2 = "Controls";
    String text3 = "Exit";
    String text4 = "CS 2Defensive";
    int menu = 0;
    private Rectangle playOneButton = new Rectangle(300, 250, 680, 80);
    private Rectangle playTwoButton = new Rectangle(300, 350, 680, 80);
    private Rectangle controlsButton = new Rectangle(300, 450, 680, 80);
    private Rectangle quit = new Rectangle(300, 550, 680, 80);

    public MenuState(GameStateManager gameStateManager) {
        super(gameStateManager);
        this.gameStateManager = gameStateManager;
    }

    @Override
    public void update() {
        FirstPlayer.isDead = false;
        SecondPlayer.isDead = false;
    }

    @Override
    public void input(KeyHandler keyHandler, MouseHandler mouseHandler) {
        if(keyHandler.up.clicked || keyHandler.up_2.clicked){
            if(menu > 0) {
                menu -= 1;
            }
        }
        if(keyHandler.down.clicked || keyHandler.down_2.clicked){
            if(menu < 3) {
                menu += 1;
            }
        }
    }

    @Override
    public void render(Graphics2D graphics2D) {
        graphics2D.setColor(Color.BLACK);
        graphics2D.setFont(this.gameStateManager.getFont().deriveFont(150f));
        FontMetrics metrics = graphics2D.getFontMetrics();
        int x = (1280 - metrics.stringWidth(text4)) / 2;
        graphics2D.drawString(text4, x, 180);
        graphics2D.setFont(this.gameStateManager.getFont().deriveFont(60f));
        metrics = graphics2D.getFontMetrics();
        x = 300 + (680-metrics.stringWidth(text)) / 2;
        graphics2D.drawString(text, x, 310);
        x = 300 + (680-metrics.stringWidth(text1)) / 2;
        graphics2D.drawString(text1, x, 410);
        x = 300 + (680-metrics.stringWidth(text2)) / 2;
        graphics2D.drawString(text2, x, 510);
        x = 300 + (680-metrics.stringWidth(text3)) / 2;
        graphics2D.drawString(text3, x, 610);
        graphics2D.draw(playOneButton);
        graphics2D.draw(playTwoButton);
        graphics2D.draw(controlsButton);
        graphics2D.draw(quit);
    }
}
