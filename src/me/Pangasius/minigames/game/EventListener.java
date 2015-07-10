package me.Pangasius.minigames.game;

import me.Pangasius.minigames.Locations;
import me.Pangasius.minigames.Main;
import me.Pangasius.minigames.Messages;
import me.Pangasius.minigames.reflection.Packets;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R1.PacketPlayOutWorldParticles;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class EventListener implements Listener{
	
	private Main plugin = Main.getMain();
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e){
		
		if(!plugin.gameIsRunning()){
			
			if(e.getTo().getY() <= 50){
				
				e.getPlayer().teleport(Locations.getLobbySpawn());
				
			}
			
			if(moved(e.getFrom(), e.getTo())) e.getPlayer().getWorld().playEffect(e.getFrom().add(0, 0.3, 0), Effect.COLOURED_DUST, 5);
			
			return;
			
		}
		
		if(plugin.getGame().isRunning()){
			
			if(moved(e.getFrom(), e.getTo())) e.getPlayer().teleport(e.getFrom());
		}
		
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e){
		
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK){
			
			
			
			if(e.getClickedBlock().getState() instanceof Sign){
				
				Sign s = (Sign) e.getClickedBlock().getState();
				if(s.getLine(0).contains("[FunGames]") && s.getLine(2).contains("Spiel betreten")){
					
					s.setLine(0, "§a[FunGames]");
					s.setLine(2, "§eSpiel betreten");
					
					if(plugin.getPlayers().isPlaying(e.getPlayer())){
						
						Messages.alreadyPlaying(e.getPlayer());
						return;
						
					}
					
					if(plugin.getPlayers().join(e.getPlayer())){
						
						if(plugin.getPlayers().isFull()) plugin.getGame().start();
						
					}
					
				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent e){
		
		if(!plugin.getPlayers().isPlaying(e.getPlayer()))return;
		
		if(!(e.getRightClicked() instanceof Chicken))return;
		
		if(!(plugin.getGame() instanceof ChickenSearchGame))return;
		
		ChickenSearchGame game = (ChickenSearchGame) plugin.getGame();
		
		if(!(game.getChickens().keySet().contains(e.getRightClicked())))return;
		
		Chicken chicken = (Chicken) e.getRightClicked();
		Player player = e.getPlayer();
		
		if(player.getUniqueId() == plugin.getPlayers().getPlayer1()){
			
			game.scorePlayer1++;
			
		}else{
			
			game.scorePlayer2++;
			
		}
		
		game.getChickens().remove(chicken);
		chicken.remove();
		game.updateScoreboard();
		
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		
		e.getPlayer().teleport(Locations.getLobbySpawn());
		
	}
	
	private boolean moved(Location from, Location to){
		
		return (from.getX() != to.getX()) || (from.getY() != to.getY()) || (from.getZ() != to.getZ());
		
	}

}
