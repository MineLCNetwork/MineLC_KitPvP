package com.minelc.KitPVP.commands;

import com.minelc.KitPVP.KitPVPMain;

public class SetSpawnCmd extends BaseCmd { 
	
	public SetSpawnCmd() {
		forcePlayer = true;
		cmdName = "setspawn";
		argLength = 1; //counting cmdName
		usage = "";
		desc = ":: Sets the spawn return point";

	}

	@Override
	public boolean run() {
		KitPVPMain.getCfg().setSpawn(player.getLocation());
		sender.sendMessage("Spawn colocado!");
		return true;
	}

}
