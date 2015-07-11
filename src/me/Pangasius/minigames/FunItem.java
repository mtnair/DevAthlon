package me.Pangasius.minigames;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@SuppressWarnings("deprecation")
public class FunItem {
	
	private static ItemStack item;
	
	static{
		
		if(item == null){
			
			item = new ItemStack(351);
			item.setDurability((short) 13);
			
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§5FunGun");
			
			item.setItemMeta(meta);
			
		}
		
	}
	
	public static void giveItem(Player p){
		
		p.getInventory().setItem(4, item);
		
	}
	
	public static ItemStack getItem(){
		
		return item;
		
	}

}
