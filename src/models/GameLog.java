package models;

import dao.NBAGameLogDAO;

public class GameLog {
	
	  private NBAGameLogDAO dao;
	  private NBAGame nbaGameID;
	  private NBAPlayer nbaPlayerID;
	  private int gameLogID;
	  private int gamePoints;
	  private int gameRebounds;
	  private int gameAssists;
	  private int gameSteals;
	  private int gameFouls;
	  private int gameMinutes;
	  
	  public GameLog(NBAGameLogDAO dao,NBAGame nbaGameID,NBAPlayer nbaPlayerID, int gameLogID, int gamePoints,
			  int gameRebounds, int gameAssists,int gameSteals, int gameFouls, int gameMinutes)
			  {
		  		this.dao = dao;
		  		this.nbaGameID = nbaGameID;
		  		this.nbaPlayerID = nbaPlayerID;
		  		this.gameLogID = gameLogID;
		  		this.gamePoints = gamePoints;
		  		this.gameRebounds = gameRebounds;
		  		this.gameAssists = gameAssists;
		  		this.gameSteals = gameSteals;
		  		this.gameFouls = gameFouls;
		  		this.gameMinutes = gameMinutes;
			  }
	  
	  public String toString()
	  {
		  return "Player:" +nbaPlayerID.getName() + ";Minutes:" + gameMinutes + ";Points:" + gamePoints;
	  }
}
