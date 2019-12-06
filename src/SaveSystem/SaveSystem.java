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
		try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream("save/DukesOfTheRealm.bin")))
		{
			stream.writeObject(kingdom);
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

	public static Kingdom Load()
	{
		Kingdom kingdom = null;
		try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream("save/DukesOfTheRealm.bin")))
		{
			kingdom = (Kingdom) stream.readObject();
			if (kingdom != null)
			{
				System.out.println("Load completed !");
			}
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
		return kingdom;
	}
}
