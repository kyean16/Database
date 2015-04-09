package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import models.NBAGame;
import models.NBAPlayer;
import models.NBATeam;

public class NBATeamDAO 
{
	private Connection conn;
	private DatabaseManager dbm;

	public NBATeamDAO(Connection conn, DatabaseManager dbm) {
		this.conn = conn;
		this.dbm = dbm;
	}
	
	/**
	 * Create table
	 * @param conn
	 * @throws SQLException
	 */
	static void create(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		
		String s = "create table NBATEAM(" + "NbaTeamID int, " + "NbaTeamName varchar(30)," + "TeamCoach varchar(30), "
				+ "nbaSeason int,"
				+ "primary key(NbaTeamID))";
		stmt.executeUpdate(s);
		System.out.println("NBATEAM created");
	}
	
	/**
	 * Insert nbaTeam to table
	 * @param nbaTeamID
	 * @param teamName
	 * @param teamCoach
	 * @param nbaSeason
	 * @return
	 */
	public NBATeam insert(int nbaTeamID, String teamName, String teamCoach, int nbaSeason) {
		try {
			
			String cmd = "insert into NBATEAM(NbaTeamID,NbaTeamName, TeamCoach, nbaSeason) "
					+ "values(?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(cmd);
			pstmt.setInt(1, nbaTeamID);
			pstmt.setString(2, teamName);
			pstmt.setString(3, teamCoach);
			pstmt.setInt(4,nbaSeason);
			pstmt.executeUpdate();

			NBATeam nbaTeam = new NBATeam(this,nbaTeamID,teamName, teamCoach,nbaSeason);
			return nbaTeam;
		} catch (SQLException e) {
			dbm.cleanup();
			throw new RuntimeException("error inserting new Team", e);
		}
	}

	/**
	 * Deletes all the content of the Table
	 * @throws SQLException
	 */
	void clear() throws SQLException {
		Statement stmt = conn.createStatement();
		String s = "delete from NBATEAM";
		stmt.executeUpdate(s);
	}
	//********************************************************Find
	 
	/**
	 * Retrieve a NBATeam object by name. 
	 * 
	 * @param name
	 * @return the NBATeam object, or null if not found
	 */
	public NBATeam findByName(String name) {
		try {
			String qry = "select * from NBATEAM where NbaTeamName = ?";
			PreparedStatement pstmt = conn.prepareStatement(qry);
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();

			// return null if faculty member doesn't exist
			if (!rs.next())
				return null;

			int id = rs.getInt("NbaTeamID");
			String nbaName = rs.getString("NbaTeamName");
			String nbaCoach = rs.getString("TeamCoach");
			int season = rs.getInt("NbaSeason");
			
			rs.close();
			
			NBATeam team = new NBATeam(this,id, nbaName, nbaCoach,season);

			return team;
		} catch (SQLException e) {
			dbm.cleanup();
			throw new RuntimeException("error finding department by name", e);
		}
	}
	
	/**Retrieve a NBATeam object by ID 
	 * 
	 * @param id
	 * @return
	 */
	public NBATeam findByID(int id) {
		try {
			String qry = "select * from NBATEAM where NbaTeamID = ?";
			PreparedStatement pstmt = conn.prepareStatement(qry);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			// return null if faculty member doesn't exist
			if (!rs.next())
				return null;

			int teamID = rs.getInt("NbaTeamID");
			String nbaName = rs.getString("NbaTeamName");
			String nbaCoach = rs.getString("TeamCoach");
			int season = rs.getInt("NbaSeason");
			
			rs.close();
			
			NBATeam team = new NBATeam(this,teamID, nbaName, nbaCoach,season);

			return team;
		} catch (SQLException e) {
			dbm.cleanup();
			throw new RuntimeException("error finding team by id", e);
		}
	}
	
	//****************************Get
	public Collection<NBAPlayer> getNBAPlayers(int teamNameID) {
		try {
			Collection<NBAPlayer> player = new ArrayList<NBAPlayer>();
			String qry = "select nbaPlayerID from NBAPLAYER where nbaPlayerTeamID = ?";
			PreparedStatement pstmt = conn.prepareStatement(qry);
			pstmt.setInt(1, teamNameID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("nbaPlayerID");
				player.add(dbm.findNBAPlayer(id));
			}
			rs.close();
			return player;
		}
		catch(SQLException e) {
			dbm.cleanup();
			throw new RuntimeException("error getting Team Players", e);
		}
	}
	
	public Collection <NBAGame> getNBAGames(int id)
	{
		try{
			Collection<NBAGame> game = new ArrayList<NBAGame>();
			String qry = "select NbaGameID from NBAGAME where NbaGameAwayTeam =? OR NbaGameHomeTeam =?";
			PreparedStatement pstmt = conn.prepareStatement(qry);
			pstmt.setInt(1,id);
			pstmt.setInt(2,id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				int gameId = rs.getInt("nbaGameID");
				game.add(dbm.findNBAGame(gameId));
			}
			rs.close();
			return game;
		}
		catch(SQLException e){
			dbm.cleanup();
			throw new RuntimeException("error getting Team Games");
		}
	}
	
	public int getTeamNumber()
	{
		try{
			
		String qry = "select * from NBATEAM";
		PreparedStatement pstmt = conn.prepareStatement(qry);
		ResultSet rs = pstmt.executeQuery();
		int rowCount = 0;
		while ( rs.next() )
		{
		    // Process the row.
		    rowCount++;
		}
		rs.close();
		return rowCount;
	} catch (SQLException e) {
		dbm.cleanup();
		throw new RuntimeException("error ", e);
	}
}
}
