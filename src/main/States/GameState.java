package main.States;

import main.Util.KeyHandler;
import main.Util.MouseHandler;

import java.awt.*;
import java.io.IOException;

public abstract class GameState {

    private GameStateManager GameStateManager;

    public GameState(GameStateManager gsm) {
        this.GameStateManager = gsm;
    }

    public abstract void update();

    public abstract void input(KeyHandler keyHandler, MouseHandler mouseHandler);

    public abstract void render(Graphics2D graphics2D) throws IOException, FontFormatException;
}
