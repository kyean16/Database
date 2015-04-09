package Testers;

import dao.DatabaseManager;

import java.util.Collection;

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
		
		dbm.commit();
		
		//Checks the NBA Team
		NBATeam test = dbm.findNBATeamByName("Timberwolves");
		System.out.println(test.getNbaTeamName());
		
		dbm.commit();
		
		dbm.close(); //Close stream
		
		
	}
}
