package me.Pangasius.minigames.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import me.Pangasius.minigames.Locations;
import me.Pangasius.minigames.Messages;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

public class ChickenSearchGame extends Game{

	/*
	 * Variables
	 */
	
	private List<Location> spawnlocations = new ArrayList<Location>();
	private HashMap<Chicken, Location> chickens = new HashMap<Chicken, Location>();
	
	private List<Location> strings = new ArrayList<Location>();
	
	private Item currentItem;
	
	public int scorePlayer1 = 0;
	public int scorePlayer2 = 0;
	
	public int spawns = 25;
	
	private Score scoreboardPlayer1;
	private Score scoreboardPlayer2;
	
	private ItemStack stick;
	
	/*
	 * Default constructor
	 */
	
	public ChickenSearchGame(){
		super(GameType.CHICKEN_SEARCH);
		
		/*
		 * Load chicken spawn locations
		 */
		
		{
			
			Location loc1 = new Location(Bukkit.getWorld("world"),-638,88,-235);
			Location loc2 = new Location(Bukkit.getWorld("world"),-648,90,-235);
			Location loc3 = new Location(Bukkit.getWorld("world"),-640,92,-230);
			Location loc4 = new Location(Bukkit.getWorld("world"),-650,90,-226);
			Location loc5 = new Location(Bukkit.getWorld("world"),-639,88,-224);
			Location loc6 = new Location(Bukkit.getWorld("world"),-641,88,-209);
			Location loc7 = new Location(Bukkit.getWorld("world"),-635,88,-212);
			Location loc8 = new Location(Bukkit.getWorld("world"),-624,88,-211);
			Location loc9 = new Location(Bukkit.getWorld("world"),-625,92,-223);
			Location loc10 = new Location(Bukkit.getWorld("world"),-633,88,-233);
			
			spawnlocations.add(loc1);
			spawnlocations.add(loc2);
			spawnlocations.add(loc3);
			spawnlocations.add(loc4);
			spawnlocations.add(loc5);
			spawnlocations.add(loc6);
			spawnlocations.add(loc7);
			spawnlocations.add(loc8);
			spawnlocations.add(loc9);
			spawnlocations.add(loc10);
			
		}
		
		{
			
			Location loc1 = new Location(Bukkit.getWorld("world"),-640,88,-215);
			Location loc2 = new Location(Bukkit.getWorld("world"),-649,88,-216);
			Location loc3 = new Location(Bukkit.getWorld("world"),-642,88,-225);
			Location loc4 = new Location(Bukkit.getWorld("world"),-638,88,-233);
			Location loc5 = new Location(Bukkit.getWorld("world"),-630,88,-222);
			Location loc6 = new Location(Bukkit.getWorld("world"),-625,88,-211);
			
			strings.add(loc1);
			strings.add(loc2);
			strings.add(loc3);
			strings.add(loc4);
			strings.add(loc5);
			strings.add(loc6);
			
		}
		
		/*
		 * Load the stick item
		 */
		
		stick = new ItemStack(Material.BLAZE_ROD);
		stick.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
		ItemMeta meta = stick.getItemMeta();
		meta.setDisplayName("§5Rückstoßstoßender Rückstoßstoßer");
		stick.setItemMeta(meta);
	}

	/*
	 * Teleport all players to their spawns
	 */
	
	@Override
	public void teleportToSpawns() {
		
		Bukkit.getPlayer(plugin.getPlayers().getPlayer1()).teleport(Locations.getChickenSearchSpawn1());
		Bukkit.getPlayer(plugin.getPlayers().getPlayer2()).teleport(Locations.getChickenSearchSpawn2());
		
	}

	/*
	 * Give the players the items
	 */
	
	@Override
	public void fillInventories() {
		
		Player player1 = Bukkit.getPlayer(plugin.getPlayers().getPlayer1());
		
		player1.getInventory().addItem(stick);
		
		Player player2 = Bukkit.getPlayer(plugin.getPlayers().getPlayer2());
		
		player2.getInventory().addItem(stick);
		
	}

	/*
	 * Excetuted on game start when the waiting period ended
	 */
	
