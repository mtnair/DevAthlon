package me.Pangasius.minigames.commands;

import me.Pangasius.minigames.Main;
import me.Pangasius.minigames.Messages;
import me.Pangasius.minigames.game.Players;

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
		
		if(args[0].endsWith("help")){
			
			Messages.help(player);
			return true;
			
		}else
		if(args[0].equalsIgnoreCase("join")){
			
			if(plugin.getPlayers().join(player)){
				
				if(plugin.getPlayers().isFull()) plugin.getGame().start();
				
			}
			
			return true;
			
		}
		
		return true;
	}

}
