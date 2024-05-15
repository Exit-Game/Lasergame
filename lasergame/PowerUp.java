package lasergame;

public class PowerUp
{

    //Position
    private int x;
    private int y;
    //Größe
    private int laenge = 10;
    private int breite = 10;

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

    public static boolean collisionPowerUp(PowerUp newPow, Character player) //Kollision mit PowerUp
    {
        //player berührt PowerUp
        return  (player.x + player.breite >= newPow.getX() && player.x <= newPow.getX() + newPow.getBreite()) &&
                (player.y + player.laenge >= newPow.getY() && player.y <= newPow.getLaenge() + newPow.getY());
    }
}
