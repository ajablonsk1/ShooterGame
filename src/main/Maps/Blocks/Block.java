package main.Maps.Blocks;

import main.Util.Collisions;
import main.Util.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Block {

    int width;
    int height;

    protected BufferedImage image;
    protected Vector2D pos;

    public Collisions collision;

    public Block(BufferedImage bufferedImage, Vector2D vector2D, int w, int h){
        this.width = w;
        this.height = h;
        this.image = bufferedImage;
        this.pos = vector2D;
    }

    public abstract boolean update(Collisions collisions);

    public void render(Graphics2D graphics2D){
        graphics2D.drawImage(image, (int) pos.x, (int) pos.y, width, height, null);
    }

    public Vector2D getVector(){
        return this.pos;
    }
}
