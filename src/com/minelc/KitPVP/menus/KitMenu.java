package com.minelc.KitPVP.menus;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffect;

import com.google.common.collect.Lists;
import com.minelc.CORE.Controller.Database;
import com.minelc.CORE.Controller.Jugador;
import com.minelc.CORE.Utils.IconMenu;
import com.minelc.KitPVP.KitPVPMain;
import com.minelc.KitPVP.utilities.GameKit;

public class KitMenu {

    private static final int menuSlotsPerRow = 9;
    private static final int menuSize = 54;
    private static final String menuName = "Menu De Kits";
    private IconMenu menu;
    
    public KitMenu(final Jugador jug) {
        List<GameKit> availableKits = KitPVPMain.getKC().getKits();
        
        int highestSlot = 0;
        for (GameKit kit: availableKits) {
        	if (kit.getPosition() > highestSlot) {
        		highestSlot = kit.getPosition();
        	}
        }

        int rowCount = menuSlotsPerRow;
        while (rowCount < (highestSlot + 1) && rowCount < menuSize) {
            rowCount += menuSlotsPerRow;
        }

        menu = new IconMenu(menuName, rowCount, new IconMenu.OptionClickEventHandler() {
            @Override
            public void onOptionClick(IconMenu.OptionClickEvent event) {

                GameKit kit = KitPVPMain.getKC().getByName(ChatColor.stripColor(event.getName()));
                
                event.setWillClose(false);
                event.setWillDestroy(false);
                
                if (kit == null) {
                    return;
                }
                
                if (!hasFreePermission(jug, kit)) {
                	return;
                }
                
                event.setWillClose(true);
                event.setWillDestroy(true);
                
                if(!jug.getKitPVP_Kit().equalsIgnoreCase(kit.getKitName())) {
                	jug.setKitPVP_Kit(kit.getKitName());
                	Database.savePlayerSV_KITPVP(jug);
                }
                
                for (PotionEffect effect : event.getPlayer().getActivePotionEffects()) {
    				event.getPlayer().removePotionEffect(effect.getType());
    			}
                
                event.getPlayer().getInventory().clear();
                event.getPlayer().getInventory().setArmorContents(null);
                
                KitPVPMain.getKC().populateInventory(event.getPlayer(), kit);
                KitPVPMain.getKC().givePotionEffects(event.getPlayer(), kit);
                event.getPlayer().sendMessage(ChatColor.GREEN+"Seleccionaste el kit "+ChatColor.YELLOW+kit.getKitName()+ChatColor.GREEN+"!");
            }
        }, KitPVPMain.get(), true);

        for (int iii = 0; iii < availableKits.size(); iii ++) {
            if (iii >= menuSize) {
                break;
            }

            GameKit kit = availableKits.get(iii);
            List<String> loreList = Lists.newLinkedList();
            
            if(kit.getName().equalsIgnoreCase("vip")) {
            	loreList.add(ChatColor.AQUA+"Kit Exclusivo Para Rango VIP!");
            } else if(kit.getName().equalsIgnoreCase("svip")) {
            	loreList.add(ChatColor.AQUA+"Kit Exclusivo Para Rango SVIP!");
            } else if(kit.getName().equalsIgnoreCase("elite")) {
            	loreList.add(ChatColor.AQUA+"Kit Exclusivo Para Rango Elite!");
            }else if(kit.getName().equalsIgnoreCase("ruby")) {
            	loreList.add(ChatColor.AQUA+"Kit Exclusivo Para Rango Ruby!");
            }
            
            loreList.addAll(kit.getLores());
            
            menu.setOption(
                    kit.getPosition(),
                    kit.getIcon(),
                    ChatColor.GOLD+ kit.getKitName(),
                    loreList.toArray(new String[loreList.size()]));
        }

        menu.open(jug.getBukkitPlayer());
    }
    
    public static boolean hasFreePermission(Jugador player, GameKit kit) {
        if(kit.getName().equalsIgnoreCase("vip")) {
            if(player.is_VIP())
            	return true;
            else
            	return false;
        }
        
        if(kit.getName().equalsIgnoreCase("svip")) {
            if(player.is_SVIP())
            	return true;
            else
            	return false;
        }
        
        if(kit.getName().equalsIgnoreCase("elite")) {
            if(player.is_ELITE())
            	return true;
            else
            	return false;
        }
        if(kit.getName().equalsIgnoreCase("ruby")) {
            if(player.is_RUBY())
            	return true;
            else
            	return false;
        }
        
        return true;
    }
    
    public boolean isPurchaseAble(GameKit kit) {
        if (kit.getCost() > 0) {
        	return true;
        }
        return false;
    }
    
    public IconMenu getMenu() {
		return menu;
	}
}

