import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class Connect4Game extends JFrame implements ActionListener
{
	private static int[][] gameboard = new int[6][7]; // The game board is 6 by 7
    private static int turnNumber = 0; // Keeps track of who's turn it is.
    private static JButton btnCol0, btnCol1, btnCol2, btnCol3, btnCol4, btnCol5, btnCol6;
    private static JLabel label0, label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11, label12, label13, label14, label15, label16, label17,
    label18, label19, label20, label21, label22, label23, label24, label25, label26, label27,
    label28, label29, label30, label31, label32, label33, label34, label35, label36, label37,
    label38, label39, label40, label41; // All the cells have their own JLabel to display
    									// the tile image.
    private static JLabel[] myLabels = {label0, label1, label2, label3, label4, label5,
    		label6, label7, label8, label9, label10, label11, label12, label13, label14,
    		label15, label16, label17, label18, label19, label20, label21, label22, label23,
    		label24, label25, label26, label27, label28, label29, label30, label31, label32,
    		label33, label34, label35, label36, label37, label38, label39, label40, label41};
    private static int column; // keeps track of what column was last selected
    public static Connect4Game app = new Connect4Game();
    
	// All three "StretchIcon" objects come from a class imported
    // online. It allows the images to be flexible with any window size.
    private static StretchIcon emptySpace = new StretchIcon("src/white.png");
    private static StretchIcon redSpace = new StretchIcon("src/red.png");
    private static StretchIcon yellowSpace = new StretchIcon("src/yellow.png");

	// This color matches the background of the tiles.
    private static Color background = new Color(88, 171, 251); 

	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Connect Four");
		frame.setSize(432, 370);
		frame.setMinimumSize(new Dimension(520, 446));
		
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
		JPanel menuPanel = new JPanel(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_START;
		c.gridx = 0;
		JButton resetGame = new JButton("Reset Game");
		resetGame.addActionListener(app);
		menuPanel.add(resetGame, c);
		c.anchor = GridBagConstraints.PAGE_START;
		c.gridx = 2;
		JLabel currentTurn = new JLabel("Turn: ");
		menuPanel.add(currentTurn, c);
		
		imagePanel.setBackground(background);
		columnPanel.setOpaque(false);

		JButton[] myButtons = {btnCol0, btnCol1, btnCol2, btnCol3, btnCol4, btnCol5, btnCol6};
		for(Integer i = 0; i < myButtons.length; i++)
		{
			myButtons[i] = new JButton(i.toString());
			myButtons[i].addActionListener(app);
			columnPanel.add(myButtons[i]);
			myButtons[i].setOpaque(false);
		}
		
		for(Integer i = 0; i < 42; i++)
		{
			myLabels[i] = new JLabel(emptySpace);
			myLabels[i].setIcon(emptySpace);
			imagePanel.add(myLabels[i]);
		}
		
		paintGame();

		// Builds the frames and places them on top of each other.
		
		masterPanel.add(gamePanel, BorderLayout.CENTER);
		gamePanel.add(columnPanel);
		gamePanel.add(imagePanel);
		masterPanel.add(menuPanel, BorderLayout.PAGE_START);
		frame.add(masterPanel);
		
		ImageIcon icon = new ImageIcon("src/icon.png");
		frame.setIconImage(icon.getImage());
		
		// Sets the JFrame to visible, sets the location to the center, and sets the X button to kill the program. 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(screenSize.width / 2 - frame.getSize().width / 2, screenSize.height / 2 - frame.getSize().height / 2);
		frame.setVisible(true);
	}
	
	public static boolean checkWinner()
	{
		// replace with this once all tests are complete
		// if(checkHorizontal() == 1 || checkVertical() == 1 || checkDiags() == 1)
		if(checkHorizontal() == 1 || checkVertical() == 1 || checkDiags() == 1)
		{
			int result = JOptionPane.showConfirmDialog(app, "Red won!\nWould you like to start a new game?", "Game over", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
			if(result == JOptionPane.NO_OPTION)
			{
				System.exit(0);
			}
			else if(result == JOptionPane.YES_OPTION)
			{
				resetGame();
			}
			return true;
		}
		// replace with this once all tests are complete
		// if(checkHorizontal() == 2 || checkVertical() == 2 || checkDiags() == 2)
		if(checkHorizontal() == 2 || checkVertical() == 2 || checkDiags() == 2)
		{
			int result = JOptionPane.showConfirmDialog(app, "Yellow won!\nWould you like to start a new game?", "Game over", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
			if(result == JOptionPane.NO_OPTION)
			{
				System.exit(0);
			}
			else if(result == JOptionPane.YES_OPTION)
			{
				resetGame();
			}
			return true;
		}
		if(checkFilledBoard())
		{
			int result = JOptionPane.showConfirmDialog(app, "It's a tie!\nWould you like to start a new game?", "Game over", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
			if(result == JOptionPane.NO_OPTION)
			{
				System.exit(0);
			}
			else if(result == JOptionPane.YES_OPTION)
			{
				resetGame();
			}
			return true;
		}
		return false;
	}
	
	// Checks for any horizontal 4 in a rows. Returns 0 if none, returns 1 or 2 if there is.
	public static int checkHorizontal()
    {
        for(int i = 0; i < gameboard.length; i++)
        {
            for(int j = 0; j < gameboard[0].length-3; j++)
            {
                if (gameboard[i][j] != 0 && gameboard[i][j]==gameboard[i][j+1] && gameboard[i][j]==gameboard[i][j+2] && gameboard[i][j]==gameboard[i][j+3])
                {
                		return gameboard[i][j];
                }
            }
        }
        return 0;
    }
	
	public static int checkDiags()
    {
        for(int i = 0; i < gameboard.length-3; i++)
        {
            for(int j = 0; j < gameboard[0].length-3; j++)
            {
                if (gameboard[i][j] != 0 && gameboard[i][j]==gameboard[i+1][j+1] && gameboard[i][j]==gameboard[i+2][j+2] && gameboard[i][j]==gameboard[i+3][j+3])
                {
                        return gameboard[i][j];
                }
            }
        }
        for(int i = gameboard.length-1; i > 2; i--)
        {
            for(int j = 0; j < gameboard[0].length-3; j++)
            {
                if (gameboard[i][j] != 0 && gameboard[i][j]==gameboard[i-1][j+1] && gameboard[i][j]==gameboard[i-2][j+2] && gameboard[i][j]==gameboard[i-3][j+3])
                {
                        return gameboard[i][j];
                }
            }
        }
        return 0;
    }
	
	// Checks for any vertical 4 in a rows. Returns 0 if none, returns 1 or 2 if there is.
	public static int checkVertical()
    {
        for(int i = 0; i < gameboard[0].length; i++)
        {
            for(int j = 0; j < gameboard.length-3; j++)
            {
                if (gameboard[j][i] != 0 && gameboard[j][i]==gameboard[j+1][i] && gameboard[j][i]==gameboard[j+2][i] && gameboard[j][i]==gameboard[j+3][i])
                {
                        return gameboard[j][i];
                }
            }
        }
        return 0;
    }
	
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
	public void actionPerformed(ActionEvent evt)
	{
		// Checks to see if the reset button was pressed, if not, it's a move.
		if(evt.getActionCommand().equals("Reset Game")) resetGame();
		else
		{
			if(!checkWinner())
			{
				column = Integer.parseInt(evt.getActionCommand());
				nextTurn();
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
		if(gameboard[0][column] == 0)
		{
	        outerLoop:
        	for(int i = 0; i < gameboard.length; i++)
            {
                if (i < gameboard.length && gameboard[i][column]!=0)
                {
                    gameboard[i-1][column] = player;
                    break outerLoop;
                }
                else if(i == 5 && gameboard[i][column]==0) gameboard[i][column] = player;
            }
		}
		else turnNumber--;
    }
	
	private static void resetGame()
    {
        for(Integer i = 0; i < 6; i++)
        {
            for(Integer j = 0; j < 7; j++)
            {
               gameboard[i][j] = 0;
            }
        }
        paintGame();  
        turnNumber = 0;
    }

}
