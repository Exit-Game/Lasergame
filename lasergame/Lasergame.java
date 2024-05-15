package lasergame;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList; //Laser erstellen
import java.util.List;
import java.util.Random; // Laser placen
import javax.swing.*;
import javax.xml.transform.stream.StreamSource;

public class Lasergame extends JPanel implements ActionListener, KeyListener
{
	//gameBoard
	int breite;
	int laenge;
	
	//gamelogic
    static Timer gameLoop;
	boolean gameOver = false;
	boolean win = false;
	int level = 1;
	
	//Laser
	Random random;
	int laseranzahl;
	List<Laser> lasers = new ArrayList<>();
	int laspass;
	
	//player
	Character player1;
	
	//PowerUp
	PowerUp newPow;

	Lasergame (int boardHeight, int boardLength, int laseranzahl2)
	{
		//gameSettings
		this.laseranzahl = laseranzahl2;
		this.breite = boardHeight;
		this.laenge = boardLength;
		setPreferredSize(new Dimension(this.breite, this.laenge));
		setBackground(Color.GRAY);
		addKeyListener(this);
		setFocusable(true);

		//Initialisierung Objekte
		random = new Random();
		player1 = new Character(5, 5, 10);
		newPow = new PowerUp(random.nextInt(15,breite-15),random.nextInt(15,laenge-15));

		//Laser erschaffen
		for (int i = 0; i < laseranzahl; i++)
		{
			lasers.add(new Laser());
			if (i > 1)
			{
				//alle anderen Laser
				Laser.createLaser(lasers.get(i), lasers.get(i-1), random, breite, laenge, player1, laseranzahl);
			}
			else
			{
				//1. Laser
				Laser.createLaser(lasers.get(i), random, breite, laenge, player1, laseranzahl);
			}
		}

		//GameLoop starten
		gameLoop = new Timer(50, this);
		gameLoop.start();
	}

	
	public void newLevel()
	{
		//Laser löschen
		lasers.clear();

		//Erhöhung Laseranzahl
		laseranzahl = laseranzahl + 5;

		//Levelerhöhung
		level++;

		//Player vom rechten zum linken Rand und Player stoppen
		player1.x = 1;
		player1.bewegungY = 0;
		player1.bewegungX = 0;

		//Erstellung Laser mit erhöhter Laseranzahl
		for (int i = 0; i < laseranzahl; i++)
		{
			lasers.add(new Laser());
			if (i > 1)
			{
				//alle anderen Laser
				Laser.createLaser(lasers.get(i), lasers.get(i-1), random, breite, laenge, player1, laseranzahl);
			}
			else
			{
				//1. Laser
				Laser.createLaser(lasers.get(i), random, breite, laenge, player1, laseranzahl);
			}

		}
	}
	
