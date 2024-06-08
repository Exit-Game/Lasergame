package lasergame;

import java.util.List;
import java.util.Random;

public class Laser
{
    //Position
    private int x;
    private int y;

    //Größe
    private int length;
    private int width;

    //Bereich
    private int laserAreaStart;
    private int laserAreaEnd;

    //Bewegung
    private int movingDirection;
    private final int speed = 10;

    //Getter/Setter zum Ändern und Abfragen von Werten dieser Klasse
    public int getWidth()
    {
        return width;
    }

    public int getLength()
    {
        return length;
    }

    public int getY()
    {
        return y;
    }

    public int getX()
    {
        return x;
    }

    public static void createLaser(Laser laser, Laser laser2, Random random, int boardWidth, int boardLength, Character player1, int laserNumber) //alle Laser außer der 1.
    {
        //definieren der Laserbreite und Laserlänge
        laser.width = random.nextInt(5, 10);
        laser.length = random.nextInt(50, boardLength-100);

        //definieren des Laserbereichs
        laser.laserAreaStart = laser2.laserAreaEnd;
        laser.laserAreaEnd = laser.laserAreaStart + ((boardWidth-(player1.getX() + player1.getWidth() + 20))/laserNumber);

        //definieren der Laserposition
        laser.x = random.nextInt(laser.laserAreaStart, laser.laserAreaEnd -laser.width);
        laser.y = random.nextInt(10, boardLength-300);

        //festsetzen der Bewegungsrichtung
        if(laser2.movingDirection == 1)
        {
            laser.movingDirection = -1;
        }
        else
        {
            laser.movingDirection = 1;
        }
    }

    public static void createLaser(Laser laser, Random random, int boardWith, int boardLength, Character player1, int laserNumber) //der 1. Laser
    {
        laser.width = random.nextInt(5, 10);
        laser.length = random.nextInt(50, boardLength-100);
        laser.laserAreaStart = player1.getX() + player1.getWidth() + 20;
        laser.laserAreaEnd = laser.laserAreaStart + ((boardWith-(player1.getX() + player1.getWidth() + 20))/laserNumber);

        laser.x = random.nextInt(laser.laserAreaStart, laser.laserAreaEnd);
        laser.y = random.nextInt(10, boardLength-300);

        laser.movingDirection = 1;
    }

    public static void movingLaser (Laser laser, int boardLength) //Bewegung der Laser definieren
    {
        //Laserbewegungen wechseln beim Berühren des Fensterrandes
        if (laser.y + laser.length >= boardLength+200)
        {
            laser.movingDirection = -1;
        }
        else if (laser.y <= 0-200)
        {
            laser.movingDirection = 1;
        }

        //Lasergeschwindigkeit
        laser.y += laser.movingDirection * laser.speed;
    }

    public static boolean collisionLaserBoarder(Laser laser, Character player, int length)
    {
        boolean collision = false;

        //player berührt Laser
        collision = (player.getX() + player.getWidth() >= laser.x && player.getX() <= laser.x + laser.width) &&
                            (player.getY() + player.getLength() >= laser.y && player.getY() <= laser.y + laser.length);

        //Player berührt linken, oberen oder unteren Rand des Fensters
        if (player.getX() <= 0 || player.getY() <= 0 || player.getY() + player.getLength() >= length)
        {
            collision = true;
        }
        System.out.println(collision);
        return collision;
    }

    public static String laserpassed (Character player1, List<Laser> lasers, int laserNumber, int laspass, int level) //gibt die Anzahl an passierten Lasern zurück
    {
        //Prüfung der passierten Laser
        for(int i = 0; i < laserNumber; i++)
        {
            if (player1.getX() + player1.getWidth() > lasers.get(i).x + lasers.get(i).width)
            {
                laspass++;
            }
        }

        //Hinzufügen der Laseranzahl der geschafften Level
        if(level>1)
        {
            int laserzwi = 0;
            for(int i = 1; i < level; i++)
            {
                laserzwi += i*5;
            }

            return String.valueOf(laspass+laserzwi);
        }

        return String.valueOf(laspass);
    }

}
