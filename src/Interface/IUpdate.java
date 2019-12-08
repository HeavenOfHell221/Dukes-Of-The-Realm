package Interface;

public interface IUpdate
{
	default void start()
	{
		
	}
	
	void update(long now, boolean pause);
}
