import java.net.*;
import java.io.*;


public class SimpleSocketConnection
{
	public static void main(String[] args)
	{

		if (args.length != 1)
		{
			System.err
			.println("You should pass the URL address you want to browse");
		}
		else
		{
			Socket socket = null;
			OutputStream out = null;
			InputStream in = null;
			try
			{
				URL u = new URL(args[0]);
				if (!u.getProtocol().equalsIgnoreCase("http"))
				{
					System.err.println("This protocol is not supported!");
				}
				else
				{
					String host = u.getHost();
					int port = u.getPort();
					String file = u.getFile();
					// default port
					if (port <= 0) port = 80;
					socket = new Socket(host, port);
					String request = "GET "+ file + " HTTP/1.0\r\n"
							+ "User-Agent: MechaMozilla\r\nAccept: text/*\r\n\r\n";
					byte[] vec = request.getBytes();
					out = socket.getOutputStream();
					in = socket.getInputStream();
					out.write(vec);
					out.flush();
					IOUtils.streamCopy(in, System.out);
					socket.close();
				}
			}
			catch (MalformedURLException e) {System.err.println(e);}
			catch (IOException e) {System.err.println(e);}
			finally
			{
				if(out!=null)
				{
					try {out.close();}
					catch(IOException e) {}
				}
				if(in!=null)
				{
					try {in.close();}
					catch(IOException e) {}
				}
				if(socket!=null)
				{
					try {socket.close();}
					catch(IOException e){}
				}
			}
		}
	}
}