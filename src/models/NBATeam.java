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
	private int nbaTeamWins;
	private int nbaTeamLosses;
	private int nbaSeason;
	
	private Collection<NBAPlayer> players;
	
	/**
	 * Constructor sets up data
	 * @param dao
	 * @param nbaTeamID
	 * @param nbaTeamName
	 * @param teamCoach
	 * @param nbaTeamWins
	 * @param nbaTeamLosses
	 * @param nbaSeason
	 */
	public NBATeam(NBATeamDAO dao, int nbaTeamID, String nbaTeamName,
			String teamCoach, int nbaTeamWins, int nbaTeamLosses, int nbaSeason)
	{
		this.dao = dao;
		this.nbaTeamID = nbaTeamID;
		this.nbaTeamName = nbaTeamName;
		this.teamCoach = teamCoach;
		this.nbaTeamWins = nbaTeamWins;
		this.nbaTeamLosses = nbaTeamLosses;
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
	
	public int getNbaTeamWins()
	{
		return nbaTeamWins;
	}
	
	public int getNbaTeamLosses()
	{
		return nbaTeamLosses;
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
	
}
