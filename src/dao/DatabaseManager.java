package dao;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Properties;

import models.*;

import org.apache.derby.jdbc.EmbeddedDriver;


public class DatabaseManager {
	
	private Driver driver;
	private Connection conn;
	
	private NBATeamDAO nbaTeamDAO;
	private NBAPlayerDAO nbaPlayerDAO;
	private NBAGameDAO nbaGameDAO;
	private NBAGameLogDAO  nbaGameLogDAO;
	
	private final String url = "jdbc:derby:NBA";

	/**
	 * Open Database
	 */
	public DatabaseManager() 
	{
		driver = new EmbeddedDriver();
		
		Properties prop = new Properties();
		prop.put("create", "false");
		
		// try to connect to an existing database
		try {
			conn = driver.connect(url, prop);
			conn.setAutoCommit(false);
		}
		catch(SQLException e) {
			// database doesn't exist, so it tries to create it
			try {
				prop.put("create", "true");
				conn = driver.connect(url, prop);
				conn.setAutoCommit(false);
				create(conn);
				
			}
			catch (SQLException e2) {
				throw new RuntimeException("Error: Cannot Connect to Database", e2);
			}
		}
		nbaTeamDAO = new NBATeamDAO(conn, this);
		nbaPlayerDAO = new NBAPlayerDAO(conn,this);
		nbaGameDAO = new NBAGameDAO(conn,this);
		nbaGameLogDAO = new NBAGameLogDAO(conn,this);
		
		System.out.println("Derby has open sucessfully");
	}
	/**
	 * Create Tables for the following classes.
	 * @param conn
	 * @throws SQLException
	 */
	private void create(Connection conn) throws SQLException 
	{
		NBATeamDAO.create(conn);
		NBAPlayerDAO.create(conn);
		NBAPlayerDAO.addConstraints(conn);
		NBAGameDAO.create(conn);
		NBAGameDAO.addConstraints(conn);
		NBAGameLogDAO.create(conn);
		NBAGameLogDAO.addConstraints(conn);
		conn.commit();
	}
	//*****************************************************************
		//Insert Function
	public NBATeam insertNBATeam(int nbaTeamID, String nbaTeamName, String teamCoach, int nbaSeason) {
		return nbaTeamDAO.insert(nbaTeamID, nbaTeamName, teamCoach,nbaSeason);
	}
	
	public NBAPlayer insertNBAPlayer(int nbaPlayerID, String nbaPlayerName, NBATeam nbaPlayerTeamID, 
			int nbaPlayerSalary, String nbaPlayerHealthStatus, int nbaPlayerAge, 
			String nbaPlayerHometown, String nbaPlayerPosition){
		return nbaPlayerDAO.insert(nbaPlayerID,  nbaPlayerName, nbaPlayerTeamID,
				nbaPlayerSalary, nbaPlayerHealthStatus, nbaPlayerAge, nbaPlayerHometown, nbaPlayerPosition);
	}
	
	public NBAGame insertNBAGame(int nbaGameID, NBATeam nbaGameAwayTeam, NBATeam nbaGameHomeTeam, int nbaGameAwayScore,
			int nbaGameHomeScore,int nbaGameSeason){
		return nbaGameDAO.insert(nbaGameID, nbaGameAwayTeam, nbaGameHomeTeam,nbaGameAwayScore,
				nbaGameHomeScore,nbaGameSeason);
	}
	
	public GameLog insertNBALog(NBAGame nbaGameID, NBAPlayer nbaPlayerID, int nbaGameLogID, int nbaGameLogPoints,
			int nbaGameLogRebounds, int nbaGameLogAssists, int nbaGameSteals, int nbaGameFouls, int nbaGameLogMinutes){
		return nbaGameLogDAO.insert(nbaGameID, nbaPlayerID, nbaGameLogID, nbaGameLogPoints,
				nbaGameLogRebounds, nbaGameLogAssists, nbaGameSteals, nbaGameFouls, nbaGameLogMinutes);
	}
	
	//****************************************************************
		//Get Function
	public int getNBATeams() //Get All Teams
	{
		return nbaTeamDAO.getTeamNumber();
	}
	
	public Collection<NBATeam> findTeamLessThanEqualTo(int maxnum)
	{
		return nbaTeamDAO.getTeamNumberPlayer(maxnum);
	}
	//****************************************************************
		//Find Function
	public NBATeam findNBATeamByName(String name) {
		return nbaTeamDAO.findByName(name);
	}

	public NBATeam findNBATeambyID(int id){
		return nbaTeamDAO.findByID(id);
	}
	
	public NBAPlayer findNBAPlayer(int id) {
		return nbaPlayerDAO.findbyID(id);
	}

	public NBAGame findNBAGame(int id){
		return nbaGameDAO.findByID(id);
	}
	
	public GameLog findGame(int id){
		return nbaGameLogDAO.findByID(id);
	}
	
	//***************************************************************
		// Utility functions
		/**
		 * Commit changes since last call to commit
		 */
		public void commit() {
			try {
				conn.commit();
			}
			catch(SQLException e) {
				throw new RuntimeException("Error:Cannot commit to database", e);
			}
		}
		/**
		 * Abort changes since last call to commit, then close connection
		 */
		public void cleanup() {
			try {
				conn.rollback();
				conn.close();
			}
			catch(SQLException e) {
				System.out.println("fatal error: cannot cleanup connection");
			}
		}
		/**
		 * Close connection and shutdown database
		 */
		public void close() {
			try {
				conn.close();
			}
			catch(SQLException e) {
				throw new RuntimeException("cannot close database connection", e);
			}
			// Now shutdown the embedded database system -- this is Derby-specific
			try {
				Properties prop = new Properties();
				prop.put("shutdown", "true");
				conn = driver.connect(url, prop);
			} catch (SQLException e) {
				// This is supposed to throw an exception...
				System.out.println();
				System.out.println("Derby has shut down successfully");
			}
		}
		/**
		 * Clear out all data from database (but leave empty tables)
		 */
		public void clearTables() {
			try {
				// This is not as straightforward as it may seem, because
				// of the cyclic foreign keys -- I had to play with
				// "on delete set null" and "on delete cascade" for a bit
				//Careful of Order
				nbaGameLogDAO.clear();
				nbaGameDAO.clear();
				nbaPlayerDAO.clear();
				nbaTeamDAO.clear();
				
			} catch (SQLException e) {
				throw new RuntimeException("Error: Cannot clear tables", e);
			}
		}
}
