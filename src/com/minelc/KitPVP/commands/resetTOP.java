package com.minelc.KitPVP.commands;

import com.minelc.CORE.Controller.Database;
import com.minelc.CORE.Controller.Jugador;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class resetTOP implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String arg, String[] args) {

        if(!(sender instanceof Player)){
            return false;
        }
        Jugador jug = Jugador.getJugador((Player) sender);
        if(!jug.is_Admin()){
            sender.sendMessage(ChatColor.RED + "No tienes permiso para usar este comando.");
            return false;
        }
        Connection connection = Database.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("UPDATE `SV_KITPVP` ");
            queryBuilder.append("SET `stats_kills_month` = 0); ");

            preparedStatement = connection.prepareStatement(queryBuilder.toString());
            preparedStatement.executeUpdate();
        } catch (final Exception Exception) {
            Exception.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

            for(Player p : Bukkit.getOnlinePlayers()){
                Jugador juga = Jugador.getJugador(p);
                juga.setMonthKillStat(0);
                Database.savePlayerSV_KITPVP(juga);
            }

            jug.setSaveSQL(true);


        return true;
    }
}
