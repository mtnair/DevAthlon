package me.Pangasius.minigames.game;

import org.bukkit.Bukkit;

import me.Pangasius.minigames.Locations;
import me.Pangasius.minigames.Main;

public abstract class Game {
	
	private GameType type;
	private Main plugin = Main.getMain();
	private int waitingPeriod = 5;
	
	public Game(GameType type){
		
		this.type = type;
		
	}
	
	public abstract void start();
	public abstract void stop();
	
	public void teleportPlayersToLobby(){
		
		if(plugin.getPlayers().getPlayer1() != null) Bukkit.getPlayer(plugin.getPlayers().getPlayer1()).teleport(Locations.getLobbySpawn());
		if(plugin.getPlayers().getPlayer2() != null) Bukkit.getPlayer(plugin.getPlayers().getPlayer2()).teleport(Locations.getLobbySpawn());
		
	}
	
	public GameType getType(){
		
		return type;
		
	}
	
	public boolean isWaitingPeriod(){
		
		return waitingPeriod > 0;
		
	}

}
