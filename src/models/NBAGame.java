package models;

import dao.NBAGameDAO;
import dao.NBAPlayerDAO;

public class NBAGame 
{
	private NBAGameDAO dao;
	private int nbaGameID;
	private NBATeam nbaGameAwayTeam;
	private NBATeam nbaGameHomeTeam;
	private int nbaGameAwayScore;
	private int nbaGameHomeScore;
	private int nbaGameSeason;
	
	/**
	 * Constructor
	 * @param dao
	 * @param nbaGameID
	 * @param nbaGameAwayTeam
	 * @param nbaGameHomeTeam
	 * @param nbaGameAwayScore
	 * @param nbaGameHomeScore
	 * @param nbaGameSeason
	 */
	public NBAGame(NBAGameDAO dao,int nbaGameID,NBATeam nbaGameAwayTeam,NBATeam nbaGameHomeTeam,
			int nbaGameAwayScore, int nbaGameHomeScore, int nbaGameSeason)
	{
		this.dao = dao;
		this.nbaGameID = nbaGameID;
		this.nbaGameAwayTeam = nbaGameAwayTeam;
		this.nbaGameHomeTeam = nbaGameHomeTeam;
		this.nbaGameAwayScore = nbaGameAwayScore;
		this.nbaGameHomeScore = nbaGameHomeScore;
		this.nbaGameSeason = nbaGameSeason;
	}
	
	public String toString()
	{
		return "Away Team:" + nbaGameAwayTeam.getNbaTeamName() + "(" + nbaGameAwayScore + ") Home Team: "
				+ nbaGameHomeTeam.getNbaTeamName() + "(" 
				+ nbaGameHomeScore + ") In " + nbaGameSeason ;
	}
	
	//**** Getters
	public int getId()
	{
		return nbaGameID;
	}
	
	public NBATeam getAwayTeam()
	{
		return nbaGameAwayTeam;
	}
	
	public NBATeam getHomeTeam()
	{
		return nbaGameHomeTeam;
	}
}
