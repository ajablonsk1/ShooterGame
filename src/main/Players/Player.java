package main.Players;

import main.Util.KeyHandler;
import main.Util.MouseHandler;
import main.Util.Vector2D;

import java.awt.*;

public abstract class Player {

    public abstract void update();

    public abstract void update(Player player);

    public abstract void render(Graphics2D graphics2D);

    public abstract void animation();

    public abstract void input(KeyHandler keyHandler, MouseHandler mouseHandler);

    public abstract void move();

    public abstract void shoot();

    public abstract int getDirection();

    public abstract Vector2D getPosition();

    public abstract int getSizeX();

    public abstract int getSizeY();

    public abstract int getHp();
}
