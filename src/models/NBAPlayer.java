package models;

import java.util.Collection;

import dao.NBAPlayerDAO;


public class NBAPlayer 
{
	private NBAPlayerDAO dao;
	private int nbaPlayerID;
	private String nbaPlayerName;
	private NBATeam nbaPlayerTeamID;
	private int nbaPlayerSalary;
	private String nbaPlayerHealthStatus;
	private int nbaPlayerAge;
	private String nbaPlayerHometown;
	private String nbaPlayerPosition;
	
	private Collection<GameLog> logs;
	
	public NBAPlayer(NBAPlayerDAO dao, int nbaPlayerID, String nbaPlayerName, NBATeam nbaPlayerTeamID, 
			int nbaPlayerSalary, String nbaPlayerHealthStatus, int nbaPlayerAge, 
			String nbaPlayerHometown, String nbaPlayerPosition)
	{
		this.dao = dao;
		this.nbaPlayerID = nbaPlayerID;
		this.nbaPlayerName = nbaPlayerName;
		this.nbaPlayerTeamID = nbaPlayerTeamID;
		this.nbaPlayerSalary = nbaPlayerSalary;
		this.nbaPlayerHealthStatus = nbaPlayerHealthStatus;
		this.nbaPlayerAge = nbaPlayerAge;
		this.nbaPlayerHometown = nbaPlayerHometown;
		this.nbaPlayerPosition = nbaPlayerPosition;
		
	}
	
	//**************Getters
	public String getName()
	{
		return nbaPlayerName;
	}
	
	public int getID()
	{
		return nbaPlayerID;
	}
	
	public NBATeam getTeamID()
	{
		return nbaPlayerTeamID;
	}
	
	public int getAge()
	{
		return nbaPlayerAge;
	}
	
	public String toString()
	{
		return "Player: " + nbaPlayerName;
	}

	public Collection<GameLog> findAgainstLogs(NBATeam against)
	{
		if(logs == null) logs = dao.getNBAGamesPlayedAgainst(against, this);
		return logs;
	}
}
