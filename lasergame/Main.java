package lasergame;
import javax.swing.*;
import java.awt.*;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        //Breite, Höhe und Laseranzahl des Spiels
    	int length = 900;
    	int width = 900;
    	int laserNumber = 5;

        //Frameeinstellungen
        JFrame window = new JFrame("Lasergame1");
        window.setVisible(true);
        window.setSize(width, length);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Lasergame in dem Frame hinzufügen
        Lasergame lasergame = new Lasergame(length,width,laserNumber);
        window.add(lasergame);
        window.pack();
        lasergame.requestFocus();
    }
}
