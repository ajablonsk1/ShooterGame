package main.Enemy;

import main.Graphics.Hearts;
import main.Graphics.ImageHandler;
import main.Players.FirstPlayer;
import main.Players.Player;
import main.Players.SecondPlayer;
import main.Util.Bullet;
import main.Util.Collisions;
import main.Util.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.SortedSet;

public class Solider extends Enemy {

    ImageHandler imageHandler1 = new ImageHandler("main/Graphics/Enemy/soldier1_machine_right.png", 52, 43);
    ImageHandler imageHandler2 = new ImageHandler("main/Graphics/Enemy/soldier1_machine_down.png", 43, 52);
    ImageHandler imageHandler3 = new ImageHandler("main/Graphics/Enemy/soldier1_machine_left.png", 52, 43);
    ImageHandler imageHandler4 = new ImageHandler("main/Graphics/Enemy/soldier1_machine.png", 43, 52);

    private LinkedList<BufferedImage> bufferedImages = new LinkedList<>();

    public int currentDirection = 2;

    private int hp = 3;

    Hearts hearts;

    private Collisions collisions;

    private Vector2D position;

    private long lastChangeDircetionTime = System.nanoTime();
    private long lastBulletTime = System.nanoTime();

    private ImageHandler heartsHandler = new ImageHandler("main/Graphics/hearts/lifebar_16x16.png", 16, 16);

    private boolean aggro;

    public Solider(Vector2D vector2D) {
        this.position = vector2D;
        collisions = new Collisions(position, 52, 43);
        bufferedImages.add(imageHandler1.getImage(0, 0));
        bufferedImages.add(imageHandler2.getImage(0, 0));
        bufferedImages.add(imageHandler3.getImage(0, 0));
        bufferedImages.add(imageHandler4.getImage(0, 0));
        hearts = new Hearts(heartsHandler, this);
    }

    @Override
    public void update(Player player) {
        if(!super.dead) {
            long time = System.nanoTime();
            if (Math.abs(time - lastChangeDircetionTime) > 1000000000) {
                currentDirection += 1;
                if (currentDirection == 4) {
                    currentDirection = 0;
                }
                lastChangeDircetionTime = time;
            }
            aggro(player);
            System.out.println(aggro);
            shoot();
            for (Bullet bullet : FirstPlayer.bullets) {
                if (collisions.CollisionEnemyShot(0, 0, bullet, this)) {
                    this.hp -= 1;
                    bullet.isDead = true;
                    if (this.hp <= 0) {
                        isDead();
                    }
                }
            }
        }
    }

        @Override
        public void render (Graphics2D graphics2D){
            if (!super.dead) {
                if (currentDirection == 0 || currentDirection == 2) {
                    graphics2D.drawImage(bufferedImages.get(currentDirection), (int) position.x, (int) position.y, 52, 43, null);
                } else if (currentDirection == 1 || currentDirection == 3) {
                    graphics2D.drawImage(bufferedImages.get(currentDirection), (int) position.x, (int) position.y, 43, 52, null);
                }
                hearts.render(graphics2D);
            }
        }


        @Override
        public Vector2D getPosition () {
            return this.position;
        }

        @Override
        public void move (Player player){
            //does not move
        }

        @Override
        public void aggro (Player player){
            if (collisions.CollisionSolider(player, this)) {
                aggro = true;
            } else {
                aggro = false;
            }
        }

        @Override
        public int getHp () {
            return this.hp;
        }

        @Override
        public void isDead () {
            super.dead = true;
        }

        public void shoot () {
            if (aggro) {
                Vector2D vector2D = new Vector2D();
                if (this.currentDirection == 3) {
                    vector2D.x = this.position.x + ((float) 43 / 2);
                    vector2D.y = this.position.y - 5;
                } else if (currentDirection == 1) {
                    vector2D.x = this.position.x + ((float) 43 / 2);
                    vector2D.y = this.position.y + 52 + 5;
                } else if (currentDirection == 2) {
                    vector2D.x = this.position.x - 5;
                    vector2D.y = this.position.y + ((float) 52 / 2);
                } else if (currentDirection == 0) {
                    vector2D.x = this.position.x + 52 + 5;
                    vector2D.y = this.position.y + ((float) 43 / 2);
                }
                Bullet bullet = new Bullet(vector2D, new ImageHandler("main/Graphics/Bullet/bullet.png", 4, 4), this);
                if (Math.abs(bullet.bulletSpawnTime - lastBulletTime) > 300000000) {
                    FirstPlayer.bullets.add(bullet);
                    lastBulletTime = (long) bullet.bulletSpawnTime;
                }
            }
        }
    @Override
    public Collisions getCollision() {
        return this.collisions;
    }
    }


