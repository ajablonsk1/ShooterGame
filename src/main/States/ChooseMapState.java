package main.States;

import main.Util.KeyHandler;
import main.Util.MouseHandler;

import java.awt.*;
import java.io.IOException;

public class ChooseMapState extends GameState {

    GameStateManager gameStateManager;

    String text4 = "CS 2Defensive";
    String map1 = "House";
    String map2 = "Storehouse";
    String map3 = "Dust";

    private Rectangle House = new Rectangle(300, 250, 680, 80);
    private Rectangle StoreHouse = new Rectangle(300, 350, 680, 80);
    private Rectangle Dust= new Rectangle(300, 450, 680, 80);

    public ChooseMapState(main.States.GameStateManager gsm) {
        super(gsm);
        this.gameStateManager = gsm;
    }

    @Override
    public void update() {

    }

    @Override
    public void input(KeyHandler keyHandler, MouseHandler mouseHandler) {
        if(keyHandler.escape.down){
            this.gameStateManager.changeState(1, null);
            return;
        }
    }

    @Override
    public void render(Graphics2D graphics2D) throws IOException, FontFormatException {
        graphics2D.setColor(Color.BLACK);
        graphics2D.setFont(this.gameStateManager.getFont().deriveFont(150f));
        FontMetrics metrics = graphics2D.getFontMetrics();
        int x = (1280 - metrics.stringWidth(text4)) / 2;
        graphics2D.drawString(text4, x, 180);
        graphics2D.setFont(this.gameStateManager.getFont().deriveFont(60f));
        metrics = graphics2D.getFontMetrics();
        x = 300 + (680-metrics.stringWidth(map1)) / 2;
        graphics2D.drawString(map1, x, 310);
        x = 300 + (680-metrics.stringWidth(map2)) / 2;
        graphics2D.drawString(map2, x, 410);
        x = 300 + (680-metrics.stringWidth(map3)) / 2;
        graphics2D.drawString(map3, x, 510);
        graphics2D.draw(House);
        graphics2D.draw(StoreHouse);
        graphics2D.draw(Dust);
    }
}
