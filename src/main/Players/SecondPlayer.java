package main.Players;

import main.Graphics.Hearts;
import main.Graphics.ImageHandler;
import main.Maps.Blocks.Block;
import main.Maps.MapObj;
import main.PowerUps.*;
import main.Util.*;
import main.Window;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class SecondPlayer extends Player{

    private ImageHandler imageHandler;
    private Vector2D position;
    BufferedImage bufferedImages[];
    BufferedImage image;
    private int sizeX;
    private int sizeY;

    public static int DMG = 1;

    public static LinkedList<Bullet> bullets = new LinkedList<>();

    private double lastBulletTime = System.nanoTime();

    private final int UP = 1;
    private final int DOWN = 2;
    private final int LEFT = 3;
    private final int RIGHT = 0;

    public static boolean isDead = false;

    private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = false;
    public boolean shoot = false;

    public int hp = 5;

    int currentDirection;

    Collisions collisions;

    private HashMap<String, Block> blockObj;

    private float dX;
    private float dY;
    private float acceleration = 1.5f;
    private float deAcceleration = 1f;
    private float maxSpeed = 4f;

    private Hearts hearts;
    String textToRender = "";

    private ImageHandler imageHandler2 = new ImageHandler("main/Graphics/PowerUp/gunThis.png", 48, 33);
    private ImageHandler imageHandler3 = new ImageHandler("main/Graphics/PowerUp/yerba.png", 32, 32);
    private ImageHandler imageHandler4 = new ImageHandler("main/Graphics/PowerUp/bulletPowerThis.png", 48, 36);

    private BufferedImage image1 = imageHandler2.getImage(0,0);
    private BufferedImage image2 = imageHandler3.getImage(0,0);
    private BufferedImage image3 = imageHandler4.getImage(0,0);

    public LinkedList<Boolean> POWERS = new LinkedList<>();
    public long[] times = new long[3];

    Font font;
    Font sizedfont;

    private long shotTime = 500000000;

    ImageHandler heartsImage = new ImageHandler("main/Graphics/hearts/lifebar_16x16.png", 16, 16);

    public SecondPlayer(ImageHandler imageHandler, Vector2D position, int sizeX, int sizeY, int currentDirection){
        this.imageHandler = imageHandler;
        this.position = position;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.currentDirection = currentDirection;
        this.bufferedImages = new BufferedImage[4];
        this.hearts = new Hearts(heartsImage, this);
        for(int i = 0; i < 4; i++){
            this.bufferedImages[i] = imageHandler.getImage(i, 0);
        }
        setImage(currentDirection);
        collisions = new Collisions(position, sizeX, sizeY);
        for(int i = 0; i < 3; i ++){
            POWERS.add(false);
        }
        InputStream font_file = Window.class.getResourceAsStream("Graphics/commando.ttf");
        font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, font_file);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sizedfont = font.deriveFont(24f);
        hp = 5;
        DMG = 1;
    }

    public void setImage(int i){
        this.image = this.imageHandler.getImage(i,0);
    };

    @Override
    public void update() {

    }

    public void update(Player player){
        animation();
        move();
        if(!collisions.CollisionTile(dX, 0) && !collisions.CollisionBetweenPlayers(player, (int) dX, 0)){
            position.x += dX;
        }
        if(!collisions.CollisionTile(0, dY) && !collisions.CollisionBetweenPlayers(player,0, (int) dY)){
            position.y += dY;
        }
        shoot();
        for(Bullet bullet: bullets){
            bullet.update(this);
        }
        for(Bullet bullet: FirstPlayer.bullets){
            if(collisions.CollisionPlayer(dX, 0, bullet, this)){
                this.hp -= SecondPlayer.DMG;
                bullet.isDead = true;
                if(this.hp <= 0){
                    this.isDead = true;
                }
            }
            else if(collisions.CollisionPlayer(0, dY, bullet, this)){
                this.hp -= SecondPlayer.DMG;
                bullet.isDead = true;
                if(this.hp <= 0){
                    this.isDead = true;
                }
            }
        }
        PowerUp powerUp = collisions.CollisionPowerUp(0, dY, this);
        if(powerUp == null){
            powerUp = collisions.CollisionPowerUp(dX, 0, this);
        }
        if(powerUp != null){
            getPowerUp(powerUp);
        }
        for(int i = 0; i < 3; i ++){
            if(times[i] != 0 && POWERS.get(i)){
                if(Math.abs(System.nanoTime() - times[i]) > 10000000000L){
                    times[i] = 0;
                    POWERS.set(i, false);
                    if(i == 1){
                        FirstPlayer.DMG -= 1;
                    }
                    else if(i == 2){
                        this.maxSpeed -= 1f;
                    }
                    else if(i == 0){
                        this.shotTime = 500000000;
                    }
                }
            }
        }
        checkForDeadBullets();
    }

    public void animation(){
        if(up){
            if(currentDirection != UP){
                setImage(UP);
                currentDirection = UP;
            }
        }
        else if(down){
            if(currentDirection != DOWN){
                setImage(DOWN);
                currentDirection = DOWN;
            }
        }
        else if(left){
            if(currentDirection != LEFT) {
                setImage(LEFT);
                currentDirection = LEFT;
            }
        }
        else if(right){
            if(currentDirection != RIGHT) {
                setImage(RIGHT);
                currentDirection = RIGHT;
            }
        }
    }

    public void render(Graphics2D g){
        g.drawImage(this.image, (int) (position.x), (int) position.y, sizeX, sizeY, null);
        hearts.render(g);
        renderActualPowerUp(g);
    }

    public void renderBullets(Graphics2D g){
        for(Bullet bullet: bullets){
            bullet.render(g);
        }
    }

    public void input(KeyHandler keyHandler, MouseHandler mouseHandler){
        if(keyHandler.up_2.down){
            this.up = true;
        }
        else{
            this.up = false;
        }
        if(keyHandler.down_2.down){
            this.down = true;
        }
        else{
            this.down = false;
        }
        if(keyHandler.left_2.down){
            this.left = true;
        }
        else{
            this.left = false;
        }
        if(keyHandler.right_2.down){
            this.right = true;
        }
        else{
            this.right = false;
        }
        if(keyHandler.shoot_2.down){
            this.shoot = true;
        }
        else{
            this.shoot = false;
        }
    }

    public void move(){
        if(position.x < 0){
            dX = 0;
            position.x = 0;
        }
        if(position.y < 0){
            dY = 0;
            position.y = 0;
        }
        if(position.x > (float) 1216){
            dX = 0;
            position.x = (float) 1216;
        }
        if(position.y >= (float) 704){
            dY = 0;
            position.y = (float) 704;
        }
        if(up){
            dY -= acceleration;
            if(dY < -maxSpeed){
                dY = -maxSpeed;
            }
        }
        else{
            if(dY < 0){
                dY += deAcceleration;
                if(dY > 0){
                    dY = 0;
                }
            }
        }
        if(down){
            dY += acceleration;
            if(dY > maxSpeed){
                dY = maxSpeed;
            }
        }
        else{
            if(dY > 0){
                dY -= deAcceleration;
                if(dY > 0){
                    dY = 0;
                }
            }
        }
        if(right){
            dX += acceleration;
            if(dX > maxSpeed){
                dX = maxSpeed;
            }
        }
        else{
            if(dX > 0){
                dX -= deAcceleration;
                if(dY < 0){
                    dX = 0;
                }
            }
        }
        if(left){
            dX -= acceleration;
            if(dX < -maxSpeed){
                dX = -maxSpeed;
            }
        }
        else{
            if(dX < 0){
                dX += deAcceleration;
                if(dX > 0){
                    dX = 0;
                }
            }
        }
    }

    public void checkForDeadBullets(){
        bullets.removeIf(bullet -> bullet.isDead);
    }

    public void shoot(){
        if(shoot){
            Vector2D vector2D = new Vector2D();
            if(this.currentDirection == UP){
                vector2D.x = this.position.x + ((float) sizeX / 2);
                vector2D.y = this.position.y - 5;
            }
            else if(currentDirection == DOWN){
                vector2D.x = this.position.x + ((float) sizeX / 2);
                vector2D.y = this.position.y + sizeY + 5;
            }
            else if(currentDirection == LEFT) {
                vector2D.x = this.position.x - 5;
                vector2D.y = this.position.y + ((float)sizeY / 2);
            }
            else if(currentDirection == RIGHT) {
                vector2D.x = this.position.x + sizeX + 5;
                vector2D.y = this.position.y + ((float)sizeY / 2);
            }
            Bullet bullet = new Bullet(vector2D, new ImageHandler("main/Graphics/Bullet/bullet.png", 4, 4), this);
            if(Math.abs(bullet.bulletSpawnTime - lastBulletTime) > shotTime){
                bullets.add(bullet);
                lastBulletTime = bullet.bulletSpawnTime;
            }
        }
    }

    public int getDirection() {
        return currentDirection;
    }

    @Override
    public Vector2D getPosition() {
        return this.position;
    }

    @Override
    public int getSizeX() {
        return this.sizeX;
    }

    @Override
    public int getSizeY() {
        return this.sizeY;
    }

    @Override
    public int getHp() {
        return this.hp;
    }

    public void getPowerUp(PowerUp powerUp){
        if(powerUp instanceof HeartPowerUp){
            if(hp < 5){
                hp += 1;
            }
        }
        else if(powerUp instanceof DMGPowerUp){
            if(FirstPlayer.DMG == 1) {
                FirstPlayer.DMG += 1;
            }
            POWERS.set(1, true);
            times[1] = System.nanoTime();
        }
        else if(powerUp instanceof SpeedPowerUp){
            if(maxSpeed != 5f) {
                maxSpeed += 1f;
            }
            POWERS.set(2, true);
            times[2] = System.nanoTime();
        }
        else if(powerUp instanceof BulletPowerUp){
            if(shotTime != 250000000){
                shotTime = 250000000;
            }
            POWERS.set(0,true);
            times[0] = System.nanoTime();
        }
    }

    public void renderActualPowerUp(Graphics2D graphics2D){
        int j = 0;
        for (int i = 0; i < 3; i++) {
            if (POWERS.get(i)) {
                if (i == 1) {
                    graphics2D.drawImage(this.image1, j * 26 + (int) position.x - 18, (int) position.y - 40, 24, 24, null);
                    j++;
                }
                if (i == 2) {
                    graphics2D.drawImage(this.image2, j * 26 + (int) position.x - 18, (int) position.y - 40, 24, 24, null);
                    j++;
                }
                if (i == 0) {
                    graphics2D.drawImage(this.image3, j * 26 + (int) position.x - 18, (int) position.y - 40, 24, 24, null);
                    j++;
                }
            }
        }
    }
}
