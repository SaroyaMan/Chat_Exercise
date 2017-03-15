package chatSystem;

public class ClientDescriptor implements StringConsumer, StringProducer
{
	private MessageBoard mb;
	
	@Override
	public void addConsumer(StringConsumer sc)
	{
		mb = (MessageBoard) sc;
	}

	@Override
	public void removeConsumer(StringConsumer sc)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void consume(String str) 
	{
		mb.consume(str);
	}
	public MessageBoard getMessageBoard() {return mb;}
}