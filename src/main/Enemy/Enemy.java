package main.Enemy;

import main.Players.Player;
import main.Util.Collisions;
import main.Util.Vector2D;

import java.awt.*;

public abstract class Enemy {

    public boolean dead = false;

    public abstract void update(Player player);

    public abstract void render(Graphics2D graphics2D);

    public abstract Vector2D getPosition();

    public abstract void move(Player player);

    public abstract void aggro(Player player);

    public abstract int getHp();

    public abstract void isDead();

    public abstract Collisions getCollision();
}
