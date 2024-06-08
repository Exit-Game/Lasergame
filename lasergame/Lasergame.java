package lasergame;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList; //Laser erstellen
import java.util.List;
import java.util.Random; // Laser placen
import javax.swing.*;

public class Lasergame extends JPanel implements ActionListener, KeyListener
{
	//gameBoard
	private final int width;
	private final int length;
	
	//gamelogic
    private static Timer gameLoop;
	private boolean gameOver = false;
	private boolean win = false;
	private int level = 1;
	
	//Laser
	private final Random random;
	private int laserNumber;
	private List<Laser> lasers = new ArrayList<>();
	private int laspass;
	private String laspassstri;
	
	//player
	private Character player1;
	
	//PowerUp
	private PowerUp newPow;

	Lasergame (int boardHeight, int boardLength, int laseranzahl2)
	{
		//gameSettings
		this.laserNumber = laseranzahl2;
		this.width = boardHeight;
		this.length = boardLength;
		setPreferredSize(new Dimension(this.width, this.length));
		setBackground(Color.GRAY);
		addKeyListener(this);
		setFocusable(true);

		//Initialisierung Objekte
		random = new Random();
		player1 = new Character(5, 450, 10);
		newPow = new PowerUp(random.nextInt(15, width -15),random.nextInt(15, length -15));

		//Laser erschaffen
		for (int i = 0; i < laserNumber; i++)
		{
			lasers.add(new Laser());
			if (i > 0)
			{
				//alle anderen Laser
				Laser.createLaser(lasers.get(i), lasers.get(i-1), random, width, length, player1, laserNumber);
			}
			else
			{
				//1. Laser
				Laser.createLaser(lasers.get(i), random, width, length, player1, laserNumber);
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
		laserNumber = laserNumber + 5;

		//Levelerhöhung
		level++;

		//Player vom rechten zum linken Rand und Player stoppen
		player1.setX(1);
		player1.setMovementY(0);
		player1.setMovementX(0);

		//Erstellung Laser mit erhöhter Laseranzahl
		for (int i = 0; i < laserNumber; i++)
		{
			lasers.add(new Laser());
			if (i > 0)
			{
				//alle anderen Laser
				Laser.createLaser(lasers.get(i), lasers.get(i-1), random, width, length, player1, laserNumber);
			}
			else
			{
				//1. Laser
				Laser.createLaser(lasers.get(i), random, width, length, player1, laserNumber);
			}

		}
	}
	
	public void draw(Graphics g) //Funktion für das Zeichnen
	{
		//player1
		g.setColor(Color.BLACK);
		g.fill3DRect(player1.getX(), player1.getY(), player1.getWidth(), player1.getLength(), true);
		
		//laser
		for (int i = 0; i < laserNumber; i++)
		{
			g.setColor(Color.GREEN);
			g.fill3DRect(lasers.get(i).getX(), lasers.get(i).getY(), lasers.get(i).getWidth(), lasers.get(i).getLength(), true);
		}

		//PowerUp
		g.setColor(Color.CYAN);
		g.fill3DRect(newPow.getX(), newPow.getY(), newPow.getWidth(), newPow.getLength(), true);
		
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
		g.setColor(Color.GREEN);
		g.setFont(new Font("Arial", Font.PLAIN, 16));
		g.drawString("Passierte Laser: " + laspassstri, 10, 20);

		//Level
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN, 16));
		g.drawString("Level: " + level,10,40);

		//Shield
		g.setColor(Color.CYAN);
		g.setFont(new Font("Arial", Font.PLAIN, 16));
		g.drawString("Shield: " + player1.getShield(),10,60);

	}

	public void paintComponent(Graphics g) //ruft die Drawfunktion auf
	{
		super.paintComponent(g);
		draw(g);
	}

	
	public void move()
	{
		//Playermovement
		player1.setX(player1.getX() + player1.getMovementX() * player1.getSpeed());
		player1.setY(player1.getY()+ player1.getMovementY() * player1.getSpeed());

		//Collisionscheck
		for (int i = 0; i < laserNumber; i++)
		{
			if(Laser.collisionLaserBoarder(lasers.get(i), player1, length))
			{
				if(player1.getShield()>0)
				{
					player1.setShield(player1.getShield()-1);
					player1.setX(5);
					player1.setY(450);
					player1.setMovementX(0);
					player1.setMovementY(0);
					break;
				}

				gameOver = true;
				gameLoop.stop();
			}

		}

		//Abfrage, wieviele Laser passiert wurden
		laspassstri = Laser.laserpassed(player1, lasers, laserNumber, laspass, level);

		//Win
		if(level == 3 && player1.getX() >= width)
		{
			win = true;
			lasers.clear();
			repaint();
			PostRequest.request();
			gameLoop.stop();
			openNote();
		}

		//PowerUpCollision
		if(PowerUp.collisionPowerUp(newPow,player1))
		{
			//Erstellen des neuen Powerups
			newPow.setX(random.nextInt(15, width -15));
			newPow.setY(random.nextInt(15, length -15));

			//Shielderhöhung
			player1.setShield(player1.getShield()+1);
		}

		//neues Level erstellen
		if(player1.getX() >= width)
		{
			newLevel();
		}

		//Bewegung der Laser
		for(int i = 0; i < laserNumber; i++)
		{
			Laser.movingLaser(lasers.get(i), length);
		}
	}

	public void openNote() //öffnen des nächsten Hinweises
	{
		try
		{
			Runtime.getRuntime().exec("notepad.exe C:/Exit-Game/Lasergame/files/Hinweis2.txt");
		}
		catch(IOException e)
		{
			e.printStackTrace();
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
			player1.setMovementX(0);
			player1.setMovementY(-1);
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN) //Taste nach unten, Player bewegt sich nach unten
		{
			player1.setMovementX(0);
			player1.setMovementY(1);
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT) //Taste nach rechts, Player bewegt sich nach rechts
		{
			player1.setMovementX(1);
			player1.setMovementY(0);
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT) //Taste nach links, Player bewegt sich nach links
		{
			player1.setMovementX(-1);
			player1.setMovementY(0);
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
				System.out.println("Spiel wird beendet");
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
		player1.setX(5);
		player1.setY(450);
		player1.setMovementX(0);
		player1.setMovementY(0);
		player1.setSpeed(10);
		level = 1;
		laserNumber = 5;
		win = false;
		gameOver = false;

		//PowerUp zurücksetzen
		newPow = null;
		newPow = new PowerUp(random.nextInt(15, width -15),random.nextInt(15, length -15));

		for (int i = 0; i < laserNumber; i++)
		{
			lasers.add(new Laser());
			if (i > 0)
			{
				//alle anderen Laser
				Laser.createLaser(lasers.get(i), lasers.get(i-1), random, width, length, player1, laserNumber);
			}
			else
			{
				//1. Laser
				Laser.createLaser(lasers.get(i), random, width, length, player1, laserNumber);
			}

		}

		//Spiel wieder starten
		gameLoop.start();
	}

	@Override
	public void keyTyped(KeyEvent e) {} //muss wegen der Implementierung vom KeyListener aufgerufen werden

	@Override
	public void keyReleased(KeyEvent e) {} //muss wegen der Implementierung vom KeyListener aufgerufen werden
}
