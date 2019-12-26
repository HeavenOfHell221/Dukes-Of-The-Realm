package Goal;

import DukesOfTheRealm.Castle;
import SimpleGoal.Goal;
import SimpleGoal.SendOstGoal;

public class AttackGoal extends Goal
{
	private final GenericGoal goals;
	private final Castle castleOrigin;
	private Castle castleDestination;
	private final int nbPikers;
	private final int nbKnights;
	private final int nbOnagers;

	public AttackGoal(final Castle castleOrigin, final int nbPikers, final int nbKnights, final int nbOnagers)
	{
		this.goals = new GenericGoal();
		this.castleOrigin = castleOrigin;
		this.nbPikers = nbPikers;
		this.nbKnights = nbKnights;
		this.nbOnagers = nbOnagers;
	}

	public void setGoal(final Castle castleDestination)
	{
		this.castleDestination = castleDestination;

		int nbPikers_ = this.castleOrigin.getNbPikers() + this.castleOrigin.getNbPikersInProduction();
		int nbOnagers_ = this.castleOrigin.getNbOnagers() + this.castleOrigin.getNbOnagersInProduction();
		int nbKnights_ = this.castleOrigin.getNbKnights() + this.castleOrigin.getNbKnightsInProduction();

		int realNbPikers = nbPikers_ < this.nbPikers ? this.nbPikers - nbPikers_ : 0;
		int realNbKnights = nbKnights_ < this.nbKnights ? this.nbKnights - nbKnights_ : 0;
		int realNbOnagers = nbOnagers_ < this.nbOnagers ? this.nbOnagers - nbOnagers_ : 0;

		this.goals.addLast(new MultiSoldierGoal(this.castleOrigin, realNbPikers, realNbKnights, realNbOnagers));
		this.goals.addLast(new SendOstGoal(castleDestination, this.nbPikers, this.nbKnights, this.nbOnagers));
	}

	@Override
	public boolean goal(final Castle castleOrigin)
	{
		if (this.goals.size() == 1)
		{
			if (this.castleOrigin.getActor() == this.castleDestination.getActor())
			{
				this.goals.pollFirst();
			}

			// Si nous n'avons plus assez d'unites
			if (castleOrigin.getNbPikers() < this.nbPikers || castleOrigin.getNbKnights() < this.nbKnights
					|| castleOrigin.getNbOnagers() < this.nbOnagers)
			{
				int nbPikers_ = castleOrigin.getNbPikers() + castleOrigin.getNbPikersInProduction();
				int nbOnagers_ = castleOrigin.getNbOnagers() + castleOrigin.getNbOnagersInProduction();
				int nbKnights_ = castleOrigin.getNbKnights() + castleOrigin.getNbKnightsInProduction();

				int realNbPikers = nbPikers_ < this.nbPikers ? this.nbPikers - nbPikers_ : 0;
				int realNbKnights = nbKnights_ < this.nbKnights ? this.nbKnights - nbKnights_ : 0;
				int realNbOnagers = nbOnagers_ < this.nbOnagers ? this.nbOnagers - nbOnagers_ : 0;

				this.goals.addFirst(new MultiSoldierGoal(castleOrigin, realNbPikers, realNbKnights, realNbOnagers));
				return false;
			}
		}

		return this.goals.goal(castleOrigin);
	}

	@Override
	public String toString()
	{
		return "AttackGoal [ nbPikers= " + this.nbPikers + ", nbKnights= " + this.nbKnights + ", nbOnagers= " + this.nbOnagers + "]";
	}

}
