package me.Pangasius.minigames.game;

import java.sql.ResultSet;
import java.sql.SQLException;

import me.Pangasius.minigames.Locations;
import me.Pangasius.minigames.Main;
import me.Pangasius.minigames.Messages;
import me.Pangasius.minigames.game.SnowballFightGame.Round;
import me.Pangasius.minigames.reflection.Packets;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R1.PacketPlayOutWorldParticles;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class EventListener implements Listener{
	
	private Main plugin = Main.getMain();
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e){
		
		if(!plugin.gameIsRunning()){
			
			if(e.getTo().getY() <= 50){
				
				e.getPlayer().teleport(Locations.getLobbySpawn());
				
			}
			
			if(moved(e.getFrom(), e.getTo())) e.getPlayer().getWorld().playEffect(e.getFrom().add(0, 0.3, 0), Effect.COLOURED_DUST, 5);
			
		}
		
		if(plugin.getGame().isWaitingPeriod() && plugin.getGame().isRunning() && plugin.getPlayers().isPlaying(e.getPlayer())){
			
			if(moved(e.getFrom(), e.getTo())) e.getPlayer().teleport(e.getFrom());
		}
		
		if(plugin.getGame() instanceof SnowballFightGame && plugin.getGame().isRunning() && plugin.getPlayers().isPlaying(e.getPlayer())){
			
			SnowballFightGame game = (SnowballFightGame) plugin.getGame();
			
			if(e.getTo().getY() <= 60){
				
				if(plugin.getPlayers().getPlayer1() == e.getPlayer().getUniqueId()){
					
					game.winsPlayer2++;
					
				}else{
					
					game.winsPlayer1++;
					
				}
				
				if(game.round == Round.ROUND1){
					
					game.teleportToSpawns();
					game.clearInventories(); 
					game.fillInventories();
					game.updateScoreboard();
					game.round = Round.ROUND2;
					
				}else if(game.round == Round.ROUND2){
					
					game.teleportToSpawns();
					game.clearInventories();
					game.fillInventories();
					game.updateScoreboard();
					game.round = Round.ROUND3;
					
				}else if(game.round == Round.ROUND3){
					
					game.stop();
					
				}
				
			}
			
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
	
	@SuppressWarnings("deprecation")
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
			
			Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
				
				@Override
				public void run() {
					try{
						ResultSet rs = plugin.getMySQL().querySQL("SELECT `chickens_found` FROM `stats` WHERE `name` = '" + Bukkit.getPlayer(plugin.getPlayers().getPlayer1()).getName() + "'");
						int chickens_found = rs.getInt("chickens_found");
						plugin.getMySQL().updateSQL("UPDATE `stats` SET `chickens_found` = " + (chickens_found + 1) + " WHERE `name` = '" + Bukkit.getPlayer(plugin.getPlayers().getPlayer1()).getName() + "'");
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			});
			
		}else{
			
			game.scorePlayer2++;
			
			Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
				
				@Override
				public void run() {
					try{
						ResultSet rs = plugin.getMySQL().querySQL("SELECT `chickens_found` FROM `stats` WHERE `name` = '" + Bukkit.getPlayer(plugin.getPlayers().getPlayer2()).getName() + "'");
						
						int chickens_found = 0;
						
						while(rs.next()){
							
							chickens_found = rs.getInt("chickens_found");
							
						}
						plugin.getMySQL().updateSQL("UPDATE `stats` SET `chickens_found` = " + (chickens_found + 1) + " WHERE `name` = '" + Bukkit.getPlayer(plugin.getPlayers().getPlayer2()).getName() + "'");
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			});
			
		}
		
		game.getChickens().remove(chicken);
		chicken.remove();
		game.updateScoreboard();
		
		if(game.spawns == 0){
			
			game.stop();
			
		}
		
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		
		e.getPlayer().teleport(Locations.getLobbySpawn());
		
		try {
			ResultSet rs = plugin.getMySQL().querySQL("SELECT * FROM `stats` WHERE `name` = '" + e.getPlayer().getName() + "'");
			
			if(rs.getFetchSize() == 0){
				
				plugin.getMySQL().updateSQL("INSERT INTO `stats`(`name`, `chickens_found`, `chickens_games`, `chickens_wins`, `snowball_fired`, "
						+ "`snowball_games`, `snowball_wins`) VALUES  ('" + e.getPlayer().getName() + "',0,0,0,0,0,0)");
				
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		if(plugin.getPlayers().isPlaying(e.getPlayer()))e.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e){
		if(plugin.getPlayers().isPlaying(e.getPlayer()))e.setCancelled(true);
	}
	
	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent e){
		if(plugin.getPlayers().isPlaying(e.getPlayer()))e.setCancelled(true);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent e){
		
		if(!(e.getEntity() instanceof Player))return;
		
		if(e.getCause() == DamageCause.FALL){
			
			e.setDamage(0);
			e.setCancelled(true);
			
		}
		
		Player p = (Player) e.getEntity();
		
		if(plugin.getGame().isRunning() && plugin.getPlayers().isPlaying(p)){
			
			Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
				
				@Override
				public void run() {
					
					p.setHealth(20);
					
				}
			}, 5);
			
		}
		
	}
	
	@EventHandler
	public void onPlayerHunger(FoodLevelChangeEvent e){
		
		if(!(e.getEntity() instanceof Player)) return;
		
		Player p = (Player) e.getEntity();
		
		p.setFoodLevel(20);
		e.setFoodLevel(20);
		
	}
	
	private boolean moved(Location from, Location to){
		
		return (from.getX() != to.getX()) || (from.getY() != to.getY()) || (from.getZ() != to.getZ());
		
	}

}
