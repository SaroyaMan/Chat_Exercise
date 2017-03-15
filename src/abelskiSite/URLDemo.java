package abelskiSite;
import java.io.*;
import java.net.*;

class URLDemo
{
	public static void main(String args[])
	{
		InputStream is = null;
		try
		{
			URL ob = new URL("https://www.yahoo.com/");
			is = ob.openStream();
			int temp = is.read();
			while(temp!=-1)		//print all the source page of yahoo.com
			{
				System.out.print((char)temp);
				temp = is.read();
			}
		}
		catch(IOException e) {e.printStackTrace();}
		finally
		{
			if (is!=null)
			{
				try {is.close();}
				catch(IOException e) {e.printStackTrace();}
			}
		}
	}
}