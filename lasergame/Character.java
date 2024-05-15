package lasergame;

public class Character
{
    //Position
    int x;
    int y;

    //Größe
    final int laenge = 25;
    final int breite = 10;

    //Bewegung
    int speed;
    int bewegungX;
    int bewegungY;

    public int getBreite() {
        return breite;
    }

    public int getLaenge() {
        return laenge;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    Character(int x, int y, int speed) //Constructor zum Erstellen eines Charakters
    {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }
}
