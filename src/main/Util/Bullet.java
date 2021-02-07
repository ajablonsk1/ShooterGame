package main.Util;

import main.Enemy.Solider;
import main.Graphics.ImageHandler;
import main.Players.Player;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bullet {

    private Vector2D position;
    private final float sizeX = 4;
    private final float sizeY = 4;
    private final float dx = 15f;
    private final float dy = 15f;

    private int direction;

    public int time = 1000;

    public double bulletSpawnTime = System.nanoTime();

    ImageHandler imageHandler;

    private Player player;

    private Collisions collisions;

    private BufferedImage image;

    public boolean isDead = false;

    private Solider solider;

    public Bullet(Vector2D position, ImageHandler imageHandler, Player player){
        this.position = position;
        this.imageHandler = imageHandler;
        this.player = player;
        this.collisions = new Collisions(this.position, 4, 4);
        this.image = imageHandler.getImage(0, 0);
        this.direction = player.getDirection();
    }

    public Bullet(Vector2D position, ImageHandler imageHandler, Solider solider){
        this.position = position;
        this.imageHandler = imageHandler;
        this.solider = solider;
        this.collisions = new Collisions(this.position, 4, 4);
        this.image = imageHandler.getImage(0, 0);
        this.direction = solider.currentDirection;
    }

    public void render(Graphics2D graphics2D){
        graphics2D.drawImage(this.image, (int) this.position.x, (int) this.position.y, (int) this.sizeX, (int) this.sizeY, null);
    }

    public void update(Player player){
        animation();
    }

    public void animation(){
        if(player != null) {
            if (direction == 1) {
                if (!collisions.CollisionTile(0, -dy) && this.position.y > 0) {
                    this.position.y -= dy;
                } else {
                    isDead = true;
                }
            } else if (direction == 2) {
                if (!collisions.CollisionTile(0, dy) && this.position.y < 750) {
                    this.position.y += dy;
                } else {
                    isDead = true;
                }
            } else if (direction == 3) {
                if (!collisions.CollisionTile(-dx, 0) && this.position.x > 0) {
                    this.position.x -= dx;
                } else {
                    isDead = true;
                }
            } else if (direction == 0) {
                if (!collisions.CollisionTile(dx, 0) && this.position.x < 1280) {
                    this.position.x += dx;
                } else {
                    isDead = true;
                }
            }
        }
        else if(solider != null){
            if (direction == 3) {
                if (!collisions.CollisionTile(0, -dy) && this.position.y > 0) {
                    this.position.y -= dy;
                } else {
                    isDead = true;
                }
            } else if (direction == 1) {
                if (!collisions.CollisionTile(0, dy) && this.position.y < 750) {
                    this.position.y += dy;
                } else {
                    isDead = true;
                }
            } else if (direction == 2) {
                if (!collisions.CollisionTile(-dx, 0) && this.position.x > 0) {
                    this.position.x -= dx;
                } else {
                    isDead = true;
                }
            } else if (direction == 0) {
                if (!collisions.CollisionTile(dx, 0) && this.position.x < 1280) {
                    this.position.x += dx;
                } else {
                    isDead = true;
                }
            }
        }
    }

    public Vector2D getPosition(){
        return this.position;
    }
}
