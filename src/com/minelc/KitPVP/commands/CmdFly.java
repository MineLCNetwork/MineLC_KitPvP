package com.minelc.KitPVP.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minelc.CORE.Controller.Jugador;


public class CmdFly implements CommandExecutor {

	public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
		Player p = (Player) s;
		Jugador jug = Jugador.getJugador(p);
		
		if(jug.is_MODERADOR()) {
			if(p.isFlying()) {
				p.setAllowFlight(false);
				p.setFlying(false);
				p.sendMessage("");
				p.sendMessage(ChatColor.DARK_PURPLE+""+ChatColor.BOLD+"MOD > "+ChatColor.YELLOW+"Vuelo"+ChatColor.GRAY+":"+ChatColor.RED+" Desactivado");
			} else {
				p.setAllowFlight(true);
				p.setFlySpeed(0.2f);
				p.sendMessage("");
				p.sendMessage(ChatColor.DARK_PURPLE+""+ChatColor.BOLD+"MOD > "+ChatColor.YELLOW+"Vuelo"+ChatColor.GRAY+":"+ChatColor.GREEN+" Activado");
			}
		} else {
			p.sendMessage(ChatColor.RED+"Solo los moderadores pueden usar este comando!");
		}
		
		return true;
	}
}

