package com.minelc.KitPVP.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minelc.CORE.Controller.Jugador;

public abstract class BaseCmd {

	public BaseCmd() {
		
	}
	
	
	public CommandSender sender;
	public String[] args;
	public String cmdName;
	public int argLength = 0;
	public boolean forcePlayer = true;
	public String usage = "";
	public Player player;
	public String desc = "";

	public boolean processCmd(CommandSender s, String[] arg) {
		sender = s;
		args = arg;

		if (forcePlayer) {
			if (!(s instanceof Player))  {
				sender.sendMessage("Solo para jugadores");
				return false;
			} else {
				player = (Player) s;
			}
		}
		
		if (!Jugador.getJugador((Player)sender).is_Owner())
			sender.sendMessage("No tienes permisos para usar este comando");
		else if (argLength > arg.length)
			s.sendMessage(ChatColor.DARK_RED + "usa: " + ChatColor.GRAY +"/sw " + helper());
		else return run();
		return true;
	}

	public abstract boolean run();
	
	
	public String helper() {
		return ChatColor.RED + cmdName + " " + usage + " "+ ChatColor.GRAY + desc;
	}
}
