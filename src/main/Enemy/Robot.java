package main.Enemy;

import main.Graphics.Hearts;
import main.Graphics.ImageHandler;
import main.Players.FirstPlayer;
import main.Players.Player;
import main.States.PlayForOne;
import main.Util.Bullet;
import main.Util.Collisions;
import main.Util.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Robot extends Enemy{
    private int width1;
    private int width2;
    private int height1;
    private int height2;
    private Vector2D position;
    private int currentDirection;
    private LinkedList<BufferedImage> bufferedImages = new LinkedList<>();
    private Hearts hearts;
    private ImageHandler heartsHandler = new ImageHandler("main/Graphics/hearts/lifebar_16x16.png", 16, 16);
    public int hp = 3;
    private float maxSpeed = 3f;
    private float dX = 0;
    private float dY = 0;
    private float deAcceleration = 1f;
    private float acceleration = 1f;
    private boolean aggro = false;
    private Collisions collisions;
    BufferedImage image;

    ImageHandler imageHandler1 = new ImageHandler("main/Graphics/Enemy/robot1_hold_right.png", 35, 43);
    ImageHandler imageHandler2 = new ImageHandler("main/Graphics/Enemy/robot1_hold_down.png", 43, 35);
    ImageHandler imageHandler3 = new ImageHandler("main/Graphics/Enemy/robot1_holdleft.png", 35, 43);
    ImageHandler imageHandler4 = new ImageHandler("main/Graphics/Enemy/robot1_hold.png", 43, 35);

    private float lastHitTime = System.nanoTime();

    public Robot(Vector2D position, int currentDirection){
        this.width1 = 35;
        this.width2 = 43;
        this.height1 = 43;
        this.height2 = 35;
        this.position = position;
        this.currentDirection = currentDirection;
        bufferedImages.add(imageHandler1.getImage(0,0));
        bufferedImages.add(imageHandler2.getImage(0,0));
        bufferedImages.add(imageHandler3.getImage(0,0));
        bufferedImages.add(imageHandler4.getImage(0,0));
        hearts = new Hearts(heartsHandler, this);
        collisions = new Collisions(this.position, 43, 35);
        this.image = bufferedImages.get(currentDirection);
    }

    public void update(Player pLayer){
        boolean flag = false;
        if(!super.dead) {
            aggro(pLayer);
            move(pLayer);
            long time = System.nanoTime();
            if(flag == false) {
                if (!collisions.CollisionTile(dX, 0) && !collisions.CollisionEnemy(pLayer, (int) dX, 0)) {
                    position.x += dX;
                }
                if (!collisions.CollisionTile(0, dY) && !collisions.CollisionEnemy(pLayer, 0, (int) dY)) {
                    position.y += dY;
                }
            }
            if (collisions.CollisionEnemy(pLayer, (int) dX, 0)) {
                if (Math.abs(time - lastHitTime) > 1000000000L) {
                    FirstPlayer.hp -= 1;
                    lastHitTime = time;
                }
            } else if (collisions.CollisionEnemy(pLayer, 0, (int) dY)) {
                if (Math.abs(time - lastHitTime) > 1000000000L) {
                    FirstPlayer.hp -= 1;
                    lastHitTime = time;
                }
            }
            for (Bullet bullet : FirstPlayer.bullets) {
                if (collisions.CollisionEnemyShot((int) dX, 0, bullet, this)) {
                    this.hp -= 1;
                    bullet.isDead = true;
                    if (this.hp <= 0) {
                        isDead();
                    }
                } else if (collisions.CollisionEnemyShot(0, (int) dY, bullet, this)) {
                    this.hp -= 1;
                    bullet.isDead = true;
                    if (this.hp <= 0) {
                        isDead();
                    }
                }
            }
        }
    }

    public void render(Graphics2D graphics2D){
        if(!super.dead) {
            graphics2D.drawImage(this.image, (int) position.x, (int) position.y, width1, height1, null);
            hearts.render(graphics2D);
        }
    }

    public void aggro(Player player){
        if(collisions.CollisionEnemyVision(player) || hp <= 2){
            this.aggro = true;
        }
        else if(!collisions.CollisionEnemyVision(player)){
            this.aggro = false;
        }
    }

    public Vector2D getPosition(){
        return this.position;
    }

    public int getHp(){
        return this.hp;
    }

    public void move(Player player) {
        if (position.x < 0) {
            dX = 0;
            position.x = 0;
        }
        if (position.y < 0) {
            dY = 0;
            position.y = 0;
        }
        if (position.x > (float) 1216) {
            dX = 0;
            position.x = (float) 1216;
        }
        if (position.y >= (float) 704) {
            dY = 0;
            position.y = (float) 704;
        }
        if(aggro) {
            if (player.getPosition().y < this.position.y) {
                dY -= acceleration;
                this.image = bufferedImages.get(3);
                if (dY < -maxSpeed) {
                    dY = -maxSpeed;
                }
            } else {
                if (dY < 0) {
                    dY += deAcceleration;
                    if (dY > 0) {
                        dY = 0;
                    }
                }
            }
            if (player.getPosition().y > this.position.y) {
                dY += acceleration;
                this.image = bufferedImages.get(1);
                if (dY > maxSpeed) {
                    dY = maxSpeed;
                }
            } else {
                if (dY > 0) {
                    dY -= deAcceleration;
                    if (dY > 0) {
                        dY = 0;
                    }
                }
            }
            if (player.getPosition().x > this.position.x) {
                dX += acceleration;
                this.image = bufferedImages.get(0);
                if (dX > maxSpeed) {
                    dX = maxSpeed;
                }
            } else {
                if (dX > 0) {
                    dX -= deAcceleration;
                    if (dY < 0) {
                        dX = 0;
                    }
                }
            }
            if (player.getPosition().x < this.position.x) {
                dX -= acceleration;
                this.image = bufferedImages.get(2);
                if (dX < -maxSpeed) {
                    dX = -maxSpeed;
                }
            } else {
                if (dX < 0) {
                    dX += deAcceleration;
                    if (dX > 0) {
                        dX = 0;
                    }
                }
            }
            if(dX > 0){
                currentDirection = 0;
            }
            if(dX < 0){
                currentDirection = 2;
            }
            if(dY > 0){
                currentDirection = 1;
            }
            if(dY < 0){
                currentDirection = 3;
            }
        }
        else{
            dX = 0;
            dY = 0;
        }
    }

    public void isDead(){
        super.dead = true;
    }

    @Override
    public Collisions getCollision() {
        return this.collisions;
    }
}
