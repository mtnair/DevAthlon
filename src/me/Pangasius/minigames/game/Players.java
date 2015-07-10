package me.Pangasius.minigames.game;

import java.util.UUID;

import me.Pangasius.minigames.Locations;
import me.Pangasius.minigames.Main;
import me.Pangasius.minigames.Messages;

import org.bukkit.entity.Player;

public class Players {
	
	private UUID player1, player2;

	public UUID getPlayer1() {
		return player1;
	}

	public UUID getPlayer2() {
		return player2;
	}
	
	public boolean join(Player player){
		
		if(player1 != null && player2 != null){
			Messages.gameIsFull(player);
			return false;
		}
		
		if(player1 == null){
			
			player1 = player.getUniqueId();
			
		}else if(player2 == null){
			
			player2 = player.getUniqueId();
			
		}
		
		Messages.joined(player);
		
		return true;
		
	}
	
	public void leave(Player player){
		
		
		if(player1 == player.getUniqueId()){
			
			Main.getMain().getGame().stop();
			player1 = null;
			player2 = null;
			
		}else if(player2 == player.getUniqueId()){
			
			Main.getMain().getGame().stop();
			player2 = null;
			player1 = null;
			
		}else{
		
			Messages.notPlaying(player);
			
		}
		
	}
	
	public boolean isFull(){
		
		return player1 != null && player2 != null;
		
	}
	
	public boolean isPlaying(Player player){
		
		return player1 == player.getUniqueId() || player2 == player.getUniqueId();
		
	}
	
	public void clear(){
		
		player1 = null;
		player2 = null;
		
	}

}
