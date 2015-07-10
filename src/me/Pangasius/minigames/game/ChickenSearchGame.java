package me.Pangasius.minigames.game;

import me.Pangasius.minigames.Locations;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChickenSearchGame extends Game{

	public ChickenSearchGame() {
		super(GameType.CHICKEN_SEARCH);
	}

	@Override
	public void teleportToSpawns() {
		
		Bukkit.getPlayer(plugin.getPlayers().getPlayer1()).teleport(Locations.getChickenSearchSpawn1());
		Bukkit.getPlayer(plugin.getPlayers().getPlayer2()).teleport(Locations.getChickenSearchSpawn2());
		
	}

	@Override
	public void fillInventories() {}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}


}