	public void draw(Graphics g) //Funktion für das Zeichnen
	{
		//player1
		g.setColor(Color.BLACK);
		g.fill3DRect(player1.x, player1.y, player1.breite, player1.laenge, true);
		
		//laser
		for (int i = 0; i < laseranzahl; i++)
		{
			g.setColor(Color.GREEN);
			g.fill3DRect(lasers.get(i).x, lasers.get(i).y, lasers.get(i).breite, lasers.get(i).laenge, true);
		}

		//PowerUp
		g.setColor(Color.CYAN);
		g.fill3DRect(newPow.getX(), newPow.getY(), newPow.getBreite(), newPow.getLaenge(), true);
		
		//GameOver
		if (gameOver)
		{
			g.setColor(Color.CYAN);
			g.setFont(new Font("Arial", Font.PLAIN, 50));
			g.drawString("GameOver", 150,300);
			setBackground(Color.RED);
		}
		else if (win) //win
		{
			g.setColor(Color.BLUE);
			g.setFont(new Font("Arial", Font.PLAIN, 50));
			g.drawString("Win", 150,300);
			setBackground(Color.GREEN);
		}
		else
		{
			setBackground(Color.GRAY);
		}
		
		//Laser passiert
		g.setColor(Color.MAGENTA);
		g.setFont(new Font("Arial", Font.PLAIN, 16));
		g.drawString("Passierte Laser: " + Integer.toString(laspass), 10, 20);

		//Level
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN, 16));
		g.drawString("Level: " + level,10,40);

	}

	public void paintComponent(Graphics g) //ruft die Drawfunktion auf
	{
		super.paintComponent(g);
		draw(g);
	}

	
	public void move()
	{
		//Playermovement
		player1.x += player1.bewegungX * player1.speed;
		player1.y += player1.bewegungY * player1.speed;

		System.out.println(gameOver);
		//Collisionscheck
		for (int i = 0; i < laseranzahl; i++)
		{
			if(Laser.collisionLaserBoarder(lasers.get(i), player1, laenge))
			{
				gameOver = true;
				gameLoop.stop();
			}
		}

		//Abfrage, wieviele Laser passiert wurden
		laspass = Laser.laserpassed(player1, lasers, laseranzahl);

		//Win
		if(level == 3 && player1.x >= breite)
		{
			win = true;
			player1 = null;
			lasers.clear();
			PostRequest.request();
			gameLoop.stop();
		}

		//PowerUpCollision
		if(PowerUp.collisionPowerUp(newPow,player1))
		{
			//Erstellen des neuen Powerups
			newPow.setX(random.nextInt(15,breite-15));
			newPow.setY(random.nextInt(15,laenge-15));

			//Geschwindigkeitserhähung Player
			player1.speed = player1.speed*2;
		}

		//neues Level erstellen
		if(player1.x >= breite)
		{
			newLevel();
		}

		//Bewegung der Laser
		for(int i = 0; i < laseranzahl; i++)
		{
			Laser.movingLaser(lasers.get(i), laenge);
		}
	}
	
	public void actionPerformed(ActionEvent e) //wird aktiviert, sobald eine Taste gedrückt wird
	{
		//Aufrufen der Move-Methode
		move();

		//Aufrufen der Draw-Methode, um alles die ganze Zeit neu zu zeichnen
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) //Sobald eine Taste gedrückt wird, wird der entsprechende Code ausgeführt
	{
		if (e.getKeyCode() == KeyEvent.VK_UP) //Taste nach oben, Player bewegt sich nach oben
		{
			player1.bewegungX = 0;
			player1.bewegungY = -1;
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN) //Taste nach unten, Player bewegt sich nach unten
		{
			player1.bewegungX = 0;
			player1.bewegungY = 1;
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT) //Taste nach rechts, Player bewegt sich nach rechts
		{
			player1.bewegungX = 1;
			player1.bewegungY = 0;
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT) //Taste nach links, Player bewegt sich nach links
		{
			player1.bewegungX = -1;
			player1.bewegungY = 0;
		}

		if (e.getKeyCode() == KeyEvent.VK_ENTER) //Entertaste, Möglichkeit das Spiel zu beenden oder neu zu starten
		{
			//stoppen des GameLoops
			gameLoop.stop();

			//Erstellung Fenster
			JFrame frame = new JFrame();

			//Abfrage im Fenster
			int result = JOptionPane.showConfirmDialog(frame, "Willst du das Spiel neustarten? Sonst wird das Spiel beendet");

			//Option ja oder Option nein
			if (result == JOptionPane.YES_OPTION)
			{
				//Spiel wird neu gestartet
				restartGame();
				System.out.println("Spiel wird neugestartet.");
			}
			else if (result == JOptionPane.NO_OPTION)
			{
				System.exit(0);
			} else
			{
				System.out.println("Benutzer hat Abbrechen geklickt.");
			}
		}
	}

	public void restartGame()
	{
		//Spielvariablen zurücksetzen
		player1.x = 5;
		player1.y = 5;
		player1.bewegungX = 0;
		player1.bewegungY = 0;
		level = 1;
		laseranzahl = 5;
		win = false;
		gameOver = false;

		//Spiel wieder starten
		gameLoop.start();
	}

	@Override
	public void keyTyped(KeyEvent e) {} //muss wegen der Implementierung vom KeyListener aufgerufen werden

	@Override
	public void keyReleased(KeyEvent e) {} //muss wegen der Implementierung vom KeyListener aufgerufen werden
}
