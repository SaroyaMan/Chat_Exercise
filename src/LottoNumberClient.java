import java.io.*;
import java.net.Socket;

public class LottoNumberClient
{	
	public static String serverName = "127.0.0.1";
	public static int serverPortNumber = 8070;
	
	public static void main(String[] args)
	{
		Socket socket = null;
		InputStream is = null;
		DataInputStream dis = null;
		try
		{
			socket = new Socket(serverName, serverPortNumber);
			System.out.println("socket was created...");
			is = socket.getInputStream();
			System.out.println("input stream was created...");
			dis = new DataInputStream(is);
			System.out.println("data input stream was created...");
			String numbers = dis.readUTF();
			System.out.println("The numbers that received is:\n"+numbers);
		}
		catch(IOException e) {e.printStackTrace();}
		finally
		{
			if (dis!= null)
			{
				try {dis.close();}
				catch(IOException e) {e.printStackTrace();}
			}
			if (socket!= null)
			{
				try {socket.close();}
				catch(IOException e) {e.printStackTrace();}
			}
		}
	}
}