package DukesOfTheRealm;

public interface ISave<T> {

	public void ReceivedDataSave(T o);
	public void SendingDataSave(T o);
}