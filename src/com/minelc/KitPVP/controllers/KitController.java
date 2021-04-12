package com.minelc.KitPVP.controllers;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.minelc.CORE.Controller.Jugador;
import com.minelc.KitPVP.KitPVPMain;
import com.minelc.KitPVP.utilities.GameKit;

public class KitController {

    private final Map<String, GameKit> kitMap = Maps.newHashMap();

    public KitController() {
        load();
    }

    public void load() {
        kitMap.clear();
        File dataDirectory = KitPVPMain.get().getDataFolder();
        File kitsDirectory = new File(dataDirectory, "kits");

        if (!kitsDirectory.exists()) {
            if (!kitsDirectory.mkdirs()) {
                return;
            }
            KitPVPMain.get().saveResource("example.yml", true);
            copyFiles(new File(dataDirectory, "example.yml"), new File(kitsDirectory, "example.yml"));
            File delete = new File(dataDirectory, "example.yml");
            delete.delete();
        }

        File[] kits = kitsDirectory.listFiles();
        if (kits == null) {
            return;
        }

        for (File kit : kits) {
            if (!kit.getName().endsWith(".yml")) {
                continue;
            }

            String name = kit.getName().replace(".yml", "");

            if (!name.isEmpty()) {
            	GameKit gkit = new GameKit(name, YamlConfiguration.loadConfiguration(kit), kit);
                kitMap.put(gkit.getName(), gkit);
            }
        }
    }
    
    public void copyFiles(File source, File target) {
	    try {
	        ArrayList<String> ignore = new ArrayList<String>(Arrays.asList("uid.dat", "session.dat"));
	        if(!ignore.contains(source.getName())) {
	            if(source.isDirectory()) {
	                if(!target.exists())
	                target.mkdirs();
	                String files[] = source.list();
	                for (String file : files) {
	                    File srcFile = new File(source, file);
	                    File destFile = new File(target, file);
	                    copyFiles(srcFile, destFile);
	                }
	            } else {
	                InputStream in = new FileInputStream(source);
	                OutputStream out = new FileOutputStream(target);
	                byte[] buffer = new byte[1024];
	                int length;
	                while ((length = in.read(buffer)) > 0)
	                    out.write(buffer, 0, length);
	                in.close();
	                out.close();
	            }
	        }
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
    
    @SuppressWarnings("deprecation")
	public void populateInventory(Player p, GameKit kit) {
    	Jugador jug = Jugador.getJugador(p);
    	
    	
    	String permission = kit.getKitName();
    	
    		if(permission.equalsIgnoreCase("vip")) {
    			if(!jug.is_VIP()) {
    				jug.setKitPVP_Kit("default");
    			}
    		} else if(permission.equalsIgnoreCase("svip")) {
    			if(!jug.is_SVIP()) {
    				jug.setKitPVP_Kit("default");
    			}
    		} else if(permission.equalsIgnoreCase("elite")) {
    			if(!jug.is_ELITE()) {
    				jug.setKitPVP_Kit("default");
    			}
    		} else if(permission.equalsIgnoreCase("ruby")) {
				if(!jug.is_RUBY()) {
					jug.setKitPVP_Kit("default");
				}
			}

		for (ItemStack itemStack : kit.getItems()) {
        	try {
        		if ((itemStack.getTypeId() == 298) || (itemStack.getTypeId() == 302)
    					|| (itemStack.getTypeId() == 306) || (itemStack.getTypeId() == 310)
    					|| (itemStack.getTypeId() == 314)) {
    				p.getInventory().setHelmet(itemStack);
    			} else if ((itemStack.getTypeId() == 299) || (itemStack.getTypeId() == 303)
    					|| (itemStack.getTypeId() == 307) || (itemStack.getTypeId() == 311)
    					|| (itemStack.getTypeId() == 315)) {
    				p.getInventory().setChestplate(itemStack);
    			} else if ((itemStack.getTypeId() == 300) || (itemStack.getTypeId() == 304)
    					|| (itemStack.getTypeId() == 308) || (itemStack.getTypeId() == 312)
    					|| (itemStack.getTypeId() == 316)) {
    				p.getInventory().setLeggings(itemStack);
    			} else if ((itemStack.getTypeId() == 301) || (itemStack.getTypeId() == 305)
    					|| (itemStack.getTypeId() == 309) || (itemStack.getTypeId() == 313)
    					|| (itemStack.getTypeId() == 317)) {
    				p.getInventory().setBoots(itemStack);
    			} else {
                p.getInventory().addItem(itemStack);
    			}
        	} catch(NullPointerException e) {
        		e.printStackTrace();
        	}

        }
    }
    
    public void givePotionEffects(Player p, GameKit kit) {
    	for (PotionEffect pEffect: kit.getPotionEffects()) {
    		p.addPotionEffect(pEffect);
    	}
    }

    public GameKit getByName(String name) {
    	GameKit gkit = kitMap.get(name);
		return gkit != null ? gkit : kitMap.get("default");
    }

    public List<GameKit> getKits() {
    	return Lists.newArrayList(kitMap.values());
    }

}
