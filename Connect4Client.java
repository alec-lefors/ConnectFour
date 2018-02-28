import java.io.*;
import java.net.*;

public class Connect4Client {

	private final static int PORT = 3102;
	
	public static void main(String[] args)
	{
		Socket MyClient;
		try {
			MyClient = new Socket("testclient", PORT);
			System.out.println(MyClient.getRemoteSocketAddress());
			OutputStream outToServer = MyClient.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);
			
			System.out.println(MyClient.getRemoteSocketAddress());
			InputStream inFromServer = MyClient.getInputStream();
			DataInputStream in = new DataInputStream(inFromServer);
			
			System.out.println(in.readUTF());
			MyClient.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
