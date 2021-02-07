package main.States;

import main.Enemy.Robot;
import main.Enemy.Solider;
import main.Enemy.Zombie;
import main.Maps.MapManager;
import main.Players.FirstPlayer;
import main.Players.SecondPlayer;
import main.GamePanel;
import main.Graphics.ImageHandler;
import main.PowerUps.PowerUp;
import main.PowerUps.PowerUpsHandler;
import main.Util.KeyHandler;
import main.Util.MouseHandler;
import main.Util.Vector2D;

import java.awt.*;
import java.util.LinkedList;

public class PlayState extends GameState {

    GameStateManager gameStateManager;
    private SecondPlayer secondPlayer;
    private FirstPlayer firstPlayer;
    public PowerUpsHandler powerUpsHandler;
    private Font font;
    private MapManager mapManager;
    public static boolean paused;
    private long time = System.nanoTime();

    public PlayState(GameStateManager gameStateManager, String path){
        super(gameStateManager);
        mapManager = new MapManager(path);
        this.gameStateManager = gameStateManager;
        this.font = gameStateManager.getFont();
        this.font = this.font.deriveFont(80f);
        this.powerUpsHandler = new PowerUpsHandler();
        Vector2D first = new Vector2D();
        Vector2D second = new Vector2D();
        if(path.equals("main/Graphics/TileMap/deHouse.xml")){
            first.x = 64;
            first.y = 384;
            second.x = 960;
            second.y = 256;
        }
        else if(path.equals("main/Graphics/TileMap/de_storehouse.xml")){
            first.x = 64;
            first.y = 384;
            second.x = 1100;
            second.y = 320;
        }
        else if (path.equals("main/Graphics/TileMap/de_dust.xml")){
            first.x = 64;
            first.y = 384;
            second.x = 1100;
            second.y = 320;
        }
        firstPlayer = new FirstPlayer(new ImageHandler("main/Graphics/PlayersGraphics/blue.png"), first, 46, 49, 0);
        FirstPlayer.hp = 5;
        secondPlayer = new SecondPlayer(new ImageHandler("main/Graphics/PlayersGraphics/brown.png"), second, 46, 49, 3);
    }

    @Override
    public void update() {
        if(!paused) {
            firstPlayer.update(secondPlayer);
            secondPlayer.update(firstPlayer);
            powerUpsHandler.update();
            if (FirstPlayer.isDead || SecondPlayer.isDead) {
                this.gameStateManager.changeState(3, null);
            }
        }
    }

    @Override
    public void input(KeyHandler keyHandler, MouseHandler mouseHandler){
        if(!paused) {
            firstPlayer.input(keyHandler, mouseHandler);
            secondPlayer.input(keyHandler, mouseHandler);
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
        secondPlayer.render(graphics2D);
        firstPlayer.renderBullets(graphics2D);
        secondPlayer.renderBullets(graphics2D);
        powerUpsHandler.render(graphics2D);
        if(this.paused){
            graphics2D.setFont(this.font.deriveFont(80f));
            FontMetrics metrics = graphics2D.getFontMetrics();
            String text = "PAUSED";
            int x = (1280 - metrics.stringWidth(text)) / 2;
            graphics2D.drawString(text, x, 400);
        }
    }

}
