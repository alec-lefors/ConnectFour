import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import resources.ResourceLoader;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
public class Connect4Game extends JFrame implements ActionListener
{
	private static int[][] gameboard = new int[6][7]; // The game board is 6 by 7
	private static int turnNumber = 0; // Keeps track of who's turn it is.
	private static JButton btnCol0, btnCol1, btnCol2, btnCol3, btnCol4, btnCol5, btnCol6; //Creates 7 buttons for the user to choose which column
	private static JLabel label0, label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11, label12, label13, label14, label15, label16, label17,
	label18, label19, label20, label21, label22, label23, label24, label25, label26, label27,
	label28, label29, label30, label31, label32, label33, label34, label35, label36, label37,
	label38, label39, label40, label41; 	// All the cells have their own JLabel to display
										// the tile image.
	// Creates a JLabel array
	private static JLabel[] myLabels = {label0, label1, label2, label3, label4, label5,
		label6, label7, label8, label9, label10, label11, label12, label13, label14,
		label15, label16, label17, label18, label19, label20, label21, label22, label23,
		label24, label25, label26, label27, label28, label29, label30, label31, label32,
		label33, label34, label35, label36, label37, label38, label39, label40, label41};
	// 7 hover buttons for user interface
	private static JLabel hov0, hov1, hov2, hov3, hov4, hov5, hov6;
	private static JLabel[] hovLabels = {hov0, hov1, hov2, hov3, hov4, hov5, hov6};
	private static int column; // keeps track of what column was last selected
	public static Connect4Game app = new Connect4Game();
	
	// All three "StretchIcon" objects come from a class imported
	// online. It allows the images to be flexible with any window size.
	private static StretchIcon emptySpace = new StretchIcon(ResourceLoader.loadImage("empty.png"));
	private static StretchIcon redSpace = new StretchIcon(ResourceLoader.loadImage("red.png"));
	private static StretchIcon yellowSpace = new StretchIcon(ResourceLoader.loadImage("yellow.png"));
	private static StretchIcon redWon = new StretchIcon(ResourceLoader.loadImage("redwon.png"));
	private static StretchIcon yellowWon = new StretchIcon(ResourceLoader.loadImage("yellowwon.png"));
	// The image of player's turn
	private static StretchIcon currentTurn = redSpace;
	
	// This color matches the background of the tiles.
	private static Color background = new Color(88, 171, 251); 
	
	// Thrown exceptions for the setLookAndFeel method
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		JFrame frame = new JFrame("Connect Four");
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		frame.setSize(432, 370);
		frame.setMinimumSize(new Dimension(520, 446));
		// Makes use of the macOS menu bar rather than the traditional menu bar in Windows.
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		/*
		 *  https://stackoverflow.com/questions/2554684/multiple-layout-managers-in-java
		 *  Overlap layout comes from the class we imported from the Internet. It is only
		 *  used with gamePanel.
		 *  
		 *  gamePanel - merges columnPanel and imagePanel into the game board
		 *  columnPanel - contains the 7 JButtons that represent each column
		 *  imagePanel - contains the 42 JLabels that represent each cell
		 *  masterPanel - Places all the components of the game together
		 *  menuPanel - Places all the components of the menu together
		 */
		JPanel gamePanel = new JPanel(new OverlapLayout());
		JPanel columnPanel = new JPanel(new GridLayout(0, 7));
		JPanel imagePanel = new JPanel(new GridLayout(6, 7));
		JPanel masterPanel = new JPanel(new BorderLayout());
		JPanel menuPanel = new JPanel(new GridLayout(0, 7));
		
		// Adds a menu bar at the top of the frame
		JMenuBar menuBar = new JMenuBar();
		
		// Creates tabs for the menu bar
		JMenu file = new JMenu("File");
		JMenu experimental = new JMenu("Prefrences");
		JMenu changeColor = new JMenu("Background Color");
		
		// Creates items for the tabs
		JMenuItem newGame = new JMenuItem("New Game", null);
		JMenuItem exitGame = new JMenuItem("Exit", null);
		JMenuItem changeColorBlack = new JMenuItem("Black", null);
		JMenuItem changeColorDefault = new JMenuItem("Default", null);
		
