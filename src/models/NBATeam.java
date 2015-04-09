package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import dao.NBATeamDAO;

public class NBATeam 
{
	private NBATeamDAO dao;
	private int nbaTeamID;
	private String nbaTeamName;
	private String teamCoach;

	private int nbaSeason;
	
	private Collection<NBAPlayer> players;
	private Collection<NBAGame> totalgames;
	
	/**
	 * Constructor sets up data
	 * @param dao
	 * @param nbaTeamID
	 * @param nbaTeamName
	 * @param teamCoach

	 * @param nbaSeason
	 */
	public NBATeam(NBATeamDAO dao, int nbaTeamID, String nbaTeamName,
			String teamCoach, int nbaSeason)
	{
		this.dao = dao;
		this.nbaTeamID = nbaTeamID;
		this.nbaTeamName = nbaTeamName;
		this.teamCoach = teamCoach;
		this.nbaSeason = nbaSeason;
	}
	
	/* Getters */
	public int getNbaTeamID()
	{
		return nbaTeamID;
	}
	
	public String getTeamCoach()
	{
		return teamCoach;
	}
	
	public int getNbaSeason()
	{
		return nbaSeason;
	}
	
	public String getNbaTeamName()
	{
		return nbaTeamName;
	}
	
	public String toString()
	{
		return "Team:" + nbaTeamName + ",Coach:" + teamCoach + ",Season:" + nbaSeason; 
	}
	
	public Collection<NBAPlayer> getPlayers()
	{
		if (players == null) players = dao.getNBAPlayers(nbaTeamID);
		return players;
	}
	
	public Collection <NBAGame> getGames()
	{
		if(totalgames == null) totalgames = dao.getNBAGames(nbaTeamID);
		return totalgames;
	}
}
