package store.jseries.jhoppers.utils.players;

import lombok.Getter;
import lombok.Setter;
import store.jseries.framework.files.Serializable;
import store.jseries.jhoppers.utils.enums.HopperPermission;

import java.util.ArrayList;
import java.util.List;

public class Member implements Serializable {

    private final List<HopperPermission> permissions;
    @Getter
    @Setter
    private double moneySold;
    @Getter
    @Setter
    private long itemsWithdrawn;

    public Member() {
        permissions = new ArrayList<>();
        moneySold = 0;
        itemsWithdrawn = 0;
    }

    public Member(boolean owner) {
        permissions = new ArrayList<>();
        moneySold = 0;
        itemsWithdrawn = 0;
        if(owner)
            permissions.add(HopperPermission.OWNER);
    }

    public void sold(double amt) {
        moneySold += amt;
    }

    public void withdrew(long amt) {
        itemsWithdrawn += amt;
    }

    public boolean hasPermission(HopperPermission permission) {
        if(permissions.contains(HopperPermission.OWNER))
            return true;
        return permissions.contains(permission);
    }

    public void togglePermission(HopperPermission permission) {
        if(permissions.contains(permission))
            permissions.remove(permission);
        else
            permissions.add(permission);
    }

    @Override
    public String serialize() {
        StringBuilder s = new StringBuilder(moneySold + "/" + itemsWithdrawn);
        for(HopperPermission permission : permissions)
            s.append("/").append(permission.getName().toUpperCase());
        return s.toString();
    }

    @Override
    public void deserialize(String s) {
        String[] split = s.split("/");
        permissions.clear();
        if(split.length>=1) {
            try {
                moneySold = Double.parseDouble(split[0]);
            } catch (Exception ignored) {}
            if(split.length>=2) {
                try {
                    itemsWithdrawn = Long.parseLong(split[1]);
                } catch (Exception ignored) {}
                if(split.length>=3) {
                    try {
                        for(int i = 2 ; i < split.length ; i++)
                         permissions.add(HopperPermission.valueOf(split[i]));
                    } catch (Exception ignored) {}
                }
            }
        }
    }

    public Member deserializeToMember(String s) {
        deserialize(s);
        return this;
    }

}