		// Sets the for text when you hover over items
		newGame.setToolTipText("Create a new game");
		exitGame.setToolTipText("Exit Connect Four");
		changeColorBlack.setToolTipText("Change color to black.");
		changeColorDefault.setToolTipText("Change color to the default color.");
		
		// Adds items to each tab
		file.add(newGame);
		file.add(exitGame);
		experimental.add(changeColor);
		changeColor.add(changeColorDefault);
		changeColor.add(changeColorBlack);
		menuBar.add(file);
		menuBar.add(experimental);
		frame.setJMenuBar(menuBar);
		
		// Creates a new game, does not ask if the "user is sure" if the board is already clear.
		newGame.addActionListener((ActionEvent event) ->
		{
			resetGame(!checkEmptyBoard());
		});
		// Closes out of the game
		exitGame.addActionListener((ActionEvent event) ->
		{
			System.exit(0);
		});
		// Default blue color of Connect Four game board
		changeColorDefault.addActionListener((ActionEvent event) ->
		{
			imagePanel.setBackground(background);
		});
		// Changes game board to black
		changeColorBlack.addActionListener((ActionEvent event) ->
		{
			imagePanel.setBackground(new Color(20, 20, 20));
		});
		
		imagePanel.setBackground(background);
		menuPanel.setBackground(new Color(255, 255, 255));
		columnPanel.setOpaque(false);
		
