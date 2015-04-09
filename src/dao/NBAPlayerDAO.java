package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;


import models.NBAPlayer;
import models.NBATeam;


public class NBAPlayerDAO 
{
	private Connection conn;
	private DatabaseManager dbm;
	
	private Map<Integer, NBAPlayer> cache;

	public NBAPlayerDAO(Connection conn, DatabaseManager dbm) {
		this.conn = conn;
		this.dbm = dbm;
		this.cache = new HashMap<Integer, NBAPlayer>();
	}
	
	/**
	 * Create the NBAPlayer table via SQL
	 * 
	 * @param conn
	 * @throws SQLException
	 */
	static void create(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		String s = "create table NBAPLAYER("
				+ "NbaPlayerID int, "
				+ "NbaPlayerName varchar(30) not null, "
				+ "NbaPlayerTeamID int not null, "
				+ "NbaPlayerSalary int, "
				+ "NbaPlayerHealthStatus varchar(30),"
				+ "NbaPlayerAge int,"
				+ "NbaPlayerHometown varchar(30),"
				+ "NbaPlayerPosition varchar(30),"
				+ "primary key(NbaPlayerID))";
		stmt.executeUpdate(s);
		System.out.println("NBAPLAYER created");
	}
	static void addConstraints(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		String s = "alter table NBAPLAYER add constraint fk_facdept "
				+ "foreign key(NbaPlayerTeamID) references NBATEAM ";
		stmt.executeUpdate(s);
	}
	
	public NBAPlayer insert(int nbaPlayerID, String nbaPlayerName, NBATeam nbaPlayerTeamID, int nbaPlayerSalary,
			String nbaPlayerHealthStatus,int nbaPlayerAge, String nbaPlayerHomeTown, String nbaPlayerPosition) {
		try {
			
			String cmd = "insert into NBAPlayer(NbaPlayerID,NbaPlayerName, NbaPlayerTeamID, NbaPlayerSalary, NbaPlayerHealthStatus,"
					+ " NbaPlayerAge,"
					+ " NbaPlayerHometown,"
					+ " NbaPlayerPosition) "
					+ "values(?, ?, ?, ?, ? ,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(cmd);
			pstmt.setInt(1, nbaPlayerID);
			pstmt.setString(2, nbaPlayerName);
			pstmt.setInt(3, nbaPlayerTeamID.getNbaTeamID());
			pstmt.setInt(4, nbaPlayerSalary);
			pstmt.setString(5,nbaPlayerHealthStatus);
			pstmt.setInt(6,nbaPlayerAge);
			pstmt.setString(7,nbaPlayerHomeTown);
			pstmt.setString(8,nbaPlayerPosition);
			pstmt.executeUpdate();

			NBAPlayer nbaPlayer = new NBAPlayer(this,nbaPlayerID, nbaPlayerName, nbaPlayerTeamID, nbaPlayerSalary,
					nbaPlayerHealthStatus, nbaPlayerAge, nbaPlayerHomeTown, nbaPlayerPosition);
			cache.put(nbaPlayerID, nbaPlayer);
			return nbaPlayer;
		} catch (SQLException e) {
			dbm.cleanup();
			throw new RuntimeException("error inserting new Player", e);
		}
	}
	
	public NBAPlayer findbyID(int id)
	{
		if (cache.containsKey(id)) return cache.get(id);
		
		try {
			String qry = "select * from NBAPLAYER where NBAPlayerID = ?";
			PreparedStatement pstmt = conn.prepareStatement(qry);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			// return null if faculty doesn't exist
			if (!rs.next())
				return null;

			int nbaPlayerID = rs.getInt("NBAPlayerID");
			String name = rs.getString("NBAPlayerName");
			int teamID = rs.getInt("DId");
			int salary = rs.getInt("NBAPlayerSalary");
			String health = rs.getString("NBAPlayerHealthStatus");
			int age = rs.getInt("NBAPlayerAge");
			String hometown = rs.getString("NBAPlayerHomeTown");
			String position = rs.getString("NBAPlayerPosition");
			rs.close();

			NBATeam team = dbm.findNBATeambyID(teamID);
			NBAPlayer player = new NBAPlayer(this, nbaPlayerID,name,team, salary,health,age,hometown,position);
			
			cache.put(id, player);
			return player;
		} catch (SQLException e) {
			dbm.cleanup();
			throw new RuntimeException("error finding Player", e);
		}
	}

	void clear() throws SQLException {
		Statement stmt = conn.createStatement();
		String s = "delete from NBAPLAYER";
		stmt.executeUpdate(s);
		cache.clear();
	}

}
