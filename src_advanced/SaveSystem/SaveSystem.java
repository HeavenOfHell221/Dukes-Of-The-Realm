package SaveSystem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import DukesOfTheRealm.Kingdom;

/**
 * Implémente l'écriture et la lecture du royaume via un FileOutputStream.
 */
public class SaveSystem
{
	/**
	 * Sauvegarde le royaume dans un fichier prédéfini "DukesOfTheRealm.bin".
	 *
	 * @param kingdom Le royaume à sauvegarder.
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
	 * Charge une partie précédement sauvegarder.
	 *
	 * <p>
	 * Après le chargement du royaume, tous les objets possédant une méthode startTransient l'appeleront
	 * pour créer les objets transient (couleur, pane, shape..).
	 * </p>
	 *
	 * @return Le royaume chargé, ou null en cas d'erreur.
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
