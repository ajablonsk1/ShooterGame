package main.States;

import main.Util.KeyHandler;
import main.Util.MouseHandler;

import java.awt.*;
import java.io.IOException;

public class ControlsState extends GameState {

    GameStateManager gameStateManager;

    String text1 = "Player 1 movement";
    String text2 = "W to go forward";
    String text3 = "S to go backward";
    String text4 = "A to go left";
    String text5 = "D to go right";
    String text6 = "Space to shoot";
    String text11 = "Player 2 movement";
    String text22 = "arrow up to go forward";
    String text33 = "arrow down to go backward";
    String text44 = "arrow left to go left";
    String text55= "arrow right to go right";
    String text66 = "enter to shoot";
    String pause = "E to pause";
    String exit = "ESC to go to the menu";
    String life = "Hearts heal";
    String dmg = "Gun increase dmg";
    String yerba = "Yerba increase speed";
    String bullet = "bullet increase shooting speed";

    public ControlsState(main.States.GameStateManager gsm) {
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

        }
    }

    @Override
    public void render(Graphics2D graphics2D) throws IOException, FontFormatException {
        graphics2D.setColor(Color.BLACK);
        graphics2D.setFont(this.gameStateManager.getFont().deriveFont(40f));
        graphics2D.drawString(text1, 150, 100);
        graphics2D.drawString(text2, 150, 200);
        graphics2D.drawString(text3, 150, 250);
        graphics2D.drawString(text4, 150, 300);
        graphics2D.drawString(text5, 150, 350);
        graphics2D.drawString(text6, 150, 400);
        graphics2D.drawString(text11, 650, 100);
        graphics2D.drawString(text22, 650, 200);
        graphics2D.drawString(text33, 650, 250);
        graphics2D.drawString(text44, 650, 300);
        graphics2D.drawString(text55, 650, 350);
        graphics2D.drawString(text66, 650, 400);
        graphics2D.drawString(pause, 150, 550);
        graphics2D.drawString(exit, 150, 600);
        graphics2D.drawString(life, 650, 525);
        graphics2D.drawString(dmg, 650, 575);
        graphics2D.drawString(yerba, 650, 625);
        graphics2D.drawString(bullet, 650, 675);

    }
}
