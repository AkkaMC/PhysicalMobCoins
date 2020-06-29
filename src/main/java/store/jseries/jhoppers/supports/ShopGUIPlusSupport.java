package store.jseries.jhoppers.supports;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import store.jseries.framework.xseries.XMaterial;

import java.util.logging.Logger;

public class ShopGUIPlusSupport {


    @Getter
    @Setter
    public static boolean enabled = false;

    public static double getItemPrice(Player player, XMaterial material) {
        try {
            if (ShopGUIPlusSupport.isEnabled())
                return net.brcdev.shopgui.ShopGuiPlusApi.getItemStackPriceSell(player, material.parseItem());
            return -1;
        } catch (Exception ignored) {
            Logger.getLogger("minecraft").info("SHOP GUI PLUS NOT LOADED CORRECTLY.");
        }
        return -1;
    }

}
