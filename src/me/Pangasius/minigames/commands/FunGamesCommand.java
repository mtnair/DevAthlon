package me.Pangasius.minigames.commands;

import me.Pangasius.minigames.Main;
import me.Pangasius.minigames.Messages;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FunGamesCommand implements CommandExecutor{

	/*
	 * Instance of the plugin
	 */
	
	Main plugin = Main.getMain();
	
	/*
	 * Command executor
	 */
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		/*
		 * Check wheter the sender is a player
		 */
		
		if(!(sender instanceof Player)){
			
			Messages.onlyPlayers(sender);
			return true;
			
		}
		
		Player player = (Player) sender;
		
		/*
		 * Print the help if no argument is given
		 */
		
		if(args.length == 0){
			
			Messages.help(player);
			return true;
			
		}
		
		/*
		 * Print the help
		 */
		
		if(args[0].equalsIgnoreCase("help")){
			
			Messages.help(player);
			return true;
			
		}
		
		/*
		 * Let a player join the game
		 */
		
		if(args[0].equalsIgnoreCase("join")){
			
			if(plugin.getPlayers().isPlaying(player)){
				
				Messages.alreadyPlaying(player);
				return true;
				
			}
			
			if(plugin.getPlayers().join(player)){
				
				if(plugin.getPlayers().isFull()) plugin.getGame().start();
				
			}
			
			return true;
			
		}
		
		/*
		 * Leave the game
		 */
		
		if(args[0].equalsIgnoreCase("leave")){
			
			plugin.getPlayers().leave(player);
			return true;
			
		}
		
		/*
		 * Show statistics to a player
		 */
		
		if(args[0].equalsIgnoreCase("stats")){
			
			if(args.length == 1){
				
				plugin.getStats().showStats(player, player.getUniqueId());
				return true;
				
			}
			
			plugin.getStats().showStats(player, Bukkit.getOfflinePlayer(args[1]).getUniqueId());
			return true;
			
		}
		
		return true;
	}

}
