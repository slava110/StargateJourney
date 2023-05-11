package net.povstalec.sgjourney.common.factions;

public abstract class Faction
{
	// TODO
	// Faction Name
	protected String factionName;
	// Faction Leader
	protected FactionLeader leader;
	// Faction Territory
	// Faction Strength
	
	// Player relations
	
	
	public Faction(String factionName)
	{
		this.factionName = factionName;
	}
	
	//============================================================================================
	//************************************Getters and setters*************************************
	//============================================================================================
	
	public String getName()
	{
		return this.factionName;
	}
	
	//============================================================================================
	//*******************************************Logic********************************************
	//============================================================================================
	
	public abstract void onLeaderDeath();
	
	/*
	 * When the Faction Leader dies, the following can happen to the Faction:
	 * 1) Faction gets a new Leader
	 * 2) Faction falls apart
	 * 3) Faction gets absorbed into another Faction
	 */
}
