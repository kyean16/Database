package models;

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
	
	public String toString()
	{
		return "Player: " + nbaPlayerName;
	}

}
