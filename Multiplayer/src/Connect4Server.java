import java.io.*;
import java.net.*;

import KnockKnock.KnockKnockProtocol;

public class Connect4Server extends Thread {
	
	public static void main(String[] args) throws IOException
	{
		if (args.length != 1) {
            System.err.println("Usage: java Connect4Server <port number>");
            System.exit(1);
        }
		
		int portNumber = Integer.parseInt(args[0]);
		
		try ( ServerSocket serverSocket = new ServerSocket(portNumber); 
				Socket clientSocket = serverSocket.accept();
	            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());)
		{
			ObjectOutputStream outs = new ObjectOutputStream(out);
			outs.writeObject(Connect4Game.getGameboard());
	    }
		catch (IOException e)
		{
			System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}
	
}
