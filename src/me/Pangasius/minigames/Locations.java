package me.Pangasius.minigames;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Locations{
	
	private static Location lobbySpawn;
	
	static{
		
		if(lobbySpawn == null){
			
			lobbySpawn = new Location(Bukkit.getWorld("world"), -312.5, 75, -147.5);
			lobbySpawn.setYaw(180);
			lobbySpawn.setPitch(0);
			
			
			
		}
		
	}
	
	public static Location getLobbySpawn(){
		
		return lobbySpawn;
		
	}

}
