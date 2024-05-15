package lasergame;

import java.util.List;
import java.util.Random;

public class Laser
{
    //Position
    int x;
    int y;

    //Größe
    int laenge;
    int breite;

    //Bereich
    private int laserbereichsstart;
    private int laserbereichsende;

    //Bewegung
    private int movingDirection;
    private final int speed = 10;

    public static void createLaser(Laser laser, Laser laser2, Random random, int boardBreite, int boardLaenge, Character player1, int laseranzahl) //alle Laser außer der 1.
    {
        //definieren des Laserbereichs
        laser.laserbereichsstart = laser2.laserbereichsende;
        laser.laserbereichsende = laser.laserbereichsstart + ((boardBreite-(player1.x + player1.breite + 20))/laseranzahl);

        //definieren der Laserposition
        laser.x = random.nextInt(laser2.laserbereichsstart, laser2.laserbereichsende);
        laser.y = random.nextInt(10, boardLaenge-300);

        //definieren der Laserbreite und Laserlänge
        laser.breite = random.nextInt(5, 10);
        laser.laenge = random.nextInt(50, boardLaenge-50);

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

    public static void createLaser(Laser laser, Random random, int breite, int laenge, Character player1, int laseranzahl) //der 1. Laser
    {
        laser.laserbereichsstart = player1.x + player1.breite + 20;
        laser.laserbereichsende = laser.laserbereichsstart + ((breite-(player1.x + player1.breite + 20))/laseranzahl);

        laser.x = random.nextInt(laser.laserbereichsstart, laser.laserbereichsende);
        laser.y = random.nextInt(10, laenge-300);
        laser.breite = random.nextInt(5, 10);
        laser.laenge = random.nextInt(50, laenge-100);

        laser.movingDirection = 1;
    }

    public static void movingLaser (Laser laser, int boardLaenge) //Bewegung der Laser definieren
    {
        //Laserbewegungen wechseln beim Berühren des Fensterrandes
        if (laser.y + laser.laenge >= boardLaenge)
        {
            laser.movingDirection = -1;
        }
        else if (laser.y <= 0)
        {
            laser.movingDirection = 1;
        }

        //Lasergeschwindigkeit
        laser.y += laser.movingDirection * laser.speed;
    }

    public static boolean collisionLaserBoarder(Laser laser, Character player, int laenge)
    {
        //player berührt Laser
        boolean collision = (player.x + player.breite >= laser.x && player.x <= laser.x + laser.breite) &&
                            (player.y + player.laenge >= laser.y && player.y <= laser.y + laser.laenge);

        //Player berührt linken, oberen oder unteren Rand des Fensters
        if (player.x <= 0 || player.y <= 0 || player.y + player.laenge >= laenge)
        {
            collision = true;
        }
        return collision;
    }

    public static int laserpassed (Character player1, List<Laser> lasers, int laseranzahl) //gibt die Anzahl an passierten Lasern zurück
    {
        int laspass = 0;

        //Prüfung der passierten Laser
        for(int i = 0; i < laseranzahl; i++)
        {
            if (player1.x + player1.breite > lasers.get(i).x + lasers.get(i).breite)
            {
                laspass++;
                System.out.println(laspass);
            }
        }
        return laspass;
    }

}
