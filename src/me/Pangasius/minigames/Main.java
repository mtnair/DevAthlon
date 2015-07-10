package me.Pangasius.minigames;

import me.Pangasius.minigames.commands.FunGamesCommand;
import me.Pangasius.minigames.game.EventListener;
import me.Pangasius.minigames.game.Game;
import me.Pangasius.minigames.game.Players;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	private static Main instance;
	
	private Players players;
	private Game currentGame;
	
	@Override
	public void onEnable() {
		
		instance = this;
		
		players = new Players();
		
		registerCommand();
		registerListener();
	}
	
	@Override
	public void onDisable() {
	}
	
	private void registerCommand(){
		
		getCommand("fungames").setExecutor(new FunGamesCommand());
		
	}
	
	private void registerListener(){
		
		getServer().getPluginManager().registerEvents(new EventListener(), this);
		
	}
	
	public Game getGame(){
		
		return currentGame;
		
	}
	
	public Players getPlayers(){
		
		return players;
		
	}
	
	public static Main getMain(){
		
		return instance;
		
	}

}
