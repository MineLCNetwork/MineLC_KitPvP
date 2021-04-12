package com.minelc.KitPVP.commands;

import com.minelc.CORE.Controller.Database;
import com.minelc.CORE.Controller.Jugador;
import com.minelc.KitPVP.controllers.LobbyGameController;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Locale;


public class winnerCoins
  implements Listener, CommandExecutor
{
  public static final String ANSI_RED = "\033[31m";
  public static final String ANSI_GREEN = "\033[32m";
  public static final String ANSI_RESET = "\033[0m";
  
  public boolean onCommand(CommandSender sender, Command command, String commandlabel, String[] args) {
    // coins
    // coins add <player> <cantidad>
    // coins remove <player> <cantidad>
    // coins set  <player> <cantidad>
    // coins get <player>
      if(sender instanceof Player){
        Jugador jug = Jugador.getJugador((Player) sender);
        if(!jug.is_Admin()){
            sender.sendMessage(ChatColor.RED + "No puedes ejecutar este comando.");
            return false;
        }
      }

      if (args.length < 1) {
       sender.sendMessage(ChatColor.GREEN + "Usa: /coins <add/remove/get,set> ");
        return false;
      }

      switch (args[0].toLowerCase()){
        case "add" :
             addCoins(sender,args);
             break;
        case "remove" :
          removeCoins(sender,args);
          break;
        case "set" :
            setCoins(sender, args);
            break;
        case "get":
            getCoins(sender, args);
            break;
        default:
          sender.sendMessage(ChatColor.GREEN + "Usa: /coins <add/remove/get,set> ");
      }
    
    return true;
  }

  private void getCoins(CommandSender sender, String[] args) {
    if(args.length <2){
      sender.sendMessage(ChatColor.RED + "Usa: /coins get <player>");
      return;

    }
    Player target = Bukkit.getPlayer(args[1]);
    if(target == null){
      sender.sendMessage(ChatColor.RED + "El jugador no está conectado.");
      return;
    }
    Jugador jug = Jugador.getJugador(target);
    sender.sendMessage(ChatColor.WHITE + "El jugador " + ChatColor.DARK_GRAY + target.getName() + ChatColor.WHITE + " tiene: " + ChatColor.GOLD + "" + jug.getLcoins() + "LCoins.");

  }

  private void setCoins(CommandSender sender, String[] args) {
    if(args.length < 3 ){
      sender.sendMessage(ChatColor.RED + "Usa: /coins set <player> <cantidad> ");
      return;
    }
    Player target = Bukkit.getPlayer(args[1]);
    if(target == null){
      sender.sendMessage(ChatColor.RED + "El jugador no está conectado.");
      return;
    }
    int cantidad = 0;
    try{
      cantidad = Integer.parseInt(args[2]);
    } catch (Exception e){
      sender.sendMessage(ChatColor.RED + "La cantidad ingresada debe ser un numeral.");
      return;
    }

    Jugador jug = Jugador.getJugador(target);
    jug.setLcoins(cantidad);
    Database.savePlayerCoins(jug);
    LobbyGameController.getLobby().updateScoreboard(jug);
    sender.sendMessage(ChatColor.GREEN + "Has seteado las LCoins de " + target.getName() + " a: " + cantidad);
    target.sendMessage(ChatColor.GREEN + "Tus coins han sido establecidas a: " + cantidad);
  }

  private void removeCoins(CommandSender sender, String[] args) {
    if(args.length < 3 ){
      sender.sendMessage(ChatColor.RED + "Usa: /coins remove <player> <cantidad> ");
      return;
    }
    Player target = Bukkit.getPlayer(args[1]);
    if(target == null){
      sender.sendMessage(ChatColor.RED + "El jugador no está conectado.");
      return;
    }
    int cantidad = 0;
    try{
      cantidad = Integer.parseInt(args[2]);
    } catch (Exception e){
      sender.sendMessage(ChatColor.RED + "La cantidad ingresada debe ser un numeral.");
      return;
    }

    Jugador jug = Jugador.getJugador(target);
    int lcoins = jug.getLcoins() - cantidad;
    jug.setLcoins(lcoins);
    Database.savePlayerCoins(jug);
    LobbyGameController.getLobby().updateScoreboard(jug);
    sender.sendMessage(ChatColor.GREEN + "Has removido " + cantidad + "LCoins de la cuenta de " + target.getName());
    target.sendMessage(ChatColor.GREEN + "Se han removido " + cantidad + "LCoins de tu cuenta.");



  }

  private void addCoins(CommandSender sender, String[] args) {
    if(args.length < 3 ){
      sender.sendMessage(ChatColor.RED + "Usa: /coins remove <player> <cantidad> ");
      return;
    }
    Player target = Bukkit.getPlayer(args[1]);
    if(target == null){
      sender.sendMessage(ChatColor.RED + "El jugador no está conectado.");
      return;
    }
    int cantidad = 0;
    try{
      cantidad = Integer.parseInt(args[2]);
    } catch (Exception e){
      sender.sendMessage(ChatColor.RED + "La cantidad ingresada debe ser un numeral.");
      return;
    }

    Jugador jug = Jugador.getJugador(target);
    int lcoins = jug.getLcoins() + cantidad;
    jug.setLcoins(lcoins);
    Database.savePlayerCoins(jug);
    LobbyGameController.getLobby().updateScoreboard(jug);
    sender.sendMessage(ChatColor.GREEN + "Has agregado " + cantidad + "LCoins de la cuenta de " + target.getName());
    target.sendMessage(ChatColor.GREEN + "Se han agregado " + cantidad + "LCoins de tu cuenta.");

  }
}


