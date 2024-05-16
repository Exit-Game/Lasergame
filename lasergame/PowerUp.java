package lasergame;

import java.util.ArrayList;

public class PowerUp
{

    //Position
    private int x;
    private int y;
    //Größe
    private int laenge = 20;
    private int breite = 20;

    PowerUp (int x, int y) //Constructor
    {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getBreite() {return breite;}

    public int getLaenge() {return laenge;}

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static boolean collisionPowerUp(PowerUp pow, Character player) //Kollision mit PowerUp
    {
        //player berührt PowerUp
        return  (player.x + player.breite >= pow.getX() && player.x <= pow.getX() + pow.getBreite()) &&
                (player.y + player.laenge >= pow.getY() && player.y <= pow.getLaenge() + pow.getY());
    }
}
