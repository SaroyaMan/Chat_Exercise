import java.io.*;
import java.net.*;
import java.util.*;

public class LottoNumberServer
{
	public static int serverPortNumber = 8070;
	
	public static void main(String[] args)
	{
		int nums[] = new int[6];
		for (int i=0; i<nums.length; i++)
		{
			nums[i] = randInt(0,42);
		}
		Socket socket = null;
		ServerSocket server = null;
		OutputStream os = null;
		DataOutputStream dos = null;
		try
		{
			server = new ServerSocket(serverPortNumber);
			while (true)
			{
				System.out.println("server.accept()...");
				socket = server.accept();
				os = socket.getOutputStream();
				dos = new DataOutputStream(os);
				dos.writeUTF(Arrays.toString(nums));
			}
		}
		catch(IOException e) {e.printStackTrace();}
		finally
		{
			if (dos != null)
			{
				try {dos.close();}
				catch (IOException e) {e.printStackTrace();}
				
			}
			if (socket != null)
			{
				try {socket.close();}
				catch (IOException e) {e.printStackTrace();}
			}
		}
	}
	
	public static int randInt(int min, int max)
	{
	    // Usually this can be a field rather than a method variable
	    Random rand = new Random();
	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
}