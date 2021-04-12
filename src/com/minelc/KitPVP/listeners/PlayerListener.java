package com.minelc.KitPVP.listeners;

import com.google.common.collect.Lists;
import com.minelc.CORE.Controller.Database;
import com.minelc.CORE.Controller.Jugador;
import com.minelc.CORE.Controller.Ranks;
import com.minelc.CORE.CoreMain;
import com.minelc.CORE.Utils.Util;
import com.minelc.KitPVP.KitPVPMain;
import com.minelc.KitPVP.controllers.LobbyGameController;
import com.minelc.KitPVP.menus.KitMenu;
import com.minelc.KitPVP.menus.TiendaMenu;
import com.minelc.KitPVP.utilities.GameKit;
import com.minelc.KitPVP.utilities.Tagged;
import org.bukkit.*;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import java.util.*;


public class PlayerListener implements Listener {
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_RESET = "\u001B[0m";
	private static Random random = new Random();
	
	@EventHandler(ignoreCancelled=true)
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		Jugador j = Jugador.getJugador(p);
		
		String msg = e.getMessage().replace("%", "");
		
		if(j.isHideRank()) {
			e.setFormat(ChatColor.YELLOW + p.getName() + ChatColor.DARK_GRAY+" » " + ChatColor.GRAY+msg);
		} else if(j.is_Owner())
			e.setFormat(ChatColor.DARK_RED+""+ChatColor.BOLD+Ranks.OWNER.name()+" "+ChatColor.DARK_GRAY+" "+j.getNameTagColor() + p.getName() + ChatColor.DARK_GRAY+" » " + ChatColor.GRAY+ChatColor.translateAlternateColorCodes('&', msg));
		else if(j.is_Admin())
			e.setFormat(ChatColor.RED+""+ChatColor.BOLD+Ranks.ADMIN.name()+" "+j.getNameTagColor() + p.getName() + ChatColor.DARK_GRAY+" » " + ChatColor.GRAY+ChatColor.translateAlternateColorCodes('&', msg));
		else if(j.is_MODERADOR())
			e.setFormat(ChatColor.DARK_PURPLE+""+ChatColor.BOLD+Ranks.MOD.name()+" "+j.getNameTagColor() + p.getName() + ChatColor.DARK_GRAY+" » " + ChatColor.GRAY+ChatColor.translateAlternateColorCodes('&', msg));
		else if(j.is_AYUDANTE())
			e.setFormat(ChatColor.DARK_PURPLE+""+ChatColor.BOLD+Ranks.AYUDANTE.name()+" "+j.getNameTagColor() + p.getName() + ChatColor.DARK_GRAY+" » " + ChatColor.GRAY+ChatColor.translateAlternateColorCodes('&', msg));
		else if(j.is_YOUTUBER())
			e.setFormat(ChatColor.RED+""+ChatColor.BOLD+"You"+ChatColor.WHITE+""+ChatColor.BOLD+"Tuber "+j.getNameTagColor() + p.getName() + ChatColor.DARK_GRAY+" » " + ChatColor.GRAY+ChatColor.translateAlternateColorCodes('&', msg));
		else if(j.is_MiniYT())
			e.setFormat(ChatColor.WHITE+""+ChatColor.BOLD+"Mini"+ChatColor.RED+""+ChatColor.BOLD+"YT "+j.getNameTagColor() + p.getName() + ChatColor.DARK_GRAY+" » " + ChatColor.GRAY+ChatColor.translateAlternateColorCodes('&', msg));

		else if(j.is_BUILDER())
			e.setFormat(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+Ranks.BUILDER.name()+" "+j.getNameTagColor() + p.getName() +ChatColor.DARK_GRAY+" » " + ChatColor.GRAY+ChatColor.translateAlternateColorCodes('&', msg));
		else if(j.is_RUBY())
			e.setFormat(ChatColor.AQUA + "♦ " + ChatColor.RED+""+ChatColor.BOLD+Ranks.RUBY.name()+" "+j.getNameTagColor() + p.getName() + ChatColor.DARK_GRAY+" » " + ChatColor.GRAY+ChatColor.translateAlternateColorCodes('&', msg));
		else if(j.is_ELITE())
			e.setFormat(ChatColor.GOLD+""+ChatColor.BOLD+Ranks.ELITE.name()+" "+j.getNameTagColor() + p.getName() + ChatColor.DARK_GRAY+" » " + ChatColor.GRAY+ChatColor.translateAlternateColorCodes('&', msg));
		else if(j.is_SVIP())
			e.setFormat(ChatColor.GREEN+""+ChatColor.BOLD+Ranks.SVIP.name()+" "+j.getNameTagColor() + p.getName() + ChatColor.DARK_GRAY+" » " + ChatColor.GRAY+ChatColor.translateAlternateColorCodes('&', msg));
		else if(j.is_VIP())
			e.setFormat(ChatColor.AQUA+""+ChatColor.BOLD+Ranks.VIP.name()+" "+j.getNameTagColor() + p.getName() + ChatColor.DARK_GRAY+" » " + ChatColor.GRAY+ChatColor.translateAlternateColorCodes('&', msg));
		else if(j.is_Premium())
			e.setFormat(ChatColor.BLUE+""+ChatColor.BOLD+Ranks.PREMIUM.name()+" "+j.getNameTagColor() + p.getName() + ChatColor.DARK_GRAY+" » " + ChatColor.GRAY+msg);
		else
			e.setFormat(ChatColor.YELLOW + p.getName() + ChatColor.DARK_GRAY+" » " + ChatColor.GRAY+msg);
		
