package chatSystem;

import java.io.IOException;
import java.net.*;

public class ServerApplication
{
	public static void main(String args[])
	{
		ServerSocket server = null;
		Socket socket = null;
		ClientDescriptor client = null;
		ConnectionProxy connection = null;
		MessageBoard mb = new MessageBoard();
		int numOfConnections=0;
		try
		{
			server = new ServerSocket(1300,5);
			while(true)
			{
				System.out.println("Socket accept()...");
				socket = server.accept();
				connection = new ConnectionProxy(socket);
				System.out.println("Number of connection proxies created: "+(++numOfConnections));
				client = new ClientDescriptor();
				connection.addConsumer(client);
				client.addConsumer(mb);    
				mb.addConsumer(connection);
				connection.start();
			}
		}
		catch (IOException e) {e.printStackTrace();}
		finally
		{
			if (socket != null)
			{
				try {socket.close();}
				catch(IOException e) {e.printStackTrace();}
			}
		}
	}
}