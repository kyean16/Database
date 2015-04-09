package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import models.NBAGame;
import models.NBAPlayer;
import models.NBATeam;

public class NBAGameDAO {
	
	private Connection conn;
	private DatabaseManager dbm;
	
	private Map<Integer, NBAGame> cache;
	
	public NBAGameDAO(Connection conn, DatabaseManager dbm) {
		this.conn = conn;
		this.dbm = dbm;
		this.cache = new HashMap<Integer, NBAGame>();
	}
	
	/**
	 * Create the NBAGame table via SQL
	 * 
	 * @param conn
	 * @throws SQLException
	 */
	static void create(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		String s = "create table NBAGAME("
				+ "NbaGameID int, "
				+ "NbaGameAwayTeam int, "
				+ "NbaGameHomeTeam int, "
				+ "NbaGameAwayScore int, "
				+ "NbaGameHomeScore int,"
				+ "NbaGameSeason int,"
				+ "primary key(NbaGameID))";
		stmt.executeUpdate(s);
		System.out.println("NBAGAME created");
	}
	
	public NBAGame insert(int nbaGameID, NBATeam nbaGameAwayTeam, NBATeam nbaGameHomeTeam, int nbaGameAwayScore,
			int nbaGameHomeScore,int nbaGameSeason) {
		try {
			
			String cmd = "insert into NBAGAME(NbaGameID,"
					+ " NbaGameAwayTeam,"
					+ " NbaGameHomeTeam,"
					+ " NbaGameAwayScore, "
					+ " NbaGameHomeScore,"
					+ " NbaGameSeason)"
					+ "values(?, ?, ?, ?, ? ,?)";
			PreparedStatement pstmt = conn.prepareStatement(cmd);
			pstmt.setInt(1, nbaGameID);
			pstmt.setInt(2, nbaGameAwayTeam.getNbaTeamID());
			pstmt.setInt(3, nbaGameHomeTeam.getNbaTeamID());
			pstmt.setInt(4, nbaGameAwayScore);
			pstmt.setInt(5,nbaGameHomeScore);
			pstmt.setInt(6,nbaGameSeason);
			pstmt.executeUpdate();

			NBAGame nbaGame = new NBAGame(this,nbaGameID,nbaGameAwayTeam, nbaGameHomeTeam, nbaGameAwayScore, nbaGameHomeScore,
					nbaGameSeason);
			cache.put(nbaGameID, nbaGame);
			return nbaGame;
		} catch (SQLException e) {
			dbm.cleanup();
			throw new RuntimeException("error inserting new Game", e);
		}
	}
	
	public NBAGame findByID(int id)
	{
		try {
			String qry = "select * from NBAGAME where NbaGameID = ?";
			PreparedStatement pstmt = conn.prepareStatement(qry);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			// return null if faculty member doesn't exist
			if (!rs.next())
				return null;

			int teamID = rs.getInt("NbaGameID");
			int awayTeam = rs.getInt("NbaGameAwayTeam");
			int homeTeam = rs.getInt("NbaGameHomeTeam");
			int awayScore = rs.getInt("NbaGameAwayScore");
			int homeScore = rs.getInt("NbaGameHomeScore");
			int season = rs.getInt("nbaGameSeason");
			
			rs.close();
			
			NBATeam away = dbm.findNBATeambyID(awayTeam);
			NBATeam home = dbm.findNBATeambyID(homeTeam);
			
			NBAGame game = new NBAGame(this,teamID, away,home, awayScore,homeScore,season);

			return game;
		} catch (SQLException e) {
			dbm.cleanup();
			throw new RuntimeException("error finding Game by id", e);
		}
	}
	
	
	void clear() throws SQLException {
		Statement stmt = conn.createStatement();
		String s = "delete from NBAGAME";
		stmt.executeUpdate(s);
		cache.clear();
	}

}
