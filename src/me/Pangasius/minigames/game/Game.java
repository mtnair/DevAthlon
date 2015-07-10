package me.Pangasius.minigames.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;

import me.Pangasius.minigames.Locations;
import me.Pangasius.minigames.Main;
import me.Pangasius.minigames.Messages;

public abstract class Game {
	
	private GameType type;
	public Main plugin = Main.getMain();
	private int waitingPeriod = 6;
	private boolean running = false;
	private int waitingPeriodScheduler;
	
	public Scoreboard scoreboard;
	
	public Game(GameType type){
		
		this.type = type;
		
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		
	}
	
	@SuppressWarnings("deprecation")
	public void start(){
		
		teleportToSpawns();
		initScoreboard();
		
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
					
					running = true;
					begin();
					fillInventories();
					
					Bukkit.getScheduler().cancelTask(waitingPeriodScheduler);
					
				}
				
				
			}

		}, 0, 20);
		
	}
	
	public void stop(){
		
		running = false;
		end();
		teleportPlayersToLobby();
		
		if(plugin.getPlayers().getPlayer1() != null) Bukkit.getPlayer(plugin.getPlayers().getPlayer1()).getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		if(plugin.getPlayers().getPlayer2() != null) Bukkit.getPlayer(plugin.getPlayers().getPlayer2()).getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		plugin.getPlayers().clear();
		
	}
	
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
	
	public GameType getType(){
		
		return type;
		
	}
	
	public boolean isRunning(){
		
		return running;
		
	}
	
	public boolean isWaitingPeriod(){
		
		return waitingPeriod > 0 && waitingPeriod < 6;
		
	}
	
	public void broadcastToPlayers(String message){
		
		Bukkit.getPlayer(plugin.getPlayers().getPlayer1()).sendMessage(message);
		Bukkit.getPlayer(plugin.getPlayers().getPlayer2()).sendMessage(message);
		
	}
	
	public void clearInventories(){
		
		Player player1 = Bukkit.getPlayer(plugin.getPlayers().getPlayer1());
		
		player1.getInventory().clear();
		player1.setLevel(0);
		player1.setExp(0);
		player1.getInventory().setArmorContents(null);
		
		Player player2 = Bukkit.getPlayer(plugin.getPlayers().getPlayer2());
		
		player2.getInventory().clear();
		player2.setLevel(0);
		player2.setExp(0);
		player2.getInventory().setArmorContents(null);
		
	}

}
