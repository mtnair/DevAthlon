package me.Pangasius.minigames.reflection;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;

public class Reflection {
	
private static String version;
	
	static{
		
		if(version == null){
			
		    version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
			
		}
		
	}
	
	public static Class<?> getCraftBukkitClass(String classString) throws ClassNotFoundException{
		
		return Class.forName("org.bukkit.craftbukkit." + version + "." + classString);
		
	}
	
	public static Class<?> getNMSClass(String classString) throws ClassNotFoundException{
		
		return Class.forName("net.minecraft.server." + version + "." + classString);
		
	}
	
	public static void setValue(Field field, Object clazz, Object newValue) throws IllegalArgumentException, IllegalAccessException{
		
		field.setAccessible(true);
		field.set(clazz, newValue);
		
	}

}
