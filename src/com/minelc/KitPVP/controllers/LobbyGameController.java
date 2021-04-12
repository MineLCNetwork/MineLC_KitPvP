package com.minelc.KitPVP.controllers;

import java.util.HashMap;
import java.util.Map;

import com.minelc.CORE.Controller.Database;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.minelc.CORE.Controller.Jugador;
import com.minelc.CORE.Controller.Ranks;
import com.minelc.KitPVP.KitPVPMain;

public class LobbyGameController {
	private static LobbyGameController Lobby = null;
	private Map<String, Team> TEAMS = new HashMap<>();

	public static LobbyGameController getLobby() {
		if(Lobby == null)
			Lobby = new LobbyGameController();

			return Lobby;
	}

	public void deleteLobby() {
		Lobby = null;
	}
	
	public void prepareforLeave(Player p) {
		if(TEAMS.containsKey("LCoins"+p.getName())) {
			TEAMS.remove("LCoins"+p.getName()).unregister();;
		}
		if(TEAMS.containsKey("kills"+p.getName())) {
			TEAMS.remove("kills"+p.getName()).unregister();;
		}
		if(TEAMS.containsKey("muertes"+p.getName())) {
			TEAMS.remove("muertes"+p.getName()).unregister();;
		}
		if(TEAMS.containsKey("progreso"+p.getName())) {
			TEAMS.remove("progreso"+p.getName()).unregister();;
		}
		if(TEAMS.containsKey("level"+p.getName())) {
			TEAMS.remove("level"+p.getName()).unregister();;
		}
		if(TEAMS.containsKey("bar"+p.getName())) {
			TEAMS.remove("bar"+p.getName()).unregister();;
		}
		if(TEAMS.containsKey("kdr"+p.getName())) {
			TEAMS.remove("kdr"+p.getName()).unregister();;
		}if(TEAMS.containsKey("jugadores"+p.getName())) {
			TEAMS.remove("jugadores"+p.getName()).unregister();;
		}
		
		KitPVPMain.get().removeVanished(p);
		KitPVPMain.get().removeInvensible(p);
	}
	
	public LobbyGameController() {
		Lobby = this;
	}
	
	public void prepareforLobby(final Player p, boolean giveKit) {
		final Jugador jug = Jugador.getJugador(p);
		Database.loadPlayerSV_KITPVP_SYNC(jug);
		p.setFlying(false);
		p.setAllowFlight(false);
		p.setFoodLevel(20);
		p.setHealth(20D);
		p.setHealth(p.getHealth());
		updateScoreboard(jug);
		if(p.getGameMode() != GameMode.SURVIVAL)
			p.setGameMode(GameMode.SURVIVAL);

		if(giveKit) {
			Bukkit.getScheduler().runTaskLater(KitPVPMain.get(), new Runnable() {
				@Override
				public void run() {
					try {
						KitPVPMain.getKC().populateInventory(p, KitPVPMain.getKC().getByName(jug.getKitPVP_Kit()));
						KitPVPMain.getKC().givePotionEffects(p, KitPVPMain.getKC().getByName(jug.getKitPVP_Kit()));
					} catch(Exception ex) {
						
					}
				}
			}, 1L);
		}
	}
	
