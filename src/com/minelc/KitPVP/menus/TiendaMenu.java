package com.minelc.KitPVP.menus;

import java.util.List;

import org.bukkit.ChatColor;

import com.google.common.collect.Lists;
import com.minelc.CORE.Controller.Database;
import com.minelc.CORE.Controller.Jugador;
import com.minelc.CORE.Utils.IconMenu;
import com.minelc.KitPVP.KitPVPMain;
import com.minelc.KitPVP.controllers.LobbyGameController;
import com.minelc.KitPVP.utilities.ShopKit;
import org.bukkit.Sound;

public class TiendaMenu {

    private static final int menuSlotsPerRow = 9;
    private static final int menuSize = 54;
    private static final String menuName = "Tienda";
    private IconMenu menu;
    
    public TiendaMenu(final Jugador jug) {
        List<ShopKit> availableKits = KitPVPMain.getSC().getShopItems();
        
        int highestSlot = 0;
        for (ShopKit kit: availableKits) {
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
                ShopKit shopItem = KitPVPMain.getSC().getByName(ChatColor.stripColor(event.getName()));
                
                event.setWillClose(false);
                event.setWillDestroy(false);
                
                if (shopItem == null) {
                    return;
                }
                
        		if (!canPurchase(jug, shopItem)) {
        		    event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.VILLAGER_NO, 0.5f,0.5f);
            		event.getPlayer().sendMessage(ChatColor.RED+"No tienes suficientes puntos para comprar esto!");
                    return;
        		} else {
        		    event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.NOTE_PLING, 0.5F,0.5F);
        			removeBalance(jug, shopItem.getCost());
        			event.getPlayer().sendMessage(ChatColor.GREEN+"Has comprado "+ChatColor.YELLOW+shopItem.getKitName()+ChatColor.GREEN+"!");
        			KitPVPMain.getSC().populateInventory(event.getPlayer(), shopItem);
        			LobbyGameController.getLobby().updateScoreboard(jug);
        		} 
            }
        }, KitPVPMain.get(), true);

        for (int iii = 0; iii < availableKits.size(); iii ++) {
            if (iii >= menuSize) {
                break;
            }

            ShopKit kit = availableKits.get(iii);
            List<String> loreList = Lists.newLinkedList();
            
        	loreList.add("\247r\2476Precio\2477: \247" + (jug.getLcoins() >= kit.getCost() ? 'a' : 'c') + kit.getCost());
        	if(jug.getLcoins() >= kit.getCost())
        		loreList.add(ChatColor.AQUA+"Click para comprar!");
        	else
        		loreList.add(ChatColor.AQUA+"Aún no puedes comprar esto!");
            
            loreList.addAll(kit.getLores());
            
            menu.setOption(
                    kit.getPosition(),
                    kit.getIcon(),
                    "\247r\247" + (canPurchase(jug, kit) ? 'a' : 'c') + kit.getKitName(),
                    loreList.toArray(new String[loreList.size()]));
        }

        menu.open(jug.getBukkitPlayer());
    }

    public boolean canPurchase(Jugador jug, ShopKit kit) {
    	return (jug.getLcoins() >= kit.getCost());
    }
    
    private void removeBalance(Jugador jug, int x) {
    	jug.removeLcoins(x);
    	Database.savePlayerCoins(jug);
    }

	public IconMenu getMenu() {
		return menu;
	}
}
