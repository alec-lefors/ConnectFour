import java.io.*;
import java.net.*;

public class Connect4Client {
	
	public static void main(String[] args) throws IOException
	{    
		if (args.length != 2)
		{
			System.err.println("Usage: java Connect4Client <host name> <port number>");
			System.exit(1);
		}
	
		String hostName = args[0];
		int portNumber = Integer.parseInt(args[1]);
	
		try (Socket clientSocket = new Socket(hostName, portNumber);
	            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
	            DataInputStream in = new DataInputStream(clientSocket.getInputStream());)
		{
			ObjectInputStream ins = new ObjectInputStream(in);
			int[][] gameboard = (int[][]) ins.readObject();
			String fromServer;
			String fromUser;
			System.out.println("--------------");
			for(int r = 0; r < gameboard.length; r++)
			{
				for(int c = 0; c < gameboard[0].length; c++)
				{
					System.out.print(gameboard[r][c]);
				}
				System.out.println();
			}
		}
		catch (UnknownHostException e)
		{
			System.err.println("Don't know about host " + hostName);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + hostName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
