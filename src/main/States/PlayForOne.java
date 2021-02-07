package main.States;

import main.Enemy.Enemy;
import main.Enemy.Robot;
import main.Enemy.Solider;
import main.Enemy.Zombie;
import main.GamePanel;
import main.Graphics.ImageHandler;
import main.Maps.MapManager;
import main.Players.FirstPlayer;
import main.PowerUps.PowerUpsHandler;
import main.Util.KeyHandler;
import main.Util.MouseHandler;
import main.Util.Vector2D;

import java.awt.*;
import java.util.LinkedList;

public class PlayForOne extends GameState {

    GameStateManager gameStateManager;
    private FirstPlayer firstPlayer;
    private Font font;
    private MapManager mapManager;
    public static boolean paused;
    public LinkedList<Enemy> enemies = new LinkedList<>();

    public PlayForOne(GameStateManager gameStateManager, String path){
        super(gameStateManager);
        mapManager = new MapManager(path);
        this.gameStateManager = gameStateManager;
        this.font = gameStateManager.getFont();
        this.font = this.font.deriveFont(80f);
        Vector2D first = new Vector2D();
        Vector2D second = new Vector2D();
        if(path.equals("main/Graphics/TileMap/deHouse.xml")){
            first.x = 64;
            first.y = 384;
            second.x = 960;
            second.y = 256;
            enemies.add(new Robot(new Vector2D(960, 256), 0));
            enemies.add(new Zombie(new Vector2D(768, 512), 0));
        }
        else if(path.equals("main/Graphics/TileMap/de_storehouse.xml")){
            first.x = 64;
            first.y = 384;
            second.x = 1100;
            second.y = 320;
            enemies.add(new Zombie(new Vector2D(1080, 512), 0));
            enemies.add(new Robot(new Vector2D(600, 128), 0));
            enemies.add(new Solider(new Vector2D(832, 576)));
        }
        else if (path.equals("main/Graphics/TileMap/de_dust.xml")){
            first.x = 64;
            first.y = 384;
            second.x = 1100;
            second.y = 320;
            enemies.add(new Zombie(new Vector2D(1080, 512), 0));
            enemies.add(new Robot(new Vector2D(600, 128), 0));
            enemies.add(new Robot(new Vector2D(720, 350), 0));
            enemies.add(new Solider(new Vector2D(852, 512)));
            enemies.add(new Solider(new Vector2D(804, 230)));
        }
        firstPlayer = new FirstPlayer(new ImageHandler("main/Graphics/PlayersGraphics/blue.png"), first, 46, 49, 0);
        FirstPlayer.hp = 5;
        if(PowerUpsHandler.powerUps != null) {
            PowerUpsHandler.powerUps.clear();
        }
    }

    @Override
    public void update() {
        if(!paused) {
            firstPlayer.update();
            for(Enemy enemy : enemies){
                enemy.update(firstPlayer);
            }
            boolean flag = true;
            for(Enemy enemy : enemies){
                if(!enemy.dead){
                    flag = false;
                    break;
                }
            }
            if(flag){
                FirstPlayer.hp = 5;
                FirstPlayer.onePlayer = true;
                this.gameStateManager.changeState(9, null);
            }
            if (FirstPlayer.hp <= 0) {
                FirstPlayer.hp = 5;
                FirstPlayer.onePlayer = true;
                this.gameStateManager.changeState(10, null);
            }
        }
    }

    @Override
    public void input(KeyHandler keyHandler, MouseHandler mouseHandler){
        if(!paused) {
            firstPlayer.input(keyHandler, mouseHandler);
        }
        if(keyHandler.escape.down){
            this.gameStateManager.changeState(1, null);
            return;
        }
    }


    @Override
    public void render(Graphics2D graphics2D){
        mapManager.render(graphics2D);
        graphics2D.setFont(this.font.deriveFont(40f));
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(GamePanel.oldFrameCount + " FPS", 1160, 40);
        firstPlayer.render(graphics2D);
        firstPlayer.renderBullets(graphics2D);
        for(Enemy enemy : enemies){
            enemy.render(graphics2D);
        }
        if(this.paused){
            graphics2D.setFont(this.font.deriveFont(80f));
            FontMetrics metrics = graphics2D.getFontMetrics();
            String text = "PAUSED";
            int x = (1280 - metrics.stringWidth(text)) / 2;
            graphics2D.drawString(text, x, 400);
        }
    }
}
