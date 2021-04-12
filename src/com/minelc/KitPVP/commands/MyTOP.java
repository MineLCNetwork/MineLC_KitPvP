package com.minelc.KitPVP.commands;

import com.minelc.CORE.Controller.Jugador;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MyTOP implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)){
            return false;
        }

        Jugador jug = Jugador.getJugador((Player) sender);
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aTu nivel en el top mensual es de: &6" + jug.getMonthKillStat() + " kills."));

        return true;
    }
}
