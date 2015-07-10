package me.Pangasius.minigames;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.command.CommandSender;
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
	
	public static void onlyPlayers(CommandSender sender){
		
		sender.sendMessage(prefix + "Dieser Befehl ist nur für Spieler verfügbar!");
		
	}
	
	public static void help(Player p){
		
		p.sendMessage(ChatColor.GREEN + "Diese Befehle existieren: ");
		p.sendMessage(ChatColor.YELLOW + "/fungames help - Zeigt die Hilfe an");
		p.sendMessage(ChatColor.YELLOW + "/fungames stats - Zeigt deine Statistik an");
		p.sendMessage(ChatColor.YELLOW + "/fungames stats <Spieler> - Zeigt die Statistik von <Spieler> an");
		p.sendMessage(ChatColor.YELLOW + "/fungames join - Betrete das Spiel");
		p.sendMessage(ChatColor.YELLOW + "/fungames leave - Verlasse das Spiel");
	}

}
