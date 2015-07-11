package me.Pangasius.minigames;

import me.Pangasius.minigames.commands.FunGamesCommand;
import me.Pangasius.minigames.game.ChickenSearchGame;
import me.Pangasius.minigames.game.EventListener;
import me.Pangasius.minigames.game.Game;
import me.Pangasius.minigames.game.Players;
import me.Pangasius.minigames.game.RageJumpGame;
import me.Pangasius.minigames.game.SnowballFightGame;
import me.Pangasius.minigames.stats.Stats;

import org.bukkit.entity.Chicken;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	/*
	 * Variables
	 */
	
	private static Main instance;
	
	private Players players;
	private Game currentGame;
	private Stats stats;
	
	private boolean ingame = false;
	
	/*
	 * Loading the plugin
	 */
	
	@Override
	public void onEnable() {
		
		instance = this;
		
		players = new Players();
		currentGame = new ChickenSearchGame();
		stats = new Stats();
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		registerCommand();
		registerListener();

	}
	
	/*
	 * Unloding the plugin
	 */
	
	@Override
	public void onDisable() {
		
		if(currentGame instanceof ChickenSearchGame){
			
			ChickenSearchGame game = (ChickenSearchGame) currentGame;
			
			for(Chicken chicken : game.getChickens().keySet()){
				
				chicken.remove();
				
			}
			
			game.getChickens().clear();
			
		}
		try{
			
			currentGame.stop();
			
		}catch(Exception e){}
		
	}
	
	/*
	 * Register the "FunGames" command
	 */
	
	private void registerCommand(){
		
		getCommand("fungames").setExecutor(new FunGamesCommand());
		
	}
	
	/*
	 * Register the event listener
	 */
	
	private void registerListener(){
		
		getServer().getPluginManager().registerEvents(new EventListener(), this);
		
	}
	
	/*
	 * Getters & Setters for variables
	 */
	
	public Game getGame(){
		
		return currentGame;
		
	}
	
	public Stats getStats(){
		
		return stats;
		
	}
	
	public void setGame(Game game){
		
		this.currentGame = game;
	}
	
	public Players getPlayers(){
		
		return players;
		
	}
	
	public boolean gameIsRunning(){
		
		return ingame;
		
	}
	
	public void setGameIsRunning(boolean running){
		
		ingame = running;
	}
	
	/*
	 * Get the instance of the plugin
	 */
	
	public static Main getMain(){
		
		return instance;
		
	}

}
