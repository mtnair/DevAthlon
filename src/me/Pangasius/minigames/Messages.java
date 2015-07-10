package me.Pangasius.minigames;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

public class Messages {
	
	private static String prefix = ChatColor.YELLOW + "[FunGames] " + ChatColor.GREEN;
	
	public static void gameIsFull(Player p){
		
		p.sendMessage(prefix + "Sorry, aber das Spiel ist bereits voll!");
		
	}
	
	public static void notPlaying(Player p){
		
		p.sendMessage(prefix + "Du bist nicht im Spiel!");
		
	}
	
	public static void alreadyPlaying(Player p){
		
		p.sendMessage(prefix + "Du bist bereits im Spiel!");
		
	}

}
