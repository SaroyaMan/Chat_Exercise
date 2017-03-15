import java.net.*;
import java.io.*;

public class TCPIPServer
{
	public static void main(String[] args)
	{
		//once this server is up and running you can call it
		//using telnet: "telnet 127.0.0.1 8070"

		int port = 8070;

		try
		{
			port = Integer.parseInt(args[0]);
		}
		catch (Exception e) {System.err.println(e);}
		if (port <= 0 || port >= 65536) port = 8070;

		try
		{
			@SuppressWarnings("resource")
			ServerSocket ss = new ServerSocket(port);
			while (true)
			{
				OutputStream out = null;
				Socket socket = null;
				try
				{
					socket = ss.accept();

					String response = "Hello " + socket.getInetAddress()
					+ " on port " 
					+ socket.getPort() 
					+ "\r\n";
					response += "This is " + socket.getLocalAddress() 
					+ " on port "
					+ socket.getLocalPort() 
					+ "\r\n";

					out = socket.getOutputStream();
					out.write(response.getBytes());
					out.flush();

				}
				catch (IOException e) {e.printStackTrace();}
				finally
				{
					if(out!=null)
					{
						try {out.close();}
						catch(IOException e) {System.err.println(e);}
					}
					if(socket!=null)
					{
						try {socket.close();}
						catch(IOException e) {System.err.println(e);}
					}
				}
			}
		}
		catch (IOException e) {System.err.println(e);}
	}
}