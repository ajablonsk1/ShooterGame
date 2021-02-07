package main.Util;

import main.GamePanel;
import main.States.GameState;
import main.States.PlayState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class KeyHandler implements KeyListener {

    public static List<Key> keys = new ArrayList<>();

    public class Key{
        public int presses = 0, absorbs = 0;
        public boolean down, clicked;

        public Key(){
            keys.add(this);
        }

        public void toggle(boolean pressed) {
            if (pressed != down) {
                down = pressed;
            }
            if (pressed) {
                presses++;
            }
        }

        public void tick(){
            if(absorbs < presses){
                absorbs++;
                clicked = true;
            }
            else{
                clicked = false;
            }
        }
    }

    public Key up = new Key();
    public Key up_2 = new Key();
    public Key down = new Key();
    public Key down_2 = new Key();
    public Key left = new Key();
    public Key left_2 = new Key();
    public Key right = new Key();
    public Key right_2 = new Key();
    public Key shoot = new Key();
    public Key shoot_2 = new Key();
    public Key menu = new Key();
    public Key escape = new Key();

    private GamePanel gamePanel;

    public KeyHandler(GamePanel gamePanel){
        gamePanel.addKeyListener(this);
        this.gamePanel = gamePanel;
    }

    public void releaseAll(){
        for (Key key : keys) {
            key.down = false;
        }
    }

    public void tick() {
        for (Key key : keys) {
            key.tick();
        }
    }

    public void toggle(KeyEvent e, boolean pressed){
        if(e.getKeyCode() == KeyEvent.VK_W) up.toggle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_S) down.toggle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_A) left.toggle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_D) right.toggle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_UP) up_2.toggle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_DOWN) down_2.toggle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_LEFT) left_2.toggle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) right_2.toggle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_SPACE) shoot.toggle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_ENTER) shoot_2.toggle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_E) menu.toggle(pressed);
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) escape.toggle(pressed);
    }

    public void checkIfClicked(KeyEvent e, boolean pressed){

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(this.gamePanel.gameStateManager.getState() instanceof PlayState){
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_E){
                PlayState.paused = !PlayState.paused;
                return;
            }
        }
        toggle(e, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        toggle(e, false);
    }
}
