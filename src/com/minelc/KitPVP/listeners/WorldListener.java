package com.minelc.KitPVP.listeners;

import com.minelc.KitPVP.KitPVPMain;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class WorldListener implements Listener {
	
	//@EventHandler
	//public void onBlockDamage(BlockDamageEvent e) {
		//e.setCancelled(false);
	//}
	
	@EventHandler
	public void onMobSpawn(CreatureSpawnEvent e) {

		if(e.getEntity().getType() != EntityType.ARMOR_STAND){
			e.setCancelled(true);
		}

	}
	
	@EventHandler
	public void onBlockIgnite(BlockIgniteEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onSignPlace(SignChangeEvent e) {
		int line = 0;
		for(String s : e.getLines()) {
			e.setLine(line, ChatColor.translateAlternateColorCodes('&', s));
			line++;
		}
	}
	
	@EventHandler 
    public void onItemFrameInteract(PlayerInteractEntityEvent e) {
		Player player = e.getPlayer();
		if(e.getRightClicked().getType() == EntityType.ITEM_FRAME) {
			if(player.isOp()) {
				e.setCancelled(false);
			} else {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent e) {
		Player player = e.getPlayer();


		if(!KitPVPMain.allowed.contains(player.getName())){
			e.setCancelled(true);
		}

	}
	
	@EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent e) {
		Player player = e.getPlayer();
		if(!KitPVPMain.allowed.contains(player.getName())){
			e.setCancelled(true);
		}

	}
	
}
