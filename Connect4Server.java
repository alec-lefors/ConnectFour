import java.io.*;
import java.net.*;

public class Connect4Server extends Thread {

	private final static int PORT = 3102;
	private ServerSocket ConnectNetwork;
	
	public Connect4Server() throws IOException
	{
		ConnectNetwork = new ServerSocket(PORT);
		ConnectNetwork.setSoTimeout(10000);
	}
	
	public void run()
	{
		while(true) {
			try {
				System.out.println("Waiting for client on port " + ConnectNetwork.getLocalPort() + "...");
				Socket server = ConnectNetwork.accept();
				
				System.out.println("Just connected to " + server.getRemoteSocketAddress());
				DataInputStream in = new DataInputStream(server.getInputStream());
	            
	            System.out.println(in.readUTF());
	            DataOutputStream out = new DataOutputStream(server.getOutputStream());
	            out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress()+ "\nGoodbye!");
	            server.close();
	            
			}
			catch (SocketTimeoutException s)
			{
				System.out.println("Socket timed out!");
				break;
			}
			catch (IOException e)
			{
				e.printStackTrace();
				break;
			}
		}
	}
	
	public static void main(String[] args)
	{
		try {
			Thread t = new Connect4Server();
			t.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
