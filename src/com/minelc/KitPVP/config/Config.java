package com.minelc.KitPVP.config;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import com.google.common.collect.Lists;
import com.minelc.KitPVP.KitPVPMain;

public class Config {

	private int pointsPerKill;
	private int vipMultiplier;
	private int svipMultiplier;
	private int eliteMultiplier;
	private int rubyMultiplier;
		
	private String world;
	private int x;
	private int y;
	private int z;
	private float yaw;
	private float pitch;
	private Location spawn;
	private List<Location> ArenaSpawns;
	
	public Config() {
		load();
	}
	
	private void load() {
		pointsPerKill = KitPVPMain.get().getConfig().getInt("points.pointsPerKill");
		vipMultiplier = KitPVPMain.get().getConfig().getInt("points.vipMultiplier");
		svipMultiplier = KitPVPMain.get().getConfig().getInt("points.svipMultiplier");
		eliteMultiplier = KitPVPMain.get().getConfig().getInt("points.eliteMultiplier");
		rubyMultiplier = (KitPVPMain.get().getConfig().getInt("points.rubyMultiplier"));
		
		world = KitPVPMain.get().getConfig().getString("spawn.world");
		x = KitPVPMain.get().getConfig().getInt("spawn.x");
		y = KitPVPMain.get().getConfig().getInt("spawn.y");
		z = KitPVPMain.get().getConfig().getInt("spawn.z");
		yaw = KitPVPMain.get().getConfig().getInt("spawn.yaw");
		pitch = KitPVPMain.get().getConfig().getInt("spawn.pitch");
		
		ArenaSpawns = Lists.newArrayList();
		spawn = new Location(KitPVPMain.get().getServer().getWorld(world), x, y, z, yaw, pitch);
		for(String s : KitPVPMain.get().getConfig().getStringList("ArenasSpawns")) {
			Location loc = parseLocation(s);
			if(loc != null) {
				ArenaSpawns.add(loc);
			}
		}
	}
	
	private Location parseLocation(String s) {
		Location locret = null;
		try {
			String[] locstring = s.split(",");
			locret = new Location(Bukkit.getWorld(locstring[0]), Double.parseDouble(locstring[1]), Double.parseDouble(locstring[2]), Double.parseDouble(locstring[3]), Float.parseFloat(locstring[4]), Float.parseFloat(locstring[5]));
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return locret;
	}
	
	public void setSpawn(Location loc) {
		world = loc.getWorld().getName().toString();
		x = loc.getBlockX();
		y = loc.getBlockY();
		z = loc.getBlockZ();
		yaw = loc.getYaw();
		pitch = loc.getPitch();
		if (getSpawn() != null) {
			World world = getSpawn().getWorld();
			world.setTime(6000);
			world.setGameRuleValue("doDaylightCycle", "false");
        }
		KitPVPMain.get().getConfig().set("spawn.world", world);
		KitPVPMain.get().getConfig().set("spawn.x", x);
		KitPVPMain.get().getConfig().set("spawn.y", y);
		KitPVPMain.get().getConfig().set("spawn.z", z);
		KitPVPMain.get().getConfig().set("spawn.yaw", yaw);
		KitPVPMain.get().getConfig().set("spawn.pitch", pitch);
		KitPVPMain.get().saveConfig();
	}
	
	public void addArenaSpawn(Location loc) {
		FileConfiguration conf = KitPVPMain.get().getConfig();
		
		List<String> aspawns = Lists.newArrayList();
		
		if(conf.contains("ArenasSpawns")) {
			aspawns = conf.getStringList("ArenasSpawns");
		}
		
		aspawns.add(loc.getWorld().getName().toString()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ()+","+loc.getYaw()+","+loc.getPitch());
		
		KitPVPMain.get().getConfig().set("ArenasSpawns", aspawns);
		KitPVPMain.get().saveConfig();
	}
	
	public Location getSpawn() {
		return spawn;
	}
		
	public int getKillValue() {
		return pointsPerKill;
	}
	
	public int getVIPMultiplier() {
		return vipMultiplier;
	}
	
	public int getSVIPMultiplier() {
		return svipMultiplier;
	}
	
	public int getEliteMultiplier() {
		return eliteMultiplier;
	}

	public List<Location> getArenaSpawns() {
		return ArenaSpawns;
	}

	public int getRubyMultiplier() {
		return rubyMultiplier;
	}

	public void setRubyMultiplier(int rubyMultiplier) {
		this.rubyMultiplier = rubyMultiplier;
	}

}
