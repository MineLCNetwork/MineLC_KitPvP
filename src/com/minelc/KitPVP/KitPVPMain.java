package com.minelc.KitPVP;

import java.util.*;

import com.minelc.CORE.Controller.Ranks;
import com.minelc.KitPVP.commands.*;
import com.minelc.KitPVP.controllers.LobbyGameController;
import net.minecraft.server.v1_8_R3.ItemSkull;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.minelc.CORE.Controller.Database;
import com.minelc.CORE.Controller.Jugador;
import com.minelc.KitPVP.config.Config;
import com.minelc.KitPVP.controllers.KitController;
import com.minelc.KitPVP.controllers.ShopController;
import com.minelc.KitPVP.controllers.WorldController;
import com.minelc.KitPVP.listeners.WorldListener;
import com.minelc.KitPVP.listeners.PlayerListener;
import com.minelc.KitPVP.runnables.Timer;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class KitPVPMain extends JavaPlugin {

	private static KitPVPMain instance;
    private WorldController wc;
    private Config config;
    private KitController kc;
    private ShopController sc;
    public static Set<Player> Vanished = Sets.newHashSet();
    private Map<Player, Long> invensibles = Maps.newHashMap();
    public static boolean stop = false;
    public static ArrayList<String> allowed = new ArrayList<String>();

    public void onEnable() {
    	try {
        	instance = this;
        	getConfig().options().copyDefaults(true);
            saveDefaultConfig();
            saveConfig();
            reloadConfig();
            
            wc = new WorldController();
            kc = new KitController();
            sc = new ShopController();
            wc.loadWorld("kitpvpmapa");
            this.wc.loadWorld("a1");
            this.wc.loadWorld("sumodos");
            this.wc.loadWorld("minelcmapa");
            
            World worldk = Bukkit.getWorld("kitpvpmapa");
            if (worldk != null) {
            	worldk.setTime(6000);
            	worldk.setGameRuleValue("doDaylightCycle", "false");
            	worldk.setGameRuleValue("doEntityDrops ", "true");
    			worldk.setAutoSave(true);
            }
            
            World worlda1 = Bukkit.getWorld("a1");
            if (worlda1 != null) {
            worlda1.setTime(6000L);
            worlda1.setGameRuleValue("doDaylightCycle", "false");
            worlda1.setGameRuleValue("doEntityDrops ", "true");
            worlda1.setAutoSave(true);
            }
            World worldsm = Bukkit.getWorld("sumodos");
            if (worldsm != null) {
            worldsm.setTime(6000L);
            worldsm.setGameRuleValue("doDaylightCycle", "false");
            worldsm.setGameRuleValue("doEntityDrops ", "true");
            worldsm.setAutoSave(true);
            }
            World worlmold = Bukkit.getWorld("minelcmapa");
            if (worlmold != null) {
            worlmold.setTime(6000L);
            worlmold.setGameRuleValue("doDaylightCycle", "false");
            worlmold.setGameRuleValue("doEntityDrops ", "true");
            worlmold.setAutoSave(true);
            }
            World lila = Bukkit.getWorld("lila");
            if (lila != null) {
            	lila.setTime(6000L);
            	lila.setGameRuleValue("doDaylightCycle", "false");
            	lila.setGameRuleValue("doEntityDrops ", "true");
            	lila.setAutoSave(true);
            }
            
            config = new Config();
            Bukkit.getPluginManager().registerEvents(new WorldListener(), this);
            
            getCommand("minelc").setExecutor(new CmdManager());
            getCommand("vanish").setExecutor(new CmdVanish());
            getCommand("fly").setExecutor(new CmdFly());
            getCommand("teleport").setExecutor(new CmdTeleport());
            getCommand("spawn").setExecutor(new CmdSpawn());
            getCommand("ender").setExecutor(new EnderCmd());
            getCommand("coins").setExecutor(new winnerCoins());
            getCommand("rachas").setExecutor(new Cmdrachas());
            getCommand("resettop").setExecutor(new resetTOP());
            getCommand("mytop").setExecutor(new MyTOP());
            allowed.addAll(getConfig().getStringList("allowed"));
            
            Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
            Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Timer(), 0L, 20L);
            
            for(Player Online : Bukkit.getOnlinePlayers()) {
            	Jugador jug  = Jugador.getJugador(Online);
            	Database.loadPlayerRank_SYNC(jug);
        		Database.loadPlayerSV_KITPVP_SYNC(jug);
        		Database.loadPlayerCoins_SYNC(jug);
            }

        	} catch(Exception ex) {
    		Bukkit.shutdown();
    		ex.printStackTrace();
    	}
    } 
    
	public void onDisable() {
		stop = true;
		for(Player Online : Bukkit.getOnlinePlayers()) {
			Online.kickPlayer("Reiniciando..");
		}
    }
    
    public static KitPVPMain get() {
        return instance;
    }
    
    public static WorldController getWC() {
        return instance.wc;
    }
    
    public static KitController getKC() {
        return instance.kc;
    }
    
    public static ShopController getSC() {
        return instance.sc;
    }
    
    public static Config getCfg() {
    	return instance.config;
    }

	public boolean isVanished(Player p) {
		return Vanished.contains(p);
	}

	public static void createNPC(Player p, int top){

        ArmorStand stand = p.getWorld().spawn(p.getLocation(), ArmorStand.class);

        stand.setCustomNameVisible(true);
        stand.setArms(true);
        stand.setBasePlate(false);
        stand.setGravity(true);
        stand.setSmall(true);
        stand.setBoots(new ItemStack(Material.DIAMOND_BOOTS));
        stand.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
        stand.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        stand.setItemInHand(new ItemStack(Material.GOLD_SWORD));
        new BukkitRunnable(){

            @Override
            public void run() {
                LinkedHashMap<String, Integer> toplist = Database.getTop(3, "stats_kills", "SV_KITPVP");
                ArrayList<String> list = new ArrayList<>(toplist.keySet());
                String player = list.get(top-1);
                if(player == null){
                    p.sendMessage(ChatColor.RED + "No existe este top aún.");
                    return;
                }
                String displayname = ChatColor.translateAlternateColorCodes('&', "&6#"+top+" &7"+player+" &a" + toplist.get(player) + " kills");

                ItemStack playerhead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                SkullMeta playerheadmeta = (SkullMeta) playerhead.getItemMeta();
                playerheadmeta.setOwner(player);
                playerheadmeta.setDisplayName(displayname);
                playerhead.setItemMeta(playerheadmeta);
                stand.setHelmet(playerhead);
                stand.setCustomName(displayname);
            }
        }.runTaskTimer(KitPVPMain.get(), 2L,20L * 300);

    }


	public void removeVanished(Player p) {
		if(Vanished.contains(p)) {
			Vanished.remove(p);
		}
	}
	
	public void addVanished(Player p) {
		if(!Vanished.contains(p)) {
			Vanished.add(p);
		}
	}
	
	public boolean isInvensible(Player p) {
		if(invensibles.containsKey(p)) {
			if(System.currentTimeMillis() - invensibles.get(p) < 4000) {
				return true;
			} else {
				invensibles.remove(p);
			}
		}
		return false;
	}
	
	public void addInvensible(Player p) {
		invensibles.put(p, System.currentTimeMillis());
	}
	
	public void addInvensible(Player p, long timeadd) {
		invensibles.put(p, System.currentTimeMillis()+timeadd);
	}
	
	public void removeInvensible(Player p) {
		if(invensibles.containsKey(p)) {
			invensibles.remove(p);
		}
	}











}
