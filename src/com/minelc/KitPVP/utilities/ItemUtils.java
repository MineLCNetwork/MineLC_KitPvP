package com.minelc.KitPVP.utilities;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class ItemUtils
{
  public static ItemStack parseItem(List<String> item) {
    if (item.size() < 2) {
      return null;
    }
    
    ItemStack itemStack = null;
    
    try {
      if (((String)item.get(0)).contains(":")) {
        Material material = Material.getMaterial(((String)item.get(0)).split(":")[0].toUpperCase());
        int amount = Integer.parseInt((String)item.get(1));
        if (amount < 1) {
          amount = 1;
        }
        short data = (short)Integer.parseInt(((String)item.get(0)).split(":")[1].toUpperCase());
        itemStack = new ItemStack(material, amount, data);
      } else {
        itemStack = new ItemStack(Material.getMaterial(((String)item.get(0)).toUpperCase()), Integer.parseInt((String)item.get(1)));
      } 
      
      if (item.size() > 2) {
        for (int x = 2; x < item.size(); x++) {
          if (((String)item.get(x)).split(":")[0].equalsIgnoreCase("name")) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(((String)item.get(x)).split(":")[1]);
            itemStack.setItemMeta(itemMeta);
          } else {
            itemStack.addUnsafeEnchantment(getEnchant(((String)item.get(x)).split(":")[0]), Integer.parseInt(((String)item.get(x)).split(":")[1]));
          }
        
        }
      
      }
    } catch (Exception ignored) {
      ignored.printStackTrace();
    } 
    return itemStack;
  }
  
  public static PotionEffect parseEffect(List<String> effect) {
    if (effect.size() < 2) {
      return null;
    }
    
    PotionEffect potionEffect = null;
    
    try {
      int length;
      
      PotionEffectType pType = getPotionType((String)effect.get(0));
      if (Integer.parseInt((String)effect.get(1)) == -1) {
        length = Integer.MAX_VALUE;
      } else {
        length = 20 * Integer.parseInt((String)effect.get(1));
      } 
      int level = Integer.parseInt((String)effect.get(2));
      
      potionEffect = new PotionEffect(pType, length, level);
    }
    catch (Exception exception) {}

    
    return potionEffect;
  }

  
  private static PotionEffectType getPotionType(String type) {
    String str;
    switch ((str = type.toLowerCase()).hashCode()) { case -1909245241: if (!str.equals("healthboost")) {
          break;
        }

















        
        return PotionEffectType.HEALTH_BOOST;case -1781004809: if (!str.equals("invisibility")) break;  return PotionEffectType.INVISIBILITY;case -1259714865: if (!str.equals("absorption"))
          break;  return PotionEffectType.ABSORPTION;case -1206104397: if (!str.equals("hunger")) break;  return PotionEffectType.HUNGER;case -1083012136: if (!str.equals("slowness")) break;  return PotionEffectType.SLOW;case -1052579859: if (!str.equals("nausea")) break;  return PotionEffectType.CONFUSION;case -982749432: if (!str.equals("poison")) break;  return PotionEffectType.POISON;case -820818432: if (!str.equals("nightvision")) break;  return PotionEffectType.NIGHT_VISION;case -787569677: if (!str.equals("wither")) break;  return PotionEffectType.WITHER;case -736186929: if (!str.equals("weakness")) break;  return PotionEffectType.WEAKNESS;case -258770869: if (!str.equals("waterbreathing")) break;  return PotionEffectType.WATER_BREATHING;case -230491182: if (!str.equals("saturation"))
          break;  return PotionEffectType.SATURATION;case 99050123: if (!str.equals("haste")) break;  return PotionEffectType.FAST_DIGGING;case 109641799: if (!str.equals("speed")) break;  return PotionEffectType.SPEED;case 151619372: if (!str.equals("blindness")) break;  return PotionEffectType.BLINDNESS;case 585192961: if (!str.equals("miningfatique")) break;  return PotionEffectType.SLOW_DIGGING;case 687774773: if (!str.equals("jumpboost")) break;  return PotionEffectType.JUMP;case 793183376: if (!str.equals("instantdamage")) break;  return PotionEffectType.HARM;case 911047549: if (!str.equals("instanthealth")) break;  return PotionEffectType.HEAL;case 1032770443: if (!str.equals("regeneration"))
          break;  return PotionEffectType.REGENERATION;case 1791316033: if (!str.equals("strength"))
          break;  return PotionEffectType.INCREASE_DAMAGE;case 1820951279: if (!str.equals("fireresistance"))
          break;  return PotionEffectType.FIRE_RESISTANCE;case 1863800889: if (!str.equals("resistance"))
          break;  return PotionEffectType.DAMAGE_RESISTANCE; }  try { return PotionEffectType.getByName(type.toUpperCase()); }
    catch (Exception ex)
    { ex.printStackTrace();
      return PotionEffectType.getById(Integer.valueOf(type).intValue()); }
  
  }

  
  private static Enchantment getEnchant(String enchant) {
    String str;
    switch ((str = enchant.toLowerCase()).hashCode()) { case -1844207466: if (!str.equals("depthstrider")) {
          break;
        }









        
        return Enchantment.DEPTH_STRIDER;case -1758030127: if (!str.equals("blastprotection"))
          break;  return Enchantment.PROTECTION_EXPLOSIONS;case -1727707761: if (!str.equals("fireprotection"))
          break;  return Enchantment.PROTECTION_FIRE;
      case -1697088540: if (!str.equals("aquaaffinity"))
          break;  return Enchantment.WATER_WORKER;
      case -1684858151: if (!str.equals("protection"))
          break;  return Enchantment.PROTECTION_ENVIRONMENTAL;
      case -1571105471: if (!str.equals("sharpness"))
          break;  return Enchantment.DAMAGE_ALL;
      case -1498282618: if (!str.equals("luckofthesea"))
          break;  return Enchantment.LUCK;
      case -874519716:
        if (!str.equals("thorns"))
          break;  return Enchantment.THORNS;case -677216191: if (!str.equals("fortune")) break;  return Enchantment.LOOT_BONUS_BLOCKS;case -226555378: if (!str.equals("fireaspect")) break;  return Enchantment.FIRE_ASPECT;case 3333041: if (!str.equals("luck")) break;  return Enchantment.LUCK;case 3333500: if (!str.equals("lure")) break;  return Enchantment.LURE;case 97513267: if (!str.equals("flame")) break;  return Enchantment.ARROW_FIRE;case 106858757: if (!str.equals("power")) break;  return Enchantment.ARROW_DAMAGE;case 107028782: if (!str.equals("punch")) break;  return Enchantment.ARROW_KNOCKBACK;case 109556736: if (!str.equals("smite")) break;  return Enchantment.DAMAGE_UNDEAD;case 173173288: if (!str.equals("infinity")) break;  return Enchantment.ARROW_INFINITE;case 296179074: if (!str.equals("projectileprotection")) break;  return Enchantment.PROTECTION_PROJECTILE;case 350056506: if (!str.equals("looting")) break;  return Enchantment.LOOT_BONUS_MOBS;case 610735774: if (!str.equals("featherfall")) break;  return Enchantment.PROTECTION_FALL;case 617956221: if (!str.equals("baneofarthropods")) break;  return Enchantment.DAMAGE_ARTHROPODS;case 915847580: if (!str.equals("respiration")) break;  return Enchantment.OXYGEN;case 961218153: if (!str.equals("efficiency"))
          break;  return Enchantment.DIG_SPEED;case 976288699: if (!str.equals("knockback"))
          break;  return Enchantment.KNOCKBACK;case 1147645450: if (!str.equals("silktouch"))
          break;  return Enchantment.SILK_TOUCH;case 1603571740: if (!str.equals("unbreaking"))
          break;  return Enchantment.DURABILITY; }  try { return Enchantment.getByName(enchant.toUpperCase()); }
    catch (Exception ex)
    { return Enchantment.getById(Integer.valueOf(enchant).intValue()); }
  
  }
  
  public static boolean isEnchanted(ItemStack itemStack) {
    if (itemStack.containsEnchantment(Enchantment.ARROW_DAMAGE))
      return true; 
    if (itemStack.containsEnchantment(Enchantment.ARROW_DAMAGE))
      return true; 
    if (itemStack.containsEnchantment(Enchantment.ARROW_FIRE))
      return true; 
    if (itemStack.containsEnchantment(Enchantment.ARROW_INFINITE))
      return true; 
    if (itemStack.containsEnchantment(Enchantment.ARROW_KNOCKBACK))
      return true; 
    if (itemStack.containsEnchantment(Enchantment.DAMAGE_ALL))
      return true; 
    if (itemStack.containsEnchantment(Enchantment.DAMAGE_ARTHROPODS))
      return true; 
    if (itemStack.containsEnchantment(Enchantment.DAMAGE_UNDEAD))
      return true; 
    if (itemStack.containsEnchantment(Enchantment.DIG_SPEED))
      return true; 
    if (itemStack.containsEnchantment(Enchantment.DURABILITY))
      return true; 
    if (itemStack.containsEnchantment(Enchantment.FIRE_ASPECT))
      return true; 
    if (itemStack.containsEnchantment(Enchantment.KNOCKBACK))
      return true; 
    if (itemStack.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS))
      return true; 
    if (itemStack.containsEnchantment(Enchantment.LOOT_BONUS_MOBS))
      return true; 
    if (itemStack.containsEnchantment(Enchantment.LUCK))
      return true; 
    if (itemStack.containsEnchantment(Enchantment.LURE))
      return true; 
    if (itemStack.containsEnchantment(Enchantment.OXYGEN))
      return true; 
    if (itemStack.containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL))
      return true; 
    if (itemStack.containsEnchantment(Enchantment.PROTECTION_EXPLOSIONS))
      return true; 
    if (itemStack.containsEnchantment(Enchantment.PROTECTION_FALL))
      return true; 
    if (itemStack.containsEnchantment(Enchantment.PROTECTION_FIRE))
      return true; 
    if (itemStack.containsEnchantment(Enchantment.PROTECTION_PROJECTILE))
      return true; 
    if (itemStack.containsEnchantment(Enchantment.SILK_TOUCH))
      return true; 
    if (itemStack.containsEnchantment(Enchantment.THORNS))
      return true; 
    if (itemStack.containsEnchantment(Enchantment.WATER_WORKER)) {
      return true;
    }
    return false;
  }
  
  public static ItemStack name(ItemStack itemStack, String name, String... lores) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    
    if (!name.isEmpty()) {
      itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
    }
    
    if (lores.length > 0) {
      List<String> loreList = new ArrayList<String>(lores.length); byte b; int i;
      String[] arrayOfString = null;
      for (i = lores.length, b = 0; b < i; ) { String lore = arrayOfString[b];
        loreList.add(ChatColor.translateAlternateColorCodes('&', lore));
        b++; }
      
      itemMeta.setLore(loreList);
    } 
    
    itemStack.setItemMeta(itemMeta);
    return itemStack;
  }
}
