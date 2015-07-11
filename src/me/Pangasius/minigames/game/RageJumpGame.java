package me.Pangasius.minigames.game;

import me.Pangasius.minigames.Locations;
import me.Pangasius.minigames.Main;
import me.Pangasius.minigames.Messages;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

public class RageJumpGame extends Game{

	private Main plugin = Main.getMain();
	
	private ItemStack bow;
	
	public Round round = Round.ROUND1;
	
	public int winsPlayer1 = 0;
	public int winsPlayer2 = 0;
	
	private Score scoreboardPlayer1;
	private Score scoreboardPlayer2;
	
	public RageJumpGame() {
		super(GameType.RAGE_JUMP);
		
		bow = new ItemStack(Material.BOW);
		bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 2);
		bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
		
		ItemMeta meta = bow.getItemMeta();
		meta.setDisplayName("§aRage§eBow");
		bow.setItemMeta(meta);
		
	}

	@Override
	public void teleportToSpawns() {
		
		Bukkit.getPlayer(plugin.getPlayers().getPlayer1()).teleport(Locations.getRageJumpSpawn1());
		Bukkit.getPlayer(plugin.getPlayers().getPlayer2()).teleport(Locations.getRageJumpSpawn2());
		
	}

	@Override
	public void fillInventories() {
		
		Player player1 = Bukkit.getPlayer(plugin.getPlayers().getPlayer1());
		
		player1.getInventory().addItem(bow);
		player1.getInventory().addItem(new ItemStack(Material.ARROW, 1));
		
		Player player2 = Bukkit.getPlayer(plugin.getPlayers().getPlayer2());
		
		player2.getInventory().addItem(bow);
		player2.getInventory().addItem(new ItemStack(Material.ARROW, 1));
		
	}

	@Override
	public void begin() {
		
		plugin.getStats().addRageJumpGames(plugin.getPlayers().getPlayer1());
		plugin.getStats().addRageJumpGames(plugin.getPlayers().getPlayer2());
		
	}

	@Override
	public void end() {
		
		if(winsPlayer1 > winsPlayer2){
			
			broadcastToPlayers(Messages.prefix + Bukkit.getPlayer(plugin.getPlayers().getPlayer1()).getName() + " hat das Spiel gewonnen!");
			broadcastToPlayers(Messages.prefix + "Das Spiel 'RageJump' endete " + winsPlayer1 + ":" + winsPlayer2);
			
			plugin.getStats().addRageJumpWins(plugin.getPlayers().getPlayer1());
			
		}else{
			
			broadcastToPlayers(Messages.prefix + Bukkit.getPlayer(plugin.getPlayers().getPlayer2()).getName() + " hat das Spiel gewonnen!");
			broadcastToPlayers(Messages.prefix + "Das Spiel 'RageJump' endete " + winsPlayer1 + ":" + winsPlayer2);
			
			plugin.getStats().addRageJumpWins(plugin.getPlayers().getPlayer2());
			
		}
		
		plugin.setGame(new ChickenSearchGame());
		plugin.getPlayers().clear();
		
	}

	@Override
	public void initScoreboard() {
		
		Objective obj = scoreboard.registerNewObjective("fungames", "sb");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName("§aRAGEJUMP");
		
		scoreboardPlayer1 = obj.getScore(Bukkit.getPlayer(plugin.getPlayers().getPlayer1()).getName());
		scoreboardPlayer1.setScore(winsPlayer1);
		
		scoreboardPlayer2 = obj.getScore(Bukkit.getPlayer(plugin.getPlayers().getPlayer2()).getName());
		scoreboardPlayer2.setScore(winsPlayer2);
		
		Bukkit.getPlayer(plugin.getPlayers().getPlayer1()).setScoreboard(scoreboard);
		Bukkit.getPlayer(plugin.getPlayers().getPlayer2()).setScoreboard(scoreboard);
	}

	@Override
	public void updateScoreboard() {
		
		scoreboardPlayer1.setScore(winsPlayer1);
		scoreboardPlayer2.setScore(winsPlayer2);
		
		Bukkit.getPlayer(plugin.getPlayers().getPlayer1()).setScoreboard(scoreboard);
		Bukkit.getPlayer(plugin.getPlayers().getPlayer2()).setScoreboard(scoreboard);
		
	}
	
}
