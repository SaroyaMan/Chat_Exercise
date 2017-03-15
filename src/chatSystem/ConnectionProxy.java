package chatSystem;

import java.io.*;
import java.net.Socket;

public class ConnectionProxy extends Thread implements StringConsumer, StringProducer
{
	private Socket socket;
	private ClientDescriptor cd;
	private ClinetGUI gui;
	private OutputStream os;
	private DataOutputStream dos;
	private InputStream is;
	private DataInputStream dis;
	
	public ConnectionProxy(Socket s)
	{
		this.socket = s;
		try
		{
			is = socket.getInputStream();
			dis = new DataInputStream(is);
			os = socket.getOutputStream();
			dos = new DataOutputStream(os);
		}
		catch(IOException e) {e.printStackTrace();}
	}
	
	@Override
	public void addConsumer(StringConsumer sc)
	{
		if (sc instanceof ClientDescriptor)
		{
			cd = (ClientDescriptor) sc;
		}
		if (sc instanceof ClinetGUI)
		{
			gui = (ClinetGUI) sc;
		}
	}

	@Override
	public void removeConsumer(StringConsumer sc)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void consume(String str)
	{
		try
		{
			dos.writeUTF(str);
		}
		catch (IOException e)
		{
			cd.getMessageBoard().removeConsumer(this);
		}
	}
	
	@Override
	public void run()
	{
		while (true)
		{
			try
			{
				String s = dis.readUTF();
				if (gui!= null) gui.consume(s);
				if (cd!= null) cd.consume(": " + s);
			}
			catch (IOException e)
			{
				if (dis != null)
				{
					try
					{
						dis.close();
					}
					catch(IOException ex) {e.printStackTrace();}
				}
				if (dos != null)
				{
					try
					{
						dos.close();
					}
					catch(IOException ex) {e.printStackTrace();}
				}
				
				if (socket != null)
				{
					try
					{
						socket.close();
					}
					catch(IOException ex) {e.printStackTrace();}
				}
			}
		}
	}
}