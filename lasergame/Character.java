package lasergame;

public class Character
{
    //Position
    private int x;
    private int y;

    //Größe
    private final int length = 25;
    private final int width = 10;

    //Bewegung
    private int speed;
    private int movementX;
    private int movementY;

    //PowerUp
    private int shield;

    //Getter/Setter zum Ändern und Abfragen von Werten dieser Klasse
    public void setMovementY(int movementY)
    {
        this.movementY = movementY;
    }

    public void setMovementX(int movementX)
    {
        this.movementX = movementX;
    }

    public void setShield(int shield)
    {
        this.shield = shield;
    }

    public int getShield()
    {
        return shield;
    }

    public int getLength()
    {
        return length;
    }

    public int getWidth()
    {
        return width;
    }

    public int getSpeed()
    {
        return speed;
    }

    public int getMovementX()
    {
        return movementX;
    }

    public int getMovementY()
    {
        return movementY;
    }

    public void setSpeed(int speed)
    {
        this.speed = speed;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    Character(int x, int y, int speed) //Constructor zum Erstellen eines Charakters
    {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }
}
