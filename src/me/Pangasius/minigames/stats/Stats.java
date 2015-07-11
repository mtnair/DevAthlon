package me.Pangasius.minigames.stats;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import me.Pangasius.minigames.Main;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Stats {

	private Main plugin = Main.getMain();
	
	private FileConfiguration stats;
	private File file;
	
	public Stats(){
		
		file = new File(plugin.getDataFolder() + "stats.yml");
		stats = YamlConfiguration.loadConfiguration(file);
		
	}
	
	public void addChickensGames(UUID uuid){
		
		stats.set(uuid.toString() + ".chickens_games", getChickensGames(uuid) + 1);
		saveStats();
		
	}
	
	public void addChickensWins(UUID uuid){
		
		stats.set(uuid.toString() + ".chickens_wins", getChickensWins(uuid) + 1);
		saveStats();
		
	}
	
	public void addChickensFound(UUID uuid){
		
		stats.set(uuid.toString() + ".chickens_found", getChickensFound(uuid) + 1);
		saveStats();
		
	}
	
	public void addSnowballGames(UUID uuid){
		
		stats.set(uuid.toString() + ".snowball_games", getSnowballGames(uuid) + 1);
		saveStats();
		
	}
	
	public void addSnowballWins(UUID uuid){
		
		stats.set(uuid.toString() + ".snowball_wins", getSnowballWins(uuid) + 1);
		saveStats();
		
	}
	
	public void addSnowballFired(UUID uuid){
		
		stats.set(uuid.toString() + ".snowball_fired", getSnowballFired(uuid) + 1);
		saveStats();
		
	}
	
	public void showStats(Player who, UUID target){
		
		who.sendMessage("§aStatistik von §e" + Bukkit.getOfflinePlayer(target).getName());
		
		who.sendMessage(" ");
		
		who.sendMessage("§aChickenSearch-Games-Played: " + getChickensGames(target));
		who.sendMessage("§aChickenSearch-Games-Won: " + getChickensWins(target));
		who.sendMessage("§aChickenSearch-Chickens-Found: " + getChickensFound(target));
		
		who.sendMessage(" ");
		
		who.sendMessage("§aSnowballFight-Games-Played: " + getSnowballGames(target));
		who.sendMessage("§aSnowballFight-Games-Won: " + getSnowballWins(target));
		who.sendMessage("§aSnowballFight-Ball-Fired: " + getSnowballFired(target));
		
	}
	
	private int getChickensGames(UUID uuid){
		
		checkStats(uuid);
		
		return stats.getInt(uuid.toString() + ".chkickens_games");
		
	}
	
	private int getChickensWins(UUID uuid){
		
		checkStats(uuid);
		
		return stats.getInt(uuid.toString() + ".chkickens_wins");
		
	}
	
	private int getChickensFound(UUID uuid){
		
		checkStats(uuid);
		
		return stats.getInt(uuid.toString() + ".chkickens_found");
		
	}
	
	private int getSnowballGames(UUID uuid){
		
		checkStats(uuid);
		
		return stats.getInt(uuid.toString() + ".snowball_games");
		
	}
	
	private int getSnowballWins(UUID uuid){
		
		checkStats(uuid);
		
		return stats.getInt(uuid.toString() + ".snowball_wins");
		
	}
	
	private int getSnowballFired(UUID uuid){
		
		checkStats(uuid);
		
		return stats.getInt(uuid.toString() + ".snowball_fired");
		
	}
	
	private void checkStats(UUID uuid){
		
		if(stats.getString(uuid.toString()) == null){
			
			stats.set(uuid.toString() + ".chickens_games", 0);
			stats.set(uuid.toString() + ".chickens_wins", 0);
			stats.set(uuid.toString() + ".chickens_found", 0);
			stats.set(uuid.toString() + ".snowball_games", 0);
			stats.set(uuid.toString() + ".snowball_wins", 0);
			stats.set(uuid.toString() + ".snowball_fired", 0);
			
			saveStats();
			
		}
		
	}
	
	private void saveStats(){
		
		try {
			stats.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
