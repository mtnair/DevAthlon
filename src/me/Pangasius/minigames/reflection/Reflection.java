package me.Pangasius.minigames.reflection;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;

public class Reflection {
	
	/*
	 * The servers version
	 */
	
	private static String version;
	
	static{
		
		/*
		 * Load the servers version if it isn't already
		 */
		
		if(version == null){
			
		    version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
			
		}
		
	}
	
	/*
	 * Get a craftbukkit class
	 */
	
	public static Class<?> getCraftBukkitClass(String classString) throws ClassNotFoundException{
		
		return Class.forName("org.bukkit.craftbukkit." + version + "." + classString);
		
	}
	
	/*
	 * Get a native minecraft source class
	 */
	
	public static Class<?> getNMSClass(String classString) throws ClassNotFoundException{
		
		return Class.forName("net.minecraft.server." + version + "." + classString);
		
	}
	
	/*
	 * Set a field's value in the given class
	 */
	
	public static void setValue(Field field, Object clazz, Object newValue) throws IllegalArgumentException, IllegalAccessException{
		
		field.setAccessible(true);
		field.set(clazz, newValue);
		
	}

}
