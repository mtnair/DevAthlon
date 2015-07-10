package me.Pangasius.minigames.game;

import java.util.UUID;

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
		
		return true;
		
	}
	
	public void leave(Player p){
		
		if(player1 != p.getUniqueId() && player2 != p.getUniqueId()){
			
		}
		
	}

}
