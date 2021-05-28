package com.minelc.KitPVP.commands;

import com.minelc.CORE.Controller.Jugador;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearEnderchestCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Lo siento hijo, sólo jugadores pueden ejecutar esta mondá.");
            return false;
        }

        Player p = (Player) commandSender;
        Jugador jug = Jugador.getJugador(p);

        if (jug.is_Admin()) {
            if (args.length == 1) {
                Player ptp = null;
                for (Player Online : Bukkit.getOnlinePlayers()) {
                    if (Online.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
                        ptp = Online;
                        if (Online.getName().equalsIgnoreCase(args[0])) {
                            break;
                        }
                    }
                }
                if (ptp != null) {
                    p.sendMessage(ChatColor.DARK_PURPLE + ""+ChatColor.BOLD + "MOD > " + ChatColor.YELLOW + "¡Reseteando el inventario de " + ChatColor.GREEN + ptp.getName() + ChatColor.YELLOW + "!");
                    ptp.getEnderChest().clear();
                } else {
                    p.sendMessage(ChatColor.DARK_PURPLE + ""+ChatColor.BOLD + "MOD > " + ChatColor.RED + "¡No se encontro al jugador " + ChatColor.YELLOW + args[0] + ChatColor.RED + "!");
                }
            } else {
                p.sendMessage(ChatColor.DARK_PURPLE + ""+ChatColor.BOLD + "MOD > " + ChatColor.YELLOW + "/" + command.getName() + " <jugador>");
            }
        } else {
            p.sendMessage(ChatColor.RED + "Solo los moderadores pueden usar este comando!");
        }
        return false;
    }
}
