package store.jseries.framework.utils.inventory;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import store.jseries.framework.xseries.XMaterial;

import java.util.ArrayList;
import java.util.List;

public class ItemStackBuilder {
    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public ItemStackBuilder(XMaterial material) {
        itemStack = material.parseItem();
        itemMeta = itemStack.getItemMeta();
    }

    public ItemStackBuilder(SkullMeta meta) {
        itemStack = XMaterial.PLAYER_HEAD.parseItem();
        itemMeta = meta;
    }

    public ItemStackBuilder(ItemStack item) {
        itemStack = item;
        itemMeta = item.getItemMeta();
    }

    public ItemStackBuilder amount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemStackBuilder setType(Material mat) {
        itemStack.setType(mat);
        return this;
    }

    public ItemStackBuilder displayName(String displayName) {
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        return this;
    }

    public ItemStackBuilder lore(String... strs) {
        List<String> strings = new ArrayList<String>();
        for (String s : strs)
            strings.add(ChatColor.translateAlternateColorCodes('&', s));

        itemMeta.setLore(strings);
        return this;
    }

    public ItemStackBuilder lore(List<String> list, String... strs) {
        List<String> strings = new ArrayList<String>();
        for (String s : list)
            strings.add(ChatColor.translateAlternateColorCodes('&', s));
        for (String s : strs)
            strings.add(ChatColor.translateAlternateColorCodes('&', s));

        itemMeta.setLore(strings);
        return this;
    }

    public ItemStackBuilder lore(List<String> str) {
        List<String> strings = new ArrayList<String>();
        for (String s : str) {
            strings.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        itemMeta.setLore(strings);
        return this;
    }

    public ItemStackBuilder enchant(Enchantment enchantment, int level) {
        itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    public List<Enchantment> getEnchantments() {
        return new ArrayList<>(itemMeta.getEnchants().keySet());
    }

    public ItemStackBuilder removeEnchants(List<Enchantment> enchants) {
        for(Enchantment enchantment : enchants)
            itemMeta.removeEnchant(enchantment);
        return this;
    }

    public ItemStackBuilder durability(int durability) {
        itemStack.setDurability((short) durability);
        return this;
    }

    public ItemStackBuilder addItemFlag(ItemFlag... flags) {
        for (ItemFlag flag : flags)
            itemMeta.addItemFlags(flag);
        return this;
    }

    public List<String> getLore() {
        return itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
    }

    public String getDisplayName() {
        return itemMeta.hasDisplayName() ? itemMeta.getDisplayName() : "";
    }

    public ItemStack build() {
        ItemStack clonedStack = itemStack.clone();
        clonedStack.setItemMeta(itemMeta.clone());
        return clonedStack;
    }
}