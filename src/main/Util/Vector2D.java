package main.Util;

public class Vector2D {

    public float x;
    public float y;

    public Vector2D(){
        this.x = 0;
        this.y = 0;
    }

    public Vector2D(float x, float y){
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D vector2D){
        new Vector2D(vector2D.x, vector2D.y);
    }

    public void addX(float x){
        this.x += x;
    }

    public void addY(float y){
        this.y += y;
    }

    public void setX(float x){
        this.x = x;
    }

    public void setY(float y){
        this.y = y;
    }

    public void setVector(Vector2D vector2D){
        this.x = vector2D.x;
        this.y = vector2D.y;
    }
    public void setVector(float x, float y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString(){
        return x + " " + y;
    }
}
