import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils
{
	public static void streamCopy(InputStream in, OutputStream out) throws IOException
	{
		synchronized(out)
		{
			synchronized(in)
			{
				byte vec[] = new byte[256];
				int numOfBytes = in.read(vec);
				while(numOfBytes!=-1)
				{
					out.write(vec,0,numOfBytes);
					numOfBytes = in.read(vec);
				}
			}
		}
	}
}