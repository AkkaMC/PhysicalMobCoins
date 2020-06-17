package store.jseries.framework.utils;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.WordUtils;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import store.jseries.framework.JFramework;
import store.jseries.framework.utils.location.Cuboid;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class JUtils {


    protected void message(CommandSender player, String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    protected void removeItemFromHand(Player player) {
        player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(Material.AIR));
    }

    protected String formatUpperCamelCase(String s) {
        return WordUtils.capitalizeFully(s, new char[]{' ', '_'});

    }

    protected void removeItemsFromHand(Player player, int amount) {
        ItemStack item = player.getInventory().getItem(player.getInventory().getHeldItemSlot());
        if (item != null && item.getType() != Material.AIR) {
            if (item.getAmount() > amount) {
                item.setAmount(item.getAmount() - amount);
                player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
            } else
                removeItemFromHand(player);
            player.updateInventory();
        }
    }

    protected String formatTime(long time) {
        long hours = TimeUnit.MILLISECONDS.toHours(time);
        time -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
        time -= TimeUnit.MINUTES.toMillis(hours);
        long seconds = TimeUnit.MILLISECONDS.toMinutes(time);
        String hourString = hours >= 10 ? hours + "" : "0" + hours;
        String minuteString = minutes >= 10 ? minutes + "" : "0" + minutes;
        String secondString = seconds >= 10 ? seconds + "" : "0" + seconds;
        return hourString + ":" + minuteString + ":" + secondString;
    }

    protected String formatTimeDays(long time) {
        long days = TimeUnit.MILLISECONDS.toDays(time);
        time -= TimeUnit.DAYS.toMillis(days);
        return (days >= 10 ? days + "" : "0" + days) + ":" + formatTime(time);
    }

    protected Location changeStringLocationToLocation(String s) {
        String[] a = s.split(",");
        if (a.length == 6)
            return changeStringLocationToLocationEye(s);
        World w = Bukkit.getServer().getWorld(a[0]);
        float x = Float.parseFloat(a[1]);
        float y = Float.parseFloat(a[2]);
        float z = Float.parseFloat(a[3]);
        return new Location(w, x, y, z);
    }

    protected Location changeStringLocationToLocationEye(String s) {
        String[] a = s.split(",");
        World w = Bukkit.getServer().getWorld(a[0]);
        float x = Float.parseFloat(a[1]);
        float y = Float.parseFloat(a[2]);
        float z = Float.parseFloat(a[3]);
        if (a.length == 6) {
            float yaw = Float.parseFloat(a[4]);
            float pitch = Float.parseFloat(a[5]);
            return new Location(w, x, y, z, yaw, pitch);
        }
        return new Location(w, x, y, z);
    }

    protected String changeLocationToString(Location location) {
        String ret = location.getWorld().getName() + "," + location.getBlockX() + "," + location.getBlockY() + ","
                + location.getBlockZ();
        return ret;
    }

    protected String changeLocationToStringEye(Location location) {
        String ret = location.getWorld().getName() + "," + location.getBlockX() + "," + location.getBlockY() + ","
                + location.getBlockZ() + "," + location.getYaw() + "," + location.getPitch();
        return ret;
    }

    protected Chunk changeStringToChunk(String chunk) {
        String[] a = chunk.split("/");
        World w = Bukkit.getServer().getWorld(a[0]);
        return w.getChunkAt(Integer.valueOf(a[1]), Integer.valueOf(a[2]));
    }

    protected String changeChunkToString(Chunk chunk) {
        String c = chunk.getWorld().getName() + "/" + chunk.getX() + "/" + chunk.getZ();
        return c;
    }

    protected String changeCuboidToString(Cuboid cuboid) {
        return cuboid.getWorld().getName() + "," + cuboid.getLowerX() + "," + cuboid.getLowerY() + ","
                + cuboid.getLowerZ() + "," + ";" + cuboid.getWorld().getName() + "," + cuboid.getUpperX() + ","
                + cuboid.getUpperY() + "," + cuboid.getUpperZ();
    }

    protected Cuboid changeStringToCuboid(String str) {

        String parsedCuboid[] = str.split(";");
        String parsedFirstLoc[] = parsedCuboid[0].split(",");
        String parsedSecondLoc[] = parsedCuboid[1].split(",");

        String firstWorldName = parsedFirstLoc[0];
        double firstX = Double.valueOf(parsedFirstLoc[1]);
        double firstY = Double.valueOf(parsedFirstLoc[2]);
        double firstZ = Double.valueOf(parsedFirstLoc[3]);

        String secondWorldName = parsedSecondLoc[0];
        double secondX = Double.valueOf(parsedSecondLoc[1]);
        double secondY = Double.valueOf(parsedSecondLoc[2]);
        double secondZ = Double.valueOf(parsedSecondLoc[3]);

        Location l1 = new Location(Bukkit.getWorld(firstWorldName), firstX, firstY, firstZ);

        Location l2 = new Location(Bukkit.getWorld(secondWorldName), secondX, secondY, secondZ);

        return new Cuboid(l1, l2);

    }

    protected String encode(ItemStack item) {
        return ItemDecoder.serializeItemStack(item);
    }

    protected ItemStack decode(String item) {
        return ItemDecoder.deserializeItemStack(item);
    }

    protected int getNumberBetween(int a, int b) {
        return ThreadLocalRandom.current().nextInt(a, b);
    }
    protected boolean hasInventoryFull(Player player) {
        int slot = 0;
        ItemStack[] arrayOfItemStack;
        int x = (arrayOfItemStack = player.getInventory().getContents()).length;
        for (int i = 0; i < x; i++) {
            ItemStack contents = arrayOfItemStack[i];
            if ((contents == null))
                slot++;
        }
        return slot == 0;
    }
    protected boolean give(ItemStack item, Player player) {
        if (hasInventoryFull(player))
            return false;
        player.getInventory().addItem(item);
        return true;
    }
    protected void give(Player player, ItemStack item) {
        if (hasInventoryFull(player))
            player.getWorld().dropItem(player.getLocation(), item);
        else
            player.getInventory().addItem(item);
    }
    protected boolean same(ItemStack stack, String name) {
        return stack.hasItemMeta() && stack.getItemMeta().hasDisplayName()
                && stack.getItemMeta().getDisplayName().equals(name);
    }

    protected String color(String message) {
        return message.replace("&", "§");
    }

    public String colorReverse(String message) {
        return message.replace("§", "&");
    }

    protected List<String> color(List<String> messages) {
        return messages.stream().map(this::color).collect(Collectors.toList());
    }

    public List<String> colorReverse(List<String> messages) {
        return messages.stream().map(this::colorReverse).collect(Collectors.toList());
    }
    protected TextComponent buildTextComponent(String message) {
        return new TextComponent(message);
    }

    protected TextComponent setHoverMessage(TextComponent component, String... messages) {
        BaseComponent[] list = new BaseComponent[messages.length];
        for (int a = 0; a != messages.length; a++)
            list[a] = new TextComponent(messages[a] + (messages.length - 1 == a ? "" : "\n"));
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, list));
        return component;
    }

    protected TextComponent setHoverMessage(TextComponent component, List<String> messages) {
        BaseComponent[] list = new BaseComponent[messages.size()];
        for (int a = 0; a != messages.size(); a++)
            list[a] = new TextComponent(messages.get(a) + (messages.size() - 1 == a ? "" : "\n"));
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, list));
        return component;
    }

    protected TextComponent setClickAction(TextComponent component, net.md_5.bungee.api.chat.ClickEvent.Action action,
                                           String command) {
        component.setClickEvent(new ClickEvent(action, command));
        return component;
    }
    protected BlockFace getClosestFace(float direction) {

        direction = direction % 360;

        if (direction < 0)
            direction += 360;

        direction = Math.round(direction / 45);

        switch ((int) direction) {
            case 0:
                return BlockFace.WEST;
            case 1:
                return BlockFace.NORTH_WEST;
            case 2:
                return BlockFace.NORTH;
            case 3:
                return BlockFace.NORTH_EAST;
            case 4:
                return BlockFace.EAST;
            case 5:
                return BlockFace.SOUTH_EAST;
            case 6:
                return BlockFace.SOUTH;
            case 7:
                return BlockFace.SOUTH_WEST;
            default:
                return BlockFace.WEST;
        }
    }

    protected int count(org.bukkit.inventory.Inventory inventory, Material material) {
        int count = 0;
        for (ItemStack itemStack : inventory.getContents())
            if (itemStack != null && itemStack.getType().equals(material))
                count += itemStack.getAmount();
        return count;
    }

    protected Enchantment enchantFromString(String str) {
        for (Enchantment enchantment : Enchantment.values())
            if (enchantment.getName().equalsIgnoreCase(str))
                return enchantment;
        return null;
    }

    protected boolean hasEnchant(Enchantment enchantment, ItemStack itemStack) {
        return itemStack.hasItemMeta() && itemStack.getItemMeta().hasEnchants()
                && itemStack.getItemMeta().hasEnchant(enchantment);
    }

    protected void scheduleFix(long delay, BiConsumer<TimerTask, Boolean> runnable) {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!JFramework.isEnabled()) {
                    cancel();
                    runnable.accept(this, false);
                    return;
                }
                Bukkit.getScheduler().runTask(JFramework.getPlugin(), () -> runnable.accept(this, true));
            }
        }, delay, delay);
    }

}