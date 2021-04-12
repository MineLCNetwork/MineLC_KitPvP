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
import com.minelc.KitPVP.utilities.ShopKit;

public class ShopController {

    private final Map<String, ShopKit> shopMap = Maps.newHashMap();

    public ShopController() {
        load();
    }

    public void load() {
        shopMap.clear();
        File dataDirectory = KitPVPMain.get().getDataFolder();
        File ShopDirectory = new File(dataDirectory, "shop");

        if (!ShopDirectory.exists()) {
            if (!ShopDirectory.mkdirs()) {
                return;
            }
            KitPVPMain.get().saveResource("example.yml", true);
            copyFiles(new File(dataDirectory, "example.yml"), new File(ShopDirectory, "example.yml"));
            File delete = new File(dataDirectory, "example.yml");
            delete.delete();
        }

        File[] Shop = ShopDirectory.listFiles();
        if (Shop == null) {
            return;
        }

        for (File kit : Shop) {
            if (!kit.getName().endsWith(".yml")) {
                continue;
            }

            String name = kit.getName().replace(".yml", "");

            if (!name.isEmpty()) {
            	ShopKit skit = new ShopKit(name, YamlConfiguration.loadConfiguration(kit), kit);
                shopMap.put(skit.getName(), skit);
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
    public void populateInventory(Player p, ShopKit kit) {
        for (ItemStack itemStack : kit.getItems()) {
          try {
            p.getInventory().addItem(new ItemStack[] { itemStack });
          } catch (NullPointerException e) {
            e.printStackTrace();
          } 
        } 
      }

      
      public void givePotionEffects(Jugador jug, ShopKit kit) {
        for (PotionEffect pEffect : kit.getPotionEffects()) {
          if (jug.getBukkitPlayer() != null) {
            jug.getBukkitPlayer().addPotionEffect(pEffect);
          }
        } 
      }

      
      public ShopKit getByName(String name) { return (ShopKit)this.shopMap.get(name); }


      
      public List<ShopKit> getShopItems() { return Lists.newArrayList(this.shopMap.values()); }
    }
