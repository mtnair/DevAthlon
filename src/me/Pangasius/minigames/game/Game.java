package me.Pangasius.minigames.game;

import me.Pangasius.minigames.Locations;
import me.Pangasius.minigames.Main;
import me.Pangasius.minigames.Messages;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;

public abstract class Game {
	
	/*
	 * Variables
	 */
	
	private GameType type;
	public Main plugin = Main.getMain();
	private int waitingPeriod = 6;
	private boolean running = false;
	private int waitingPeriodScheduler;
	
	public Scoreboard scoreboard;
	
	/*
	 * Constructor
	 */
	
	public Game(GameType type){
		
		this.type = type;
		
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		
	}
	
	/*
	 * Executed on games start
	 */
	
	@SuppressWarnings("deprecation")
	public void start(){
		
		teleportToSpawns();
		initScoreboard();
		clearInventories();
		fillInventories();
		
		/*
		 * Start the waiting period
		 */
		
		waitingPeriodScheduler = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				
				waitingPeriod--;
				
				if(waitingPeriod > 1){
					
					broadcastToPlayers(Messages.prefix + " Das Spiel startet in " + waitingPeriod + " Sekunden!");
					
				}else if(waitingPeriod == 1){
					
					broadcastToPlayers(Messages.prefix + " Das Spiel startet in 1 Sekunde!");
					
				}
				
				if(waitingPeriod == 0){
					
					broadcastToPlayers(Messages.prefix + " Das Spiel startet jetzt!");
					running = true;
					begin();
					fillInventories();
					
					Bukkit.getScheduler().cancelTask(waitingPeriodScheduler);
					
				}
				
				
			}

		}, 0, 20);
		
	}
	
	/*
	 * Executed when the game stopped
	 */
	
	public void stop(){
		
		running = false;
		teleportPlayersToLobby();
		clearInventories();
		
		if(plugin.getPlayers().getPlayer1() != null) Bukkit.getPlayer(plugin.getPlayers().getPlayer1()).getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		if(plugin.getPlayers().getPlayer2() != null) Bukkit.getPlayer(plugin.getPlayers().getPlayer2()).getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		scoreboard.clearSlot(DisplaySlot.SIDEBAR);
		end();
		
	}
	
	/*
	 * Method to teleport all players back to the lobby
	 */
	
	public void teleportPlayersToLobby(){
		
		if(plugin.getPlayers().getPlayer1() != null) Bukkit.getPlayer(plugin.getPlayers().getPlayer1()).teleport(Locations.getLobbySpawn());
		if(plugin.getPlayers().getPlayer2() != null) Bukkit.getPlayer(plugin.getPlayers().getPlayer2()).teleport(Locations.getLobbySpawn());
		
	}
	
	public abstract void teleportToSpawns();
	public abstract void fillInventories();
	public abstract void begin();
	public abstract void end();
	public abstract void initScoreboard();
	public abstract void updateScoreboard();
	
	/*
	 * Returns the type of the game
	 */
	
	public GameType getType(){
		
		return type;
		
	}
	
	/*
	 * Getters to get the state of the game
	 */
	
	public boolean isRunning(){
		
		return running && waitingPeriod == 0;
		
	}
	
	public boolean isWaitingPeriod(){
		
		return waitingPeriod > 0 && waitingPeriod < 6;
		
	}
	
	/*
	 * Broadcast a message to all players
	 */
	
	public void broadcastToPlayers(String message){
		
		if(plugin.getPlayers().getPlayer1() != null) Bukkit.getPlayer(plugin.getPlayers().getPlayer1()).sendMessage(message);
		if(plugin.getPlayers().getPlayer2() != null) Bukkit.getPlayer(plugin.getPlayers().getPlayer2()).sendMessage(message);
		
	}
	
	/*
	 * Clear the player's inventories and change their gamemode
	 */
	
	public void clearInventories(){
		
		Player player1 = Bukkit.getPlayer(plugin.getPlayers().getPlayer1());
		
		player1.getInventory().clear();
		player1.setLevel(0);
		player1.setExp(0);
		player1.getInventory().setArmorContents(null);
		player1.setGameMode(GameMode.SURVIVAL);
		
		Player player2 = Bukkit.getPlayer(plugin.getPlayers().getPlayer2());
		
		player2.getInventory().clear();
		player2.setLevel(0);
		player2.setExp(0);
		player2.getInventory().setArmorContents(null);
		player2.setGameMode(GameMode.SURVIVAL);
		
	}

}
