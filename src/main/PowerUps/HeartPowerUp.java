package main.PowerUps;

import main.Graphics.Hearts;
import main.Graphics.ImageHandler;
import main.Util.Collisions;
import main.Util.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class HeartPowerUp extends PowerUp{

    BufferedImage bufferedImage;

    Collisions collisions;

    public HeartPowerUp(ImageHandler imageHandler, Vector2D position) {
        super(imageHandler, position);
        super.imageHandler = imageHandler;
        this.bufferedImage = imageHandler.getImage(0, 0);
        this.collisions = new Collisions(this.position, 32, 32);
    }

    public void spawn(Graphics2D graphics2D){
        int x;
        int y;
        while(collisions.CollisionTile(0, 0)){
            Random r = new Random();
            x = r.nextInt(20);
            System.out.println(x);
            y = r.nextInt(12);
            System.out.println(y);
            this.position.x = x*64;
            this.position.y = y*64;
            this.collisions.setPosition(this.position);
        }
        graphics2D.drawImage(bufferedImage, (int) this.position.x, (int) this.position.y, 48, 48, null);
    }

    @Override
    public Vector2D getPosition() {
        return this.position;
    }
}
