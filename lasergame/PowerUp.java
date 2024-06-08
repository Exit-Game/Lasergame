package lasergame;

public class PowerUp
{

    //Position
    private int x;
    private int y;
    //Größe
    private int length = 20;
    private int width = 20;

    PowerUp (int x, int y) //Constructor
    {
        this.x = x;
        this.y = y;
    }

    //Getter/Setter zum Ändern und Abfragen von Werten dieser Klasse
    public int getX() {
        return x;
    }

    public int getWidth() {return width;}

    public int getLength() {return length;}

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
        return  (player.getX() + player.getWidth() >= pow.getX() && player.getX() <= pow.getX() + pow.getWidth()) &&
                (player.getY() + player.getLength() >= pow.getY() && player.getY() <= pow.getLength() + pow.getY());
    }
}
