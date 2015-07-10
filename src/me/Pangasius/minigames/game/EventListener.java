package me.Pangasius.minigames.game;

import me.Pangasius.minigames.Locations;
import me.Pangasius.minigames.Main;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class EventListener implements Listener{
	
	private Main plugin = Main.getMain();
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e){
		
		if(!plugin.gameIsRunning()){
			
			if(e.getTo().getY() <= 50){
				
				e.getPlayer().teleport(Locations.getLobbySpawn());
				
			}
			
			return;
			
		}
		
		if(plugin.getGame().isWaitingPeriod()){
			
			if(moved(e.getFrom(), e.getTo())) e.getPlayer().teleport(e.getFrom());
		}
		
	}
	
	private boolean moved(Location from, Location to){
		
		return (from.getX() != to.getX()) || (from.getY() != to.getY()) || (from.getZ() != to.getZ());
		
	}

}
