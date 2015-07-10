package me.Pangasius.minigames;

import java.sql.SQLException;

import me.Pangasius.minigames.commands.FunGamesCommand;
import me.Pangasius.minigames.game.ChickenSearchGame;
import me.Pangasius.minigames.game.EventListener;
import me.Pangasius.minigames.game.Game;
import me.Pangasius.minigames.game.Players;
import me.Pangasius.minigames.game.SnowballFightGame;
import me.Pangasius.minigames.stats.MySQL;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	private static Main instance;
	
	private Players players;
	private Game currentGame;
	private boolean ingame = false;
	
	private MySQL mysql;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {
		
		instance = this;
		
		players = new Players();
		currentGame = new ChickenSearchGame();
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		mysql = new MySQL(getConfig().getString("mysql.host"), getConfig().getString("mysql.port"), getConfig().getString("mysql.database"), getConfig().getString("mysql.username"), getConfig().getString("mysql.password"));
		try {
			mysql.openConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			mysql.updateSQL("CREATE TABLE IF NOT EXISTS stats (name VARCHAR(16), chickens_found VARCHAR(10000), chickens_games VARCHAR(10000), chickens_wins VARCHAR(10000), snowball_fired VARCHAR(10000), snowball_games VARCHAR(10000), snowball_wins VARCHAR(10000))");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		registerCommand();
		registerListener();
		
		Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
			
			@Override
			public void run() {
				try {
					mysql.querySQL("SELECT 1");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}, 100, 100);
	}
	
	@Override
	public void onDisable() {
		
		try {
			mysql.closeConn();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
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
	
	public static Main getMain(){
		
		return instance;
		
	}
	
	public MySQL getMySQL(){
		
		return mysql;
		
	}

}
