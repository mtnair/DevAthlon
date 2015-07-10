package me.Pangasius.minigames;

import me.Pangasius.minigames.commands.FunGamesCommand;
import me.Pangasius.minigames.game.Game;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	private static Main instance;
	
	private Game currentGame;
	
	@Override
	public void onEnable() {
		
		instance = this;
		
		registerCommands();
	}
	
	@Override
	public void onDisable() {
	}
	
	private void registerCommands(){
		
		getCommand("fungames").setExecutor(new FunGamesCommand());
		
	}
	
	public Game getGame(){
		
		return currentGame;
		
	}
	
	public static Main getMain(){
		
		return instance;
		
	}

}
