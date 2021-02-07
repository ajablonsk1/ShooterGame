package main.States;

import main.GamePanel;
import main.Util.KeyHandler;
import main.Util.MouseHandler;
import main.Util.Vector2D;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GameStateManager {

    private ArrayList<GameState> gameStates;

    public static Vector2D map;

    public static final int PLAY = 0;
    public static final int MENU = 1;
    public static final int PAUSE = 2;
    public static final int GAME_OVER = 3;
    public static final int CONTROLS = 4;
    public static final int MAP = 5;
    public static final int PLAY_ONE = 6;
    public static final int CHOOSE_MAP = 7;
    public static final int CHOOSE_LEVEL = 8;
    public static final int YOU_WON = 9;
    public static final int GAME_OVER_ONE = 10;


    GameState playState;


    private GamePanel gamePanel;


    public GameStateManager(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        map = new Vector2D(gamePanel.width, gamePanel.height);
        gameStates = new ArrayList<>();

        gameStates.add(new MenuState(this));
    }

    public void removeState(int state){
        gameStates.remove(state);
    }

    public void addState(int state, String path){
        if(state == PLAY){
            gameStates.add(new PlayState(this, path));
        }
        if(state == MENU){
            gameStates.add(new MenuState(this));
        }
        if(state == PAUSE){
            gameStates.add(new PauseState(this));
        }
        if(state == GAME_OVER){
            gameStates.add(new GameOverState(this));
        }
        if(state == CONTROLS){
            gameStates.add(new ControlsState(this));
        }
        if(state == MAP){
            gameStates.add(new MapState(this));
        }
        if(state == PLAY_ONE){
            gameStates.add(new PlayForOne(this, path));
        }
        if(state == CHOOSE_MAP){
            gameStates.add(new ChooseMapState(this));
        }
        if(state == CHOOSE_LEVEL){
            gameStates.add(new ChooseLevelState(this));
        }
        if(state == YOU_WON){
            gameStates.add(new WinState(this));
        }
        if(state == GAME_OVER_ONE){
            gameStates.add(new GameOverStateForOnePlayer(this));
        }
    }

    public void changeState(int state, String path){
        gameStates.remove(0);
        addState(state, path);
    }

    public void pause(){
        this.playState = gameStates.remove(0);
        addState(PAUSE, null);
    }

    public void unpause(){
        gameStates.remove(0);
        gameStates.add(this.playState);
    }


    public void update(){
        for(int i = 0; i < gameStates.size(); i++){
            gameStates.get(i).update();
        }
    }

    public void input(KeyHandler keyHandler, MouseHandler mouseHandler) {
        for(int i = 0; i < gameStates.size(); i++){
            gameStates.get(i).input(keyHandler, mouseHandler);
        }
    }

    public void render(Graphics2D g) throws IOException, FontFormatException {
        for(int i = 0; i < gameStates.size(); i++){
            gameStates.get(i).render(g);
        }
    }

    public Font getFont(){
        return gamePanel.getFont();
    }

    public GameState getState(){
        if(gameStates.size() != 0) {
            return gameStates.get(0);
        }
        return null;
    }
}