		e.setFormat(ChatColor.GREEN+"Lv "+j.getKitPVP_Stats_Level()+" "+e.getFormat());
	}
	
	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		if(e.getResult() == Result.KICK_FULL) {
			if(Jugador.getJugador(e.getPlayer().getName()).is_VIP())
				e.allow();

		}
	}
	
	
	@EventHandler
	public void onLogin(AsyncPlayerPreLoginEvent e) {
		Jugador jug  = Jugador.getJugador(e.getName());
    	Database.loadPlayerRank_SYNC(jug);
		Database.loadPlayerSV_KITPVP_SYNC(jug);
		Database.loadPlayerCoins_SYNC(jug);
	}

	@EventHandler(priority=EventPriority.NORMAL)
	  public void onPreCommand(PlayerCommandPreprocessEvent event)
	  {
		String msg = event.getMessage().toLowerCase();
	    if ((msg.startsWith("/worldedit:/calc")) || 
	      (msg.startsWith("/worldedit:/eval")) || 
	      (msg.startsWith("/worldedit:/solve")) || 
	      (msg.startsWith("//calc")) || 
	      (msg.startsWith("/help")) || 
	      (msg.startsWith("/ver")) || 
	      (msg.startsWith("/pl")) || 
	      (msg.startsWith("/bukkit")) || 
	      (msg.startsWith("/op")) || 
	      (msg.startsWith("/minecraft:op")) || 
	      (msg.startsWith("/deop")) || 
	      (msg.startsWith("/minecraft:deop")) || 
	      (msg.startsWith("/say")) ||
	      (msg.startsWith("/summon")) ||
	      (msg.startsWith("/minecraft:summon")) ||
	      (msg.startsWith("//sphere")) ||
	      (msg.startsWith("/worldborder")) ||
	      (msg.startsWith("/minecraft:worldborder")) ||
	      (msg.startsWith("/viaver")) ||
	      (msg.startsWith("//eval")) || 
	      (msg.startsWith("//solve")))
	    	
	    {
	    	Jugador jug = Jugador.getJugador(event.getPlayer());
	    	if(!jug.is_Owner()){
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.RED+"No tienes permitido hacer esto!");
			}
	    }
	    LinkedHashMap<Player , Integer> prueba = new LinkedHashMap<>();

	Player p = getKey(prueba, prueba.get(1));
	  }

	public static <K, V> K getKey(Map<K, V> map, V value)
	{
		for (Map.Entry<K, V> entry: map.entrySet())
		{
			if (value.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}

	@EventHandler
	public void Chest(InventoryCloseEvent e){
		if(e.getInventory().getType() == InventoryType.CHEST)
			e.getInventory().clear();

	}

    @EventHandler
    public void onPlayerInteractWithEntity(PlayerInteractEntityEvent e) {
    	if (e.getRightClicked() instanceof LivingEntity) {
    		LivingEntity npc = (LivingEntity) e.getRightClicked();
    		String name = npc.getName();
    		
    		if(ChatColor.stripColor(name).equalsIgnoreCase("Kits")) {
    			new KitMenu(Jugador.getJugador(e.getPlayer()));
    		}
    		if(ChatColor.stripColor(name).equalsIgnoreCase("Tienda")) {
    			new TiendaMenu(Jugador.getJugador(e.getPlayer()));
    		}
    		if(ChatColor.stripColor(name).equalsIgnoreCase("Spawn")) {
    			List<Location> locs = KitPVPMain.getCfg().getArenaSpawns();
    			e.getPlayer().teleport(locs.get(random.nextInt(locs.size())));	
    		}
    	}
    }
	
	
	
	
	@EventHandler
	public void onEntityDamageEntity(EntityDamageByEntityEvent e) {
		 Entity ent = e.getEntity();
		 if(ent instanceof Player) {
			 Jugador target = Jugador.getJugador(((Player) ent));
				 Entity damager = e.getDamager();
				 if (e.getCause().equals(DamageCause.PROJECTILE)) {
					 if (damager instanceof Snowball) {
						 Snowball snowball = (Snowball) damager;
						 if(snowball.getShooter() instanceof Player) {
							 Jugador killer = Jugador.getJugador(((Player) snowball.getShooter()));
								 Tagged.addTagged(target, killer, System.currentTimeMillis());
								 return;
							 }
					 	} else if (damager instanceof Egg) {
							 Egg egg = (Egg) damager;
							 if(egg.getShooter() instanceof Player) {
								 Jugador killer = Jugador.getJugador(((Player) egg.getShooter()));
									 Tagged.addTagged(target, killer, System.currentTimeMillis());
									 return;
								 }
					 	} else if (damager instanceof Arrow) {
							 Arrow arrow = (Arrow) damager;
							 if(arrow.getShooter() instanceof Player) {
								 Jugador killer = Jugador.getJugador(((Player) arrow.getShooter()));
									 Tagged.addTagged(target, killer, System.currentTimeMillis());
									 return;
							 }
					 	} else if (damager instanceof EnderPearl) {
						 EnderPearl ePearl = (EnderPearl) damager;
						 if(ePearl.getShooter() instanceof Player) {
							 Jugador killer = Jugador.getJugador(((Player) ePearl.getShooter()));
								 Tagged.addTagged(target, killer, System.currentTimeMillis());
								 return;
							 }
						 }
				 } else if (damager instanceof Player) {
					 Jugador killer = Jugador.getJugador(((Player) damager));
						 Tagged.addTagged(target, killer, System.currentTimeMillis());

					}
		 }
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerSpawnLocationEvent e) {
		e.setSpawnLocation(KitPVPMain.getCfg().getSpawn());
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {

		e.setJoinMessage(null);
		Player p = e.getPlayer();
		Jugador jug = Jugador.getJugador(p);
		jug.setBukkitPlayer(p);
		if(jug.is_RUBY()) {
			p.sendMessage(ChatColor.GREEN + "Hola! al ser rango RUBY tienes un descuento del 35% en el apartado de reparaciones de armaduras .");
		}else if(jug.is_ELITE()) {
			p.sendMessage(ChatColor.GREEN + "Hola! al ser rango ELITE tienes un descuento del 20% en el apartado de reparaciones de armaduras. ");
		}else if(jug.is_SVIP()) {
			 p.sendMessage(ChatColor.GREEN + "Hola! al ser rango SVIP tienes un descuento del 10% en el apartado de reparaciones de armaduras. ");
		}else if(jug.is_VIP()) {
			p.sendMessage(ChatColor.GREEN + "Hola! al ser rango VIP tienes un descuento del 5% en el apartado de reparaciones de armaduras. ");
		}else if(jug.is_YOUTUBER()) {
			p.sendMessage(ChatColor.GREEN + "Hola! al tener el rango youtuber puedes usar /yt o /twitch antes de tu mensaje, para poder anunciar tu canal en todo el servidor ");
		}
		
		e.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		LobbyGameController.getLobby().prepareforLobby(p, false);
		
		Util.sendTitle(p, 20, 60, 20, ChatColor.GREEN+"KitPVP", ChatColor.GOLD+"www.minelc.net");
		
		addPermissionDEFAULT(p);
		
		if(jug.is_MODERADOR() || jug.is_AYUDANTE()) {
			addPermissionMODHelper(p);
		}
		
		if(jug.is_Admin() || jug.is_MODERADOR()) {
			addPermissionMOD(p);
			addPermissionMODHelper(p);
		}
		if(jug.is_YOUTUBER()) {
			addPermissionYT(p);
		}
		if(jug.is_VIP()){
			addPermissionVIP(p);
		}
		if(jug.is_SVIP()){
			addPermissionSVIP(p);
		}
		if(jug.is_ELITE()){
			addPermissionELITE(p);
		}
		if(jug.is_RUBY()){
			addPermissionRUBY(p);
		}

		for(Player vanished : KitPVPMain.Vanished) {
			p.hidePlayer(vanished);
		}
		for(Player all : Bukkit.getOnlinePlayers()){
			Jugador juga = Jugador.getJugador(all);
			LobbyGameController.getLobby().updateScoreboard(juga);
		}
		//
	}
	
	public void addPermissionDEFAULT(Player p) {
		for(String str : KitPVPMain.get().getConfig().getStringList("permissions.default")){
		    Bukkit.getConsoleSender().sendMessage(str);
			p.addAttachment(CoreMain.getInstance(), str, true);
		}


	    }
	public void addPermissionYT(Player p) {
		for(String str : KitPVPMain.get().getConfig().getStringList("permissions.YOUTUBER")){
			p.addAttachment(CoreMain.getInstance(), str, true);
		}
	}
	public void addPermissionMOD(Player p) {
		for(String str : KitPVPMain.get().getConfig().getStringList("permissions.MOD")){
			p.addAttachment(CoreMain.getInstance(), str, true);
		}

	}
	public void addPermissionVIP(Player p) {
		for(String str : KitPVPMain.get().getConfig().getStringList("permissions.VIP")){
			p.addAttachment(CoreMain.getInstance(), str, true);
		}

	}
	public void addPermissionSVIP(Player p) {
		for(String str : KitPVPMain.get().getConfig().getStringList("permissions.SVIP")){
			p.addAttachment(CoreMain.getInstance(), str, true);
		}

	}
	public void addPermissionELITE(Player p) {
		for(String str : KitPVPMain.get().getConfig().getStringList("permissions.ELITE")){
			p.addAttachment(CoreMain.getInstance(), str, true);
		}

	}
	public void addPermissionRUBY(Player p) {
		for(String str : KitPVPMain.get().getConfig().getStringList("permissions.RUBY")){
			p.addAttachment(CoreMain.getInstance(), str, true);
		}

	}
	public void addPermissionADMIN(Player p) {
		for(String str : KitPVPMain.get().getConfig().getStringList("permissions.ADMIN")){
			p.addAttachment(CoreMain.getInstance(), str, true);
		}
	    
	}
	
	public void addPermissionMODHelper(Player p) {
		for(String str : KitPVPMain.get().getConfig().getStringList("permissions.HELPER")){
			p.addAttachment(CoreMain.getInstance(), str, true);
		}
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		e.setRespawnLocation(KitPVPMain.getCfg().getSpawn());
		LobbyGameController.getLobby().updateScoreboard(Jugador.getJugador(e.getPlayer()));
		LobbyGameController.getLobby().prepareforLobby(e.getPlayer(), true);
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		e.setQuitMessage(null);
		Player p = e.getPlayer();
		Jugador jug = Jugador.getJugador(p);
		playerQuit(jug);
		Tagged.removeTagged(jug);
		LobbyGameController.getLobby().prepareforLeave(p);
		Jugador.removeJugador(p.getName());
		
		for(Player Online : Bukkit.getOnlinePlayers()) {
			Online.getScoreboard().getTeam(p.getName()).unregister();
		}
		p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).unregister();
		p.getScoreboard().getObjective(DisplaySlot.BELOW_NAME).unregister();
		new BukkitRunnable(){

			@Override
			public void run() {
				for(Player all : Bukkit.getOnlinePlayers()){
					Jugador juga = Jugador.getJugador(all);
					LobbyGameController.getLobby().updateScoreboard(juga);
				}
			}
		}.runTaskLater(KitPVPMain.get(),40L);
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent e) {
		Tagged.removeTagged(Jugador.getJugador(e.getPlayer()));
		e.setLeaveMessage(null);
	}


	@EventHandler
	public void Anvil(InventoryClickEvent e) {
		if(e.getInventory().getType() == InventoryType.ANVIL){
			Map<Enchantment, Integer> enchant = e.getCurrentItem().getEnchantments();
			for(Integer x : enchant.values()){
				if(x > 2)
					e.setCancelled(true);

			}

		}
	}

	@EventHandler 
    public void onPlayerInteract(PlayerInteractEvent e) {

		if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK) {
				if(e.getClickedBlock().getType().name().contains("SIGN")) {
					Sign sign = (Sign) e.getClickedBlock().getState();
					String line0 = sign.getLine(0).toLowerCase();
					String line1 = sign.getLine(1).toLowerCase();

					if(line0.contains("free")) {
						ItemStack pots = new ItemStack(Material.getMaterial(373), 1, (short) 16421);
						for(int x=0; x<=36; x++){
							e.getPlayer().getInventory().addItem(pots);
						}
						return;
					}
					if(line0.contains("kits")) {
						new KitMenu(Jugador.getJugador(e.getPlayer()));
						return;
					}
					 if(line0.contains("kit")){
						GameKit gameKit = KitPVPMain.getKC().getByName(ChatColor.stripColor(sign.getLine(1)));
						if(gameKit == null){
							e.getPlayer().sendMessage(ChatColor.RED + "Este kit no existe.");
							return;
						}

						if(!KitMenu.hasFreePermission(Jugador.getJugador(e.getPlayer()), gameKit)){
							e.getPlayer().sendMessage(ChatColor.RED + "Necesitas tener un rango superior para usar este kit.");
							e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.VILLAGER_NO, 5f , 5f);

							return;
						}
						e.getPlayer().getInventory().clear();
						 for (PotionEffect effect : e.getPlayer().getActivePotionEffects ()){
							 e.getPlayer().removePotionEffect(effect.getType());
						 }
						KitPVPMain.getKC().givePotionEffects(e.getPlayer(), gameKit);
						KitPVPMain.getKC().populateInventory(e.getPlayer(), gameKit);
						e.getPlayer().sendMessage(ChatColor.GREEN + "Has seleccionado el kit: " + ChatColor.GOLD + gameKit.getKitName());
						e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.NOTE_PLING, 5f , 5f);
					}


					else if(line0.contains("tienda")) {
						new TiendaMenu(Jugador.getJugador(e.getPlayer()));
					} else if(line0.contains("spawn")) {
						List<Location> locs = KitPVPMain.getCfg().getArenaSpawns();
						e.getPlayer().teleport(locs.get(random.nextInt(locs.size())));
						KitPVPMain.get().addInvensible(e.getPlayer());
					} else if(line0.contains("comprar")){
						String nocolorint = ChatColor.stripColor(sign.getLine(3).replace("$", ""));
						int price = Integer.parseInt(nocolorint);
						Jugador jug = Jugador.getJugador(e.getPlayer());
						int descuento =0;
						if(jug.is_RUBY())
						{
							descuento = (price * 35) / 100;
							price = price - descuento;
						}else if(jug.is_ELITE()) 
						{
							descuento = (price * 20) / 100;
							price = price - descuento;
						}else if(jug.is_SVIP())
						{
							descuento = (price * 10) / 100;
							price = price - descuento;
						}else if(jug.is_VIP())
						{
							descuento = (price * 5) / 100;
							price = price - descuento;
						}
						if(canPurchase(jug, price))
						{
							if(line1.contains("aspecto"))
							    {
								if(compraraspec(e.getPlayer(), price)) {
									removeBalance(jug, price);
								 }
							}else if(line1.contains("filo")) {
								if(comprarfilo(e.getPlayer(), price)) {
									removeBalance(jug, price);
								}
							}else if(line1.contains("irrompibilidad")) {
									if(comprarirrompi(e.getPlayer(), price)) {
										removeBalance(jug, price);
									}
								}else if(line1.contains("espinas")) {
									if(comprarespinas(e.getPlayer(), price)) {
										removeBalance(jug, price);
									}
								}else if(line1.contains("poder")) {
									if(comprarpoder(e.getPlayer(), price)) {
									removeBalance(jug, price);
									}
								}else if(line1.contains("proteccion")) {
								if(comprarprote(e.getPlayer(), price)) {
									removeBalance(jug, price);
								}
							}
							//agregar aqui.
						 LobbyGameController.getLobby().updateScoreboard(jug);
				         e.getPlayer().updateInventory();
				         e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.NOTE_PLING, 0.5f,0.5f);
						} else {
                            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.VILLAGER_NO, 0.5f,0.5f);

                            e.getPlayer().sendMessage(ChatColor.RED+"No tienes suficientes monedas para comprar esto.");
				         }
					} 
					else if(line0.contains("items")){
						String id = sign.getLine(1).toLowerCase();
						String cantidad = sign.getLine(2).toLowerCase();
						String precio = ChatColor.stripColor(sign.getLine(3).replace("$", ""));
						int price = Integer.parseInt(precio);
						Jugador jug = Jugador.getJugador(e.getPlayer());
						int descuento =0;
						if(jug.is_RUBY())
						{
							descuento = (price * 35) / 100;
							price = price - descuento;
						}else if(jug.is_ELITE()) 
						{
							descuento = (price * 20) / 100;
							price = price - descuento;
						}else if(jug.is_SVIP())
						{
							descuento = (price * 10) / 100;
							price = price - descuento;
						}else if(jug.is_VIP())
						{
							descuento = (price * 5) / 100;
							price = price - descuento;
						}
						if(canPurchase(jug, price))
						{
							ItemStack item;
							if(id.contains(":")){
								int itemid = Integer.parseInt(id.split(":")[0]);
								int itemdata = Integer.parseInt(id.split(":")[1]);

								item = new ItemStack(Material.getMaterial(itemid), Integer.parseInt(cantidad), (short) itemdata);
							} else {
								item = new ItemStack(Material.getMaterial(id), Integer.parseInt(cantidad));

							}
							removeBalance(jug, price);
							//agregar aqui.
						 LobbyGameController.getLobby().updateScoreboard(jug);
						 e.getPlayer().getInventory().addItem(item);
				         e.getPlayer().updateInventory();
                            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.NOTE_PLING, 0.5f,0.5f);
						} else {
                            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.VILLAGER_NO, 0.5f,0.5f);
				        	 e.getPlayer().sendMessage(ChatColor.RED+"No tienes suficientes monedas para comprar esto.");
				         }
					}
					else if(line0.contains("reparar")) {
						String nocolorint = ChatColor.stripColor(sign.getLine(2).replace("$", ""));
						int price = Integer.parseInt(nocolorint);
						Jugador jug = Jugador.getJugador(e.getPlayer());
						int descuento =0;
						if(jug.is_RUBY())
						{
							descuento = (price * 35) / 100;
							price = price - descuento;
						} else if(jug.is_ELITE())
						{
							descuento = (price * 20) / 100;
							price = price - descuento;
						}else if(jug.is_SVIP())
						{
							descuento = (price * 10) / 100;
							price = price - descuento;
						}else if(jug.is_VIP())
						{
							descuento = (price * 5) / 100;
							price = price - descuento;
						}
						if(canPurchase(jug, price)) {
							if(line1.contains("botas")) {
								if(repararBotas(e.getPlayer(), price)) {
									removeBalance(jug, price);
								}
							} else if(line1.contains("pantalones")) {
								if(repararPantalones(e.getPlayer(), price)) {
									removeBalance(jug, price);
								}
							} else if(line1.contains("pechera")) {
								if(repararPechera(e.getPlayer(), price)) {
									removeBalance(jug, price);
								}
							} else if(line1.contains("casco")) {
								if(repararCasco(e.getPlayer(), price)) {
									removeBalance(jug, price);
								}
							} else if(line1.contains("hacha")) {
								if(repararHacha(e.getPlayer(), price)) {
									removeBalance(jug, price);
								}
							} else if(line1.contains("espada")) {
								if(repararEspada(e.getPlayer(), price)) {
									removeBalance(jug, price);
								}
							}
							LobbyGameController.getLobby().updateScoreboard(jug);
							e.getPlayer().updateInventory();
                            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ANVIL_USE, 0.5f,0.5f);
						} else {
                            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.VILLAGER_NO, 0.5f,0.5f);
							e.getPlayer().sendMessage(ChatColor.RED+"No tienes suficientes puntos para reparar esto.");
						}
					}
				} else if(e.getClickedBlock().getType() == Material.ENDER_CHEST) {
					e.getPlayer().openInventory(e.getPlayer().getEnderChest());
				} else if(e.getClickedBlock().getType() == Material.ANVIL) {
					e.getPlayer().sendMessage(ChatColor.RED+"Proximamente fusion de items!");
				}

		} //else if(e.getAction() == Action.RIGHT_CLICK_AIR && e.getMaterial() == Material.POTION) {
			//if(e.getPlayer().getWorld() == KitPVPMain.getCfg().getSpawn().getWorld()) {
				//e.setCancelled(false);
				//e.getPlayer().updateInventory();
			//}
		}
    //}
	public boolean comprarprote(Player p, int price) {

		ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);

		EnchantmentStorageMeta meta = (EnchantmentStorageMeta)book.getItemMeta();
		meta.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1,true);
		book.setItemMeta(meta);
		p.setItemInHand(book);
		p.sendMessage(ChatColor.GREEN+"Libro Comprado!");

		return true;

	}
	public boolean comprarpoder(Player p, int price) {
		if(p.getItemInHand() == null) {
			p.sendMessage(ChatColor.RED+"Coloca el item en tu mano!");
			return false;
		}
		if(p.getItemInHand().getType() != Material.BOW){
			p.sendMessage(ChatColor.RED+"Solo puedes usar este encantamiento en arcos.");
			return false;
		}
		ItemStack newitem = new ItemStack(p.getItemInHand().getType());
		newitem.addUnsafeEnchantments(p.getItemInHand().getEnchantments());
		ItemMeta meta = newitem.getItemMeta();
		meta.addEnchant(Enchantment.ARROW_DAMAGE, 2, true);
		List<String> lore = Lists.newArrayList();
		lore.add(ChatColor.AQUA+"Item De Tienda");
		meta.setLore(lore);
		newitem.setItemMeta(meta);
		p.setItemInHand(newitem);
		p.sendMessage(ChatColor.GREEN+"Encantamiento Agregado!");
		return true;

	}
	//encantamientos
	public boolean compraraspec(Player p, int price) {
		if(p.getItemInHand().getType().name().contains("AXE")||p.getItemInHand().getType().name().contains("SWORD")) {
			ItemStack newitem = new ItemStack(p.getItemInHand().getType());
			newitem.addUnsafeEnchantments(p.getItemInHand().getEnchantments());
			ItemMeta meta = newitem.getItemMeta();
			meta.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
        	List<String> lore = Lists.newArrayList();
        	lore.add(ChatColor.AQUA+"Item De Tienda");
        	meta.setLore(lore);
        	newitem.setItemMeta(meta);
			p.setItemInHand(newitem);
			p.sendMessage(ChatColor.GREEN+"Encantamiento Agregado!");
			return true;
		} else {
			p.sendMessage(ChatColor.RED+"Coloca la espada en tu mano!");
		}
		return false;
	}
	public boolean comprarfilo(Player p, int price) {
		if(p.getItemInHand().getType().name().contains("AXE")) {
			ItemStack newitem = new ItemStack(p.getItemInHand().getType());
			newitem.addUnsafeEnchantments(p.getItemInHand().getEnchantments());
			ItemMeta meta = newitem.getItemMeta();
			meta.addEnchant(Enchantment.DAMAGE_ALL, 3, true);
        	List<String> lore = Lists.newArrayList();
        	lore.add(ChatColor.AQUA+"Item De Tienda");
        	meta.setLore(lore);
        	newitem.setItemMeta(meta);
			p.setItemInHand(newitem);
			p.sendMessage(ChatColor.GREEN+"Encantamiento Agregado!");
			return true;
		} else {
			p.sendMessage(ChatColor.RED+"Solo puedes encantar un hacha!");
		}
		return false;
	}	
	
	public boolean comprarirrompi(Player p, int price) {
		if(p.getItemInHand().getType() != null && p.getItemInHand().getType() !=Material.AIR ) {
			ItemStack newitem = new ItemStack(p.getItemInHand().getType());
			newitem.addUnsafeEnchantments(p.getItemInHand().getEnchantments());
			ItemMeta meta = newitem.getItemMeta();
			meta.addEnchant(Enchantment.DURABILITY, 1, true);
        	List<String> lore = Lists.newArrayList();
        	lore.add(ChatColor.AQUA+"Item De Tienda");
        	meta.setLore(lore);
        	newitem.setItemMeta(meta);
			p.setItemInHand(newitem);
			p.sendMessage(ChatColor.GREEN+"Encantamiento Agregado!");
			return true;
		} else {
			p.sendMessage(ChatColor.RED+"Coloca el item en tu mano!");
		}
		return false;
	}	
	public boolean comprarespinas(Player p, int price) {
		if(p.getItemInHand().getType() != null && p.getItemInHand().getType() !=Material.AIR ) {
			ItemStack newitem = new ItemStack(p.getItemInHand().getType());
			newitem.addEnchantments(p.getItemInHand().getEnchantments());
			ItemMeta meta = newitem.getItemMeta();
			meta.addEnchant(Enchantment.THORNS, 2, true);
        	List<String> lore = Lists.newArrayList();
        	lore.add(ChatColor.AQUA+"Item De Tienda");
        	meta.setLore(lore);
        	newitem.setItemMeta(meta);
			p.setItemInHand(newitem);
			p.sendMessage(ChatColor.GREEN+"Encantamiento Agregado!");
			return true;
		} else {
			p.sendMessage(ChatColor.RED+"Coloca el item en tu mano!");
		}
		return false;
		
}  //items nuevos
	public boolean tiendacasco(Player p, int price) {
		if(p.getItemInHand().getType() != null && p.getItemInHand().getType() !=Material.AIR ) {
			p.sendMessage(ChatColor.RED+"No debes tener ningun item en tu mano!");
			return false;
		} else {
		ItemStack newitem = new ItemStack(Material.DIAMOND_HELMET);
		ItemMeta meta = newitem.getItemMeta();
    	List<String> lore = Lists.newArrayList();
    	lore.add(ChatColor.AQUA+"Item De Tienda");
    	meta.setLore(lore);
    	newitem.setItemMeta(meta);
    	p.getInventory().addItem(newitem);
		p.sendMessage(ChatColor.GREEN+"Item comprado!");
		}
		return true;
}
	public boolean tiendapechera(Player p, int price) {
		if(p.getItemInHand().getType() != null && p.getItemInHand().getType() !=Material.AIR ) {
			p.sendMessage(ChatColor.RED+"No debes tener ningun item en tu mano!");
			return false;
			//return true;
		} else {
		ItemStack newitem = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemMeta meta = newitem.getItemMeta();
    	List<String> lore = Lists.newArrayList();
    	lore.add(ChatColor.AQUA+"Item De Tienda");
    	meta.setLore(lore);
    	newitem.setItemMeta(meta);
    	p.getInventory().addItem(newitem);
		p.sendMessage(ChatColor.GREEN+"Item comprado!");
		
		}
		return true;
		//return false;
}
	public boolean tiendapantalon(Player p, int price) {
		if(p.getItemInHand().getType() != null && p.getItemInHand().getType() !=Material.AIR ) {
			p.sendMessage(ChatColor.RED+"No debes tener ningun item en tu mano!");
			//return true;
			return false;
		} else {
		ItemStack newitem = new ItemStack(Material.DIAMOND_LEGGINGS);
		ItemMeta meta = newitem.getItemMeta();
    	List<String> lore = Lists.newArrayList();
    	lore.add(ChatColor.AQUA+"Item De Tienda");
    	meta.setLore(lore);
    	newitem.setItemMeta(meta);
    	p.getInventory().addItem(newitem);
		p.sendMessage(ChatColor.GREEN+"Item comprado!");
	
		}
		return true;
		//return false;
}
	public boolean tiendabotas(Player p, int price) {
		if(p.getItemInHand().getType() != null && p.getItemInHand().getType() !=Material.AIR ) {
			p.sendMessage(ChatColor.RED+"No debes tener ningun item en tu mano!");
			//return true;
			return false;
		} else {
		ItemStack newitem = new ItemStack(Material.DIAMOND_BOOTS);
		ItemMeta meta = newitem.getItemMeta();
    	List<String> lore = Lists.newArrayList();
    	lore.add(ChatColor.AQUA+"Item De Tienda");
    	meta.setLore(lore);
    	newitem.setItemMeta(meta);
    	p.getInventory().addItem(newitem);
		p.sendMessage(ChatColor.GREEN+"Item comprado!");
		
		}
		return true;
		//return false;
}
	public boolean tiendahacha(Player p, int price) {
		if(p.getItemInHand().getType() != null && p.getItemInHand().getType() !=Material.AIR ) {
			p.sendMessage(ChatColor.RED+"No debes tener ningun item en tu mano!");
			return false;
			//return true;
		} else {
		ItemStack newitem = new ItemStack(Material.DIAMOND_AXE);
		ItemMeta meta = newitem.getItemMeta();
    	List<String> lore = Lists.newArrayList();
    	lore.add(ChatColor.AQUA+"Item De Tienda");
    	meta.setLore(lore);
    	newitem.setItemMeta(meta);
    	p.getInventory().addItem(newitem);
		p.sendMessage(ChatColor.GREEN+"Item comprado!");
		
		}
		return true;
		//return false;
}	public boolean tiendaespada(Player p, int price) {
	if(p.getItemInHand().getType() != null && p.getItemInHand().getType() !=Material.AIR ) {
		p.sendMessage(ChatColor.RED+"No debes tener ningun item en tu mano!");
		return false;
		//return true;
	} else {
	ItemStack newitem = new ItemStack(Material.DIAMOND_SWORD);
	ItemMeta meta = newitem.getItemMeta();
	List<String> lore = Lists.newArrayList();
	lore.add(ChatColor.AQUA+"Item De Tienda");
	meta.setLore(lore);
	newitem.setItemMeta(meta);
	p.getInventory().addItem(newitem);
	p.sendMessage(ChatColor.GREEN+"Item comprado!");
	
	}
	return true;
	//return false;
}

