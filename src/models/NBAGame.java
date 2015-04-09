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
				+ nbaGameHomeScore + ")";
	}
}
