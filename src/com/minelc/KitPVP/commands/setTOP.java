package com.minelc.KitPVP.commands;

import com.minelc.CORE.Controller.Jugador;
import com.minelc.KitPVP.KitPVPMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class setTOP implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player))
            return  false;
        Jugador jug = Jugador.getJugador((Player) sender);
        if(!(jug.is_Owner() || jug.is_Admin()))
            return false;
        if(args.length <= 0){
            sender.sendMessage(ChatColor.RED + "ingresa un numero del 1 - 3");
            return false;
        }
        int top = Integer.parseInt(args[0]);
        if(top > 3 || top < 1){
            sender.sendMessage(ChatColor.RED + "Solo puedes ingresar valores del 1 al 3");
            return false;
        }
        Location loc = ((Player) sender).getLocation();

        KitPVPMain.get().getConfig().set("top." + top+".x", loc.getX());
        KitPVPMain.get().getConfig().set("top." +top +".y", loc.getY());
        KitPVPMain.get().getConfig().set("top." +top+".z", loc.getZ());
        KitPVPMain.get().getConfig().set("top."+top+".pitch", loc.getPitch());
        KitPVPMain.get().getConfig().set("top."+top+".yaw", loc.getYaw());
        KitPVPMain.get().reloadConfig();
        KitPVPMain.createNPC((Player) sender, top);

        return false;
    }
}
