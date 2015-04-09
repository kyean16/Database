package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
				+ "nbaTeamWins int, " + "nbaTeamLosses int, " + "nbaSeason int,"
				+ "primary key(NbaTeamID))";
		stmt.executeUpdate(s);
		System.out.println("NBATEAM created");
	}
	
	/**
	 * Insert nbaTeam to table
	 * @param nbaTeamID
	 * @param teamName
	 * @param teamCoach
	 * @param nbaTeamWins
	 * @param nbaTeamLosses
	 * @param nbaSeason
	 * @return
	 */
	public NBATeam insert(int nbaTeamID, String teamName, String teamCoach, int nbaTeamWins,int nbaTeamLosses, int nbaSeason) {
		try {
			
			String cmd = "insert into NBATEAM(NbaTeamID,NbaTeamName, TeamCoach, nbaTeamWins, nbaTeamLosses, nbaSeason) "
					+ "values(?, ?, ?, ?, ? ,?)";
			PreparedStatement pstmt = conn.prepareStatement(cmd);
			pstmt.setInt(1, nbaTeamID);
			pstmt.setString(2, teamName);
			pstmt.setString(3, teamCoach);
			pstmt.setInt(4, nbaTeamWins);
			pstmt.setInt(5,nbaTeamLosses);
			pstmt.setInt(6,nbaSeason);
			pstmt.executeUpdate();

			NBATeam nbaTeam = new NBATeam(this,nbaTeamID,teamName, teamCoach, nbaTeamWins,nbaTeamLosses,nbaSeason);
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
			int wins = rs.getInt("NbaTeamWins");
			int losses = rs.getInt("NbaTeamlosses");
			int season = rs.getInt("NbaSeason");
			
			rs.close();
			
			NBATeam team = new NBATeam(this,id, nbaName, nbaCoach,wins,losses,season);

			return team;
		} catch (SQLException e) {
			dbm.cleanup();
			throw new RuntimeException("error finding department by name", e);
		}
	}
}
