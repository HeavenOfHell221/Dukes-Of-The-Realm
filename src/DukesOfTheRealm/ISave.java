package DukesOfTheRealm;

public interface ISave<T> {

	void ReceivedDataSave(T o);
	void SendingDataSave(T o);
}