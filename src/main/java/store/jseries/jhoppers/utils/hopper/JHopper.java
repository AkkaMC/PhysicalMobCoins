package store.jseries.jhoppers.utils.hopper;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import store.jseries.framework.xseries.XMaterial;
import store.jseries.jhoppers.JHoppers;
import store.jseries.jhoppers.utils.enums.HopperFeature;
import store.jseries.jhoppers.utils.enums.HopperPermission;
import store.jseries.jhoppers.utils.players.Member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class JHopper {

    @Getter
    @Setter
    private String hopperType;
    @Getter
    @Setter
    private Map<XMaterial, Long> items;
    @Getter
    @Setter
    private Location location;
    @Getter
    @Setter
    private Map<UUID,Member> members;

    public JHopper() {
        hopperType = "";
        items = new HashMap<>();
        location = null;
        members = new HashMap<>();
    }

    public JHopper(String type, Location loc, UUID owner) {
        hopperType = type;
        items = new HashMap<>();
        location = loc;
        members = new HashMap<>();
        members.put(owner,new Member(true));
    }

    public void addItem(XMaterial type, long amt) {
        if(items.containsKey(type))
            amt+=items.get(type);
        items.put(type,amt);
        if(JHoppers.getInstance().getHopperTypeManager().getType(hopperType).getFeatures().contains(HopperFeature.AUTO_SELL)) {
            sellForOwner(type, items.get(type));
        }
    }

    public boolean removeItem(XMaterial type, long amt) {
        if(!items.containsKey(type))
            return false;
        if(items.get(type)<amt)
            return false;
        items.put(type,items.get(type)-amt);
        return true;
    }

    public boolean removeAllItem(XMaterial type) {
        if(!items.containsKey(type))
            return false;
        if(items.get(type)<=0)
            return false;
        items.remove(type);
        return true;
    }

    public void removeMember(UUID uuid) {
        members.remove(uuid);
    }

    public UUID getOwner() {
        for(UUID uuid : members.keySet()) {
            if(members.get(uuid).hasPermission(HopperPermission.OWNER))
                return uuid;
        }
        return null;
    }

    public void togglePermission(UUID uuid, HopperPermission hopperPermission) {
        if(members.containsKey(uuid)) {
            Member member = members.get(uuid);
            member.togglePermission(hopperPermission);
            members.put(uuid,member);
        }
    }

    public boolean hasPermission(UUID uuid, HopperPermission hopperPermission) {
        if(members.containsKey(uuid)) {
            return members.get(uuid).hasPermission(hopperPermission);
        } else
            return false;
    }

    public void soldItems(UUID uuid, double amt) {
        if(members.containsKey(uuid)) {
            Member member = members.get(uuid);
            member.setMoneySold(member.getMoneySold() + amt);
            members.put(uuid,member);
        }
    }


    public void withdrawItems(UUID uuid, int amt) {
        if(members.containsKey(uuid)) {
            Member member = members.get(uuid);
            member.setItemsWithdrawn(member.getItemsWithdrawn() + amt);
            members.put(uuid,member);
        }
    }

    public void sellForOwner(XMaterial mat, Long amt) {
        UUID uuid = getOwner();
        double price = JHoppers.getInstance().getPriceManager().getPrice(Bukkit.getPlayer(uuid),mat);
        double worth = price * amt;
        items.put(mat,items.get(mat)-amt);
        JHoppers.getEcon().depositPlayer(Bukkit.getOfflinePlayer(uuid),worth);
    }

    public String getId() {
        Chunk chunk = location.getChunk();
        return chunk.getWorld().getName() + "/" + chunk.getX() + "/" + chunk.getZ();
    }



}
