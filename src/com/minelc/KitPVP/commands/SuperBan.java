package com.minelc.KitPVP.commands;

import com.minelc.CORE.Controller.Jugador;
import com.minelc.KitPVP.KitPVPMain;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;





public class SuperBan
  implements CommandExecutor
{
	private KitPVPMain plugin;
  public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
    Player p = (Player)s;
    Jugador jug = Jugador.getJugador(p);
    
    if (jug.is_Admin()||jug.is_MODERADOR()) {
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
        	String Name = ptp.getName();
        	String ip = ptp.getAddress().getHostString();
        	if(ip.equals("142.44.142.214")) {
        		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lMOD> &cEste usuario entro correctamente, si quieres banearlo usa &4/ban"));
        	} else {
        		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "ban-ip " + ip + " No puedes entrar de este modo.");
                for(Player player : Bukkit.getServer().getOnlinePlayers()){
      				 if(player.hasPermission("broadcast.message")) {
      					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lSuperBan> &4&lEl Jugador: &c" + Name + " &4&lcon la ip: &c"+ ip + " &4&lHa sido sancionado!")); 
      				 }
      				}
                for (Player Online : Bukkit.getOnlinePlayers())
                  p.showPlayer(Online); 
        	}
          
        }
        else {
          p.sendMessage(ChatColor.DARK_PURPLE + ""+ChatColor.BOLD + "MOD > " + ChatColor.YELLOW + "No se encontro al jugador " + ChatColor.RED + args[0] + ChatColor.YELLOW + "!");
        } 
      } else if (args.length == 2) {
        Player ptp1 = null;
        Player ptp2 = null;
        for (Player Online : Bukkit.getOnlinePlayers()) {
          if (Online.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
            ptp1 = Online;
          }
          
          if (Online.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
            ptp2 = Online;
          }
          if (ptp1 != null && ptp2 != null)
            break; 
        }  if (ptp1 != null && ptp2 != null) {


          
          for (Player Online : Bukkit.getOnlinePlayers())
            p.showPlayer(Online); 
        } else {
          p.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "MOD > " + ChatColor.YELLOW + "No se encontro al jugador!");
        } 
      } else {
        
        p.sendMessage(ChatColor.DARK_PURPLE + ""+ ChatColor.BOLD + "MOD > " + ChatColor.YELLOW + "Usa /sban <jugador>!");
      } 
    } else {
      p.sendMessage(ChatColor.RED + "Solo los moderadores pueden usar este comando!");
    } 
    return true;
  }
}