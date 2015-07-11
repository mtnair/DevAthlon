package me.Pangasius.minigames;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Locations{
	
	/*
	 * Location variables 
	 */
	
	private static Location lobbySpawn;
	
	private static Location chickenSearchSpawn1;
	private static Location chickenSearchSpawn2;
	
	private static Location snowballFightSpawn1;
	private static Location snowballFightSpawn2;
	
	private static Location superMarioSpawn1;
	private static Location superMarioSpawn2;
	
	static{
		
		/*
		 * Initialize Locations if they arent already
		 */
		
		if(lobbySpawn == null){
			
			lobbySpawn = new Location(Bukkit.getWorld("world"), -312.5, 75, -147.5);
			lobbySpawn.setYaw(180);
			lobbySpawn.setPitch(0);
			
			chickenSearchSpawn1 = new Location(Bukkit.getWorld("world"), -643.5, 88, -225.5);
			chickenSearchSpawn2 = chickenSearchSpawn1;
			
			snowballFightSpawn1 = new Location(Bukkit.getWorld("world"), 20.5, 81, -142.5);
			snowballFightSpawn2 = new Location(Bukkit.getWorld("world"), 20.5, 81, -151.5);
			
		}
		
	}
	
	/*
	 *  Methods to get the locations
	 */
	
	public static Location getLobbySpawn(){
		
		return lobbySpawn;
		
	}

	public static Location getChickenSearchSpawn1() {
		
		return chickenSearchSpawn1;
		
	}

	public static Location getChickenSearchSpawn2() {
		
		return chickenSearchSpawn2;
		
	}

	public static Location getSnowballFightSpawn1() {
		
		return snowballFightSpawn1;
		
	}

	public static Location getSnowballFightSpawn2() {
		
		return snowballFightSpawn2;
		
	}

	public static Location getSuperMarioSpawn1() {
		
		return superMarioSpawn1;
		
	}

	public static Location getSuperMarioSpawn2() {
		
		return superMarioSpawn2;
		
	}

	
	
}
