package com.minelc.KitPVP.utilities;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import com.google.common.collect.Lists;
import com.minelc.KitPVP.KitPVPMain;

public class GameKit {

    private String kitName;
    private int permCost;
    private int cost;
    private List<ItemStack> items = Lists.newArrayList();
    private List<PotionEffect> potionEffects = Lists.newArrayList();

    private ItemStack icon;
    private int position;
    private List<String> lores;

    public GameKit(String name, FileConfiguration storage, File kit) {
        try {
        	System.out.print("Cargando kit: "+name);
            List<String> itemDatas = storage.getStringList("items");
            for (String itemData : itemDatas) {
            	List<String> item = Arrays.asList(itemData.split(" "));
                ItemStack itemStack = ItemUtils.parseItem(item);

                if (itemStack != null) {
                    items.add(itemStack);
                } else {
                	System.out.print("Error con el kit: "+name);
                }
            }
            
            List<String> effects = storage.getStringList("potionEffects");
            for (String effect : effects) {
            	
            	List<String> effectDetails = Arrays.asList(effect.split(" "));
                PotionEffect potionEffect = ItemUtils.parseEffect(effectDetails);

                if (potionEffect != null) {
                    potionEffects.add(potionEffect);
                }
            }

           if (storage.getString("kitName") != null) {
           	 	kitName = storage.getString("kitName");
           } else {
           		kitName = name;
           		storage.set("kitName", name);
           	try {
					storage.save(kit);
				} catch (IOException e) {
					e.printStackTrace();
				}
           }
           
           if (storage.getString("permCost") != null) {
          	 	permCost = storage.getInt("permCost");
          } else {
          		permCost = 1000000;
          		storage.set("permCost", permCost);
          	try {
					storage.save(kit);
				} catch (IOException e) {
					e.printStackTrace();
				}
          }
            
            cost = storage.getInt("cost", 0);
            position = storage.getInt("menuPostion");

            String icon = storage.getString("icon").toUpperCase();
        	List<String> item2 = Arrays.asList(icon.split(" "));
            this.icon = ItemUtils.parseItem(item2);
            
            lores = Lists.newLinkedList();
            if (storage.contains("details")) {
                for (String string : storage.getStringList("details")) {
                    lores.add(ChatColor.translateAlternateColorCodes('&', string));
                }
            }
        } catch (NullPointerException e) {
        	e.printStackTrace();
        	KitPVPMain.get().getLogger().info("Error en el kit: " + ChatColor.RED + name); 
        }
    	
    }
    
    public Collection<ItemStack> getItems() {
        return items;
    }
    
    public Collection<PotionEffect> getPotionEffects() {
        return potionEffects;
    }
    
    public String getName() {
        return kitName;
    }
    
    public String getKitName() {
    	return kitName;
    }

    public int getCost() {
        return cost;
    }
    
    public int getPermCost() {
    	return permCost;
    }

    public int getPosition() {
        return position;
    }
    
    public ItemStack getIcon() {
        return icon;
    }

    public List<String> getLores() {
        return lores;
    }
}

