package SaveSystem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import DukesOfTheRealm.Kingdom;

public class SaveSystem
{
	public static void Save(final Kingdom kingdom)
	{

		final KingdomData data = new KingdomData(kingdom);
		data.Save();

		try(ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream("save/DukesOfTheRealm.bin")))
		{
			stream.writeObject(data);
		}
		catch (final FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			System.out.println("Save completed !");
		}
	}

	public static void Load()
	{
		KingdomData kingdomData;

		try(ObjectInputStream stream = new ObjectInputStream(new FileInputStream("save/DukesOfTheRealm.bin")))
		{
			kingdomData = (KingdomData) stream.readObject();
			kingdomData.Load();
		}
		catch (final FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
		catch (final ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}