package me.Pangasius.minigames.game;

public abstract class Game {
	
	private GameType type;
	
	public Game(GameType type){
		
		this.type = type;
		
	}
	
	public abstract void start();
	public abstract void stop();
	
	public GameType getType(){
		
		return type;
		
	}

}