	public void updateScoreboard(Jugador jugOnline) {
		Player Online = jugOnline.getBukkitPlayer();
		
		Scoreboard sb = Online.getScoreboard();
		
		if(sb == null) {
			sb = Bukkit.getScoreboardManager().getNewScoreboard();
			jugOnline.getBukkitPlayer().setScoreboard(sb);
			
			if(TEAMS.containsKey("LCoins"+Online.getName())) {
				TEAMS.remove("LCoins"+Online.getName());
			}
			if(TEAMS.containsKey("kills"+Online.getName())) {
				TEAMS.remove("kills"+Online.getName());
			}
			if(TEAMS.containsKey("muertes"+Online.getName())) {
				TEAMS.remove("muertes"+Online.getName());
			}
			if(TEAMS.containsKey("kdr"+Online.getName())) {
				TEAMS.remove("kdr"+Online.getName());
			}
			if(TEAMS.containsKey("jugadores"+Online.getName())) {
				TEAMS.remove("jugadores"+Online.getName());
			}
			if(TEAMS.containsKey("nivel"+Online.getName())) {
				TEAMS.remove("nivel"+Online.getName());
			}
			if(TEAMS.containsKey("progreso"+Online.getName())) {
				TEAMS.remove("progreso"+Online.getName());
			}
			 if(TEAMS.containsKey("bar"+Online.getName())) {
				TEAMS.remove("bar"+Online.getName());
			}
		}

		 if(!TEAMS.containsKey("LCoins"+Online.getName())) {
			Team refill = sb.registerNewTeam(ChatColor.YELLOW+"");
			refill.addEntry(ChatColor.YELLOW+"");
			TEAMS.put("LCoins"+Online.getName(), refill);
		}
		if(!TEAMS.containsKey("kills"+Online.getName())) {
			Team kills = sb.registerNewTeam("kills");
			kills.addEntry("Kills:");
			TEAMS.put("kills"+Online.getName(), kills);
		}
		if(!TEAMS.containsKey("muertes"+Online.getName())) {
			Team jugadores = sb.registerNewTeam("muertes");
			jugadores.addEntry("Muertes:");
			TEAMS.put("muertes"+Online.getName(), jugadores);
		}
		if(!TEAMS.containsKey("kdr"+Online.getName())) {
			Team jugadores = sb.registerNewTeam("kdr");
			jugadores.addEntry("KDR:");
			TEAMS.put("kdr"+Online.getName(), jugadores);
		}
		if(!TEAMS.containsKey("jugadores"+Online.getName())) {
			Team jugadores = sb.registerNewTeam("jugadores");
			jugadores.addEntry("Jugadores:");
			TEAMS.put("jugadores"+Online.getName(), jugadores);
		}
		if(!TEAMS.containsKey("nivel"+Online.getName())) {
			Team jugadores = sb.registerNewTeam("nivel");
			jugadores.addEntry("Nivel:");
			TEAMS.put("nivel"+Online.getName(), jugadores);
		}
		if(!TEAMS.containsKey("progreso"+Online.getName())) {
			Team jugadores = sb.registerNewTeam("progreso");
			jugadores.addEntry("Progreso:");
			TEAMS.put("progreso"+Online.getName(), jugadores);
		}
		if(!TEAMS.containsKey("bar"+Online.getName())) {
			Team jugadores = sb.registerNewTeam("bar");
			jugadores.addEntry(ChatColor.AQUA + "");
			TEAMS.put("bar"+Online.getName(), jugadores);
		}
		Objective objHealth = sb.getObjective("ShowHealth");
		
		if(objHealth == null) {
			objHealth = sb.registerNewObjective("ShowHealth", "health");
			objHealth.setDisplaySlot(DisplaySlot.BELOW_NAME);
			objHealth.setDisplayName(ChatColor.RED+"❤");
		}
		
		Objective objGame = sb.getObjective("KitPVP");
		
		if(objGame == null) {
			objGame = sb.registerNewObjective("KitPVP", "dummy");
			
			objGame.setDisplaySlot(DisplaySlot.SIDEBAR);
			
			// objGame.setDisplayName(ChatColor.GOLD+""+ChatColor.BOLD+"KitPVP");
			objGame.setDisplayName(ChatColor.RED+""+ChatColor.BOLD+"KitPvP");
			
			//LCoins
			objGame.getScore("            ").setScore(15);
			objGame.getScore("LCoins").setScore(14);
			objGame.getScore(ChatColor.YELLOW+"").setScore(13);
			//Asesinatos
			objGame.getScore("  ").setScore(12);
			objGame.getScore("Nivel:").setScore(11);
			objGame.getScore("Progreso:").setScore(10);
			//Muertes
			objGame.getScore(ChatColor.AQUA + "").setScore(9);
			objGame.getScore("   ").setScore(8);
			objGame.getScore("Kills:").setScore(7);
			objGame.getScore("Muertes:").setScore(6);
			objGame.getScore("KDR:").setScore(5);
			objGame.getScore("       ").setScore(4);
			objGame.getScore("Jugadores:").setScore(3);
			objGame.getScore("    ").setScore(2);
			objGame.getScore(ChatColor.translateAlternateColorCodes('&', "&eplay.minelc.net")).setScore(1);

		}
		 TEAMS.get("LCoins"+Online.getName()).setPrefix(ChatColor.GOLD+""+jugOnline.getLcoins() + " ⛁");
		TEAMS.get("kills"+Online.getName()).setSuffix(ChatColor.GREEN+" "+jugOnline.getKitPVP_Stats_kills());
		TEAMS.get("jugadores"+Online.getName()).setSuffix(ChatColor.GREEN+" "+ Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers());

		TEAMS.get("muertes"+Online.getName()).setSuffix(ChatColor.GREEN+" "+jugOnline.getKitPVP_Stats_deaths());
		TEAMS.get("kdr"+Online.getName()).setSuffix(ChatColor.GREEN+" "+jugOnline.getKitPVP_Stats_kdr());
		TEAMS.get("nivel"+Online.getName()).setSuffix(ChatColor.GRAY+" "+ (jugOnline.getKitPVP_Stats_Level()));

		TEAMS.get("bar"+Online.getName()).setSuffix(getProgreso(jugOnline));

		int stats_level = jugOnline.getKitPVP_Stats_Level();
		int nex_kills =  (stats_level + 1) * 20;
		int kills = jugOnline.getKitPVP_Stats_kills() - (nex_kills - 20);
		int result = ((kills * 20) / nex_kills) * 10 ;

		TEAMS.get("progreso"+Online.getName()).setSuffix(ChatColor.AQUA+" "+ result + "%");

		for(Player tmOnline : Bukkit.getOnlinePlayers()) {
			Jugador jugTM = Jugador.getJugador(tmOnline);
			
			try {
				Team tm = sb.getTeam(jugTM.getBukkitPlayer().getName());
				
				if(tm != null) {
					continue;
				}
				
				tm = sb.registerNewTeam(jugTM.getBukkitPlayer().getName());
				
				if(jugTM.isHideRank())
					tm.setPrefix(ChatColor.GRAY.toString());
				else if(jugTM.is_Owner())
					tm.setPrefix(ChatColor.DARK_RED+""+ChatColor.BOLD+Ranks.OWNER.name()+" "+jugTM.getNameTagColor());
				else if(jugTM.is_Admin())
					tm.setPrefix(ChatColor.RED+""+ChatColor.BOLD+Ranks.ADMIN.name()+" "+jugTM.getNameTagColor());
				else if(jugTM.is_MODERADOR())
					tm.setPrefix(ChatColor.DARK_PURPLE+""+ChatColor.BOLD+Ranks.MOD.name()+" "+jugTM.getNameTagColor());
				else if(jugTM.is_AYUDANTE())
					tm.setPrefix(ChatColor.DARK_PURPLE+""+ChatColor.BOLD+Ranks.AYUDANTE.name()+" "+jugTM.getNameTagColor());
				else if(jugTM.is_YOUTUBER()) {
					String youtuber = ChatColor.RED+""+ChatColor.BOLD+"YouTuber ";
					tm.setPrefix(youtuber+jugTM.getNameTagColor());	
				} else if(jugTM.is_MiniYT()) {
					String youtuber = ChatColor.RED+""+ChatColor.BOLD+"MiniYT ";
					tm.setPrefix(youtuber+jugTM.getNameTagColor());
				}
				else if(jugTM.is_BUILDER())
					tm.setPrefix(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+Ranks.BUILDER.name()+" "+jugTM.getNameTagColor());
				else if(jugTM.is_RUBY())
					tm.setPrefix(ChatColor.RED+""+ChatColor.BOLD+Ranks.RUBY.name()+" "+jugTM.getNameTagColor());
				else if(jugTM.is_ELITE())
					tm.setPrefix(ChatColor.GOLD+""+ChatColor.BOLD+Ranks.ELITE.name()+" "+jugTM.getNameTagColor());
				else if(jugTM.is_SVIP())
					tm.setPrefix(ChatColor.GREEN+""+ChatColor.BOLD+Ranks.SVIP.name()+" "+jugTM.getNameTagColor());
				else if(jugTM.is_VIP())
					tm.setPrefix(ChatColor.AQUA+""+ChatColor.BOLD+Ranks.VIP.name()+" "+jugTM.getNameTagColor());
				else if(jugTM.is_Premium())
					tm.setPrefix(ChatColor.YELLOW.toString());
				else
					tm.setPrefix(ChatColor.GRAY.toString());

				tm.addEntry(tmOnline.getName());
				} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
		for(Player tmOnline : Bukkit.getOnlinePlayers()) {
			Scoreboard sbTM = tmOnline.getScoreboard();
			try {
				
				if(sbTM == null) {
					continue;
				}
				Team tm = sbTM.getTeam(jugOnline.getBukkitPlayer().getName());
				
				if(tm != null) {
					continue;
				}
				
				tm = sbTM.registerNewTeam(jugOnline.getBukkitPlayer().getName());
				
				if(jugOnline.isHideRank())
					tm.setPrefix(ChatColor.GRAY.toString());
				else if(jugOnline.is_Owner())
					tm.setPrefix(ChatColor.DARK_RED+""+ChatColor.BOLD+Ranks.OWNER.name()+" "+jugOnline.getNameTagColor());
				else if(jugOnline.is_Admin())
					tm.setPrefix(ChatColor.RED+""+ChatColor.BOLD+Ranks.ADMIN.name()+" "+jugOnline.getNameTagColor());
				else if(jugOnline.is_MODERADOR())
					tm.setPrefix(ChatColor.DARK_PURPLE+""+ChatColor.BOLD+Ranks.MOD.name()+" "+jugOnline.getNameTagColor());
				else if(jugOnline.is_AYUDANTE())
					tm.setPrefix(ChatColor.DARK_PURPLE+""+ChatColor.BOLD+Ranks.AYUDANTE.name()+" "+jugOnline.getNameTagColor());
				else if(jugOnline.is_YOUTUBER()) {
					String youtuber = ChatColor.RED+""+ChatColor.BOLD+"YouTuber ";
					tm.setPrefix(youtuber+jugOnline.getNameTagColor());	
				} else if(jugOnline.is_MiniYT()) {
					String youtuber = ChatColor.RED+""+ChatColor.BOLD+"MiniYT ";
					tm.setPrefix(youtuber+jugOnline.getNameTagColor());
				}
				else if(jugOnline.is_BUILDER())
					tm.setPrefix(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+Ranks.BUILDER.name()+" "+jugOnline.getNameTagColor());
				else if(jugOnline.is_RUBY())
					tm.setPrefix(ChatColor.RED+""+ChatColor.BOLD+Ranks.RUBY.name()+" "+jugOnline.getNameTagColor());
				else if(jugOnline.is_ELITE())
					tm.setPrefix(ChatColor.GOLD+""+ChatColor.BOLD+Ranks.ELITE.name()+" "+jugOnline.getNameTagColor());
				else if(jugOnline.is_SVIP())
					tm.setPrefix(ChatColor.GREEN+""+ChatColor.BOLD+Ranks.SVIP.name()+" "+jugOnline.getNameTagColor());
				else if(jugOnline.is_VIP())
					tm.setPrefix(ChatColor.AQUA+""+ChatColor.BOLD+Ranks.VIP.name()+" "+jugOnline.getNameTagColor());
				else if(jugOnline.is_Premium())
					tm.setPrefix(ChatColor.YELLOW.toString());
				else
					tm.setPrefix(ChatColor.GRAY.toString());

				tm.addEntry(jugOnline.getBukkitPlayer().getName());
				} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	private static String getProgreso(Jugador jugOnline) {

		int stats_level = jugOnline.getKitPVP_Stats_Level();
		int nex_kills =  (stats_level + 1) * 20;
		int kills = jugOnline.getKitPVP_Stats_kills() - (nex_kills - 20);
		int result = ((kills * 20) / nex_kills) * 10 ;
		int percent = ((10 * result) / 100);

		String newbar = ChatColor.AQUA + "";
		for(int x=0; x<10; x++){
			String ab = "■";
			if(percent == 0)
				ab = ChatColor.WHITE + ab;

			newbar = newbar + ab;
			percent--;
		}
		return newbar;

	}

}
