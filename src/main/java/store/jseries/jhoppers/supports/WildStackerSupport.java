package store.jseries.jhoppers.supports;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Item;

public class WildStackerSupport {

    @Getter
    @Setter
    public static boolean enabled = false;

    public static int getItemAmount(Item item) {
        if(WildStackerSupport.isEnabled())
            return com.bgsoftware.wildstacker.api.WildStackerAPI.getItemAmount(item);
        return item.getItemStack().getAmount();
    }

}
