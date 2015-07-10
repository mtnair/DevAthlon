package me.Pangasius.minigames.commands;

import me.Pangasius.minigames.Main;
import me.Pangasius.minigames.Messages;
import me.Pangasius.minigames.game.Players;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FunGamesCommand implements CommandExecutor{

	
	Main plugin = Main.getMain();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)){
			
			Messages.onlyPlayers(sender);
			return true;
			
		}
		
		Player player = (Player) sender;
		
		if(args.length == 0){
			
			Messages.help(player);
			return true;
			
		}
		
		if(args[0].equalsIgnoreCase("help")){
			
			Messages.help(player);
			return true;
			
		}
		
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
		
		if(args[0].equalsIgnoreCase("leave")){
			
			plugin.getPlayers().leave(player);
			return true;
			
		}
		
		if(args[0].equalsIgnoreCase("stats")){
			
			if(args.length == 1){
				
				player.sendMessage(ChatColor.GREEN + "Deine Statistik:");
				return true;
				
			}
			
			player.sendMessage(ChatColor.GREEN + "Die Statistik von " + args[1] + ":");
			return true;
			
		}
		
		return true;
	}

}
