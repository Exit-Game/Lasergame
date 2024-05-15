package lasergame;
import javax.swing.*;
import java.awt.*;

public class Main
{
    public static void main(String[] args) throws Exception
    {	
    	int laenge = 900;
    	int breite = 900;
    	int laseranzahl = 5;

        /*
        JFrame window2 = new JFrame("Settings");

        window2.setSize(laenge,breite);
        window2.setLocationRelativeTo(null);
        window2.setResizable(false);
        window2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Settings setting = new Settings(laenge,breite,laseranzahl);
        window2.add(setting);
        window2.add(new JLabel("Settings"));
        window2.setVisible(true);
        //window2.pack();
        */
        
        JFrame window = new JFrame("Lasergame1");
        window.setVisible(true);
        window.setSize(breite, laenge);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Lasergame lasergame = new Lasergame(laenge,breite,laseranzahl);
        window.add(lasergame);
        window.pack();
        lasergame.requestFocus();
    }
}
