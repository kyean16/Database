package Testers;

import dao.DatabaseManager;
import java.util.Collection;

import models.NBAPlayer;
import models.NBATeam;

public class Test 
{
	public static void main(String[] args) 
	{
		DatabaseManager dbm = new DatabaseManager();
		
		//Create NBA teams if they do not exist
		dbm.clearTables();
		
		//Create 3 NBA Teams
		NBATeam timberwolves = dbm.insertNBATeam(1,"Timberwolves","Karl",0,0,2015);
		NBATeam suns = dbm.insertNBATeam(2,"Suns","George",0,0,2015);
		NBATeam newyorkknicks = dbm.insertNBATeam(3,"New York Knicks","Fisher",0,0,2015);
		NBATeam bulls = dbm.insertNBATeam(4,"Bulls","Tibs",0,0,2015);
		
		dbm.commit();
		
		//Insert 5 Players
		NBAPlayer kobe = dbm.insertNBAPlayer(0, "Kobe Bryan", suns, 1000, "Old", 34, "LA", "Guard");
		NBAPlayer nash = dbm.insertNBAPlayer(1, "Steve Nash", suns, 10, "Healthy", 40, "Canada", "Guard");
		NBAPlayer duncan = dbm.insertNBAPlayer(2, "Duncan", timberwolves, 1000000, "Healthy", 26, "Austin", "Forward");
		NBAPlayer rose = dbm.insertNBAPlayer(3, "Rose", bulls, 0, "Injured", 25, "Chichago", "Guard");
		NBAPlayer shaq = dbm.insertNBAPlayer(4, "Shaqtactus", suns, 100, "Healthy", 33, "Alabama", "Center");
		
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
				
		
		dbm.commit();
		
		dbm.close(); //Close stream
		
		
	}
}
