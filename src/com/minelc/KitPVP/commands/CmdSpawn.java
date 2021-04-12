package com.minelc.KitPVP.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minelc.CORE.Controller.Jugador;
import com.minelc.KitPVP.KitPVPMain;
import com.minelc.KitPVP.utilities.Tagged;


public class CmdSpawn implements CommandExecutor {

	public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
		final Player p = (Player) s;
		Jugador jug = Jugador.getJugador(p);
		final Location locbefore = p.getLocation().clone();
		
		if(jug.is_MODERADOR()) {
			p.sendMessage(ChatColor.YELLOW+"Fuiste enviado al spawn");
			p.teleport(KitPVPMain.getCfg().getSpawn());
		} else {
			if((System.currentTimeMillis() - Tagged.getTime(jug)) > 12500) {
				p.sendMessage(ChatColor.YELLOW+"Seras enviado al spawn en "+ChatColor.RED+"5"+ChatColor.YELLOW+" segundos, no te muevas!");
				
				Bukkit.getScheduler().runTaskLater(KitPVPMain.get(), new Runnable() {
					
					@Override
					public void run() {
						if(p.getWorld().getName().equalsIgnoreCase(locbefore.getWorld().getName())) {
							if(p.getLocation().distance(locbefore) <= 2 && !p.isSprinting()) {
								p.sendMessage(ChatColor.YELLOW+"Fuiste enviado al spawn");
								p.teleport(KitPVPMain.getCfg().getSpawn());
							}
						}
					}
				}, 100L);
			} else {
				p.sendMessage(ChatColor.RED+"No puedes usar este comando mientras estes en combate!");
			}
		}
		
		return true;
	}
}