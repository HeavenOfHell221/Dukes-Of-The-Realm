package SaveSystem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import DukesOfTheRealm.Kingdom;

/**
 *
 *
 */
public class SaveSystem
{
	/**
	 *
	 * @param kingdom
	 */
	public static void save(final Kingdom kingdom)
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

	/**
	 *
	 * @return
	 */
	public static Kingdom load()
	{
		Kingdom kingdom = null;
		try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream("save/DukesOfTheRealm.bin")))
		{
			kingdom = (Kingdom) stream.readObject();
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
