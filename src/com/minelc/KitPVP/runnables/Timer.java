package com.minelc.KitPVP.runnables;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Timer implements Runnable {
	int restartCount = 86400; // 24 hrs
	
	@Override
	public void run() {
		//autoRestart
		if(restartCount > 1 && restartCount <= 10) {
			broadcast(ChatColor.RED+"El servidor sera reiniciado en "+restartCount+" segundos!");
		} else if(restartCount == 1) {
			broadcast(ChatColor.RED+"El servidor sera reiniciado en "+restartCount+" segundo!");
		} else if(restartCount == 0) {
			Bukkit.shutdown();
		}
		restartCount--;
	}
	
	private static void broadcast(String msg) {
		for(Player Online : Bukkit.getOnlinePlayers()) {
			Online.sendMessage(msg);
		}
	}

}
