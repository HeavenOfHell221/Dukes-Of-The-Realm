package Duke;

import DukesOfTheRealm.Castle;
import java.util.ArrayList;

public abstract class Duke {

	protected String name;
	
	public Duke(String name)
	{
		this.name = name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}
}
