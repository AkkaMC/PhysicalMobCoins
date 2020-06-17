package store.jseries.jhoppers.inventories;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import store.jseries.framework.utils.inventory.ItemStackBuilder;
import store.jseries.framework.utils.inventory.JInventory;
import store.jseries.framework.xseries.XMaterial;
import store.jseries.jhoppers.utils.enums.PlaceParticle;
import store.jseries.jhoppers.utils.hopper.HopperType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParticleEffectInventory extends JInventory {
    @Override
    public void open(Player player, Object... args) {
        if(args.length > 0) {
            HopperType type = (HopperType) args[0];
            createInventory("particle-effect", "&8JHoppers Menu", 45);
            for (int i : Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 15, 16, 17, 18, 24, 26, 27, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44))
                addItem(i, new ItemStackBuilder(XMaterial.BLACK_STAINED_GLASS_PANE).displayName(" ").build());
            addButton(25, new ItemStackBuilder(XMaterial.BARRIER).displayName("&c&l< GO BACK").build())
                    .setClick(e -> {
                        new HopperEditInventory().open(player, type);
                    });
            List<PlaceParticle> placeParticles = new ArrayList<>(Arrays.asList(PlaceParticle.values()));
            int slot = 10;
            for(int i = 0 ; i < 15 ; i++) {
                if (slot == 15)
                    slot = 19;
                if (slot == 24)
                    slot = 28;
                if(placeParticles.size()>i) {
                    PlaceParticle particle = placeParticles.get(i);
                    if(type.getPlaceParticle() == particle)
                        addItem(slot, new ItemStackBuilder(particle.getId()).displayName("&9&l" + particle.getName() + " PARTICLE &7[Selected]").lore("&7Already selected.").addItemFlag(ItemFlag.HIDE_ENCHANTS).enchant(Enchantment.DURABILITY,0).build());
                    else
                        addButton(slot, new ItemStackBuilder(particle.getId()).displayName("&9&l" + particle.getName() + " PARTICLE &7[Available]").lore("&7Click to select.").build())
                                .setClick(e -> {
                                    type.setPlaceParticle(particle);
                                    new ParticleEffectInventory().open(player,type);
                                });

                } else {
                    addItem(slot, new ItemStack(Material.AIR));
                }
                slot+=1;
            }
            player.openInventory(getInventory(type));
        }
    }

    @Override
    public void onClose(InventoryCloseEvent event) {

    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onPlayerPersonalInventoryClick(InventoryClickEvent event) {
        event.setCancelled(true);
    }
}
