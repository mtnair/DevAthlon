package me.Pangasius.minigames.game;

import me.Pangasius.minigames.Locations;
import me.Pangasius.minigames.Messages;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

public class SnowballFightGame extends Game{

	/*
	 * Variables
	 */
	
	public Round round = Round.ROUND1;
	
	public int winsPlayer1 = 0;
	public int winsPlayer2 = 0;
	
	private Score scoreboardPlayer1;
	private Score scoreboardPlayer2;
	
	/*
	 * Default constructor
	 */
	
	public SnowballFightGame() {
		super(GameType.SNOWBALL_FIGHT);
	}

	/*
	 * Teleport all players to their spawns
	 */
	
	@Override
	public void teleportToSpawns() {
		Bukkit.getPlayer(plugin.getPlayers().getPlayer1()).teleport(Locations.getSnowballFightSpawn1());
		Bukkit.getPlayer(plugin.getPlayers().getPlayer2()).teleport(Locations.getSnowballFightSpawn2());
	}
	
	/*
	 * Give each player 128 snowballs to fight with
	 */
	
	@Override
	public void fillInventories() {
		Player player1 = Bukkit.getPlayer(plugin.getPlayers().getPlayer1());
		
		player1.getInventory().addItem(new ItemStack(Material.SNOW_BALL, 64));
		player1.getInventory().addItem(new ItemStack(Material.SNOW_BALL, 64));
		
		Player player2 = Bukkit.getPlayer(plugin.getPlayers().getPlayer2());
		
		player2.getInventory().addItem(new ItemStack(Material.SNOW_BALL, 64));
		player2.getInventory().addItem(new ItemStack(Material.SNOW_BALL, 64));
		
	}

	/*
	 * Nothing to do here
	 */
	
	@Override
	public void begin() {}

	/*
	 * Check which player has won and let the game stop
	 */
	
	@Override
	public void end() {
	
		if(winsPlayer1 > winsPlayer2){
			
			broadcastToPlayers(Messages.prefix + Bukkit.getPlayer(plugin.getPlayers().getPlayer1()).getName() + " hat das Spiel gewonnen!");
			broadcastToPlayers(Messages.prefix + "Endstand: " + winsPlayer1 + ":" + winsPlayer2);
			
		}else{
			
			broadcastToPlayers(Messages.prefix + Bukkit.getPlayer(plugin.getPlayers().getPlayer2()).getName() + " hat das Spiel gewonnen!");
			broadcastToPlayers(Messages.prefix + "Endstand: " + winsPlayer1 + ":" + winsPlayer2);
			
		}
		
		plugin.setGame(new ChickenSearchGame());
		plugin.getPlayers().clear();
		
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
		scoreboardPlayer1.setScore(winsPlayer1);
		
		scoreboardPlayer2 = obj.getScore(Bukkit.getPlayer(plugin.getPlayers().getPlayer2()).getName());
		scoreboardPlayer2.setScore(winsPlayer2);
		
		Bukkit.getPlayer(plugin.getPlayers().getPlayer1()).setScoreboard(scoreboard);
		Bukkit.getPlayer(plugin.getPlayers().getPlayer2()).setScoreboard(scoreboard);
	}

	/*
	 * Update the scoreboard
	 */
	
	@Override
	public void updateScoreboard() {
		
		scoreboardPlayer1.setScore(winsPlayer1);
		scoreboardPlayer2.setScore(winsPlayer2);
		
		Bukkit.getPlayer(plugin.getPlayers().getPlayer1()).setScoreboard(scoreboard);
		Bukkit.getPlayer(plugin.getPlayers().getPlayer2()).setScoreboard(scoreboard);
		
	}
	
	/*
	 * Enum the check which round the game is currently in
	 */
	
	public enum Round{
		
		ROUND1, ROUND2, ROUND3;
		
	}

}
