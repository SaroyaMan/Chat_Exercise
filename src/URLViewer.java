import java.io.*;
import java.net.*;

public class URLViewer
{
	public static void main(String args[])
	{
		if(args.length!=1)
		{
			System.out.println("You should pass the URL address you want to view...");
		}
		else
		{
			InputStream in = null;
			try
			{
				URL url = new URL(args[0]);
				in = url.openStream();
				IOUtils.streamCopy(in,System.out);
			}
			catch(IOException e) {e.printStackTrace();}
			finally
			{
				if(in!=null)
				{
					try {in.close();}
					catch(IOException e) {e.printStackTrace();}
				}
			}
		}
	}
}