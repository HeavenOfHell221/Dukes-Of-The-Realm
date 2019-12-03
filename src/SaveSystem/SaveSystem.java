package SaveSystem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import DukesOfTheRealm.Kingdom;
import DukesOfTheRealm.Main;

public class SaveSystem 
{
	public static void Save(final Kingdom kingdom)
	{	
		
		KingdomData data = new KingdomData(kingdom);
		data.Save();
		
		try(ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream("save/DukesOfTheRealm.bin")))
		{
			stream.writeObject(data);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
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
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
}