	@SuppressWarnings("deprecation")
	@Override
	public void begin() {
		
		plugin.getStats().addChickensGames(plugin.getPlayers().getPlayer1());
		plugin.getStats().addChickensGames(plugin.getPlayers().getPlayer2());
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				
				for(Location loc : strings){
					
					loc.getBlock().setType(Material.TRIPWIRE);
					
				}
				
			}
			
		});
		
		Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
			
			Random r = new Random();
			
			@Override
			public void run() {
				
				if(spawns == 0)return;
				
				/*
				 * Spawn a egg at a random location from the list
				 */
				
				Location eggSpawn = spawnlocations.get(r.nextInt(spawnlocations.size()));
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					
					@Override
					public void run() {
						currentItem = Bukkit.getWorld("world").dropItemNaturally(eggSpawn.clone().add(0, 20, 0), new ItemStack(344));
						currentItem.setCustomName("Huhn");
						currentItem.setCustomNameVisible(true);
					}
				});
				
				/*
				 * wait 90 ticks and change the item to a real chicken
				 */
				
				Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
					
					@Override
					public void run() {
						
						Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
							
							@Override
							public void run() {
								
								Chicken chicken = (Chicken) Bukkit.getWorld("world").spawnEntity(eggSpawn, EntityType.CHICKEN);
								
								Bukkit.getWorld("world").playEffect(eggSpawn, Effect.LAVA_POP, 8);
								
								Firework fw = (Firework) Bukkit.getWorld("world").spawn(eggSpawn.clone().add(0, 10, 0), Firework.class);
								FireworkMeta meta = fw.getFireworkMeta();
								meta.addEffect(FireworkEffect.builder().withColor(Color.GREEN).trail(true).flicker(true).build());
								fw.setFireworkMeta(meta);
								
								chickens.put(chicken, eggSpawn);
								currentItem.remove();
								
							}
						});
						
					}
				}, 90);
			
				spawns--;
			}
			
			
		}, 0, 100);
		
		/*
		 * Freeze all the spawned chickens
		 */
		
		Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				
				for(Chicken chicken : chickens.keySet()){
					
					chicken.teleport(chickens.get(chicken));
					
				}
				
			}
		}, 0, 5);
		
	}

	/*
	 * Executed when the game ended
	 */
	
	@Override
	public void end() {
		
		/*
		 * Check which player won the game and broadcast
		 */
		
		if(scorePlayer1 > scorePlayer2){
			
			broadcastToPlayers(Messages.prefix + Bukkit.getPlayer(plugin.getPlayers().getPlayer1()).getName() + " hat das Spiel gewonnen!");
			broadcastToPlayers(Messages.prefix + "Das Spiel 'ChickenSearch' endete  " + scorePlayer1 + ":" + scorePlayer2);
			
			plugin.getStats().addChickensWins(plugin.getPlayers().getPlayer1());
			
		}else{
			
			broadcastToPlayers(Messages.prefix + Bukkit.getPlayer(plugin.getPlayers().getPlayer2()).getName() + " hat das Spiel gewonnen!");
			broadcastToPlayers(Messages.prefix + "Das Spiel 'ChickenSearch' endete  " + scorePlayer1 + ":" + scorePlayer2);
			
			plugin.getStats().addChickensWins(plugin.getPlayers().getPlayer2());
			
		}
		
		/*
		 * Remove all chickens
		 */
		
		for(Chicken chicken : chickens.keySet()){
			
			chicken.remove();
			
		}
		
		chickens.clear();
		
		/*
		 * Start the snowball game
		 */
		
		if(plugin.getPlayers().isFull()){
			
			plugin.setGame(new SnowballFightGame());
			plugin.getGame().start();
			
		}
		
	}

	/*
	 * Get the chickens
	 */
	
	public HashMap<Chicken, Location> getChickens() {
		return chickens;
	}

	/*
	 * Initialize the scoreboard
	 */
	
	@Override
	public void initScoreboard() {
		Objective obj = scoreboard.registerNewObjective("fungames", "sb");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName("§aCHICKENSEARCH");
		
		scoreboardPlayer1 = obj.getScore(Bukkit.getPlayer(plugin.getPlayers().getPlayer1()).getName());
		scoreboardPlayer1.setScore(scorePlayer1);
		
		scoreboardPlayer2 = obj.getScore(Bukkit.getPlayer(plugin.getPlayers().getPlayer2()).getName());
		scoreboardPlayer2.setScore(scorePlayer2);
		
		Bukkit.getPlayer(plugin.getPlayers().getPlayer1()).setScoreboard(scoreboard);
		Bukkit.getPlayer(plugin.getPlayers().getPlayer2()).setScoreboard(scoreboard);
		
	}
	
	/*
	 * Update the scoreboard
	 */

	@Override
	public void updateScoreboard() {
		
		scoreboardPlayer1.setScore(scorePlayer1);
		scoreboardPlayer2.setScore(scorePlayer2);
		
		Bukkit.getPlayer(plugin.getPlayers().getPlayer1()).setScoreboard(scoreboard);
		Bukkit.getPlayer(plugin.getPlayers().getPlayer2()).setScoreboard(scoreboard);
		
	}
	
	


}