		JButton[] myButtons = {btnCol0, btnCol1, btnCol2, btnCol3, btnCol4, btnCol5, btnCol6};
		// These methods keep track of who's turn it is in the UI. 
		MouseListener hoverListener = new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				for(int i = 0; i < 7; i++)
				{
					if(e.getSource().equals(myButtons[i])) hovLabels[i].setIcon(currentTurn);
				}
			}
			
			public void mouseExited(MouseEvent e)
			{
				for(int i = 0; i < 7; i++)
				{
					if(e.getSource().equals(myButtons[i])) hovLabels[i].setIcon(null);
				}
			}
			
			public void mouseClicked(MouseEvent e)
			{
				for(int i = 0; i < 7; i++)
				{
					if(e.getSource().equals(myButtons[i])) hovLabels[i].setIcon(currentTurn);
				}
			}
		};
		// Sets up the game board for the first run
		for(Integer i = 0; i < myButtons.length; i++)
		{
			myButtons[i] = new JButton(i.toString());
			myButtons[i].addActionListener(app);
			myButtons[i].addMouseListener(hoverListener);
			columnPanel.add(myButtons[i]);
			myButtons[i].setOpaque(false);
		}
		
		for(Integer i = 0; i < 42; i++)
		{
			myLabels[i] = new JLabel(emptySpace);
			myLabels[i].setIcon(emptySpace);
			imagePanel.add(myLabels[i]);
		}
		
		for(int i = 0; i < 7; i++)
		{
			hovLabels[i] = new JLabel(" ");
			menuPanel.add(hovLabels[i]);
			hovLabels[i].setPreferredSize(new Dimension(68, 68));
		}
		
		paintGame();

		// Builds the frames and places them on top of each other.
		masterPanel.add(menuPanel, BorderLayout.PAGE_START);
		masterPanel.add(gamePanel, BorderLayout.CENTER);
		gamePanel.add(columnPanel);
		gamePanel.add(imagePanel);
		frame.add(masterPanel);
		
		frame.addComponentListener(new ComponentAdapter()
		{
			public void componentResized(ComponentEvent e) {
				hovLabels[0].setPreferredSize(getSizeOfHover(myLabels[0].getWidth(), myLabels[0].getHeight()));
			}
		});
		
		frame.setIconImage(redSpace.getImage());
		
		// Sets the JFrame to visible, sets the location to the center, and sets the X button to kill the program. 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(screenSize.width / 2 - frame.getSize().width / 2, screenSize.height / 2 - frame.getSize().height / 2);
		frame.setVisible(true);
	}
	// Method runs every check after every turn, allows the user to start over
	public static boolean checkWinner()
	{
		String winnerName = new String();
		boolean isWinner = true;
		// Runs each check method once. Each check method returns an integer for the winner.
		int horizontal = checkHorizontal();
		int vertical = checkVertical();
		int diagonal = checkDiags();
		if((horizontal | vertical | diagonal) == 0) isWinner = false;
		else if((horizontal | vertical | diagonal) == 1) winnerName = "Red";
		else winnerName = "Yellow";
		// Player won, player can start over without "Are you sure?"
		if(isWinner)
		{
			int result = JOptionPane.showConfirmDialog(app, winnerName + " won!\nWould you like to start a new game?", "Game over", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
			if(result == JOptionPane.YES_OPTION)
			{
				resetGame(false);
			}
			return true;
		}
		// No one won, makes sure the board is empty.
		if(checkFilledBoard())
		{
			int result = JOptionPane.showConfirmDialog(app, "It's a tie!\nWould you like to start a new game?", "Game over", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
			if(result == JOptionPane.YES_OPTION)
			{
				resetGame(false);
			}
			return true;
		}
		return false;
	}
	
	//The winningPiece method returns a Stretch icon in order to replace the tiles that ended up winning the game with a glowing variation
	public static StretchIcon winningPiece(int i, int j)
	{
		if (gameboard[i][j]==1) return redWon;
		else return yellowWon;
	}
	
	// Checks for any horizontal 4 in a rows. Returns 0 if none, returns 1 or 2 if there is.
	public static int checkHorizontal()
	{
		//Checks to see if consecutive horizontal pieces are equal integers.
		for(int i = 0; i < gameboard.length; i++)
		{
			for(int j = 0; j < gameboard[0].length-3; j++)
			{
				if (gameboard[i][j] != 0 && gameboard[i][j]==gameboard[i][j+1] && gameboard[i][j]==gameboard[i][j+2] && gameboard[i][j]==gameboard[i][j+3])
				{
					// Changes these pieces to glowing variation
					int winningLabel = j + 7*i;
					for (int k = 0; k < 4; k++)
					{
						myLabels[winningLabel+k].setIcon(winningPiece(i, j+k));
					}
					return gameboard[i][j];
				}
			}
		}
		return 0;
	}
	
	// Checks for any diagonal 4 in a rows. Returns 0 if none, returns 1 or 2 if there is.
	public static int checkDiags()
	{
		// Checks to see if consecutive diagonal pieces, from top left to bottom right, are equal integers.
		for(int i = 0; i < gameboard.length-3; i++)
		{
			for(int j = 0; j < gameboard[0].length-3; j++)
			{
				if (gameboard[i][j] != 0 && gameboard[i][j]==gameboard[i+1][j+1] && gameboard[i][j]==gameboard[i+2][j+2] && gameboard[i][j]==gameboard[i+3][j+3])
				{
					int winningLabel = j + 7*i;
					for (int k = 0; k < 4; k++)
					{
						myLabels[winningLabel+(7*k)+k].setIcon(winningPiece(i+k, j+k));
					}
					return gameboard[i][j];
				}
			}
		}
		// Checks to see if consecutive diagonal pieces, from bottom right to top left, are equal integers
		for(int i = gameboard.length-1; i > 2; i--)
		{
			for(int j = 0; j < gameboard[0].length-3; j++)
			{
				if (gameboard[i][j] != 0 && gameboard[i][j]==gameboard[i-1][j+1] && gameboard[i][j]==gameboard[i-2][j+2] && gameboard[i][j]==gameboard[i-3][j+3])
				{
					// Changes these pieces to glowing variation
					int winningLabel = j + 7*i;
					for (int k = 0; k < 4; k++)
					{
						myLabels[winningLabel-(7*k)+k].setIcon(winningPiece(i-k, j+k));
					}
					return gameboard[i][j];
				}
			}
		}
		return 0;
	}
	
	// Checks for any vertical 4 in a rows. Returns 0 if none, returns 1 or 2 if there is.
	public static int checkVertical()
	{
		// Checks to see if consecutive vertical pieces are equal integers
		for(int i = 0; i < gameboard[0].length; i++)
		{
			for(int j = 0; j < gameboard.length-3; j++)
			{
				if (gameboard[j][i] != 0 && gameboard[j][i]==gameboard[j+1][i] && gameboard[j][i]==gameboard[j+2][i] && gameboard[j][i]==gameboard[j+3][i])
				{
					// Changes these pieces to glowing variation
					int winningLabel = i + 7*j;
					for (int k = 0; k < 4; k++)
					{
						myLabels[winningLabel+(7*k)].setIcon(winningPiece(j+k, i));
					}
					return gameboard[j][i];
				}
			}
		}
		return 0;
	}
	
	// Checks to see if the board is full, denoting a tie.
	public static boolean checkFilledBoard()
	{
		for(int i = 0; i < gameboard.length; i++)
		{
			for(int j = 0; j < gameboard[0].length; j++)
			{
				if(gameboard[i][j] == 0) return false;
			}
		}
		return true;
	}
	
	// Checks if the board is empty.
	public static boolean checkEmptyBoard()
	{
		for(int i = 0; i < gameboard.length; i++)
		{
			for(int j = 0; j < gameboard[0].length; j++)
			{
				// The instant the method finds a 1 or a 2, we know the board is not empty
				if(gameboard[i][j] != 0) return false;
			}
		}
		return true;
	}
	
	// Paints an image of the board onto the JFrame.
	private static void paintGame()
	{
		int o = 0;
		for(Integer i = 0; i < 6; i++)
		{
			for(Integer j = 0; j < 7; j++)
			{
				if(gameboard[i][j] == 0) myLabels[o].setIcon(emptySpace);
				else if(gameboard[i][j] == 1) myLabels[o].setIcon(redSpace);
				else myLabels[o].setIcon(yellowSpace);
				o++;
			}
		}
	}
	
	// Determines what happens after a column is selected.
	public void actionPerformed(ActionEvent e)
	{
		if(!checkWinner())
		{
			column = Integer.parseInt(e.getActionCommand());
			nextTurn();
			// Console output
			System.out.println("--------------");
			System.out.println("Col: " + column);
			System.out.println("Turn: " + turnNumber);
			paintGame();
			for(int r = 0; r < gameboard.length; r++)
			{
				for(int c = 0; c < gameboard[0].length; c++)
				{
					System.out.print(gameboard[r][c]);
				}
				System.out.println();
			}
			checkWinner();
		}
	}
	
	// Keeps track of turns in the game. Red always goes first.
	public static void nextTurn()
	{
		if(turnNumber % 2 == 0) placeTile(1);
		else placeTile(2);
		turnNumber++;
	}
	
	// Keeps track of all tiles, also checks for invalid moves.
	public static void placeTile(int player)
	{
		//Determines if column is full by checking the top space.
		if(gameboard[0][column] == 0)
		{
			//Goes down the desired column, if there is a nonzero space found, the method places a tile above it
			outerLoop:
				for(int i = 0; i < gameboard.length; i++)
				{
					if (i < gameboard.length && gameboard[i][column]!=0)
					{
						gameboard[i-1][column] = player;
						break outerLoop;
					}
					// Places a tile on the bottom if the column was previously empty
					else if(i == 5 && gameboard[i][column]==0) gameboard[i][column] = player;
				}
		// Changes the current player icon.
		if(player == 2) currentTurn = redSpace;
		else currentTurn = yellowSpace;
		}
		// Allows the user to go again if they clicked a full column
		else turnNumber--;
	}
	
	// Reset game will clear the board. If requiresDiaglog is true then a dialog will appear
	// before restarting the game. If it is false no dialog will appear.
	private static void resetGame(boolean requiresDialog)
	{
		if(requiresDialog)
		{
			int result = JOptionPane.showConfirmDialog(app, "Are you sure?", "Start a new game?", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
			if(result == JOptionPane.YES_OPTION)
			{
				//Reruns the code without an "Are you sure?"
				resetGame(false);
			}
		}
		else
		{
			for(Integer i = 0; i < 6; i++)
			{
				for(Integer j = 0; j < 7; j++)
				{
					gameboard[i][j] = 0;
				}
			}
			paintGame();
			currentTurn = redSpace;
			turnNumber = 0;
		}
	}
	
	// Gets the height and width of a cell and returns the smallest value of the two.
	private static Dimension getSizeOfHover(int w, int h)
	{
		if(w < h) return (new Dimension(w, w));
		return (new Dimension(h, h));
	}

}