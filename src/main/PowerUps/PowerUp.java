package main.PowerUps;

import main.Graphics.ImageHandler;
import main.Util.Collisions;
import main.Util.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

public abstract class PowerUp {


    ImageHandler imageHandler;

    Vector2D position;


    public PowerUp(ImageHandler imageHandler, Vector2D position){
        this.position = position;
        this.imageHandler = imageHandler;
    }

    public abstract void spawn(Graphics2D graphics2D);

    public abstract Vector2D getPosition();
}
