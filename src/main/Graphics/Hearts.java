package main.Graphics;

import main.Enemy.Enemy;
import main.Enemy.Robot;
import main.Enemy.Solider;
import main.Enemy.Zombie;
import main.Players.FirstPlayer;
import main.Players.Player;
import main.Players.SecondPlayer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Hearts {

    private ImageHandler imageHandler;

    private Player player;
    private Enemy enemy;

    private BufferedImage image;
    private BufferedImage image2;

    public Hearts(ImageHandler imageHandler, Player player){
        this.imageHandler = imageHandler;
        this.player = player;
        this.image = imageHandler.getImage(0, 1);
        this.image2 = imageHandler.getImage(1, 0);
    }
    public Hearts(ImageHandler imageHandler, Enemy enemy){
        this.imageHandler = imageHandler;
        this.enemy = enemy;
        this.image = imageHandler.getImage(0, 1);
        this.image2 = imageHandler.getImage(1, 0);
    }


    public void render(Graphics2D graphics2D){
        if(player instanceof FirstPlayer) {
            for (int i = 0; i < player.getHp(); i++) {
                graphics2D.drawImage(this.image, i * 16 + (int) player.getPosition().x - 16, (int) player.getPosition().y - 15, 16, 16, null);
            }
        }
        else if(player instanceof SecondPlayer){
            for (int i = 0; i < player.getHp(); i++) {
                graphics2D.drawImage(this.image2, i * 16 + (int) player.getPosition().x - 16, (int) player.getPosition().y - 15, 16, 16, null);
            }
        }
        else if(enemy != null && enemy instanceof Zombie){
            for(int i = 0; i < enemy.getHp(); i++){
                graphics2D.drawImage(this.image2,i * 16 + (int) enemy.getPosition().x - 16, (int) enemy.getPosition().y - 15, 16, 16, null);
            }
        }
        else if(enemy != null && (enemy instanceof Robot || enemy instanceof Solider)){
            for(int i = 1; i < enemy.getHp() + 1; i++){
                graphics2D.drawImage(this.image2,i * 16 + (int) enemy.getPosition().x - 16, (int) enemy.getPosition().y - 15, 16, 16, null);
            }
        }
    }
}
