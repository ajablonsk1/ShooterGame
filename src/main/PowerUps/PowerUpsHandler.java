package main.PowerUps;

import main.Graphics.ImageHandler;
import main.Util.Vector2D;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class PowerUpsHandler {

    public static LinkedList<PowerUp> powerUps;
    public static LinkedList<Long> spawnTimes;
    private long timeBetweenPowerUps = 3000000000L;

    private int HeartPowerUp = 1;
    private int DMGPowerUp = 2;
    private int SpeedPowerUp = 3;
    private int BulletPowerUp = 4;

    private ImageHandler imageHandler1 = new ImageHandler("main/Graphics/PowerUp/heartUp.png", 32, 32);
    private ImageHandler imageHandler2 = new ImageHandler("main/Graphics/PowerUp/gunThis.png", 48, 33);
    private ImageHandler imageHandler3 = new ImageHandler("main/Graphics/PowerUp/yerba.png", 32, 32);
    private ImageHandler imageHandler4 = new ImageHandler("main/Graphics/PowerUp/bulletPowerThis.png", 48, 36);

    private long lastSpawnTime = System.nanoTime();
    private long lastRemoveTime = System.nanoTime();

    public PowerUpsHandler(){
        powerUps =  new LinkedList<>();
        spawnTimes = new LinkedList<>();
    }

    public void render(Graphics2D graphics2D){
        for(PowerUp powerUp: powerUps){
            powerUp.spawn(graphics2D);
        }
    }

    public void update(){
        long time  = System.nanoTime();
        if(powerUps.size() < 4){
            if(Math.abs(time - lastSpawnTime) > timeBetweenPowerUps){
                Random r = new Random();
                int random  = r.nextInt(4) + 1;
                int x = ThreadLocalRandom.current().nextInt(0, 20);
                int y = ThreadLocalRandom.current().nextInt(0, 12);
                if(random == HeartPowerUp){
                    powerUps.add(new HeartPowerUp(imageHandler1, new Vector2D(x*64, y*64)));
                    spawnTimes.add(time);
                }
                if(random == DMGPowerUp){
                    powerUps.add(new DMGPowerUp(imageHandler2, new Vector2D(x*64, y*64)));
                    spawnTimes.add(time);
                }
                if(random == SpeedPowerUp){
                    powerUps.add(new SpeedPowerUp(imageHandler3, new Vector2D(x*64, y*64)));
                    spawnTimes.add(time);
                }
                if(random == BulletPowerUp){
                    powerUps.add(new BulletPowerUp(imageHandler4, new Vector2D(x*64, y*64)));
                    spawnTimes.add(time);
                }
                lastSpawnTime = time;
            }
        }
        if(powerUps.size() > 0) {
            if (Math.abs(time - spawnTimes.get(0)) > 14000000000L) {
                powerUps.remove(0);
                spawnTimes.remove(0);
            }
        }
    }

}
