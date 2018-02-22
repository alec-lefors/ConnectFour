import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class Connect4Game extends JFrame implements ActionListener
{
	public static int[][] gameboard = new int[6][7];
    public static int turnNumber = 0;
    private static JButton btnCol0, btnCol1, btnCol2, btnCol3, btnCol4, btnCol5, btnCol6;
    private static JLabel label0, label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11, label12, label13, label14, label15, label16, label17, label18, label19, label20, label21, label22, label23, label24, label25, label26, label27, label28, label29, label30, label31, label32, label33, label34, label35, label36, label37, label38, label39, label40, label41;
    private static JLabel[] myLabels = {label0, label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11, label12, label13, label14, label15, label16, label17, label18, label19, label20, label21, label22, label23, label24, label25, label26, label27, label28, label29, label30, label31, label32, label33, label34, label35, label36, label37, label38, label39, label40, label41};
    private static int column; // keeps track of what column was last selected
    public static Connect4Game app = new Connect4Game();
    public static StretchIcon emptySpace = new StretchIcon("src/white.png"); 	// All three "StretchIcon" objects
    public static StretchIcon redSpace = new StretchIcon("src/red.png");		// come from a class imported
    public static StretchIcon yellowSpace = new StretchIcon("src/yellow.png");// online. It allows the images to
    																			// be flexible with any window size.
    private static Color background = new Color(88, 171, 251);

	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Connect Four");
		frame.setSize(432, 370);
		frame.setMinimumSize(new Dimension(520, 446));
		
		// https://stackoverflow.com/questions/2554684/multiple-layout-managers-in-java
		JPanel masterPanel = new JPanel(new OverlapLayout()); // Overlap layout comes from the class we
															 // imported from the Internet. It is only
															 // used here.
		JPanel columnPanel = new JPanel(new GridLayout(0, 7));
		JPanel imagePanel = new JPanel(new GridLayout(6, 7));
		
		imagePanel.setBackground(background);
		columnPanel.setOpaque(false);

		JButton[] myButtons = {btnCol0, btnCol1, btnCol2, btnCol3, btnCol4, btnCol5, btnCol6};
		for(Integer i = 0; i < myButtons.length; i++)
		{
			myButtons[i] = new JButton(i.toString());
			myButtons[i].addActionListener(app);
			columnPanel.add(myButtons[i]);
			myButtons[i].setOpaque(false);
			myButtons[i].setContentAreaFilled(false);
			myButtons[i].setBorderPainted(false);
		}
		
		for(Integer i = 0; i < 42; i++)
		{
			myLabels[i] = new JLabel(emptySpace);
			myLabels[i].setIcon(emptySpace);
			imagePanel.add(myLabels[i]);
		}
		
		paintGame(myLabels);

		frame.add(masterPanel);
		masterPanel.add(columnPanel);
		masterPanel.add(imagePanel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	public static boolean checkWinner()
	{
		// replace with this once all tests are complete
		// if(checkHorizontal() == 1 || checkVertical() == 1 || checkDiags() == 1)
		if(checkHorizontal() == 1)
		{
			int result = JOptionPane.showConfirmDialog(app, "Red won!", "Game over", JOptionPane.CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if(result == JOptionPane.OK_OPTION)
			{
				System.exit(0);
			}
			return true;
		}
		// replace with this once all tests are complete
		// if(checkHorizontal() == 2 || checkVertical() == 2 || checkDiags() == 2)
		if(checkHorizontal() == 2)
		{
			int result = JOptionPane.showConfirmDialog(app, "Yellow won!", "Game over", JOptionPane.CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if(result == JOptionPane.OK_OPTION)
			{
				System.exit(0);
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
	
	// Paints an image of the board onto the JFrame.
	private static void paintGame(JLabel[] imageboard)
	{
		int o = 0;
		for(Integer i = 0; i < 6; i++)
		{
			for(Integer j = 0; j < 7; j++)
			{
				if(gameboard[i][j] == 0) imageboard[o].setIcon(emptySpace);
				else if(gameboard[i][j] == 1) imageboard[o].setIcon(redSpace);
				else imageboard[o].setIcon(yellowSpace);
				o++;
			}
		}
	}
	
	// Determines what happens after a column is selected.
	public void actionPerformed(ActionEvent evt)
	{
		if(!checkWinner())
		{
			column = Integer.parseInt(evt.getActionCommand());
			nextTurn();
			for(int i = 0; i < 20; i++) System.out.println();
			System.out.println("Col: " + column);
			System.out.println("Turn: " + turnNumber);
			paintGame(myLabels);
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
		if(gameboard[0][column] == 0)
		{
	        outerLoop:
	        	for(int i = 0; i < gameboard.length; i++)
                {
                    if (i<=gameboard.length-1 && gameboard[i][column]!=0)
                    {
                        gameboard[i-1][column] = player;
                        break outerLoop;
                    }
                    else if(i == 5 && gameboard[i][column]==0) gameboard[i][column] = player;
                }
		}
		else turnNumber--;
    }

}
