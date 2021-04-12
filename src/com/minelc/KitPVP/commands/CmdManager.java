package com.minelc.KitPVP.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minelc.CORE.Controller.Jugador;


public class CmdManager implements CommandExecutor {

	private List<BaseCmd> cmds = new ArrayList<BaseCmd>();

	//Add New Commands Here
	public CmdManager() {
		cmds.add(new SetSpawnCmd());
		cmds.add(new addArenaSpawnCmd());
	}

	public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
		if (args.length == 0 || getCommands(args[0]) == null) {
			for (BaseCmd cmd : cmds) {
				if (Jugador.getJugador((Player) s).is_Owner()) s.sendMessage(ChatColor.GRAY + "- " + ChatColor.RED + "/minelc " + cmd.helper());
			}
		} else getCommands(args[0]).processCmd(s, args);
		return true;
	}

	private BaseCmd getCommands(String s) {
		for (BaseCmd cmd : cmds) {
			if (cmd.cmdName.equalsIgnoreCase(s)) {
				return cmd;
			}
		}
		return null;
	}




}

