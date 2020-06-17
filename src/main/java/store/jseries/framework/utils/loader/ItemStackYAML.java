package store.jseries.framework.utils.loader;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import store.jseries.framework.utils.JUtils;
import store.jseries.framework.xseries.XMaterial;

public class ItemStackYAML extends JUtils {

    @SuppressWarnings("deprecation")
    public ItemStack load(YamlConfiguration configuration, String path) {

        String material = configuration.getString(path + "material", "STONE");
        int data = configuration.getInt(path + ".data", 0);
        int amount = configuration.getInt(path + ".amount", 1);
        short durability = (short) configuration.getInt(path + ".durability", 0);

        Material mat = XMaterial.valueOf(material).parseMaterial();

        ItemStack item = new ItemStack(mat, amount, (byte) data);

        item.setDurability(durability);

        ItemMeta meta = item.getItemMeta();

        List<String> tmpLore = configuration.getStringList(path + "lore");
        if (tmpLore.size() != 0) {
            List<String> lore = meta.getLore() == null ? new ArrayList<>() : meta.getLore();
            lore.addAll(color(tmpLore));
            meta.setLore(lore);
        }

        String displayName = configuration.getString(path + "name", null);
        if (displayName != null)
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',displayName));

        List<String> enchants = configuration.getStringList(path + "enchants");

        if (enchants.size() != 0) {

            for (String enchantString : enchants) {

                try {

                    String[] splitEnchant = enchantString.split(",");
                    int level = 0;
                    String enchant = splitEnchant[0];
                        level = Integer.valueOf(splitEnchant[1]);
                    Enchantment enchantment = Enchantment.getByName(enchant);
                    meta.addEnchant(enchantment, level, true);
                }  catch (Exception ex) {
                }

            }
        }

        List<String> flags = configuration.getStringList(path + "flags");

        if (flags.size() != 0) {

            for (String flagString : flags) {

                try {
                    ItemFlag flag = ItemFlag.valueOf(flagString);
                     meta.addItemFlags(flag);
                } catch (Exception e) {

                }

            }
        }

        item.setItemMeta(meta);

        return item;

    }

    @SuppressWarnings("deprecation")
    public void save(ItemStack item, YamlConfiguration configuration, String path) {

        if (item == null)
            return;

        configuration.set(path + "material", XMaterial.matchXMaterial(item).name());
        configuration.set(path + "data", item.getData().getData());
        configuration.set(path + "amount", item.getAmount());
        configuration.set(path + "durability", item.getDurability());
        ItemMeta meta = item.getItemMeta();
        if (meta.hasDisplayName())
            configuration.set(path + "name", meta.getDisplayName().replace("&", "�"));
        if (meta.hasLore())
            configuration.set(path + "lore", colorReverse(Objects.requireNonNull(meta.getLore())));
        if (meta.getItemFlags().size() != 0)
            configuration.set(path + "flags",
                    meta.getItemFlags().stream().map(Enum::name).collect(Collectors.toList()));
        if (meta.hasEnchants()) {
            List<String> enchantList = new ArrayList<>();
            meta.getEnchants().forEach((enchant, level) -> enchantList.add(enchant.getName() + "," + level));
            configuration.set(path + "enchants", enchantList);
        }

    }

}