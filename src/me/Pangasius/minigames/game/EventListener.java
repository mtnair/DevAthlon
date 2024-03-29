package me.Pangasius.minigames.game;

import me.Pangasius.minigames.FunItem;
import me.Pangasius.minigames.Locations;
import me.Pangasius.minigames.Main;
import me.Pangasius.minigames.Messages;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EventListener implements Listener{
	
	/*
	 * Instance of the plugin
	 */
	
	private Main plugin = Main.getMain();
	
	/*
	 * PlayerMoveEvent
	 */
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e){
		
		if(!plugin.getGame().isRunning() && !plugin.getGame().isWaitingPeriod()){
			
			/*
			 * Teleport players back when they fall out of spawn
			 */
			
			if(e.getTo().getY() <= 50){
				
				e.getPlayer().teleport(Locations.getLobbySpawn());
				
			}
			
			/*
			 * Play effect at players location
			 */
			
			if(moved(e.getFrom(), e.getTo())) e.getPlayer().getWorld().playEffect(e.getFrom().add(0, 0.3, 0), Effect.COLOURED_DUST, 5);
			
		}
		
		/*
		 * No moving during waiting period
		 */
		
		/*if(plugin.getGame().isWaitingPeriod() && plugin.getPlayers().isPlaying(e.getPlayer())){
			
			if(moved(e.getFrom(), e.getTo())) e.getPlayer().teleport(e.getFrom());
		}*/
		
		/*
		 * Check snowball fight stats and teleport if its needed
		 */
		
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
		
		if(plugin.getGame() instanceof RageJumpGame && plugin.getGame().isRunning() && plugin.getPlayers().isPlaying(e.getPlayer())){
			
			if(e.getTo().getY() <= 77){
				
				if(plugin.getPlayers().getPlayer1() == e.getPlayer().getUniqueId()){
					
					Bukkit.getPlayer(plugin.getPlayers().getPlayer1()).teleport(Locations.getRageJumpSpawn1());
					
				}else{
					
					Bukkit.getPlayer(plugin.getPlayers().getPlayer2()).teleport(Locations.getRageJumpSpawn2());
					
				}
				
			}
			
		}
		
		if(plugin.getGame() instanceof ChickenSearchGame && plugin.getGame().isRunning() && plugin.getPlayers().isPlaying(e.getPlayer())){
		
			if(e.getPlayer().getLocation().getBlock().getType() == Material.TRIPWIRE){
				
				e.getPlayer().getLocation().getBlock().setType(Material.AIR);
				
				e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1));
				
			}
			
		}
		
	}
	
	/*
	 * Listener to join the game by clicking on the sign
	 */
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e){
		
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK){
			
			
			if(e.getClickedBlock().getState() instanceof Sign){
				
				Sign s = (Sign) e.getClickedBlock().getState();
				if(s.getLine(0).contains("[FunGames]") && s.getLine(2).contains("Spiel betreten")){
					
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
	
	/*
	 * Listener to recognize wheter a player found a chicken
	 */
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent e){
		
		if(!plugin.getPlayers().isPlaying(e.getPlayer()))return;
		
		if(!(e.getRightClicked() instanceof Chicken))return;
		
		if(!(plugin.getGame() instanceof ChickenSearchGame))return;
		
		ChickenSearchGame game = (ChickenSearchGame) plugin.getGame();
		
		if(!(game.getChickens().keySet().contains(e.getRightClicked())))return;
		
		Chicken chicken = (Chicken) e.getRightClicked();
		Player player = e.getPlayer();
		
		/*
		 * Increase the players score
		 */
		
		if(player.getUniqueId() == plugin.getPlayers().getPlayer1()){
			
			game.scorePlayer1++;
			
			plugin.getStats().addChickensFound(plugin.getPlayers().getPlayer1());
			
		}else{
			
			game.scorePlayer2++;
			
			plugin.getStats().addChickensFound(plugin.getPlayers().getPlayer2());
			
		}
		
		/*
		 * Remove the found chicken
		 */
		
		game.getChickens().remove(chicken);
		chicken.remove();
		game.updateScoreboard();
		
		if(game.getChickens().size() == 0 && game.spawns == 0){
			
			game.stop();
			
		}
		
	}
	
	/*
	 * Teleport players back to spawn when the join
	 */
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		
		e.getPlayer().teleport(Locations.getLobbySpawn());
		FunItem.giveItem(e.getPlayer());
		
	}
	
	/*
	 * Leave the game if a player left during the game
	 */
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e){
		
		if(plugin.getPlayers().isPlaying(e.getPlayer())) plugin.getPlayers().leave(e.getPlayer());
		
	}
	
	/*
	 * No block break/place during the game
	 */
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e){
		e.setCancelled(true);
	}
	
	/*
	 * Cancel picking up items
	 */
	
	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent e){
		if(plugin.getPlayers().isPlaying(e.getPlayer()))e.setCancelled(true);
	}
	
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e){
		e.setCancelled(true);
	}
	
	/*
	 * Stop damage
	 */
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent e){
		
		if(!(e.getEntity() instanceof Player))return;
		
		if(e.getCause() == DamageCause.FALL){
			
			e.setDamage(0);
			e.setCancelled(true);
			
		}
		
		if(!plugin.getGame().isRunning() && !plugin.getGame().isWaitingPeriod()){
			
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
	
	/*
	 * Stop the changing of the food level
	 */
	
	@EventHandler
	public void onPlayerHunger(FoodLevelChangeEvent e){
		
		if(!(e.getEntity() instanceof Player)) return;
		
		Player p = (Player) e.getEntity();
		
		p.setFoodLevel(20);
		e.setFoodLevel(20);
		
	}
	
	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent e){
		
		if(!(e.getEntity() instanceof Snowball)) return;
		
		if(!(e.getEntity().getShooter() instanceof Player)) return;
		
		Player p = (Player) e.getEntity().getShooter();
		
		if(plugin.getPlayers().isPlaying(p) && plugin.getGame().isRunning() && plugin.getGame() instanceof SnowballFightGame){
			
			SnowballFightGame game = (SnowballFightGame) plugin.getGame();
			
			plugin.getStats().addSnowballFired(p.getUniqueId());
			
			game.snowballs.add(e.getEntity());
			
		}
		
	}
	
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent e){
		
		if(plugin.getGame().isRunning() && plugin.getGame() instanceof SnowballFightGame){
			
			SnowballFightGame game = (SnowballFightGame) plugin.getGame();
			
			if(game.snowballs.contains(e.getEntity())) game.snowballs.remove(e.getEntity());
			
		}
		
	}
	
	@EventHandler
	public void onPlayerInteractFunItem(PlayerInteractEvent e){
		
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
			
			if(e.getPlayer().getItemInHand().hasItemMeta() && e.getPlayer().getItemInHand().getItemMeta().hasDisplayName() && e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("�5FunGun")){
				
				Projectile p = e.getPlayer().launchProjectile(Arrow.class);
				p.setCustomName("CatSpawnEgg");
				p.setCustomNameVisible(false);				
			}
			
		}
				
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onSpawnerEggHit(ProjectileHitEvent e){
		
		if(!(e.getEntity() instanceof Arrow)) return;
		
		if(e.getEntity().getCustomName() != null && e.getEntity().getCustomName().equalsIgnoreCase("CatSpawnEgg")){
			
			Ocelot oc = (Ocelot) e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.OCELOT);
			
			oc.setBreed(true);
			oc.setBaby();
			
			Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
				
				@Override
				public void run() {
					
					
					oc.getWorld().spigot().playEffect(oc.getLocation(), Effect.LAVA_POP, 0, 0, 2, 2, 2, 3, 20, 2);
					oc.remove();
					
					
				}
			}, 50);
			
			e.getEntity().remove();
			
		}
		
	}
	
	@EventHandler
	public void onPlayerInteractRageJump(PlayerInteractEvent e){
		
		if(plugin.getGame() instanceof RageJumpGame && plugin.getGame().isRunning() && plugin.getPlayers().isPlaying(e.getPlayer())){
			
			RageJumpGame game = (RageJumpGame) plugin.getGame();
			
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK){
				
				if(e.getClickedBlock().getType() == Material.GOLD_BLOCK){
					
					if(e.getPlayer().getUniqueId() == plugin.getPlayers().getPlayer1()){
						
						game.winsPlayer1++;
						
					}else{
						
						game.winsPlayer2++;
						
					}
					
					game.broadcastToPlayers(Messages.prefix + e.getPlayer().getName() + " hat das Zeil erreicht!");
					
					Firework fw = (Firework) Bukkit.getWorld("world").spawn(e.getClickedBlock().getLocation(), Firework.class);
					FireworkMeta meta = fw.getFireworkMeta();
					meta.addEffect(FireworkEffect.builder().withColor(Color.RED).trail(true).flicker(true).build());
					fw.setFireworkMeta(meta);
					
					if(game.round == Round.ROUND1){
						game.round = Round.ROUND2;
						game.teleportToSpawns();
						game.clearInventories();
						game.fillInventories();
						game.updateScoreboard();
						
						
						
					}else if(game.round == Round.ROUND2){
						game.round = Round.ROUND3;
						game.teleportToSpawns();
						game.clearInventories();
						game.fillInventories();
						game.updateScoreboard();
					}else if(game.round == Round.ROUND3){
						game.stop();
					}
					
				}
				
			}
			
			
		}
		
	}
	
	/*
	 * Check whether a player has moved or not
	 */
	
	private boolean moved(Location from, Location to){
		
		return (from.getX() != to.getX()) || (from.getY() != to.getY()) || (from.getZ() != to.getZ());
		
	}
	
	private boolean equals(Location loc1, Location loc2){
		
		return loc1.getBlockX() == loc2.getBlockX() && loc1.getBlockY() == loc2.getBlockY() && loc1.getBlockZ() == loc2.getBlockZ();
		
	}

}
