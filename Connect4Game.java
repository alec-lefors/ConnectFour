public class ConnectFour
{

    /*Creates the 6x7 2-D Array that will represent the gameboard
    * Integer array- will be converted to Graphics. 0 represents a blank space, 1 represents red, 2 represents yellow.
    */
    
    public static int[][] gameboard = new int[6][7];
    public static int turnNumber = 0;
    
    do
    {
        nextTurn();
    }
    //while !gameOver();
    
    
    // Keeps track of turns in the game. Red always goes first.
    public static void nextTurn()
    {
        if(turnNumber % 2 == 0) placeTile(1);
        else placeTile(2);
        turnNumber++;
    }
    
    // Make a play
    public static void placeTile(int player)
    {
        while(true)
        {
            if(gameboard[0][column]==0)
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
            else 
            {
                turnNumber--;
            }
            break;
       }
    }
    public static boolean checkHorizontal()
    {
        for(int i = 0; i < gameboard.length; i++)
        {
            for(int j = 0; j < gameboard[0].length-4; j++)
            {
                if (gameboard[i][j]
            }
        }
        return false;
    }
    public static boolean gameOver()
    {
        return (checkHorizontal()||checkVertical()||checkDiagonal());
    }
    
}