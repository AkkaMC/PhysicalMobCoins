package store.jseries.jhoppers.utils.hopper;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Chunk;
import org.bukkit.Location;
import store.jseries.framework.xseries.XMaterial;
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

    public void addItem(XMaterial type, long amt) {
        if(items.containsKey(type))
            amt+=items.get(type);
        items.put(type,amt);
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

    public String getId() {
        Chunk chunk = location.getChunk();
        return chunk.getWorld().getName() + "/" + chunk.getX() + "/" + chunk.getZ();
    }



}
