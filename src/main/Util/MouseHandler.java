package main.Util;

import main.GamePanel;
import main.Players.FirstPlayer;
import main.Players.SecondPlayer;
import main.States.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener {

    private static int mouseX = -1;
    private static int mouseY = -1;
    private static int mouseB = -1;

    GamePanel gamePanel;

    public MouseHandler(GamePanel gamePanel){
        gamePanel.addMouseListener(this);
        gamePanel.addMouseMotionListener(this);
        this.gamePanel = gamePanel;
    }

    public int getMouseX(){
        return mouseX;
    }

    public int getMouseY(){
        return mouseY;
    }

    public int getButton(){
        return mouseB;
    }



    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseB = e.getButton();
        int x = e.getX();
        int y = e.getY();
        if(this.gamePanel.gameStateManager.getState() instanceof MenuState) {
            if (x >= 300 && x <= 980 && y >= 250 && y <= 330) {
                this.gamePanel.gameStateManager.changeState(8, null);
            }
            if (x >= 300 && x <= 980 && y >= 350 && y <= 430) {
                this.gamePanel.gameStateManager.changeState(7, null);
            }
            if (x >= 300 && x <= 980 && y >= 450 && y <= 530) {
                this.gamePanel.gameStateManager.changeState(4, null);
            }
            if (x >= 300 && x <= 980 && y >= 550 && y <= 630) {
                System.exit(1);
            }
        }
        else if(this.gamePanel.gameStateManager.getState() instanceof ChooseMapState){
            if (x >= 300 && x <= 980 && y >= 250 && y <= 330) {
                if(FirstPlayer.isDead){
                    FirstPlayer.hp = 5;
                    FirstPlayer.isDead = false;
                    FirstPlayer.onePlayer = false;
                }
                if(SecondPlayer.isDead){
                    SecondPlayer.isDead = false;
                }
                this.gamePanel.gameStateManager.changeState(0, "main/Graphics/TileMap/deHouse.xml");
            }
            if (x >= 300 && x <= 980 && y >= 350 && y <= 430) {
                if(FirstPlayer.isDead){
                    FirstPlayer.hp = 5;
                    FirstPlayer.isDead = false;
                    FirstPlayer.onePlayer = false;
                }
                if(SecondPlayer.isDead){
                    SecondPlayer.isDead = false;
                }
                this.gamePanel.gameStateManager.changeState(0, "main/Graphics/TileMap/de_storehouse.xml");
            }
            if (x >= 300 && x <= 980 && y >= 450 && y <= 530) {
                if(FirstPlayer.isDead){
                    FirstPlayer.hp = 5;
                    FirstPlayer.isDead = false;
                    FirstPlayer.onePlayer = false;
                }
                if(SecondPlayer.isDead){
                    SecondPlayer.isDead = false;
                }
                this.gamePanel.gameStateManager.changeState(0, "main/Graphics/TileMap/de_dust.xml");
            }
        }
        else if(this.gamePanel.gameStateManager.getState() instanceof ChooseLevelState){
            if (x >= 300 && x <= 980 && y >= 250 && y <= 330) {
                if(FirstPlayer.isDead){
                    FirstPlayer.hp = 5;
                    FirstPlayer.isDead = false;
                    FirstPlayer.onePlayer = true;
                }
                this.gamePanel.gameStateManager.changeState(6, "main/Graphics/TileMap/deHouse.xml");
            }
            if (x >= 300 && x <= 980 && y >= 350 && y <= 430) {
                if(FirstPlayer.isDead){
                    FirstPlayer.hp = 5;
                    FirstPlayer.isDead = false;
                    FirstPlayer.onePlayer = true;
                }
                this.gamePanel.gameStateManager.changeState(6, "main/Graphics/TileMap/de_storehouse.xml");
            }
            if (x >= 300 && x <= 980 && y >= 450 && y <= 530) {
                if(FirstPlayer.isDead){
                    FirstPlayer.hp = 5;
                    FirstPlayer.isDead = false;
                    FirstPlayer.onePlayer = false;
                }
                if(SecondPlayer.isDead){
                    SecondPlayer.isDead = false;
                }
                this.gamePanel.gameStateManager.changeState(6, "main/Graphics/TileMap/de_dust.xml");
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseB = -1;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
}
