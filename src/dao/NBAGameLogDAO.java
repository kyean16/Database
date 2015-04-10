package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import models.*;

public class NBAGameLogDAO {
	
	private Connection conn;
	private DatabaseManager dbm;
	
	private Map<Integer, GameLog> cache;
	
	public NBAGameLogDAO(Connection conn, DatabaseManager dbm) {
		this.conn = conn;
		this.dbm = dbm;
		this.cache = new HashMap<Integer, GameLog>();
	}
	
	/**
	 * Create the NBAGameLogDAO table via SQL
	 * 
	 * @param conn
	 * @throws SQLException
	 */
	static void create(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		String s = "create table GAMELOG("
				+ "NbaGameID int not null, "
				+ "NbaPlayer int not null, "
				+ "NbaGameLogID int not null, "
				+ "NbaGameLogPoints int not null, "
				+ "NbaGameLogRebounds int not null,"
				+ "NbaGameLogAssists int not null,"
				+ "NbaGameLogSteals int not null,"
				+ "NbaGameLogFouls int not null,"
				+ "NbaGameLogMinutes int not null,"
				+ "check (NbaGameLogFouls <= 6 AND NbaGameLogFouls >= 0),"
				+ "primary key(NbaGameLogID))";
		stmt.executeUpdate(s);
	}
	
	static void addConstraints(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		String s = "alter table GAMELOG add constraint fk_LogGame "
				+ "foreign key(NbaGameID) references NBAGame";
		stmt.executeUpdate(s);
		s = "alter table GAMELOG add constraint fk_LogPlayer "
				+ "foreign key(NbaPlayer) references NBAPlayer";
		stmt.executeUpdate(s);
		System.out.println("GAMELOG created");
	}
	
	/**
	 * Insert Game Log to Table
	 * @param nbaGameID
	 * @param nbaPlayerID
	 * @param nbaGameLogID
	 * @param nbaGameLogPoints
	 * @param nbaGameLogRebounds
	 * @param nbaGameLogAssists
	 * @param nbaGameLogSteals
	 * @param nbaGameLogFouls
	 * @param nbaGameLogMinutes
	 * @return
	 */
	public GameLog insert(NBAGame nbaGameID, NBAPlayer nbaPlayerID, int nbaGameLogID, int nbaGameLogPoints,
	int nbaGameLogRebounds, int nbaGameLogAssists, int nbaGameLogSteals, int nbaGameLogFouls, int nbaGameLogMinutes) {
		try {
			
			String cmd = "insert into GAMELOG("
					+ " NbaGameID,"
					+ " NbaPlayer,"
					+ " NbaGameLogID,"
					+ " NbaGameLogPoints,"
					+ " NbaGameLogRebounds,"
					+ " NbaGameLogAssists,"
					+ " NbaGameLogSteals,"
					+ " NbaGameLogFouls,"
					+ " NbaGameLogMinutes)"
					+ "values(?, ?, ?, ?, ? ,?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(cmd);
			pstmt.setInt(1, nbaGameID.getId());
			pstmt.setInt(2, nbaPlayerID.getID());
			pstmt.setInt(3, nbaGameLogID);
			pstmt.setInt(4, nbaGameLogPoints);
			pstmt.setInt(5,nbaGameLogRebounds);
			pstmt.setInt(6,nbaGameLogAssists);
			pstmt.setInt(7, nbaGameLogSteals);
			pstmt.setInt(8, nbaGameLogFouls);
			pstmt.setInt(9, nbaGameLogMinutes);
			pstmt.executeUpdate();

			GameLog gameLog = new GameLog(this,nbaGameID, nbaPlayerID, nbaGameLogID, nbaGameLogPoints,
					nbaGameLogRebounds, nbaGameLogAssists, nbaGameLogSteals , nbaGameLogFouls, nbaGameLogMinutes);
			cache.put(nbaGameLogID, gameLog);
			return gameLog;
		} catch (SQLException e) {
			dbm.cleanup();
			throw new RuntimeException("error inserting new Game", e);
		}
	}
	
	/**
	 * Find GameLog ny ID
	 * @param id
	 * @return
	 */
	public GameLog findByID(int id)
	{
		if (cache.containsKey(id)) return cache.get(id);
		
		try {
			String qry = "select * "        //Select all gamelogs with the same gameLogIn
						+"from GAMELOG "
						+"where NbaGameLogID = ?";
			PreparedStatement pstmt = conn.prepareStatement(qry);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			// return null if GameLog doesn't exist
			if (!rs.next())
				return null;
			
			int nbaGameID = rs.getInt("NbaGameID");
			int nameID = rs.getInt("NbaPlayer");
			int gameLogID = rs.getInt("NbaGameLogID");
			int points = rs.getInt("NbaGameLogPoints");
			int rebounds = rs.getInt("NbaGameLogRebounds");
			int assists = rs.getInt("NbaGameLogAssists");
			int steals = rs.getInt("NbaGameLogSteals");
			int fouls = rs.getInt("NbaGameLogFoul");
			int minutes = rs.getInt("NbaGameLogMinutes");
			rs.close();

			NBAGame game = dbm.findNBAGame(nbaGameID);
			NBAPlayer player = dbm.findNBAPlayer(nameID);
			
			GameLog log = new GameLog(this,game,player,gameLogID,points,rebounds,assists,steals,fouls,minutes);
			
			cache.put(id, log);
			return log;
		} catch (SQLException e) {
			dbm.cleanup();
			throw new RuntimeException("error finding GameLog", e);
		}
	}
	
	void clear() throws SQLException {
		Statement stmt = conn.createStatement();
		String s = "delete from GAMELOG";
		stmt.executeUpdate(s);
		cache.clear();
	}


}