public boolean tiendadebilidad(Player p, int price) {
	if(p.getItemInHand().getType() != null && p.getItemInHand().getType() !=Material.AIR ) {
		p.sendMessage(ChatColor.RED+"No debes tener ningun item en tu mano!");
		return false;
		//return true;
	} else {
	ItemStack newitem = new ItemStack(Material.POTION, 1, (short) 16488);
	ItemMeta meta = newitem.getItemMeta();
	List<String> lore = Lists.newArrayList();
	lore.add(ChatColor.AQUA+"Item De Tienda");
	meta.setLore(lore);
	newitem.setItemMeta(meta);
	p.getInventory().addItem(newitem);
	p.sendMessage(ChatColor.GREEN+"Item comprado!");
	}
	return true;
	//return false;
}
public boolean tiendadaño(Player p, int price) {
	if(p.getItemInHand().getType() != null && p.getItemInHand().getType() !=Material.AIR ) {
		p.sendMessage(ChatColor.RED+"No debes tener ningun item en tu mano!");
		return false;
		//return true;
	} else {
	ItemStack newitem = new ItemStack(Material.POTION, 1, (short) 16428);
	ItemMeta meta = newitem.getItemMeta();
	List<String> lore = Lists.newArrayList();
	lore.add(ChatColor.AQUA+"Item De Tienda");
	meta.setLore(lore);
	newitem.setItemMeta(meta);
	p.getInventory().addItem(newitem);
	p.sendMessage(ChatColor.GREEN+"Item comprado!");
	//return true;
	}
	//return false;
	return true;
}
public boolean tiendaresistencia(Player p, int price) {
	if(p.getItemInHand().getType() != null && p.getItemInHand().getType() !=Material.AIR ) {
		p.sendMessage(ChatColor.RED+"No debes tener ningun item en tu mano!");
		return false;
	} else {
	ItemStack newitem = new ItemStack(Material.POTION, 1, (short) 8259);
	ItemMeta meta = newitem.getItemMeta();
	List<String> lore = Lists.newArrayList();
	lore.add(ChatColor.AQUA+"Item De Tienda");
	meta.setLore(lore);
	newitem.setItemMeta(meta);
	p.getInventory().addItem(newitem);
	p.sendMessage(ChatColor.GREEN+"Item comprado!");
	}
	return true;
}
public boolean tiendafuerza(Player p, int price) {
	if(p.getItemInHand().getType() != null && p.getItemInHand().getType() !=Material.AIR ) {
		p.sendMessage(ChatColor.RED+"No debes tener ningun item en tu mano!");
		return false;
	} else {
	ItemStack newitem = new ItemStack(Material.POTION, 1, (short) 16457);
	ItemMeta meta = newitem.getItemMeta();
	List<String> lore = Lists.newArrayList();
	lore.add(ChatColor.AQUA+"Item De Tienda");
	meta.setLore(lore);
	newitem.setItemMeta(meta);
	p.getInventory().addItem(newitem);
	p.sendMessage(ChatColor.GREEN+"Item comprado!");
	}
	return true;
}
public boolean repararEspada(Player p, int price) {
	if(p.getItemInHand().getType().name().contains("SWORD")) {
		ItemStack newitem = new ItemStack(p.getItemInHand().getType());
		newitem.addEnchantments(p.getItemInHand().getEnchantments());
		newitem.setItemMeta(p.getItemInHand().getItemMeta());
		p.setItemInHand(newitem);
		p.sendMessage(ChatColor.GREEN+"Espada reparada!");
		return true;
	} else {
		p.sendMessage(ChatColor.RED+"Coloca la espada en tu mano!");
	}
	return false;
}
	public boolean repararBotas(Player p, int price) {
		if(p.getItemInHand().getType().name().contains("BOOTS")) {
			ItemStack newitem = new ItemStack(p.getItemInHand().getType());
			newitem.addEnchantments(p.getItemInHand().getEnchantments());
			newitem.setItemMeta(p.getItemInHand().getItemMeta());
			p.setItemInHand(newitem);
			p.sendMessage(ChatColor.GREEN+"Botas reparadas!");
			return true;
		} else {
			p.sendMessage(ChatColor.RED+"Coloca las botas en tu mano!");
		}
		return false;
	}
	
	public boolean repararPantalones(Player p, int price) {
		if(p.getItemInHand().getType().name().contains("LEGGINGS")) {
			ItemStack newitem = new ItemStack(p.getItemInHand().getType());
			newitem.addEnchantments(p.getItemInHand().getEnchantments());
			newitem.setItemMeta(p.getItemInHand().getItemMeta());
			p.setItemInHand(newitem);
			p.sendMessage(ChatColor.GREEN+"Pantalones reparados!");
			return true;
		} else {
			p.sendMessage(ChatColor.RED+"Coloca los pantalones en tu mano!");
		}
		return false;
	}


	public boolean repararPechera(Player p, int price) {
		if(p.getItemInHand().getType().name().contains("CHESTPLATE")) {
			ItemStack newitem = new ItemStack(p.getItemInHand().getType());
			newitem.addEnchantments(p.getItemInHand().getEnchantments());
			newitem.setItemMeta(p.getItemInHand().getItemMeta());
			p.setItemInHand(newitem);
			p.sendMessage(ChatColor.GREEN+"Pechera reparada!");
			return true;
		} else {
			p.sendMessage(ChatColor.RED+"Coloca la pechera en tu mano!");
		}
		return false;
	}
	
	public boolean repararCasco(Player p, int price) {
		if(p.getItemInHand().getType().name().contains("HELMET")) {
			ItemStack newitem = new ItemStack(p.getItemInHand().getType());
			newitem.addEnchantments(p.getItemInHand().getEnchantments());
			newitem.setItemMeta(p.getItemInHand().getItemMeta());
			p.setItemInHand(newitem);
			p.sendMessage(ChatColor.GREEN+"Casco reparado!");
			return true;
		} else {
			p.sendMessage(ChatColor.RED+"Coloca el casco en tu mano!");
		}
		return false;
	}
	public boolean repararHacha(Player p, int price) {
		if(p.getItemInHand().getType().name().contains("AXE")) {
			ItemStack newitem = new ItemStack(p.getItemInHand().getType());
			newitem.addUnsafeEnchantments(p.getItemInHand().getEnchantments());
			newitem.setItemMeta(p.getItemInHand().getItemMeta());
			p.setItemInHand(newitem);
			p.sendMessage(ChatColor.GREEN+"Hacha reparada!");
			return true;
		} else {
			p.sendMessage(ChatColor.RED+"Coloca el Hacha en tu mano!");
		}
		return false;
	}
	private boolean canPurchase(Jugador jug, int price) {
    	return (jug.getLcoins() >= price);
    }
    
    private void removeBalance(Jugador jug, int x) {
    	jug.removeLcoins(x);
    	Database.savePlayerCoins(jug);
    	jug.getBukkitPlayer().playSound(jug.getBukkitPlayer().getLocation(), Sound.NOTE_PLING, 0.5F, 0.5F);
    }
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onInvClick(InventoryClickEvent e) {
		
		Player p = (Player) e.getWhoClicked();
		if(p.getOpenInventory().getType() != InventoryType.ENDER_CHEST) return;
		ItemStack item = new ItemStack(Material.getMaterial(373), 6 , (short) 16421);
		if(e.getCurrentItem().equals(item))
			e.setCancelled(true);

		if(e.getAction() == InventoryAction.HOTBAR_SWAP || e.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD) 
			e.setCancelled(true);

	}
	

	
	@EventHandler(priority = EventPriority.HIGH)
	public void onDeath(PlayerDeathEvent e) {
		e.setDeathMessage(null);
		final Player p = e.getEntity();
		Jugador target = Jugador.getJugador(p);
		LobbyGameController.getLobby().updateScoreboard(target);
			Entity ent = e.getEntity();
		
		DamageCause damageCause = DamageCause.CUSTOM;
		if (ent.getLastDamageCause() != null) {
			damageCause = ent.getLastDamageCause().getCause();
		}
		
		DamageCause dCause = damageCause;
		onPlayerDeath(target, dCause);
		
		Bukkit.getScheduler().runTaskLater(KitPVPMain.get(), new Runnable() {
			@Override
			public void run() {
				if( p.isDead()){}
			}
		}, 20L);
	}
	
	private void onPlayerDeath(final Jugador target, final DamageCause dCause) {
		target.addKitPVP_Stats_deaths(1);
		Database.savePlayerSV_KITPVP(target);
		if ((System.currentTimeMillis() - Tagged.getTime(target)) < 10000) {
			Jugador killer = Tagged.getKiller(target);
			if (killer != null) {
                killer.getBukkitPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 3, true));
				killer.addKitPVP_Stats_kills(1);
				killer.addMonthKillStat(1);
				Database.savePlayerSV_KITPVP(killer);
				int killTotal = KitPVPMain.getCfg().getKillValue();
				if(killer.is_RUBY()) {
					killTotal = KitPVPMain.getCfg().getKillValue() * KitPVPMain.getCfg().getRubyMultiplier();
				} else if (killer.is_ELITE()) {
					killTotal = KitPVPMain.getCfg().getKillValue() * KitPVPMain.getCfg().getEliteMultiplier();
				} else if (killer.is_SVIP()) {
					killTotal = KitPVPMain.getCfg().getKillValue() * KitPVPMain.getCfg().getSVIPMultiplier();
				} else if (killer.is_VIP()) {
					killTotal = KitPVPMain.getCfg().getKillValue() * KitPVPMain.getCfg().getVIPMultiplier();
				} else {
					killTotal = KitPVPMain.getCfg().getKillValue();
				}
				
				addBalance(killer, killTotal);

				LobbyGameController.getLobby().updateScoreboard(killer);
				LobbyGameController.getLobby().updateScoreboard(target);
				
				if(dCause == DamageCause.PROJECTILE)
					target.getBukkitPlayer().playSound(target.getBukkitPlayer().getLocation(), Sound.ENDERMAN_SCREAM, 1F, 2F);
				else
					target.getBukkitPlayer().playSound(target.getBukkitPlayer().getLocation(), Sound.ENDERMAN_DEATH, 1F, 2F);
				
				String deathMsg = getDeathMessage(dCause, true, target, killer);
				for(Player p : Bukkit.getOnlinePlayers())
					p.sendMessage(deathMsg);
				//save
				Database.savePlayerSV_KITPVP(killer);
				
				//efecto
				killer.getBukkitPlayer().addPotionEffect(effect);
			}
		} else {
			String msg = getDeathMessage(dCause, false, target, target);
			for(Player p : Bukkit.getOnlinePlayers())
				p.sendMessage(msg);

		}
		//save
		Database.savePlayerSV_KITPVP(target);
		LobbyGameController.getLobby().updateScoreboard(target);

	}
	
	private PotionEffect effect = new PotionEffect(PotionEffectType.REGENERATION, 60, 0);	
	private void playerQuit(Jugador jug) {
		long ttime = Tagged.getTime(jug);
		Jugador killer = Tagged.getKiller(jug);
		long dtime = System.currentTimeMillis() - ttime;
		if (killer != null) {
			if (killer != jug && ((dtime < 12500 && jug.getBukkitPlayer().getHealth() < 10) || (dtime < 4000 && jug.getBukkitPlayer().getHealth() < 19))) {
				if(KitPVPMain.stop) return;
				
					killer.addKitPVP_Stats_kills(1);
					jug.addKitPVP_Stats_deaths(1);
					Database.savePlayerSV_KITPVP(jug);
					Database.savePlayerSV_KITPVP(killer);
					int killTotal = KitPVPMain.getCfg().getKillValue();
					if(killer.is_RUBY()) {
						killTotal = KitPVPMain.getCfg().getKillValue() * KitPVPMain.getCfg().getRubyMultiplier();
					} else if (killer.is_ELITE()) {
						killTotal = KitPVPMain.getCfg().getKillValue() * KitPVPMain.getCfg().getEliteMultiplier();
					} else if (killer.is_SVIP()) {
						killTotal = KitPVPMain.getCfg().getKillValue() * KitPVPMain.getCfg().getSVIPMultiplier();
					} else if (killer.is_VIP()) {
						killTotal = KitPVPMain.getCfg().getKillValue() * KitPVPMain.getCfg().getVIPMultiplier();
					} else {
						killTotal = KitPVPMain.getCfg().getKillValue();
					}
					addBalance(killer, killTotal);
					LobbyGameController.getLobby().updateScoreboard(killer);
					
					killer.getBukkitPlayer().sendMessage(ChatColor.GRAY+jug.getBukkitPlayer().getName()+ChatColor.YELLOW+" fue asesinado por "+ChatColor.GRAY+killer.getBukkitPlayer().getName());
					String deathMsg = ChatColor.DARK_GRAY+jug.getBukkitPlayer().getName() + ChatColor.DARK_GRAY+"(" +ChatColor.DARK_GRAY+ (int) jug.getBukkitPlayer().getHealth() +" �?�" +ChatColor.DARK_GRAY+")" +" se desconecto en combate!";
					for(Player Online : Bukkit.getOnlinePlayers()) {
						Online.sendMessage(deathMsg);
					}
					
					//save
					Database.savePlayerSV_KITPVP(jug);
					Database.savePlayerSV_KITPVP(killer);

					Player p = jug.getBukkitPlayer();
					Location loc = p.getLocation();

					for(ItemStack is : p.getInventory().getContents()) {
						try {
							if(is != null) {
								loc.getWorld().dropItem(loc, is);
							}
						} catch(Exception ex){
							ex.printStackTrace();
						}
					}
					for(ItemStack is : p.getInventory().getArmorContents()) {
						try {
							if(is != null) {
								loc.getWorld().dropItem(loc, is);
							}
						} catch(Exception ex){
							ex.printStackTrace();
						}
					}
					
					p.getInventory().clear();
					p.getInventory().setArmorContents(null);
				}
		}
	}
	
	private String getDeathMessage(DamageCause dCause, boolean withHelp, Jugador target, Jugador killer) {
		String first = "";
		String second = ChatColor.YELLOW+" por "+ChatColor.GRAY+killer.getBukkitPlayer().getName();
		try {
		if (dCause.equals(DamageCause.BLOCK_EXPLOSION) || dCause.equals(DamageCause.ENTITY_EXPLOSION)) {
			first = ChatColor.GRAY+target.getBukkitPlayer().getName()+ChatColor.YELLOW+" exploto";
		} else if (dCause.equals(DamageCause.DROWNING)) {
			first = ChatColor.GRAY+target.getBukkitPlayer().getName()+ChatColor.YELLOW+" se ahogo";
		} else if (dCause.equals(DamageCause.FIRE) || dCause.equals(DamageCause.FIRE_TICK)) {
			first = ChatColor.GRAY+target.getBukkitPlayer().getName()+ChatColor.YELLOW+" murio rostizado";
		} else if (dCause.equals(DamageCause.ENTITY_ATTACK)) {
			String item = killer.getBukkitPlayer().getItemInHand().getItemMeta().getDisplayName();
			if(item == null) {
				first = ChatColor.GRAY+target.getBukkitPlayer().getName()+ChatColor.YELLOW+" fue asesinado por "+ChatColor.GRAY+killer.getBukkitPlayer().getName();
				second = "";
			} else {
				first = ChatColor.GRAY+target.getBukkitPlayer().getName()+ChatColor.YELLOW+" fue asesinado por "+ChatColor.GRAY+killer.getBukkitPlayer().getName() +ChatColor.YELLOW+ " usando " +ChatColor.RED + killer.getBukkitPlayer().getItemInHand().getItemMeta().getDisplayName();
				second = "";
			}


		} else if (dCause.equals(DamageCause.FALLING_BLOCK)) {
			first = ChatColor.GRAY+target.getBukkitPlayer().getName()+ChatColor.YELLOW+" fue aplastado";
		} else if (dCause.equals(DamageCause.LAVA)) {
			first = ChatColor.GRAY+target.getBukkitPlayer().getName()+ChatColor.YELLOW+" trato de nadar en lava y murio";
		} else if (dCause.equals(DamageCause.PROJECTILE)) {
			first = ChatColor.GRAY+target.getBukkitPlayer().getName()+ChatColor.YELLOW+" fue disparado por "+ChatColor.GRAY+killer.getBukkitPlayer().getName();
			second = "";
		} else if (dCause.equals(DamageCause.SUFFOCATION)) {
			first = ChatColor.GRAY+target.getBukkitPlayer().getName()+ChatColor.YELLOW+" murio sofocado";
		} else if (dCause.equals(DamageCause.VOID)) {
			first = ChatColor.GRAY+target.getBukkitPlayer().getName()+ChatColor.YELLOW+" cayo al vacio";
		} else {
			first = ChatColor.GRAY+target.getBukkitPlayer().getName()+ChatColor.YELLOW+" murio";
		}
		} catch(Exception ex) {
			ex.printStackTrace();
			return ChatColor.GRAY+target.getBukkitPlayer().getName()+ChatColor.YELLOW+" murio.";
		}
		if (withHelp) {
			return first + second+ChatColor.YELLOW+"!";
		} else {
			return first +ChatColor.YELLOW+ ".";
		}
	}
	
	private void addBalance(Jugador jug, int x) {
    	jug.getBukkitPlayer().playSound(jug.getBukkitPlayer().getLocation(), Sound.LEVEL_UP, 1f, 1.3f);
    	jug.getBukkitPlayer().sendMessage(ChatColor.GOLD+"+"+x+" LCoins");
    	jug.addLcoins(x);
    	Database.savePlayerCoins(jug);
    }
	
}
