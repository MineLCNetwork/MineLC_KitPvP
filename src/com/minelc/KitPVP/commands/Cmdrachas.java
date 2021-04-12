package com.minelc.KitPVP.commands;

import com.minelc.CORE.Controller.Database;
import com.minelc.CORE.Controller.Jugador;
import com.minelc.KitPVP.controllers.LobbyGameController;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;




public class Cmdrachas
  implements Listener, CommandExecutor
{
  public static final String ANSI_RED = "\033[31m";
  public static final String ANSI_GREEN = "\033[32m";
  public static final String ANSI_RESET = "\033[0m";
  
  public boolean onCommand(CommandSender sender, Command command, String commandlabel, String[] args) {
    if (sender instanceof Player) {
      sender.sendMessage(ChatColor.WHITE + "Error, Comando desconocido.");
    } else {
      
      if (args.length == 0) {
        
        System.out.println("\033[32m/rachas <player> <cantidad>\033[0m");
        return true;
      } 
      if (args.length == 1) {
        
        System.out.println("\033[32m/rachas <player> <cantidad>\033[0m");
        return true;
      } 
      if (args.length == 2) {
        
        int rachas = Integer.parseInt(args[1]);
        Player p2 = Bukkit.getPlayer(args[0]).getPlayer().getKiller();
        Jugador jug2 = Jugador.getJugador(p2);
        Database.loadPlayerCoins_SYNC(jug2);
        Database.loadPlayerRank_SYNC(jug2);
        
        if (!p2.isOnline()) {
          
          System.out.println("\033[31mEl jugador no esta conectado.\033[0m");
          return false;
        } 
        else if(p2.isOnline()) {
          for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6" + p2.getName() + " &ftiene una racha de &6" + rachas + " kills!!"));
          }
          LobbyGameController.getLobby().updateScoreboard(jug2);
          return true;
        }
    
      } 




      
      return true;
    } 
    
    return true;
  }
}

