package store.jseries.jhoppers.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import store.jseries.framework.utils.inventory.ItemStackBuilder;
import store.jseries.framework.utils.inventory.SkullUtils;
import store.jseries.framework.xseries.XMaterial;
import store.jseries.jhoppers.utils.files.Placeholder;

import java.util.ArrayList;
import java.util.List;

public class ConfigUtil {

    public static List<Integer> slotsFromSection(FileConfiguration config, String path) {
        List<Integer> slots = new ArrayList<>();
        if (config.contains(path + ".slot"))
            slots.add(config.getInt(path + ".slot"));
        if (config.contains(path + ".slots"))
            slots.addAll(config.getIntegerList(path + ".slots"));
        return slots;
    }

    public static ItemStack fromConfigSection(FileConfiguration config, String path, Placeholder... placeholders) {
        String mat = config.contains(path + ".material") ? config.getString(path + ".material") : "BARRIER";
        mat = applyPlaceholders(mat, placeholders).toUpperCase();
        ItemStackBuilder builder;
        if (mat.contains("HEAD")) {
            String head = config.contains(path + ".head-url") ? applyPlaceholders(config.getString(path + ".head-url"), placeholders) : "6f3e8b8f3d98e9322a5a5982efe022635db160a66697b1a11a6d2848a7e06e";
            if (head.toUpperCase().startsWith("NAME:"))
                builder = new ItemStackBuilder(SkullUtils.getSkull(head.substring(5)));
            else
                builder = new ItemStackBuilder(SkullUtils.getSkullFromUrl(head));
        } else if (mat.contains("STAINED_GLASS_PANE")) {
            if (config.contains(path + ".glass-color"))
                builder = new ItemStackBuilder(XMaterial.valueOf(config.getString(path + ".glass-color").toUpperCase() + "_STAINED_GLASS_PANE"));
            else
                builder = new ItemStackBuilder(XMaterial.valueOf(mat));
        } else if (mat.contains("STAINED_GLASS")) {
            if (config.contains(path + ".glass-color"))
                builder = new ItemStackBuilder(XMaterial.valueOf(config.getString(path + ".glass-color").toUpperCase() + "_STAINED_GLASS"));
            else
                builder = new ItemStackBuilder(XMaterial.valueOf(mat));
        } else {
            builder = new ItemStackBuilder(XMaterial.valueOf(mat));
        }
        builder.addItemFlag(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
        if (config.contains(path + ".itemName"))
            builder.displayName(applyPlaceholders(config.getString(path + ".itemName"), placeholders));
        List<String> lore = new ArrayList<>();
        if (config.contains(path + ".itemLore")) {
            for (String s : config.getStringList(path + ".itemLore"))
                lore.add(applyPlaceholders(s, placeholders));
        }
        if (lore.size() > 0)
            builder.lore(lore);
        if (config.contains(path + ".itemDurability"))
            builder.durability(config.getInt(path + ".itemDurability"));
        if (config.contains(path + ".itemGlowing") && config.getBoolean(path + ".itemGlowing"))
            builder.enchant(Enchantment.DURABILITY, 0);
        if (config.contains(path + ".itemAmount"))
            builder.amount(config.getInt(path + ".itemAmount"));
        return builder.build();
    }

   /* public static ItemStack fromConfigSection(FileConfiguration config, String path, String headUrl) {
        String mat = config.contains(path + ".material") ? config.getString(path + ".material").toUpperCase() : "BARRIER";
        ItemStack item;
        if (mat.contains("HEAD")) {
            String head = config.contains(path + ".head-url") ? config.getString(path + ".head-url") : "6f3e8b8f3d98e9322a5a5982efe022635db160a66697b1a11a6d2848a7e06e";
            if(head.equalsIgnoreCase("%playerhead%") || head.equalsIgnoreCase("%player_head%"))
                head = headUrl;
            item = new ItemStackBuilder(SkullUtils.getSkullFromUrl(head)).build();
        } else if (mat.contains("STAINED_GLASS_PANE")) {
            if (config.contains(path + ".glass-color"))
                item = new ItemStackBuilder(XMaterial.valueOf(config.getString(path + ".glass-color").toUpperCase() + "_STAINED_GLASS_PANE")).build();
            else
                item = new ItemStackBuilder(XMaterial.valueOf(mat)).build();
        } else if (mat.contains("STAINED_GLASS")) {
            if (config.contains(path + ".glass-color"))
                item = new ItemStackBuilder(XMaterial.valueOf(config.getString(path + ".glass-color").toUpperCase() + "_STAINED_GLASS")).build();
            else
                item = new ItemStackBuilder(XMaterial.valueOf(mat.toUpperCase())).build();
        } else {
            item = new ItemStackBuilder(XMaterial.valueOf(mat.toUpperCase())).build();
        }
        ItemStackBuilder builder = new ItemStackBuilder(item);
        builder.addItemFlag(ItemFlag.HIDE_ENCHANTS);
        if (config.contains(path + ".itemName"))
            builder.displayName(config.getString(path + ".itemName"));
        if (config.contains(path + ".itemLore"))
            builder.lore(config.getStringList(path + ".itemLore"));
        if (config.contains(path + ".itemDurability"))
            builder.durability(config.getInt(path + ".itemDurability"));
        if (config.contains(path + ".itemGlowing") && config.getBoolean(path + ".itemGlowing"))
            builder.enchant(Enchantment.DURABILITY, 0);
        if(config.contains(path + ".itemAmount"))
            builder.amount(config.getInt(path + ".itemAmount"));
        return builder.build();
    }

    public static ItemStack fromConfigSection(FileConfiguration config, String path, XMaterial xmat) {
        String mat = config.contains(path + ".material") ? config.getString(path + ".material").toUpperCase() : "BARRIER";
        ItemStack item;
        if (mat.equalsIgnoreCase("%item_material%")) {
            item = new ItemStackBuilder(xmat).build();
        } else if (mat.contains("HEAD")) {
            String head = config.contains(path + ".head-url") ? config.getString(path + ".head-url") : "6f3e8b8f3d98e9322a5a5982efe022635db160a66697b1a11a6d2848a7e06e";
            item = new ItemStackBuilder(SkullUtils.getSkullFromUrl(head)).build();
        } else if (mat.contains("STAINED_GLASS_PANE")) {
            if (config.contains(path + ".glass-color"))
                item = new ItemStackBuilder(XMaterial.valueOf(config.getString(path + ".glass-color").toUpperCase() + "_STAINED_GLASS_PANE")).build();
            else
                item = new ItemStackBuilder(XMaterial.valueOf(mat)).build();
        } else if (mat.contains("STAINED_GLASS")) {
            if (config.contains(path + ".glass-color"))
                item = new ItemStackBuilder(XMaterial.valueOf(config.getString(path + ".glass-color").toUpperCase() + "_STAINED_GLASS")).build();
            else
                item = new ItemStackBuilder(XMaterial.valueOf(mat.toUpperCase())).build();
        } else {
            item = new ItemStackBuilder(XMaterial.valueOf(mat.toUpperCase())).build();
        }
        ItemStackBuilder builder = new ItemStackBuilder(item);
        builder.addItemFlag(ItemFlag.HIDE_ENCHANTS);
        if (config.contains(path + ".itemName"))
            builder.displayName(config.getString(path + ".itemName"));
        if (config.contains(path + ".itemLore"))
            builder.lore(config.getStringList(path + ".itemLore"));
        if (config.contains(path + ".itemDurability"))
            builder.durability(config.getInt(path + ".itemDurability"));
        if (config.contains(path + ".itemGlowing") && config.getBoolean(path + ".itemGlowing"))
            builder.enchant(Enchantment.DURABILITY, 0);
        if(config.contains(path + ".itemAmount"))
            builder.amount(config.getInt(path + ".itemAmount"));
        return builder.build();
    }*/

    private static String applyPlaceholders(String s, Placeholder... placeholders) {
        if (placeholders.length < 1) {
            return s;
        }
        for (Placeholder holder : placeholders)
            s = s.replaceAll(holder.getId(), holder.getValue());
        return s;
    }

}
