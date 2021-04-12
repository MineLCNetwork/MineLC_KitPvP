package com.minelc.KitPVP.commands;

import com.minelc.KitPVP.KitPVPMain;

public class addArenaSpawnCmd extends BaseCmd { 
	
	public addArenaSpawnCmd() {
		forcePlayer = true;
		cmdName = "addarenaspawn";
		argLength = 1; //counting cmdName
		usage = "";
		desc = ":: Agregar un spawn al mapa!";

	}

	@Override
	public boolean run() {
		KitPVPMain.getCfg().addArenaSpawn(player.getLocation());
		sender.sendMessage("Spawn agregado!!");
		return true;
	}

}
