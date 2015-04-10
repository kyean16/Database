package Testers;

import dao.DatabaseManager;
import java.util.Collection;

import models.*;

public class Test 
{
	public static void main(String[] args) 
	{
		DatabaseManager dbm = new DatabaseManager();
		
		//Create NBA teams if they do not exist
		dbm.clearTables();
		
		//Insert 6 NBA Teams
		NBATeam timberwolves = dbm.insertNBATeam(1,"Timberwolves","Karl",2015);
		NBATeam suns = dbm.insertNBATeam(2,"Suns","George",2015);
		NBATeam newyorkknicks = dbm.insertNBATeam(3,"New York Knicks","",2015);
		NBATeam bulls = dbm.insertNBATeam(4,"Bulls","Tibs",2015);
		NBATeam pacers = dbm.insertNBATeam(5,"Pacers","Voguel",2015);
		NBATeam heat = dbm.insertNBATeam(6,"Heat","Spoelstra",2015);

		dbm.commit();
		
		//Insert 10 Players
		NBAPlayer kobe = dbm.insertNBAPlayer(0, "Kobe Bryan", suns, 1000, "Old", 34, "LA", "Guard");
		NBAPlayer nash = dbm.insertNBAPlayer(1, "Steve Nash", suns, 10, "Healthy", 40, "Canada", "Guard");
		NBAPlayer duncan = dbm.insertNBAPlayer(2, "Duncan", timberwolves, 1000000, "Healthy", 26, "Austin", "Forward");
		NBAPlayer rose = dbm.insertNBAPlayer(3, "Rose", bulls, 0, "Injured", 25, "Chicago", "Guard");
		NBAPlayer shaq = dbm.insertNBAPlayer(4, "Shaqtactus", suns, 100, "Healthy", 33, "Alabama", "Center");
		NBAPlayer durant = dbm.insertNBAPlayer(5, "Durant", suns, 1000000, "Healthy", 27, "D.C", "Guard");
		NBAPlayer howard = dbm.insertNBAPlayer(6, "Howard", heat, 9550000, "Healthy", 23, "Orlando", "Center");
		NBAPlayer lebron = dbm.insertNBAPlayer(7, "Lebron James", timberwolves, 1000000, "Healthy", 26, "Cleveland", "Forward");
		NBAPlayer noah = dbm.insertNBAPlayer(8, "Noah", bulls, 0, "Injured", 25, "France", "Center");
		NBAPlayer melo = dbm.insertNBAPlayer(9, "Carmelo", newyorkknicks, 100, "Healthy", 30, "NY", "Guard");

		dbm.commit();
		
		//Insert 2 Games
		NBAGame game1 = dbm.insertNBAGame(0, suns, bulls, 80, 82, 2015);
		NBAGame game2 = dbm.insertNBAGame(1, timberwolves,suns, 120, 118, 2015);
		dbm.commit();
		
		//Insert 7 Game Logs
		GameLog log1 = dbm.insertNBALog(game1, nash, 0, 14, 2, 12, 1, 4, 33);
		GameLog log2 = dbm.insertNBALog(game2, nash, 1, 16, 1, 11, 2, 2, 36);
		GameLog log3 = dbm.insertNBALog(game1, kobe, 2, 28, 5, 4, 2, 5, 38);
		GameLog log7 = dbm.insertNBALog(game2, kobe, 6, 28, 5, 4, 2, 5, 38);
		GameLog log4 = dbm.insertNBALog(game2, duncan,3, 30, 13, 5, 0, 1, 30);
		GameLog log5 = dbm.insertNBALog(game2, shaq, 4, 11, 4, 1, 0, 5, 25);
		GameLog log6 = dbm.insertNBALog(game1, rose, 5, 11, 2, 9, 1, 4, 25);
		dbm.commit();
		
		//Checks the NBA Team
		System.out.println("");
		System.out.println("Looking for All NBA Teams:");
		
		//Checks for all NbaTeam
		for(int i = 1 ; i <= dbm.getNBATeams() ;i++)
		{
		  NBATeam test = dbm.findNBATeambyID(i);
		  System.out.println(test.toString());
		}
		
		//Checks for Player in a particular Team
		System.out.println("");
		System.out.println("Looking for all players on the Suns:");
		
		Collection<NBAPlayer> players = suns.getPlayers();
		for (NBAPlayer player : players) {
			System.out.println(player.toString());
		}
		
		//Looking for Teams with less than one player
		System.out.println("");
		System.out.println("Looking for teams with less or equal to 2 Player");
		Collection<NBATeam> teams = dbm.findTeamLessThanEqualTo(2);
		for(NBATeam team: teams){
			System.out.println("Team:" + team.getNbaTeamName() + ":" + team.getPlayers() 
					+ "(" + team.getAmountPlayers() + ")");
		}
		
		//Checks for all games played by the suns home and away
		System.out.println("");
		System.out.println("Looking for all games played by the Suns at Home and Away:");
		
		Collection<NBAGame> games = suns.getGames();
		for (NBAGame game : games){
			System.out.println(game.toString());
		}
		
		//Checks for all game log Steve Nash has played against the bulls.
		System.out.println("");
		System.out.println("All Game Logs of Steve Nash playing against the Bulls with his current team:");
		Collection<GameLog> logs = nash.findAgainstLogs(bulls);
		for (GameLog log : logs){
			System.out.println(log.toString());
		}
		dbm.commit();
		
		dbm.close(); //Close stream
		
		
	}
}
