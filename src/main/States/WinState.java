package main.States;

import main.Players.FirstPlayer;
import main.Players.SecondPlayer;
import main.Util.KeyHandler;
import main.Util.MouseHandler;

import java.awt.*;
import java.io.IOException;

public class WinState extends GameState {

    GameStateManager gameStateManager;
    Font font;

    public WinState(main.States.GameStateManager gsm) {
        super(gsm);
        gameStateManager = gsm;
        font = gameStateManager.getFont();
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
    public void render(Graphics2D graphics2D){
        graphics2D.setFont(this.font.deriveFont(100f));
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString("YOU WON", 440, 300);
        graphics2D.setFont(this.font.deriveFont(40f));
        graphics2D.drawString("Press E to main menu", 440, 520);
    }
}
